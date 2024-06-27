package treasurehunt;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import treasurehunt.gameobject.GameObject;
import treasurehunt.gameobject.MapSize;
import treasurehunt.gameobject.Mountain;
import treasurehunt.gameobject.Player;
import treasurehunt.gameobject.Result;
import treasurehunt.gameobject.Treasure;
import treasurehunt.models.Coordinates;

public final class GameStatus {

  private final MapSize mapSize;
  private final Map<Coordinates, GameObject> map;
  private final List<Player> players;

  private int indexCurrentPlayer = 0;

  private GameStatus(MapSize mapSize, Map<Coordinates, GameObject> map, List<Player> players) {
    this.mapSize = mapSize;
    this.map = map;
    this.players = players;
  }

  public static RequireMapSize with() {
    return new Builder();
  }

  // package visibility for testing purpose
  boolean allow(Coordinates coordinates) {
    if (!coordinates.isWithin(new Coordinates(0, 0),
        new Coordinates(mapSize.length(), mapSize.height()))) {
      return false;
    }
    var gameObject = map.get(coordinates);
    return !(gameObject != null && gameObject.isObstacle());
  }

  // package visibility for testing purpose
  Player nextPlayer() {
    Player current = players.get(indexCurrentPlayer);
    indexCurrentPlayer = (indexCurrentPlayer + 1) % players.size();
    return current;
  }

  public void run() {
    var done = false;

    while (!done) {
      if (noMoreSteps()) {
        System.out.println("finished !");
        done = true;
        continue;
      }
      var currentPlayer = nextPlayer();
      System.out.println(currentPlayer.name() + " turn ! ");
      var currentStep = currentPlayer.nextStep();
      if (currentStep.isEmpty()) {
        // do nothing
        System.out.println("nothing to do!");
        continue;
      }
      System.out.println("currentStep = " + currentStep.orElseThrow());
      switch (currentStep.orElseThrow()) {
        case A -> stepForwardAndCheckAction(currentPlayer);
        case G -> currentPlayer.turnLeft();
        case D -> currentPlayer.turnRight();
      }
    }
  }

  private void stepForwardAndCheckAction(Player currentPlayer) {
    currentPlayer.stepForward(this::allow);
    var gameObject = map.get(currentPlayer.coordinates());
    if (gameObject == null) {
      return;
    }
    switch (gameObject) {
      case Treasure treasure -> {
        System.out.println(currentPlayer.name() + " got a treasure !");
        currentPlayer.pickUpTreasure(treasure);
      }
      case Mountain mountain -> {
        // nothing
      }
      case Player player -> {
        // nothing
      }
    }
  }

  // package visibility for testing purpose
  boolean noMoreSteps() {
    return players.stream().noneMatch(Player::hasNextStep);
  }

  @Override
  public String toString() {
    return "treasurehunt.GameStatus{" + "mapSize=" + mapSize + ", map=" + map + '}';
  }

  public void export(Path targetFile) {
    try (var writer = Files.newBufferedWriter(targetFile)) {
      writer.write(mapSize.toResultFormat());
      writer.write("\n");
      var lines =
          map.values().stream().map(Result::toResultFormat).collect(Collectors.joining("\n"));
      writer.write(lines);
    } catch (IOException e) {
      System.err.println("Error exporting result in file : " + e.getMessage());
    }
  }

  public interface RequireMapSize {

    RequirePlayer mapSize(MapSize mapSize);
  }

  public interface RequirePlayer {

    MapBuilder addPlayer(Player player);
  }

  public interface MapBuilder extends RequirePlayer {

    MapBuilder addGameObject(GameObject gameObject);

    GameStatus build();
  }

  public static class Builder implements RequireMapSize, MapBuilder {

    private final HashMap<Coordinates, GameObject> gameObjectMap = new HashMap<>();
    private final ArrayList<Player> players = new ArrayList<>();
    private MapSize mapSize = null;

    // package private because I want to use it but I want other people to use the with method
    Builder() {
    }

    private static void checkMapDoesNotContainThisCoordinate(GameObject gameObject,
                                                             HashMap<Coordinates, GameObject> gameObjectMap) {
      if (gameObjectMap.containsKey(gameObject.coordinates())) {
        throw new AlreadyUsedCoordinateException(
            "Coordinates " + gameObject.coordinates() + " is already set for : " +
            gameObjectMap.get(gameObject.coordinates()));
      }
    }

    private static void checkCoordinatesLegalInMap(GameObject gameObject, MapSize mapSize) {
      if (!gameObject.coordinates()
          .isWithin(new Coordinates(0, 0), new Coordinates(mapSize.length(), mapSize.height()))) {
        throw new CoordinateOutOfBoundsException("coordinates must be within map size");
      }
    }

    @Override
    public RequirePlayer mapSize(MapSize mapSize) {
      Objects.requireNonNull(mapSize);
      if (this.mapSize != null) {
        throw new IllegalStateException("map size is already set");
      }
      this.mapSize = mapSize;
      return this;
    }

    @Override
    public MapBuilder addGameObject(GameObject gameObject) {
      Objects.requireNonNull(gameObject);
      checkMapSizeNotNull();
      checkCoordinatesLegalInMap(gameObject, mapSize);
      checkMapDoesNotContainThisCoordinate(gameObject, gameObjectMap);

      gameObjectMap.put(gameObject.coordinates(), gameObject);
      return this;
    }

    @Override
    public MapBuilder addPlayer(Player player) {
      Objects.requireNonNull(player);
      checkMapSizeNotNull();
      checkCoordinatesLegalInMap(player, mapSize);
      checkMapDoesNotContainThisCoordinate(player, gameObjectMap);

      gameObjectMap.put(player.coordinates(), player);
      players.add(player);
      return this;
    }

    private void checkMapSizeNotNull() {
      if (this.mapSize == null) {
        throw new IllegalStateException("set map size first");
      }
    }

    @Override
    public GameStatus build() {
      if (mapSize == null) {
        throw new IllegalStateException("map size is not set");
      }
      if (gameObjectMap.isEmpty()) {
        throw new IllegalStateException("cannot create an empty map");
      }
      if (players.isEmpty()) {
        throw new IllegalStateException("players list is empty");
      }

      return new GameStatus(mapSize, Map.copyOf(gameObjectMap), List.copyOf(players));
    }
  }

}
