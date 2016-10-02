package nyu.edu.cs.pqs.ConnectFour.tests;

import static org.junit.Assert.assertArrayEquals;
import nyu.edu.cs.pqs.ConnectFour.api.IGameModel;
import nyu.edu.cs.pqs.ConnectFour.impl.Config;
import nyu.edu.cs.pqs.ConnectFour.impl.Config.Player;
import nyu.edu.cs.pqs.ConnectFour.impl.PlayerFactory;
import nyu.edu.cs.pqs.ConnectFour.impl.PlayerMove;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GameBoardTests {

  IGameModel model;

  @Before
  public void setUp() throws Exception {
    PlayerFactory.getFactoryInstance();
    model = PlayerFactory.createTwoPlayerGame("Player 1", "Player 2");
  }

  @After
  public void tearDown() {
    PlayerFactory.reset();
  }

  private void player1Move(int column) {
    PlayerMove move = getMove(column, Player.Player1);
    model.moveMade(move);
  }

  private void player2Move(int column) {
    PlayerMove move = getMove(column, Player.Player2);
    model.moveMade(move);
  }

  private PlayerMove getMove(int column, Player playerID) {
    int row = model.getGameState().getBottomAvailableRowForColumn(column);
    PlayerMove move = new PlayerMove(row, column, playerID);
    return move;
  }

  private Player[] getEmptyBoardRow() {
    Player[] emptyRow = new Player[Config.NumOfColumns];
    for (int col = 0; col < emptyRow.length; col++) {
      emptyRow[col] = Player.None;
    }
    return emptyRow;
  }

  @Test
  public void GameBoardTest_testMoves() {
    player1Move(0);
    player2Move(1);
    Player[][] boardState = model.getGameState().getBoardSate();

    Player[] emptyRow = getEmptyBoardRow();
    for (int row = 0; row < Config.NumOfColumns - 2; row++) {
      assertArrayEquals(boardState[row], emptyRow);
    }
    Player[] playedRow = emptyRow;
    playedRow[0] = Player.Player1;
    playedRow[1] = Player.Player2;
    assertArrayEquals(boardState[Config.NumOfRows - 1], playedRow);
  }

}
