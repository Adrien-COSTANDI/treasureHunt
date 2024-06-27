package treasurehunt.gameobject;

public record MapSize(int length, int height) implements Parseable, Result {

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

  @Override
  public String toResultFormat() {
    return ParseableType.C + SEPARATOR + length + SEPARATOR + height;
  }
}