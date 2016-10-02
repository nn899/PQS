package nyu.edu.cs.pqs.ConnectFour.tests;

import nyu.edu.cs.pqs.ConnectFour.impl.PlayerFactory;

import org.junit.After;
import org.junit.Test;

public class IllegalGameTypeTests {

  @After
  public void tearDown() {
    PlayerFactory.reset();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreate2PlayerGame_nullPlayer1() {
    PlayerFactory.getFactoryInstance();
    PlayerFactory.createTwoPlayerGame(null, "Player 2");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreate2PlayerGame_nullPlayer2() {
    PlayerFactory.getFactoryInstance();
    PlayerFactory.createTwoPlayerGame("Player 1", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreate1PlayerGame_nullName() {
    PlayerFactory.getFactoryInstance();
    PlayerFactory.createSinglePlayerGame(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreate2PlayerGame_withSameNames() {
    PlayerFactory.getFactoryInstance();
    PlayerFactory.createTwoPlayerGame("Player 1", "Player 1");
  }

}
