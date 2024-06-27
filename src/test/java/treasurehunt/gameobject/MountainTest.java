package treasurehunt.gameobject;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import treasurehunt.models.Coordinates;

class MountainTest {

  @Test
  void legalMountain() {
    assertAll(() -> assertThrows(NullPointerException.class, () -> new Mountain(null)),
        () -> assertDoesNotThrow(() -> new Mountain(new Coordinates(1, 1))));
  }

  @Test
  void testIsObstacle() {
    var mountain = new Mountain(new Coordinates(1, 1));
    assertTrue(mountain.isObstacle());
  }

  @Test
  void testResultFormat() {
    var mountain = new Mountain(new Coordinates(1, 2));
    assertEquals("M - 1 - 2", mountain.toResultFormat());
  }
}