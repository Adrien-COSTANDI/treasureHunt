package treasurehunt.gameobject;

import java.util.Objects;
import treasurehunt.models.Coordinates;

public record Mountain(Coordinates coordinates) implements GameObject, Parseable {

  public Mountain {
    Objects.requireNonNull(coordinates);
  }

  @Override
  public boolean isObstacle() {
    return true;
  }
}
