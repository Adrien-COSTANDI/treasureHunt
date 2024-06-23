package treasurehunt.gameobject;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import treasurehunt.models.Coordinates;
import treasurehunt.models.Orientation;

public final class Player implements GameObject, Parseable {

  private final String name;
  private final List<Step> steps;
  private Coordinates coordinates;
  private Orientation orientation;
  private final Iterator<Step> stepIterator;
  private int treasures = 0;

  public Player(String name, Coordinates coordinates, Orientation orientation, List<Step> steps) {
    Objects.requireNonNull(name);
    Objects.requireNonNull(coordinates);
    Objects.requireNonNull(orientation);
    this.name = name;
    this.coordinates = coordinates;
    this.orientation = orientation;
    this.steps = List.copyOf(steps); // not null and no null inside;
    this.stepIterator = steps.iterator();
  }

  public Optional<Step> nextStep() {
    if (stepIterator.hasNext()) {
      return Optional.of(stepIterator.next());
    }
    return Optional.empty();
  }

  public boolean hasNextStep() {
    return stepIterator.hasNext();
  }

  public void turnLeft() {
    this.orientation = orientation.left();
  }

  public void turnRight() {
    this.orientation = orientation.right();
  }

  public void stepForward(Predicate<Coordinates> isLegalCoordinate) {
    var newCoordinates = switch (orientation) {
      case N -> new Coordinates(coordinates.x(), coordinates.y() - 1);
      case S -> new Coordinates(coordinates.x(), coordinates.y() + 1);
      case E -> new Coordinates(coordinates.x() + 1, coordinates.y());
      case O -> new Coordinates(coordinates.x() - 1, coordinates.y());
    };

    if (isLegalCoordinate.test(newCoordinates)) {
      this.coordinates = newCoordinates;
      System.out.println(name + " moved at coord " + newCoordinates);
      return;
    }
    System.out.println(name + " blocked at coord " + newCoordinates);
  }

  public String name() { return name; }

  public Orientation orientation() {
    return orientation;
  }

  @Override
  public Coordinates coordinates() { return coordinates; }

  @Override
  public boolean isObstacle() {
    return true;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj == null || obj.getClass() != this.getClass()) {
      return false;
    }
    var that = (treasurehunt.gameobject.Player) obj;
    return Objects.equals(this.name, that.name) &&
           Objects.equals(this.coordinates, that.coordinates) &&
           Objects.equals(this.orientation, that.orientation) &&
           Objects.equals(this.steps, that.steps);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, coordinates, orientation, steps);
  }

  @Override
  public String toString() {
    return "Player[" + "name=" + name + ", " + "coordinates=" + coordinates + ", " +
           "orientation=" + orientation + ", " + "steps=" + steps + ']';
  }

  public int treasures() {
    return treasures;
  }

  public void pickUpTreasure(Treasure treasure) {
    if (!treasure.coordinates().equals(coordinates)) {
      throw new IllegalArgumentException("Treasure coordinates do not match");
    }
    treasure.pickedUp();
    treasures++;
  }

  public enum Step {
    A, G, D
  }
}
