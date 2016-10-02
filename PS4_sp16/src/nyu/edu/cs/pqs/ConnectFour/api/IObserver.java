package nyu.edu.cs.pqs.ConnectFour.api;

import nyu.edu.cs.pqs.ConnectFour.impl.Config.Player;
import nyu.edu.cs.pqs.ConnectFour.impl.PlayerMove;

/**
 * Game observer interface in the observer pattern. As the name suggests, it observers the moves
 * made in the game by players
 * 
 * @author Nikita Amartya
 *
 */
public interface IObserver {

  /**
   * Notifies of the beginning of a game
   * 
   * @param assignedPlayerID
   *          {@link Player}
   */
  public void notifyGameBegin(Player assignedPlayerID);

  /**
   * Notifies that a player has made a move
   * 
   * @param move
   *          {@link PlayerMove}
   */
  public void movePlayed(PlayerMove move);

  /**
   * Notifies game has ended
   * 
   * @param winningPlayerID
   *          {@link Player}
   */
  public void gameEnd(Player winningPlayerID);

  /**
   * Gives the name of the player
   * 
   * @return name of the {@link Player}
   */
  public String getPlayerName();

}
