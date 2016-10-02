package nyu.edu.cs.pqs.ConnectFour.impl;

import nyu.edu.cs.pqs.ConnectFour.impl.Config.Player;

/**
 * @author Nikita Amartya
 *
 */
public class BoardStatus {

  private Player[][] boardState;
  private Object     boardLock;

  /**
   * Initialize board status. All tiles are unoccupied
   */
  BoardStatus() {
    boardLock = new Object();
    synchronized (boardLock) {
      boardState = new Player[Config.NumOfRows][Config.NumOfColumns];
      for (int i = 0; i < Config.NumOfRows; i++) {
        for (int j = 0; j < Config.NumOfColumns; j++) {
          boardState[i][j] = Player.None;
        }
      }
    }
  }

  /**
   * Check if a mode made on board is in accordance to the rules of the game
   * 
   * @param move
   * @return {@link true} if the mode is correct
   */
  private boolean checkMoveLegal(PlayerMove move) {
    if (move.getRow() >= 0 && move.getRow() < Config.NumOfRows && move.getCol() >= 0
        && move.getCol() < Config.NumOfColumns) {
      boolean isCellFree = false;
      boolean allBelowOccupied = true;
      if (boardState[move.getRow()][move.getCol()] == Player.None) {
        isCellFree = true;
      }
      for (int row = move.getRow() + 1; row < Config.NumOfRows; row++) {
        if (boardState[row][move.getCol()] == Player.None) {
          allBelowOccupied = false;
        }
      }
      return (isCellFree && allBelowOccupied);
    }
    else {
      return false;
    }
  }

  /**
   * Assign a player ID to a tile once the player has made a legal move
   * 
   * @param move
   */
  void singleMove(PlayerMove move) {
    synchronized (boardLock) {
      if (!checkMoveLegal(move)) {
        throw new IllegalArgumentException("Illegal move");
      }
      boardState[move.getRow()][move.getCol()] = move.getPlayerID();
    }
  }

  /**
   * Check if there are {@link Config#ConnectionReqToWin} tile on the board which are of the same
   * player and can be connected by a single horizontal, vertical or diagonal line on board
   * 
   * @param move
   * @return {@link true} when {@link Config#ConnectionReqToWin} tiles can be connected
   */
  boolean isWinningMove(PlayerMove move) {
    // A win can be horizontal, vertical or diagonal.
    synchronized (boardLock) {
      return isHorizontalWin(move) || isVerticalWin(move) || isSouthWestToNorthEastDiagonalWin(move)
          || isNorthWestToSouthEastDiagnonalWin(move);
    }
  }

  /**
   * 
   * @param move
   * @return true if tiles can be connected vertically on board
   */
  private boolean isVerticalWin(PlayerMove move) {
    int index = move.getRow();
    for (int i = move.getRow() + 1; i < Config.NumOfRows; i++) {
      if (boardState[i][move.getCol()] != move.getPlayerID()) {
        break;
      }
      else {
        index = i;
      }
    }
    if ((index - move.getRow() + 1) >= Config.ConnectionReqToWin) {
      return true;
    }
    else {
      return false;
    }
  }

  /**
   * @param move
   * @return true if tiles can be connected horizontally on board
   */
  private boolean isHorizontalWin(PlayerMove move) {
    Player player = move.getPlayerID();
    Player[] row = boardState[move.getRow()];

    int left = move.getCol();
    int j;
    for (j = move.getCol() - 1; j >= 0; j--) {
      if (row[j] == player) {
        left = j;
      }
      else {
        break;
      }
    }

    int right = move.getCol();
    for (j = move.getCol() + 1; j < Config.NumOfColumns; j++) {
      if (row[j] == player) {
        right = j;
      }
      else {
        break;
      }
    }

    return (right - left + 1) >= Config.ConnectionReqToWin;
  }

  /**
   * @param move
   * @return true if tiles can be connected diagonally on board
   */
  private boolean isSouthWestToNorthEastDiagonalWin(PlayerMove move) {
    Player player = move.getPlayerID();

    int leftCol = move.getCol();
    for (int i = move.getRow() - 1; i >= 0 && leftCol > 0; i--) {
      int j = leftCol - 1;
      if (boardState[i][j] == player) {
        leftCol--;
      }
      else {
        break;
      }
    }

    int rightCol = move.getCol();
    for (int i = move.getRow() + 1; i < Config.NumOfRows && rightCol < Config.NumOfColumns - 1; i++) {
      int j = rightCol + 1;
      if (boardState[i][j] == player) {
        rightCol++;
      }
      else {
        break;
      }
    }

    return (rightCol - leftCol + 1) >= Config.ConnectionReqToWin;
  }

  /**
   * @param move
   * @return true if tiles can be connected diagonally on board
   */
  private boolean isNorthWestToSouthEastDiagnonalWin(PlayerMove move) {
    Config.Player player = move.getPlayerID();

    int leftCol = move.getCol();
    for (int i = move.getRow() + 1; i < Config.NumOfRows && leftCol > 0; i++) {
      int j = leftCol - 1;
      if (boardState[i][j] == player) {
        leftCol--;
      }
      else {
        break;
      }
    }

    int rightCol = move.getCol();
    for (int i = move.getRow() - 1; i >= 0 && rightCol < Config.NumOfColumns - 1; i--) {
      int col = rightCol + 1;
      if (boardState[i][col] == player) {
        rightCol++;
      }
      else {
        break;
      }
    }

    return (rightCol - leftCol + 1) >= Config.ConnectionReqToWin;
  }

  /**
   * @return true if board is completely occupied
   */
  boolean isBoardFull() {
    synchronized (boardLock) {
      for (Config.Player[] row : boardState) {
        for (Config.Player entry : row) {
          if (entry == Config.Player.None) {
            return false;
          }
        }
      }
      return true;
    }
  }

  /**
   * @return two dimensional array of Players against each tile on board
   */
  Player[][] getStatus() {
    synchronized (boardLock) {
      Player[][] result = new Player[Config.NumOfRows][];
      for (int i = 0; i < Config.NumOfRows; i++) {
        result[i] = boardState[i].clone();
      }
      return result;
    }
  }

  /**
   * @param col
   * @return the row number of unoccupied row for a given column
   */
  int getRow(int col) {
    synchronized (boardLock) {
      int result = -1;
      for (int row = Config.NumOfRows - 1; row >= 0; row--) {
        if (boardState[row][col] == Player.None) {
          result = row;
          break;
        }
      }
      return result;
    }
  }

  /**
   * Reset board state such that no player is occupying any tile
   */
  void reset() {
    synchronized (boardLock) {
      boardState = new Player[Config.NumOfRows][Config.NumOfColumns];
      for (int i = 0; i < Config.NumOfRows; i++) {
        for (int j = 0; j < Config.NumOfColumns; j++) {
          boardState[i][j] = Player.None;
        }
      }
    }
  }

}
