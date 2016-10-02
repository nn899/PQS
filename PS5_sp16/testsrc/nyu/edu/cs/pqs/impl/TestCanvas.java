package nyu.edu.cs.pqs.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.awt.Point;

import nyu.edu.cs.pqs.api.Listener;

import org.junit.Test;

public class TestCanvas {

  Model testmodel = Model.getInstance();

  private class MockListener implements Listener {
    private Point   currentCoordinates;
    private Color   currentColor = Color.YELLOW;
    private boolean cleanCanvas  = false;

    public MockListener(Model model) {
      model.addListener(this);
    }

    @Override
    public void mouseClicked(int x, int y) {
      currentCoordinates = new Point(x, y);
    }

    @Override
    public void mouseMovedOnCanvas(int x, int y) {
      currentCoordinates = new Point(x, y);
    }

    @Override
    public void setColor(Color color) {
      currentColor = color;
    }

    @Override
    public void resetCanvas() {
      cleanCanvas = true;
    }

    public Point getCurrentPoint() {
      return currentCoordinates;
    }

    public Color getCurrentColor() {
      return currentColor;
    }

    public boolean isCleanCanvas() {
      return cleanCanvas;
    }
  }

  MockListener mockObj = new MockListener(testmodel);

  @Test
  public void testRemoveListener() {
    View view = new View(testmodel);
    assertTrue(testmodel.removeListener(view));
    assertTrue(!(testmodel.removeListener(view)));
  }

  @Test
  public void testAll() {
    testmodel.mouseMoved(44, 56);
    Point testPoint = new Point(44, 56);
    assertEquals(testPoint, mockObj.getCurrentPoint());
    assertFalse(mockObj.isCleanCanvas());
    testmodel.setColor(Color.GREEN);
    assertEquals(Color.GREEN, mockObj.getCurrentColor());
    testmodel.mouseClicked(20, 30);
    Point testPoint2 = new Point(20, 30);
    assertEquals(testPoint2, mockObj.getCurrentPoint());
    testmodel.cleanAll();
    assertTrue(mockObj.isCleanCanvas());
  }

  @Test
  public void testMouseClick() {
    testmodel.mouseClicked(10, 20);
    Point testPoint = new Point(10, 20);
    assertEquals(testPoint, mockObj.getCurrentPoint());
  }

  @Test
  public void testMouseMoved() {
    testmodel.mouseMoved(94, 55);
    Point testPoint = new Point(94, 55);
    assertEquals(testPoint, mockObj.getCurrentPoint());
  }

  @Test
  public void testChangeColor() {
    testmodel.setColor(Color.RED);
    assertEquals(Color.RED, mockObj.getCurrentColor());
    testmodel.setColor(Color.MAGENTA);
    assertEquals(Color.MAGENTA, mockObj.getCurrentColor());
  }

  @Test
  public void testCleanCanvas() {
    assertFalse(mockObj.isCleanCanvas());
    testmodel.cleanAll();
    assertTrue(mockObj.isCleanCanvas());
  }

  @Test
  public void testMoveMouseThenCleanCanvas() {
    testmodel.mouseMoved(44, 56);
    Point testPoint = new Point(44, 56);
    assertEquals(testPoint, mockObj.getCurrentPoint());
    assertFalse(mockObj.isCleanCanvas());
    testmodel.cleanAll();
    assertTrue(mockObj.isCleanCanvas());
  }

}
