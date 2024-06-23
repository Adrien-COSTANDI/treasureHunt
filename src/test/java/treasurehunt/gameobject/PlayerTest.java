package treasurehunt.gameobject;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import treasurehunt.models.Coordinates;
import treasurehunt.models.Orientation;

class PlayerTest {

  @Test
  void testConstructor() {
    var steps = List.of(Player.Step.A, Player.Step.G, Player.Step.D);
    var initialCoordinates = new Coordinates(0, 0);
    var initialOrientation = Orientation.N;

    assertAll(() -> assertThrows(NullPointerException.class,
            () -> new Player(null, initialCoordinates, initialOrientation, steps)),
        () -> assertThrows(NullPointerException.class,
            () -> new Player("Test", null, initialOrientation, steps)),
        () -> assertThrows(NullPointerException.class,
            () -> new Player("Test", initialCoordinates, null, steps)),
        () -> assertThrows(NullPointerException.class,
            () -> new Player("Test", initialCoordinates, initialOrientation, null)),
        () -> assertThrows(NullPointerException.class,
            () -> new Player("Test",
                initialCoordinates,
                initialOrientation,
                Collections.singletonList(null))));
  }

  @Test
  void testNextStep() {
    var steps = List.of(Player.Step.A, Player.Step.G, Player.Step.D);
    var initialCoordinates = new Coordinates(0, 0);
    var initialOrientation = Orientation.N;
    var player = new Player("TestPlayer", initialCoordinates, initialOrientation, steps);

    assertAll(() -> assertTrue(player.hasNextStep()),
        () -> assertEquals(Player.Step.A, player.nextStep().orElseThrow()),

        () -> assertTrue(player.hasNextStep()),
        () -> assertEquals(Player.Step.G, player.nextStep().orElseThrow()),

        () -> assertTrue(player.hasNextStep()),
        () -> assertEquals(Player.Step.D, player.nextStep().orElseThrow()),

        () -> assertFalse(player.hasNextStep()),
        () -> assertTrue(player.nextStep().isEmpty()));
  }

  @Test
  void testTurnLeft() {
    var steps = List.of(Player.Step.A, Player.Step.G, Player.Step.D);
    var initialCoordinates = new Coordinates(0, 0);
    var initialOrientation = Orientation.N;
    var player = new Player("TestPlayer", initialCoordinates, initialOrientation, steps);

    assertAll(() -> {
      player.turnLeft();
      assertEquals(Orientation.O, player.orientation());
    }, () -> {
      player.turnLeft();
      assertEquals(Orientation.S, player.orientation());
    }, () -> {
      player.turnLeft();
      assertEquals(Orientation.E, player.orientation());
    }, () -> {
      player.turnLeft();
      assertEquals(Orientation.N, player.orientation());
    });
  }

  @Test
  void testTurnRight() {
    var steps = List.of(Player.Step.A, Player.Step.G, Player.Step.D);
    var initialCoordinates = new Coordinates(0, 0);
    var initialOrientation = Orientation.N;
    var player = new Player("TestPlayer", initialCoordinates, initialOrientation, steps);

    assertAll(() -> {
      player.turnRight();
      assertEquals(Orientation.E, player.orientation());
    }, () -> {
      player.turnRight();
      assertEquals(Orientation.S, player.orientation());
    }, () -> {
      player.turnRight();
      assertEquals(Orientation.O, player.orientation());
    }, () -> {
      player.turnRight();
      assertEquals(Orientation.N, player.orientation());
    });
  }

  @Test
  void testStepForward() {
    var initialCoordinates = new Coordinates(10, 10);

    assertAll(() -> {
          var player = new Player("TestPlayer", initialCoordinates, Orientation.N, List.of());
          player.stepForward(coords -> true); // Legal move
          assertEquals(new Coordinates(10, 9), player.coordinates());
        }, () -> {
          var player = new Player("TestPlayer", initialCoordinates, Orientation.S, List.of());
          player.stepForward(coords -> true); // Legal move
          assertEquals(new Coordinates(10, 11), player.coordinates());
        }, () -> {
          var player = new Player("TestPlayer", initialCoordinates, Orientation.E, List.of());
          player.stepForward(coords -> true); // Legal move
          assertEquals(new Coordinates(11, 10), player.coordinates());
        }, () -> {
          var player = new Player("TestPlayer", initialCoordinates, Orientation.O, List.of());
          player.stepForward(coords -> true); // Illegal move
          assertEquals(new Coordinates(9, 10), player.coordinates()); // Coordinates should not change
        }, () -> {
          var player = new Player("TestPlayer", new Coordinates(10, 10), Orientation.N, List.of());
          player.stepForward(coords -> false); // Illegal move
          assertEquals(new Coordinates(10, 10), player.coordinates()); // Coordinates should not change
        }
    );
  }

  @Test
  void testIsObstacle() {
    var steps = List.of(Player.Step.A, Player.Step.G, Player.Step.D);
    var initialCoordinates = new Coordinates(0, 0);
    var initialOrientation = Orientation.N;
    var player = new Player("TestPlayer", initialCoordinates, initialOrientation, steps);

    assertTrue(player.isObstacle());
  }

  @Test
  void pickUpTreasure() {
    var player = new Player("TestPlayer", new Coordinates(10, 10), Orientation.N, List.of());
    var treasure = new Treasure(new Coordinates(10, 10), 2);
    assertEquals(0, player.treasures());
    assertEquals(2, treasure.amount());
    player.pickUpTreasure(treasure);
    assertEquals(1, player.treasures());
    assertEquals(1, treasure.amount());
  }

  @Test
  void pickUpTreasureCoordinatesDontMatch() {
    var player = new Player("TestPlayer", new Coordinates(0, 0), Orientation.N, List.of());
    var treasure = new Treasure(new Coordinates(10, 10), 2);
    assertThrows(IllegalArgumentException.class, () -> player.pickUpTreasure(treasure));
  }

}
