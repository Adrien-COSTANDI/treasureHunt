package treasurehunt.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CoordinatesTest {

  @Test
  void legalCoordinates() {
    assertAll(
        () -> assertThrows(IllegalArgumentException.class, () -> new Coordinates(-1, 1)),
        () -> assertThrows(IllegalArgumentException.class, () -> new Coordinates(1, -1)),
        () -> assertThrows(IllegalArgumentException.class, () -> new Coordinates(-1, -1)),
        () -> assertDoesNotThrow(() -> new Coordinates(0, 0)),
        () -> assertDoesNotThrow(() -> new Coordinates(1, 1))
    );
  }

  @Test
  void testIsWithin() {
    Coordinates coordinates = new Coordinates(5, 5);
    assertAll(
        () -> assertTrue(coordinates.isWithin(10, 10)),
        () -> assertFalse(coordinates.isWithin(5, 5)),
        () -> assertFalse(coordinates.isWithin(5, 6)),
        () -> assertFalse(coordinates.isWithin(6, 5))
    );
  }
}