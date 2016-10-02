package nyu.edu.cs.pqs.ConnectFour.impl;


/**
 * This class defines the basic infrastructure required for connect four game: Number of Rows
 * Number of Columns, Minimum consecutive length required to win and Number of Players
 * 
 * @author Nikita Amartya
 *
 */
public class Config {
  // prevent instantiation
  private Config() {
    throw new UnsupportedOperationException("No instance of this class is allowed");
  }

  public static final int NumOfPlayers       = 2;
  public static final int NumOfRows          = 6;
  public static final int NumOfColumns       = 7;
  // Minimum consecutive length required to win
  public static final int ConnectionReqToWin = 4;

  public static enum Player {
    Player1, Player2, None
  }

}
