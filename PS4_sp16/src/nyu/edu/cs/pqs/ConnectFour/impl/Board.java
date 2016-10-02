package nyu.edu.cs.pqs.ConnectFour.impl;

import nyu.edu.cs.pqs.ConnectFour.impl.Config.Player;

/**
 * @author Nikita Amartya
 *
 */
public final class Board {

  private BoardStatus newBoard;

  /**
   * @param board
   */
  Board(BoardStatus board) {
    this.newBoard = board;
  }

  /**
   * Gets the mapping of Player position on board
   * 
   * @return 2d array of {@link Player} indicating position of players on board
   */
  public Player[][] getBoardSate() {
    return newBoard.getStatus();
  }

  /**
   * Tests if a move can result in a win
   * 
   * @param move
   * @return {@link true} if 4 tiles can be connected through straight line else returns {@link
   *         false}
   */
  public boolean resultsInWin(PlayerMove move) {
    return newBoard.isWinningMove(move);
  }

  /**
   * Checks if the board is fully occupied
   * 
   * @return {@link true} if board is full, else returns {@link false}
   */
  public boolean allTilesOccupied() {
    return newBoard.isBoardFull();
  }

  /**
   * Get bottom most unoccupied row number if a move is made for a column
   * 
   * @param c
   * @return row number
   */
  public int getBottomAvailableRowForColumn(int c) {
    return newBoard.getRow(c);
  }

}
