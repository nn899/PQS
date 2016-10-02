package nyu.edu.cs.pqs.ConnectFour.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import nyu.edu.cs.pqs.ConnectFour.api.IGameModel;
import nyu.edu.cs.pqs.ConnectFour.api.IObserver;
import nyu.edu.cs.pqs.ConnectFour.impl.Config.Player;

/**
 * Implementation of Observer Interface for Computer Player
 * 
 * @author Nikita Amartya
 *
 */
final public class Computer implements IObserver {

  private String     playerName;
  private Player     playerID;
  private IGameModel model;

  /**
   * Initialize player name and model
   * 
   * @param model
   *          {@link IGameMode}
   */
  Computer(IGameModel model) {
    this.playerName = "Computer";
    this.model = model;
  }

  @Override
  public void notifyGameBegin(Player ID) {
    playerID = ID;
    if (model.playerMakingNextMove() == playerID) {
      PlayerMove computerMove = getBestMove(model.getGameState());
      model.moveMade(computerMove);
    }
  }

  @Override
  public void movePlayed(PlayerMove move) {
    Board board = model.getGameState();
    if (model.playerMakingNextMove() == playerID && !board.resultsInWin(move)) {
      PlayerMove computerMove = getBestMove(board);
      model.moveMade(computerMove);
    }
  }

  @Override
  public void gameEnd(Player winner) {
    // do nothing
  }

  @Override
  public String getPlayerName() {
    return this.playerName;
  }

  /**
   * Get the best move that Computer can make for a given board state
   * 
   * @param board
   * @return {@link PlayerMove}
   */
  private PlayerMove getBestMove(Board board) {
    Player[][] boardState = board.getBoardSate();
    List<PlayerMove> moves = new ArrayList<PlayerMove>();
    for (int c = 0; c < Config.NumOfColumns; c++) {
      for (int r = Config.NumOfRows - 1; r >= 0; r--) {
        if (boardState[r][c] == Player.None) {
          moves.add(new PlayerMove(r, c, boardState[r][c]));
          break;
        }
      }
    }
    PlayerMove randomMove = moves.get(new Random().nextInt(moves.size()));
    PlayerMove computerMove = new PlayerMove(randomMove.getRow(), randomMove.getCol(), playerID);
    for (PlayerMove move : moves) {
      if (computerWins(board, move) || userWins(board, move)) {
        computerMove = new PlayerMove(move.getRow(), move.getCol(), playerID);
        break;
      }
    }
    return computerMove;
  }

  /**
   * Takes a move on a board as input and checks if the resulting move is a win for Computer
   * 
   * @param board
   * @param move
   * @return {@link true} if computer made a winning move, else return {@link false}
   */
  private boolean computerWins(Board board, PlayerMove move) {
    return board.resultsInWin(new PlayerMove(move.getRow(), move.getCol(), playerID));
  }

  /**
   * Takes a move on a board as input and checks if the resulting move is a win for user
   * 
   * @param board
   * @param move
   * @return {@link true} if user has made a winning move, else return {@link false}
   */
  private boolean userWins(Board board, PlayerMove move) {
    Player userID;
    if (playerID == Player.Player1) {
      userID = Player.Player2;
    }
    else {
      userID = Player.Player1;
    }
    PlayerMove userMove = new PlayerMove(move.getRow(), move.getCol(), userID);
    return board.resultsInWin(userMove);
  }

}
