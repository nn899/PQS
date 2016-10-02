/**
 * 
 */
package nyu.edu.cs.pqs.ConnectFour.tests;

import static org.junit.Assert.fail;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import nyu.edu.cs.pqs.ConnectFour.api.IGameModel;
import nyu.edu.cs.pqs.ConnectFour.api.IObserver;
import nyu.edu.cs.pqs.ConnectFour.impl.Config.Player;
import nyu.edu.cs.pqs.ConnectFour.impl.PlayerMove;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author NikitaAmartya
 *
 */
public class TestObserverObj implements IObserver {

  public IGameModel     model;
  protected Player      playerID;
  protected Player      winner;
  protected PlayerMove  lastMove;
  protected List<State> states;
  protected Object      notifLock;

  public TestObserverObj(IGameModel model) {
    this.model = model;
    states = new LinkedList<State>();
    notifLock = new Object();

  }

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
  }

  /**
   * @throws java.lang.Exception
   */
  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void test() {
    fail("Not yet implemented");
  }

  @Override
  public void notifyGameBegin(Player assignedPlayerID) {
    synchronized (notifLock) {
      playerID = assignedPlayerID;
      states.add(State.started);
    }
  }

  @Override
  public void movePlayed(PlayerMove move) {
    synchronized (notifLock) {
      lastMove = move;
      states.add(State.movePlayed);
    }
  }

  @Override
  public void gameEnd(Player winningPlayerID) {
    synchronized (notifLock) {
      winner = winningPlayerID;
      states.add(State.finished);
    }
  }

  @Override
  public String getPlayerName() {
    return "";
  }

  public List<State> getNotifs() {
    synchronized (notifLock) {
      return new CopyOnWriteArrayList<State>(states);
    }
  }

  public State getNotifFromEnd(int index) {
    synchronized (notifLock) {
      int actualIndex = states.size() + index;
      return states.get(actualIndex);
    }
  }

  public Player getWinner() {
    synchronized (notifLock) {
      return winner;
    }
  }

  public Player getPlayerID() {
    synchronized (notifLock) {
      return playerID;
    }
  }

  public PlayerMove getLastMove() {
    synchronized (notifLock) {
      return lastMove;
    }
  }

  public enum State {
    started, movePlayed, finished
  }

}
