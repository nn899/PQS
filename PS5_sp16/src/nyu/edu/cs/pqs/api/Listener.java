package nyu.edu.cs.pqs.api;

import java.awt.Color;

/**
 * @author nn899
 *
 */
public interface Listener {

  /**
   * Saves the coordinates of mouse click
   * 
   * @param x
   * @param y
   */
  public void mouseClicked(int x, int y);

  /**
   * Saves the coordinates of mouse movement
   * 
   * @param x
   * @param y
   */
  public void mouseMovedOnCanvas(int x, int y);

  /**
   * Sets the color of drawing lines
   * 
   * @param color
   */
  public void setColor(Color color);

  /**
   * Cleans all the drawing board and resets to default
   **/
  public void resetCanvas();

}
