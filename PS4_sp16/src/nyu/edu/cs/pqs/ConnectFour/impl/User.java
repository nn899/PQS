package nyu.edu.cs.pqs.ConnectFour.impl;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import nyu.edu.cs.pqs.ConnectFour.api.IGameModel;
import nyu.edu.cs.pqs.ConnectFour.api.IObserver;
import nyu.edu.cs.pqs.ConnectFour.impl.Config.Player;

public class User implements IObserver {
  private String     playerName;
  private Player     playerID;
  private IGameModel model;
  private JButton[]  buttons;
  private JLabel[][] boardOnDisplay;
  private ImageIcon  arrow = new ImageIcon(getClass().getResource("../resources/arrow.png"));
  private ImageIcon  tile1 = new ImageIcon(getClass().getResource("../resources/Empty_Tile.png"));
  private ImageIcon  tile2 = new ImageIcon(getClass().getResource("../resources/Red_Tile.png"));
  private ImageIcon  tile3 = new ImageIcon(getClass().getResource("../resources/Yellow_Tile.png"));
  ConnectFourGUI     UIObj = new ConnectFourGUI();

  /**
   * Accepts a model and a player name as string
   * 
   * @param model
   * @param name
   */
  protected User(IGameModel model, String name) {
    if (name == null || name == "" || model == null) {
      throw new IllegalArgumentException("Illegal name or model.");
    }
    this.model = model;
    this.playerName = name;
    this.playerID = Player.None;
    UIObj.createAndShowGUI();
    ActionListener buttonListener = getButtonListener();
    addButtonsToTopRow(UIObj.getContentPane(), buttonListener);
    addGameBoardPanels(UIObj.getContentPane());
    clearBoard();
  }

  /**
   * ActionListener for buttons on in the top row
   * 
   * @return ActionListener
   */
  private ActionListener getButtonListener() {
    ActionListener buttonListener = new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent event) {
        JButton sourceButton = (JButton) event.getSource();
        int column = Integer.parseInt(sourceButton.getName());
        int row = model.getGameState().getBottomAvailableRowForColumn(column);
        setButtonsEnabled(false);
        model.moveMade(new PlayerMove(row, column, playerID));
      }
    };
    return buttonListener;
  }

  /**
   * Add buttons at the top row which need to be pressed to make a move
   * 
   * @param contentPane
   * @param buttonListener
   */
  private void addButtonsToTopRow(Container contentPane, ActionListener buttonListener) {
    buttons = new JButton[Config.NumOfColumns];
    for (int col = 0; col < buttons.length; col++) {
      JButton button = new JButton(arrow);
      button.setName(String.valueOf(col));
      button.addActionListener(buttonListener);
      buttons[col] = button;
      contentPane.add(button);
    }
    setButtonsEnabled(false);
  }

  /**
   * Enable/Disable JButtons at the top row of game board
   * 
   * @param isEnabled
   */
  private void setButtonsEnabled(boolean isEnabled) {
    for (JButton button : buttons) {
      button.setEnabled(isEnabled);
    }
  }

  /**
   * Add contentPane for the game board
   * 
   * @param contentPane
   */
  private void addGameBoardPanels(Container contentPane) {
    boardOnDisplay = new JLabel[Config.NumOfRows][Config.NumOfColumns];
    for (int row = 0; row < boardOnDisplay.length; row++) {
      for (int col = 0; col < boardOnDisplay[0].length; col++) {
        JLabel label = new JLabel();
        boardOnDisplay[row][col] = label;
        contentPane.add(label);
      }
    }
  }

  /**
   * Clear all the tiles of the board to start new game
   */
  private void clearBoard() {
    for (JLabel[] row : boardOnDisplay) {
      for (JLabel label : row) {
        label.setIcon(tile1);
      }
    }
  }

  @Override
  public void notifyGameBegin(Player id) {
    playerID = id;
    if (model.playerMakingNextMove() == playerID) {
      setButtonsEnabled(true);
    }
    else {
      setButtonsEnabled(false);
    }

  }

  @Override
  public void movePlayed(PlayerMove move) {
    JLabel labelToUpdate = boardOnDisplay[move.getRow()][move.getCol()];

    if (model.playerMakingNextMove() == playerID) {
      labelToUpdate.setIcon(tile2);
    }
    else {
      labelToUpdate.setIcon(tile3);
    }

    if (model.playerMakingNextMove() == playerID) {
      setButtonsEnabled(true);

      Player[] topRow = model.getGameState().getBoardSate()[0];
      for (int col = 0; col < Config.NumOfColumns; col++) {
        if (topRow[col] != Player.None) {
          buttons[col].setEnabled(false);
        }
      }
    }
  }

  @Override
  public void gameEnd(Player winningPlayerID) {
    setButtonsEnabled(false);
    String title = "Game Over";
    String message;
    if (winningPlayerID == Player.None) {
      message = "Game Draw";
    }
    else {
      message = (winningPlayerID == playerID ? "You" : "Opponent");
      message += " won, Starting another game.";
    }

    int response = JOptionPane.showOptionDialog(UIObj.getMainFrame(), message, title,
        JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, new String[] { "No", "Yes" },
        null);
    if (response == 0) {
      System.exit(0);
    }
    else {
      if (!model.isGameOn()) {
        model.startGame(playerName);
      }
      clearBoard();
    }
  }

  @Override
  public String getPlayerName() {
    return playerName;
  }

}
