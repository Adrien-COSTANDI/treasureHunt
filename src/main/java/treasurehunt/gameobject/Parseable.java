package treasurehunt.gameobject;

import static java.lang.Integer.parseInt;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import treasurehunt.models.Coordinates;
import treasurehunt.models.Orientation;

public sealed interface Parseable permits Mountain, Player, Treasure, MapSize {

  String SEPARATOR = " - ";

  static List<Parseable> parseLines(Stream<String> lines) {
    return lines.map(line -> {
      if (line.startsWith("#")) {
        return null;
      }

      var tokens = line.split(SEPARATOR);
      var parseableType = ParseableType.valueOf(tokens[0]);

      return switch (parseableType) {
        case C -> parseMap(tokens);
        case M -> parseMountain(tokens);
        case T -> parseTreasure(tokens);
        case A -> parsePlayer(tokens);
      };
    }).filter(Objects::nonNull).toList();
  }

  private static Parseable parsePlayer(String[] tokens) {
    var steps = Arrays.stream(tokens[5].split("")).map(Player.Step::valueOf).toList();

    return new Player(tokens[1],
        new Coordinates(parseInt(tokens[2]), parseInt(tokens[3])),
        Orientation.valueOf(tokens[4]),
        steps);
  }

  private static Parseable parseTreasure(String[] tokens) {
    return new Treasure(new Coordinates(parseInt(tokens[1]), parseInt(tokens[2])),
        parseInt(tokens[3]));
  }

  private static Parseable parseMountain(String[] tokens) {
    return new Mountain(new Coordinates(parseInt(tokens[1]), parseInt(tokens[2])));
  }

  private static Parseable parseMap(String[] tokens) {
    return new MapSize(parseInt(tokens[1]), parseInt(tokens[2]));
  }

}
