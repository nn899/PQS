package nyu.edu.cs.pqs.ConnectFour.impl;

import javax.swing.JOptionPane;

/**
 * Main() of the Connect Four Game Application. This class initializes the game in a single user or
 * double player mode. In double user mode, two windows pop-up on top of each other.
 * 
 * @author Nikita Amartya
 *
 */
public class AppMain {

  public static void main(String[] args) {

    int response = JOptionPane.showOptionDialog(null, "Please select one option", "Connect Four",
        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new String[] { "Quit",
            "Two Players", "Single Player" }, null);
    PlayerFactory.getFactoryInstance();

    // Two Player Mode
    if (response == 1) {
      PlayerFactory.createTwoPlayerGame("Player 1", "Player 2");
    }
    // Player Vs Computer
    else if (response == 2) {
      PlayerFactory.createSinglePlayerGame("User");
    }
    // Quit
    else if (response == 0) {
      System.exit(0);
    }
  }

}
