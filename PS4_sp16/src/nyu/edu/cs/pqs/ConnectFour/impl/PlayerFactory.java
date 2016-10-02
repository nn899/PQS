package nyu.edu.cs.pqs.ConnectFour.impl;

import nyu.edu.cs.pqs.ConnectFour.api.IGameModel;

public class PlayerFactory {

  private static PlayerFactory INSTANCE;
  private static Object        lock  = new Object();
  private static IGameModel    model = new AppModel();

  private PlayerFactory() {

  }

  public static PlayerFactory getFactoryInstance() {
    synchronized (lock) {
      if (INSTANCE == null) {
        INSTANCE = new PlayerFactory();
      }
      return INSTANCE;
    }
  }

  public static IGameModel createTwoPlayerGame(String firstPlayerName, String secondPlayerName) {
    model.addObserverToModel(new User(model, firstPlayerName));
    model.addObserverToModel(new User(model, secondPlayerName));
    model.startGame(firstPlayerName);
    return model;
  }

  public static IGameModel createSinglePlayerGame(String firstPlayerName) {
    model.addObserverToModel(new User(model, firstPlayerName));
    model.addObserverToModel(new Computer(model));
    model.startGame(firstPlayerName);
    return model;
  }

  // Used in test cases
  public static IGameModel createModelWithoutPlayers() {
    return new AppModel();
  }

  // Used in test cases
  public static void reset() {
    model = null;
  }

}
