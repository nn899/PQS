package nyu.edu.cs.pqs.ConnectFour.impl;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JFrame;

/**
 * Basic components of the User Interface are defined in this class
 * 
 * @author Nikita Amartya
 *
 */
public class ConnectFourGUI {

  private JFrame    mainFrame;
  private Container contentPane;

  /**
   * Creates a JFrame, adds rows and columns to it and sets other properties
   */
  public void createAndShowGUI() {
    mainFrame = new JFrame();
    mainFrame.setTitle("Connect 4 - PS4");
    mainFrame.setResizable(false);
    GridLayout board = new GridLayout(Config.NumOfRows + 1, Config.NumOfColumns);
    mainFrame.setLayout(board);
    mainFrame.pack();
    contentPane = mainFrame.getContentPane();
    contentPane.setBackground(Color.BLACK);
    mainFrame.setSize(715, 680);
    mainFrame.setVisible(true);
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  /**
   * @return UI contentPane {@link contentPane}
   */
  public Container getContentPane() {
    return contentPane;
  }

  /**
   * @return UI JFrame {@link mainFrame}
   */
  public JFrame getMainFrame() {
    return mainFrame;
  }

}
