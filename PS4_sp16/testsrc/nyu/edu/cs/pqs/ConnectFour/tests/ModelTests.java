/**
 * 
 */
package nyu.edu.cs.pqs.ConnectFour.tests;

import static org.junit.Assert.assertEquals;
import nyu.edu.cs.pqs.ConnectFour.api.IGameModel;
import nyu.edu.cs.pqs.ConnectFour.api.IObserver;
import nyu.edu.cs.pqs.ConnectFour.impl.Config.Player;
import nyu.edu.cs.pqs.ConnectFour.impl.PlayerFactory;
import nyu.edu.cs.pqs.ConnectFour.impl.PlayerMove;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author Nikita Amartya
 *
 */
public class ModelTests {

  private IGameModel       model;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * @throws java.lang.Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
  }

  /**
   * @throws java.lang.Exception
   */
  @AfterClass
  public static void tearDownAfterClass() throws Exception {
  }

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    model = PlayerFactory.createModelWithoutPlayers();
  }

  /**
   * @throws java.lang.Exception
   */
  @After
  public void tearDown() throws Exception {
  }

  public String addTwoPlayersToModel() {
    String firstPlayerName = "1";
    model.addObserverToModel(new TestUser(model, firstPlayerName));
    model.addObserverToModel(new TestUser(model, "2"));
    return firstPlayerName;
  }

  public void playMove(int column) {
    PlayerMove move = getMove(column);
    model.moveMade(move);
    ;
  }

  public PlayerMove getMove(int column) {
    int row = model.getGameState().getBottomAvailableRowForColumn(column);
    PlayerMove move = new PlayerMove(row, column, model.playerMakingNextMove());
    return move;
  }

  @Test
  public void testAddRemoveObserver() {
    TestUser player1 = new TestUser(model, "1");
    TestUser player2 = new TestUser(model, "2");
    model.addObserverToModel(player1);
    model.addObserverToModel(player2);
    model.removeObserverFromModel(player1);
    model.removeObserverFromModel(player2);
  }

  @Test(expected = IllegalStateException.class)
  public void testAddObserver_addThreePlayers() {
    TestUser player1 = new TestUser(model, "1");
    TestUser player2 = new TestUser(model, "2");
    model.addObserverToModel(player1);
    model.addObserverToModel(player2);
    IObserver obs = new TestObserverObj(model);
    model.addObserverToModel(obs);

  }

  @Test(expected = IllegalStateException.class)
  public void testAddPlayerWhenGameOn() {
    String firstPlayer = addTwoPlayersToModel();
    model.startGame(firstPlayer);
    model.addObserverToModel(new TestUser(model, "3"));
  }

  @Test
  public void testAddRemoveObserver2() {
    TestUser player = new TestUser(model, "name");
    model.addObserverToModel(player);
    model.removeObserverFromModel(player);
    model.addObserverToModel(player);
  }

  @Test
  public void testNotifyGameStarted_startWithOnePlayer() {
    model.addObserverToModel(new TestUser(model, "PS4"));
    thrown.expect(IllegalStateException.class);
    thrown.expectMessage("All players need to be added to start game.");
    model.startGame("PS4");
  }

  @Test
  public void testStartWhenGameIsOn() {
    String name = addTwoPlayersToModel();
    model.startGame(name);
    thrown.expect(IllegalStateException.class);
    thrown.expectMessage("Game is in progress");
    model.startGame(name);
  }

  @Test
  public void testWrongPlayerName() {
    addTwoPlayersToModel();

    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("Player name missing or not correct");
    model.startGame("Random TestName");
  }

  @Test
  public void testPlayerNameNull() {
    addTwoPlayersToModel();
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("Player name missing or not correct");
    model.startGame(null);
  }

  @Test
  public void testEmptyPlayerName() {
    addTwoPlayersToModel();
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("Player name missing or not correct");
    model.startGame("");
  }

  @Test
  public void testFirstChance() {
    TestUser player1 = new TestUser(model, "1");
    TestUser player2 = new TestUser(model, "2");
    model.addObserverToModel(player1);
    model.addObserverToModel(player2);
    model.startGame("1");
    assertEquals(model.playerMakingNextMove(), Player.Player1);
  }

  @Test
  public void testNotifyMovePlayed_whenGameNotStarted() {
    addTwoPlayersToModel();
    thrown.expect(IllegalStateException.class);
    thrown.expectMessage("Game is not in progress");
    int col = 0;
    int row = model.getGameState().getBottomAvailableRowForColumn(col);
    model.moveMade(new PlayerMove(row, col, Player.Player1));
  }

}
