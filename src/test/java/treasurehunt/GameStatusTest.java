package treasurehunt;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import treasurehunt.gameobject.MapSize;
import treasurehunt.gameobject.Mountain;
import treasurehunt.gameobject.Player;
import treasurehunt.models.Coordinates;
import treasurehunt.models.Orientation;

class GameStatusTest {

  @Test
  void builderHasGoodType() {
    var builder = GameStatus.with();
    assertInstanceOf(GameStatus.RequireMapSize.class, builder);
  }

  @Test
  void withEmptyOnlyMapSize() {
    var builder = GameStatus.with().mapSize(new MapSize(2, 2));
    assertInstanceOf(GameStatus.RequirePlayer.class, builder);
  }

  @Test
  void a() {
    var builder = GameStatus.with()
        .mapSize(new MapSize(2, 2))
        .addPlayer(new Player("John", new Coordinates(0, 0), Orientation.S, List.of()));

    assertInstanceOf(GameStatus.MapBuilder.class, builder);
  }

  @Test
  void aa() {
    Executable executable = () -> GameStatus.with()
        .mapSize(new MapSize(2, 2))
        .addPlayer(new Player("John", new Coordinates(0, 0), Orientation.S, List.of()))
        .addGameObject(new Mountain(new Coordinates(0, 0)))
        .build();

    assertThrows(IllegalArgumentException.class, executable);
  }

  @Test
  void dqsaa() {
    Executable executable = () -> {
      var builder = new GameStatus.Builder();
      builder.mapSize(new MapSize(2, 2));
      builder.addGameObject(new Mountain(new Coordinates(0, 0))).build();
    };

    assertThrows(IllegalStateException.class, executable);
  }

  @Test
  void dqsfezfaa() {
    Executable executable = () -> {
      var builder = new GameStatus.Builder();
      builder.addGameObject(new Mountain(new Coordinates(0, 0))).build();
    };

    assertThrows(IllegalArgumentException.class, executable);
  }

  @Test
  void fdqsfezfaa() {
    Executable executable = () -> {
      var builder = new GameStatus.Builder();
      builder.addGameObject(new Mountain(new Coordinates(0, 0)));
      builder.addGameObject(new Mountain(new Coordinates(0, 0))).build();
    };

    assertThrows(IllegalArgumentException.class, executable);
  }

  @Test
  void fdqsfefzezfaa() {
    Executable executable = () -> {
      var builder = new GameStatus.Builder();
      builder.mapSize(new MapSize(2, 2));
      builder.mapSize(new MapSize(1, 1));
    };

    assertThrows(IllegalStateException.class, executable);
  }

  @Test
  void fdqsfefzezdfaa() {
    Executable executable = () -> {
      var builder = new GameStatus.Builder();
      builder.mapSize(new MapSize(2, 2));
      builder.addGameObject(new Mountain(new Coordinates(10, 10))).build();
    };

    assertThrows(IllegalArgumentException.class, executable);
  }

  @Test
  void run() {
  }
}