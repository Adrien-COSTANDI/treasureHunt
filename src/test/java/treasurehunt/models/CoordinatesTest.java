package treasurehunt.models;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CoordinatesTest {

  @Test
  void legalCoordinates() {
    assertAll(() -> assertThrows(IllegalArgumentException.class, () -> new Coordinates(-1, 1)),
        () -> assertThrows(IllegalArgumentException.class, () -> new Coordinates(1, -1)),
        () -> assertThrows(IllegalArgumentException.class, () -> new Coordinates(-1, -1)),
        () -> assertDoesNotThrow(() -> new Coordinates(0, 0)),
        () -> assertDoesNotThrow(() -> new Coordinates(1, 1)));
  }

  @Test
  void testIsWithin() {
    Coordinates coordinates = new Coordinates(5, 5);
    assertAll(() -> assertTrue(coordinates.isWithin(new Coordinates(0, 0),
            new Coordinates(10, 10))),
        () -> assertFalse(coordinates.isWithin(new Coordinates(0, 0), new Coordinates(5, 5))),
        () -> assertFalse(coordinates.isWithin(new Coordinates(0, 0), new Coordinates(5, 6))),
        () -> assertFalse(coordinates.isWithin(new Coordinates(0, 0), new Coordinates(6, 5))));
  }
}