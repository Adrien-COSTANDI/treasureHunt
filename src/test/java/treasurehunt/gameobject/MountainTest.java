package treasurehunt.gameobject;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import treasurehunt.models.Coordinates;

class MountainTest {
  @Test
  void legalMountain() {
    assertAll(
        () -> assertThrows(NullPointerException.class, () -> new Mountain(null)),
        () -> assertDoesNotThrow(() -> new Mountain(new Coordinates(1, 1)))
    );
  }

  @Test
  void testIsObstacle() {
    var mountain = new Mountain(new Coordinates(1, 1));
    assertTrue(mountain.isObstacle());
  }
}