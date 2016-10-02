package nyu.edu.cs.pqs.ConnectFour.impl;

import java.util.LinkedList;
import java.util.List;

import nyu.edu.cs.pqs.ConnectFour.api.IGameModel;
import nyu.edu.cs.pqs.ConnectFour.api.IObserver;
import nyu.edu.cs.pqs.ConnectFour.impl.Config.Player;

/**
 * Implements the {@link IGameModel} interface
 * 
 * @author Nikita Amartya
 *
 */
final class AppModel implements IGameModel {

  private boolean         gameOn;
  private BoardStatus     board;
  private Player          whoMakesNextMove;
  private List<IObserver> observers;
  private int             numOfUsers;
  private Object          lock;

  /**
   * Initialize member variables
   */
  AppModel() {
    gameOn = false;
    board = new BoardStatus();
    whoMakesNextMove = Player.None;
    observers = new LinkedList<IObserver>();
    numOfUsers = 0;
    lock = new Object();
  }

  @Override
  public void startGame(String firstPlayerName) {
    synchronized (lock) {
      if (numOfUsers != Config.NumOfPlayers) {
        throw new IllegalStateException("All players need to be added to start game.");
      }
      if (firstPlayerName == null || notAPlayer(firstPlayerName)) {
        throw new IllegalArgumentException("Player name missing or not correct.");
      }
      if (gameOn) {
        throw new IllegalStateException("Game is in progress.");
      }

      gameOn = true;
      board.reset();
      int playerIndex = 0;
      Player[] players = new Player[] { Player.Player1, Player.Player2 };

      for (IObserver obs : observers) {
        if (obs instanceof IObserver) {
          IObserver player = (IObserver) obs;
          if (player.getPlayerName().equals(firstPlayerName)) {
            whoMakesNextMove = players[playerIndex];
          }
          player.notifyGameBegin(players[playerIndex]);
          playerIndex++;
        }
        else {
          obs.notifyGameBegin(Player.None);
        }
      }
    }
  }

  private boolean notAPlayer(String firstPlayerName) {
    for (IObserver obs : observers) {
      if (obs instanceof IObserver && ((IObserver) obs).getPlayerName().equals(firstPlayerName)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public void addObserverToModel(IObserver observer) {
    synchronized (lock) {
      if (gameOn && observer instanceof IObserver) {
        throw new IllegalStateException("Cannot add player when game is in progress.");
      }
      // add a player
      if (observer instanceof IObserver) {
        IObserver newPlayer = (IObserver) observer;
        if (numOfUsers == Config.NumOfPlayers) {
          throw new IllegalStateException("Maximum number of players already added.");
        }
        for (IObserver storedObserver : observers) {
          if (storedObserver instanceof IObserver) {
            IObserver storedPlayer = (IObserver) storedObserver;
            if (storedPlayer.getPlayerName() == newPlayer.getPlayerName()) {
              throw new IllegalArgumentException("Chose another name.");
            }
          }
        }
        numOfUsers++;
      }
      observers.add(observer);
    }
  }

  @Override
  public void removeObserverFromModel(IObserver observer) {
    synchronized (lock) {
      if (gameOn && observer instanceof IObserver) {
        throw new IllegalStateException("Cannot remove. Game is in progress.");
      }
      if (!observers.remove(observer)) {
        throw new IllegalArgumentException("Unable to remove");
      }
      if (observer instanceof IObserver) {
        numOfUsers--;
      }
    }
  }

  @Override
  public void moveMade(PlayerMove move) {

    if (move.getPlayerID() == null || move.getPlayerID() == Player.None) {
      throw new IllegalArgumentException("Only a registerd player can make a move.");
    }

    synchronized (lock) {
      if (!gameOn) {
        throw new IllegalStateException("Game is not in progress");
      }

      // Make a single move and switch the control to other player
      board.singleMove(move);
      switch (whoMakesNextMove) {
        case Player1:
          whoMakesNextMove = Player.Player2;
          break;
        case Player2:
          whoMakesNextMove = Player.Player1;
          break;
        default:
          throw new IllegalStateException("Invalid switching");
      }

      for (IObserver obs : observers) {
        obs.movePlayed(move);
      }
      if (board.isWinningMove(move)) {
        gameOver(move.getPlayerID());
      }
      else if (board.isBoardFull()) {
        gameOver(Player.None);
      }
    }
  }

  @Override
  public Player playerMakingNextMove() {
    synchronized (lock) {
      return whoMakesNextMove;
    }
  }

  @Override
  public void gameOver(Player playerID) {
    synchronized (lock) {
      if (!gameOn) {
        throw new IllegalStateException("Game is not in progress");
      }
      gameOn = false;
      for (IObserver obs : observers) {
        obs.gameEnd(playerID);
      }
    }
  }

  @Override
  public Board getGameState() {
    return new Board(board);
  }

  @Override
  public boolean isGameOn() {
    synchronized (lock) {
      return gameOn;
    }
  }

}
