package nyu.edu.cs.pqs.impl;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import nyu.edu.cs.pqs.api.Listener;

/**
 * Model holds the coordinates in case of mouse click and mouse movements. Also, it sets the color
 * chosen when a color button is pressed. Model passes the information to Listener
 * 
 * @author nn899
 *
 */
final class Model {

  private List<Listener> listeners;
  private static Model   instance = new Model();

  /**
   * Creates array list of listener
   */
  private Model() {
    listeners = new ArrayList<Listener>();
  }

  /**
   * @return instance of model
   */
  public static Model getInstance() {
    return instance;
  }

  /**
   * Adds listener
   * 
   * @param listener
   */
  public void addListener(Listener listener) {
    listeners.add(listener);
  }

  /**
   * Removes listener
   * 
   * @param listener
   * @return
   */
  public boolean removeListener(Listener listener) {
    return listeners.remove(listener);
  }

  /**
   * Gets coordinates of mouse click
   * 
   * @param x
   * @param y
   */
  public void mouseClicked(int x, int y) {
    for (Listener listener : listeners) {
      listener.mouseClicked(x, y);
    }
  }

  /**
   * Gets coordinates of mouse movement
   * 
   * @param x
   * @param y
   */
  public void mouseMoved(int x, int y) {
    for (Listener listener : listeners) {
      listener.mouseMovedOnCanvas(x, y);
    }
  }

  /**
   * Sets the color of drawing pencil
   * 
   * @param color
   */
  public void setColor(Color color) {
    for (Listener listener : listeners) {
      listener.setColor(color);
    }
  }

  /**
   * Clears the canvas
   */
  public void cleanAll() {
    for (Listener listener : listeners) {
      listener.resetCanvas();
    }
  }

}
