package treasurehunt.gameobject;

import treasurehunt.models.Coordinates;

public sealed interface GameObject extends Result permits Player, Mountain, Treasure {

  Coordinates coordinates();

  boolean isObstacle();

}
