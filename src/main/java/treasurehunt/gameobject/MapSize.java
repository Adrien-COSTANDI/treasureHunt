package treasurehunt.gameobject;

public record MapSize(int length, int height) implements Parseable {

  public MapSize {
    if (length <= 0) {
      throw new IllegalArgumentException(
          "length of map must be greater than 0. Current length : " + length);
    }
    if (height <= 0) {
      throw new IllegalArgumentException(
          "height of map must be greater than 0. Current height : " + height);
    }
  }
}