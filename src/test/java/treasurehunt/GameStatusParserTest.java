package treasurehunt;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class GameStatusParserTest {

  @Test
  void parseGameStatusFromEmptyFile() {
    assertThrows(IllegalStateException.class,
        () -> GameStatusParser.parseGameStatusFromFile(Path.of("src/test/resources/empty.txt")));
  }

  @Test
  void parseGameStatusFromEmptyMap() {
    assertThrows(IllegalStateException.class,
        () -> GameStatusParser.parseGameStatusFromFile(Path.of("src/test/resources/emptyMap.txt")));
  }

  @Test
  void parseGameStatusFromEmptyFileWithComment() {
    assertThrows(IllegalStateException.class,
        () -> GameStatusParser.parseGameStatusFromFile(Path.of(
            "src/test/resources/emptyWithComment.txt")));
  }

  @Test
  void parseGameStatusFromFile() {
    var gameStatus = assertDoesNotThrow(() -> GameStatusParser.parseGameStatusFromFile(Path.of(
        "src/test/resources/script.txt")));
    assertNotNull(gameStatus);
  }
}