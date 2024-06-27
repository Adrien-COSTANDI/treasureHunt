package main;

import java.nio.file.Path;
import treasurehunt.GameStatusParser;

public class Main {

  public static void main(String[] args) {

    var gameStatus =
        GameStatusParser.parseGameStatusFromFile(Path.of("src/main/resources/script.txt"));
    System.out.println("gameStatus = " + gameStatus);
    gameStatus.run();
    System.out.println("gameStatus = " + gameStatus);

    gameStatus.export(Path.of("src/main/resources/result.txt"));
  }
}
