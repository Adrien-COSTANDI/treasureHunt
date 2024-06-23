package treasurehunt.gameobject;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class MapSizeTest {

  @Test
  void legalMapSize() {
    assertAll(
        () -> assertThrows(IllegalArgumentException.class, () -> new MapSize(-1, 1)),
        () -> assertThrows(IllegalArgumentException.class, () -> new MapSize(1, -1)),
        () -> assertThrows(IllegalArgumentException.class, () -> new MapSize(-1, -1)),
        () -> assertThrows(IllegalArgumentException.class, () -> new MapSize(0, 0)),
        () -> assertDoesNotThrow(() -> new MapSize(1, 1)));
  }

}