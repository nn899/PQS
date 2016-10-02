package nyu.edu.cs.pqs.ConnectFour.api;

import nyu.edu.cs.pqs.ConnectFour.impl.Board;
import nyu.edu.cs.pqs.ConnectFour.impl.Config.Player;
import nyu.edu.cs.pqs.ConnectFour.impl.PlayerMove;

/**
 * Interface for Model in Observer pattern.
 * 
 * @author Nikita Amartya
 *
 */
public interface IGameModel {

  /**
   * Start a game with first chance assigned to the supplied player.
   * 
   * @param whoMakesFirstMove
   *          name of player who gets to play the first chance in every game.
   * @throws IllegalStateException
   *           if called when game has already been started.
   * @throws IllegalStateException
   *           if called when all players are not in game.
   * @throws IllegalArgumentException
   *           if player name missing or not correct.
   */
  public void startGame(String whoMakesFirstMove);

  /**
   * Returns game status
   * 
   * @return {@link true} if game is in progress else returns {@link false}
   */
  public boolean isGameOn();

  /**
   * @return instance of {@link Board}
   */
  public Board getGameState();

  /**
   * Add an observer to the game.
   * 
   * @param observer
   * @throws IllegalStateException
   *           if game is already in progress or maximum number of players are already added
   * @throws IllegalArgumentException
   *           if player by a name is already added
   **/
  public void addObserverToModel(IObserver observer);

  /**
   * Remove an observer from the game.
   * 
   * @param observer
   * 
   * @throws IllegalStateException
   *           if game is already in progress
   * @throws IllegalArgumentException
   *           if unable to remove the Observer
   */
  public void removeObserverFromModel(IObserver observer);

  /**
   * Broadcast to all observers that a player has made a move
   * 
   * @param move
   *          played by a player who is part of the game.
   * @throws IllegalArgumentException
   *           if move is played by someone other than the player.
   * @throws IllegalStateException
   *           if called when game is not in progress.
   * 
   */
  public void moveMade(PlayerMove move);

  /**
   * 
   * @return the {@link Player} who gets to make the next move
   */
  public Player playerMakingNextMove();

  /**
   * Broadcast to all observers that the game has ended.
   * 
   * @param playerID
   *          of the winning player. If game results in draw {@link Player#None} is passed as param
   * @throws IllegalStateException
   *           if called when game is not in progress
   */
  public void gameOver(Player playerID);

}
