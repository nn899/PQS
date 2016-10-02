package nyu.edu.cs.pqs.ConnectFour.impl;

import nyu.edu.cs.pqs.ConnectFour.impl.Config.Player;

/**
 * This class defines characteristic of a move made by any {@link User} or {@link Computer}
 * 
 * @author Nikita Amartya
 *
 */
public class PlayerMove {

  private int    row;
  private int    col;
  private Player playedID;

  /**
   * Constructs a single move
   * 
   * @param row
   *          of the move
   * @param col
   *          column of the move
   * @param playedBy
   *          who plays this move
   */
  public PlayerMove(int row, int col, Player playedBy) {
    this.row = row;
    this.col = col;
    this.playedID = playedBy;
  }

  /**
   * Get playerid of player who plays the move
   * 
   * @return playerid
   */
  public Player getPlayerID() {
    return playedID;
  }

  /**
   * Get row of the move
   * 
   * @return row of the move
   */
  public int getRow() {
    return row;
  }

  /**
   * Get column of the move
   * 
   * @return column of the move
   */
  public int getCol() {
    return col;
  }

}
