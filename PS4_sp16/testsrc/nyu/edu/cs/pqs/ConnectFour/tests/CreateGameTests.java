package nyu.edu.cs.pqs.ConnectFour.tests;

import static org.junit.Assert.assertNotNull;
import nyu.edu.cs.pqs.ConnectFour.impl.PlayerFactory;

import org.junit.Test;

public class CreateGameTests {

  @Test
  public void testCreate1PlayerGame() {
    PlayerFactory.getFactoryInstance();
    PlayerFactory.createSinglePlayerGame("player1");
    assertNotNull(PlayerFactory.getFactoryInstance());
  }

  // Game is already created so this test case should throw exception
  @Test(expected = IllegalStateException.class)
  public void testCreate2PlayerGame() {
    PlayerFactory.getFactoryInstance();
    PlayerFactory.createTwoPlayerGame("player1", "player2");
  }

}
