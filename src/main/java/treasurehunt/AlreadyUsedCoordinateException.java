package treasurehunt;

public class AlreadyUsedCoordinateException extends RuntimeException {

  public AlreadyUsedCoordinateException() {
  }

  public AlreadyUsedCoordinateException(String message) {
    super(message);
  }

  public AlreadyUsedCoordinateException(String message, Throwable cause) {
    super(message, cause);
  }
}
