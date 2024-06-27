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
   * This method returns true if this coordinate is between 0 and maxX and maxY excluded.
   *
   * @param minCoordinate included minimum coordinate boundary
   * @param maxCoordinate excluded maximum coordinate boundary
   * @return true if this coordinate is within the boundaries, false otherwise
   */
  public boolean isWithin(Coordinates minCoordinate, Coordinates maxCoordinate) {
    return x >= minCoordinate.x() && y >= minCoordinate.y() && x < maxCoordinate.x() &&
           y < maxCoordinate.y();
  }

  @Override
  public String toString() {
    return "(" + x + ", " + y + ")";
  }
}
