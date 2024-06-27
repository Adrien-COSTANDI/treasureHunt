package treasurehunt.gameobject;

import java.util.Objects;
import treasurehunt.models.Coordinates;

public record Mountain(Coordinates coordinates) implements GameObject, Parseable, Result {

  public Mountain {
    Objects.requireNonNull(coordinates);
  }

  @Override
  public boolean isObstacle() {
    return true;
  }

  @Override
  public String toResultFormat() {
    return ParseableType.M + SEPARATOR + coordinates.x() + SEPARATOR + coordinates.y();
  }
}
