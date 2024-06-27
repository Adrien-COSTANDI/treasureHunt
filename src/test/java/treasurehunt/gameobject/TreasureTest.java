package treasurehunt.gameobject;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import treasurehunt.models.Coordinates;

class TreasureTest {

  @Test
  void legalTreasure() {
    var coordinates = new Coordinates(1, 1);
    assertAll(() -> assertThrows(NullPointerException.class, () -> new Treasure(null, 1)),
        () -> assertThrows(IllegalArgumentException.class, () -> new Treasure(coordinates, 0)),
        () -> assertThrows(IllegalArgumentException.class, () -> new Treasure(coordinates, -1)),
        () -> assertDoesNotThrow(() -> new Treasure(coordinates, 1)),
        () -> assertDoesNotThrow(() -> new Treasure(coordinates, 10)));
  }

  @Test
  void testIsObstacle() {
    var treasure = new Treasure(new Coordinates(1, 1), 10);
    assertFalse(treasure.isObstacle());
  }

  @Test
  void testGetCoordinates() {
    var treasure = new Treasure(new Coordinates(1, 1), 10);
    assertEquals(new Coordinates(1, 1), treasure.coordinates());
  }

  @Test
  void testGetAmount() {
    var treasure = new Treasure(new Coordinates(1, 1), 10);
    assertEquals(10, treasure.amount());
  }

  @Test
  void testPickedUp() {
    var treasure = new Treasure(new Coordinates(1, 1), 1);
    assertEquals(1, treasure.amount());
    treasure.pickedUp();
    assertEquals(0, treasure.amount());
    assertThrows(IllegalStateException.class, treasure::pickedUp);
  }

  @Test
  void testResultFormat() {
    var treasure = new Treasure(new Coordinates(1, 2), 3);
    assertEquals("T - 1 - 2 - 3", treasure.toResultFormat());
  }
}