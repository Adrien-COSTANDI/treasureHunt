package treasurehunt.models;

public record Coordinates(int x, int y) {

  public Coordinates {
    if (x < 0) {
      throw new IllegalArgumentException("Illegal negative coordinate : x = " + x);
    }
    if (y < 0) {
      throw new IllegalArgumentException("Illegal negative coordinate : y = " + y);
    }
  }

  /**
   * This method returns true if this coordinate is between 0 and length and height excluded.
   *
   * @param length excluded length boundary
   * @param height excluded height boundary
   * @return true if this coordinate is within the boundaries, false otherwise
   */
  public boolean isWithin(int length, int height) {
    return x >= 0 && y >= 0 && x < length && y < height;
  }

  @Override
  public String toString() {
    return "(" + x + ", " + y + ")";
  }
}
