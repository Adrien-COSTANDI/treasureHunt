package treasurehunt;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import treasurehunt.gameobject.MapSize;
import treasurehunt.gameobject.Mountain;
import treasurehunt.gameobject.Player;
import treasurehunt.gameobject.Treasure;
import treasurehunt.models.Coordinates;
import treasurehunt.models.Orientation;

class GameStatusTest {

  @Test
  void emptyBuilderHasGoodType() {
    var builder = GameStatus.with();
    assertInstanceOf(GameStatus.RequireMapSize.class, builder);
  }

  @Test
  void mapSizeBuilderHasGoodType() {
    var builder = GameStatus.with().mapSize(new MapSize(2, 2));
    assertInstanceOf(GameStatus.RequirePlayer.class, builder);
  }

  @Test
  void mapBuilderHasGoodType() {
    var builder = GameStatus.with()
        .mapSize(new MapSize(2, 2))
        .addPlayer(new Player("John", new Coordinates(0, 0), Orientation.S, List.of()));

    assertInstanceOf(GameStatus.MapBuilder.class, builder);
  }

  @Test
  void cannotPut2ObjectsOnSameCoordinates() {
    Executable executable = () -> GameStatus.with()
        .mapSize(new MapSize(2, 2))
        .addPlayer(new Player("John", new Coordinates(0, 0), Orientation.S, List.of()))
        .addGameObject(new Mountain(new Coordinates(0, 0)))
        .build();

    assertThrows(AlreadyUsedCoordinateException.class, executable);
  }

  @Test
  void missingPlayers() {
    Executable executable = () -> {
      var builder = new GameStatus.Builder();
      builder.mapSize(new MapSize(2, 2));
      builder.addGameObject(new Mountain(new Coordinates(0, 0))).build();
    };

    assertThrows(IllegalStateException.class, executable);
  }

  @Test
  void mustDeclareMapSizeFirst() {
    Executable executable = () -> {
      var builder = new GameStatus.Builder();
      builder.addGameObject(new Mountain(new Coordinates(0, 0))).build();
    };

    assertThrows(IllegalStateException.class, executable);
  }

  @Test
  void mustDeclareMapSizeOnlyOnce() {
    Executable executable = () -> {
      var builder = new GameStatus.Builder();
      builder.mapSize(new MapSize(2, 2));
      builder.mapSize(new MapSize(1, 1));
    };

    assertThrows(IllegalStateException.class, executable);
  }

  @Test
  void coordinateOutOfBounds() {
    Executable executable = () -> {
      var builder = new GameStatus.Builder();
      builder.mapSize(new MapSize(2, 2));
      builder.addGameObject(new Mountain(new Coordinates(10, 10))).build();
    };

    assertThrows(CoordinateOutOfBoundsException.class, executable);
  }

  @Test
  void nextPlayer() {
    var john = new Player("John", new Coordinates(0, 0), Orientation.S, List.of());
    var jane = new Player("Jane", new Coordinates(0, 1), Orientation.S, List.of());

    var gameStatus =
        GameStatus.with().mapSize(new MapSize(2, 2)).addPlayer(john).addPlayer(jane).build();

    assertAll(() -> assertEquals(john, gameStatus.nextPlayer()),
        () -> assertEquals(jane, gameStatus.nextPlayer()),
        () -> assertEquals(john, gameStatus.nextPlayer()),
        () -> assertEquals(jane, gameStatus.nextPlayer()));
  }

  @Test
  void allow() {

    var gameStatus = GameStatus.with()
        .mapSize(new MapSize(3, 3))
        .addPlayer(new Player("John", new Coordinates(0, 0), Orientation.S, List.of()))
        .addGameObject(new Mountain(new Coordinates(0, 1)))
        .addGameObject(new Treasure(new Coordinates(1, 0), 2))
        .build();

    assertAll(() -> assertFalse(gameStatus.allow(new Coordinates(0, 0))),   // john is already here
        () -> assertFalse(gameStatus.allow(new Coordinates(0, 1))),   // a mountain is already here
        () -> assertFalse(gameStatus.allow(new Coordinates(10, 10))), // outside the map
        () -> assertTrue(gameStatus.allow(new Coordinates(1, 0))),    // a treasure is already here
        () -> assertTrue(gameStatus.allow(new Coordinates(1, 1)))    // nothing is already here
    );
  }

  @Test
  void noMoreStepsFalse() {
    var gameStatus = GameStatus.with()
        .mapSize(new MapSize(3, 3))
        .addPlayer(new Player("John", new Coordinates(0, 0), Orientation.S, List.of(Player.Step.D)))
        .addPlayer(new Player("Jane",
            new Coordinates(0, 1),
            Orientation.S,
            List.of(Player.Step.D, Player.Step.D)))
        .build();

    assertFalse(gameStatus.noMoreSteps());
  }

  @Test
  void noMoreStepsTrue() {
    var gameStatus = GameStatus.with()
        .mapSize(new MapSize(3, 3))
        .addPlayer(new Player("John", new Coordinates(0, 0), Orientation.S, List.of()))
        .addPlayer(new Player("Jane", new Coordinates(0, 1), Orientation.S, List.of()))
        .build();

    assertTrue(gameStatus.noMoreSteps());
  }

  @Test
  void run() {
    var gameStatus = GameStatus.with()
        .mapSize(new MapSize(3, 3))
        .addPlayer(new Player("John", new Coordinates(0, 0), Orientation.S, List.of(Player.Step.D)))
        .addPlayer(new Player("Jane",
            new Coordinates(0, 1),
            Orientation.S,
            List.of(Player.Step.D, Player.Step.D)))
        .build();

    assertFalse(gameStatus.noMoreSteps());
    gameStatus.run();
    assertTrue(gameStatus.noMoreSteps());
  }
}