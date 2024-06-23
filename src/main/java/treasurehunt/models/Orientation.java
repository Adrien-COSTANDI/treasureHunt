package treasurehunt.models;

public enum Orientation {
  N, E, S, O;

  public Orientation right() {
    return switch (this) {
      case N -> Orientation.E;
      case E -> Orientation.S;
      case S -> Orientation.O;
      case O -> Orientation.N;
    };
  }

  public Orientation left() {
    return switch (this) {
      case N -> Orientation.O;
      case E -> Orientation.N;
      case S -> Orientation.E;
      case O -> Orientation.S;
    };
  }
}
