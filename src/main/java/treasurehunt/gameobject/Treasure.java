package treasurehunt.gameobject;

import java.util.Objects;
import treasurehunt.models.Coordinates;

public final class Treasure implements GameObject, Parseable, Result {

  private final Coordinates coordinates;
  private int amount;


  public Treasure(Coordinates coordinates, int amount) {
    Objects.requireNonNull(coordinates);
    if (amount <= 0) {
      throw new IllegalArgumentException(
          "Treasure must have at least one amount. Current amount : " + amount);
    }
    this.coordinates = coordinates;
    this.amount = amount;
  }

  @Override
  public boolean isObstacle() {
    return false;
  }

  public void pickedUp() {
    if (amount <= 0) {
      throw new IllegalStateException("Not enough amount to pick up. Current amount : " + amount);
    }
    this.amount--;
  }

  @Override
  public Coordinates coordinates() { return coordinates; }

  public int amount() { return amount; }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj == null || obj.getClass() != this.getClass()) {
      return false;
    }
    var that = (Treasure) obj;
    return Objects.equals(this.coordinates, that.coordinates) && this.amount == that.amount;
  }

  @Override
  public int hashCode() {
    return Objects.hash(coordinates, amount);
  }

  @Override
  public String toString() {
    return "Treasure[" + "coordinates=" + coordinates + ", " + "amount=" + amount + ']';
  }

  @Override
  public String toResultFormat() {
    return ParseableType.T + SEPARATOR + coordinates.x() + SEPARATOR + coordinates.y() + SEPARATOR +
           amount;
  }
}
