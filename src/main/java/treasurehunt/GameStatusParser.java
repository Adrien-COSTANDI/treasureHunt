package treasurehunt;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import treasurehunt.gameobject.MapSize;
import treasurehunt.gameobject.Mountain;
import treasurehunt.gameobject.Parseable;
import treasurehunt.gameobject.Player;
import treasurehunt.gameobject.Treasure;

public class GameStatusParser {

  public static GameStatus parseGameStatusFromFile(Path path) {
    Objects.requireNonNull(path);

    var builder = new GameStatus.Builder();

    List<Parseable> parseables;

    try (var lines = Files.lines(path)) {
      parseables = Parseable.parseLines(lines);
    } catch (IOException e) {
      System.err.println("Error reading config file: " + e.getMessage());
      System.exit(1);
      return null; // not reached, for compilation purposes
    }

    for (var parseable : parseables) {
      switch (parseable) {
        case MapSize map -> builder.mapSize(map);
        case Treasure treasure -> builder.addGameObject(treasure);
        case Mountain mountain -> builder.addGameObject(mountain);
        case Player player -> builder.addPlayer(player);
      }
    }

    return builder.build();
  }

}
