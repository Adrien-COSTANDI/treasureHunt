package treasurehunt;

public class CoordinateOutOfBoundsException extends RuntimeException {

  public CoordinateOutOfBoundsException() {
  }

  public CoordinateOutOfBoundsException(String message) {
    super(message);
  }

  public CoordinateOutOfBoundsException(String message, Throwable cause) {
    super(message, cause);
  }
}
