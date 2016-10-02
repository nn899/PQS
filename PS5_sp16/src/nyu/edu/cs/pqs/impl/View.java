package nyu.edu.cs.pqs.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import nyu.edu.cs.pqs.api.Listener;

/**
 * This class implements the Listener Interface and creates the drawing canvas. Code reference:
 * http://www.ssaurel.com/blog/learn-how-to-make-a-swing-painting-and-drawing-application/
 * 
 * @author nn899
 *
 */
public class View implements Listener {

  // Creates the Canvas and draws the lines on the canvas
  private class PaintUtil extends JPanel {

    private static final long serialVersionUID = -3696930695366459542L;
    private Image             image;
    private Graphics2D        graphics;
    private int               currentX, currentY, prevX, prevY;

    PaintUtil() {
      setDoubleBuffered(false);
      addMouseListener(new MouseAdapter() {
        public void mousePressed(MouseEvent e) {
          model.mouseClicked(e.getX(), e.getY());
        }
      });
      addMouseMotionListener(new MouseMotionAdapter() {
        public void mouseDragged(MouseEvent e) {
          model.mouseMoved(e.getX(), e.getY());
        }
      });
    }

    /**
     * Clears the canvas and sets the default color to BLACK
     */
    private void clear() {
      graphics.setPaint(Color.white);
      graphics.fillRect(0, 0, getSize().width, getSize().height);
      graphics.setPaint(Color.black);
      repaint();
    }

    /**
     * Sets the color being used on the canvas
     * 
     * @param color
     */
    private void setColor(Color color) {
      graphics.setPaint(color);
    }

    /**
     * Sets the coordinates when mouse is clicked
     * 
     * @param x
     * @param y
     */
    private void mouseIsClicked(int x, int y) {
      prevX = x;
      prevY = y;
    }

    /**
     * Sets the coordinates when mouse is moved on the canvas
     * 
     * @param x
     * @param y
     */
    private void mouseIsMoved(int x, int y) {
      currentX = x;
      currentY = y;
      if (graphics != null) {
        graphics.drawLine(prevX, prevY, currentX, currentY);
        repaint();
        prevX = currentX;
        prevY = currentY;
      }
    }

    /*
     * (non-Javadoc)
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    protected void paintComponent(Graphics g) {
      if (image == null) {
        image = createImage(getSize().width, getSize().height);
        graphics = (Graphics2D) image.getGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setPaint(Color.black);
        clear();
      }
      g.drawImage(image, 0, 0, null);
    }

  }

  private Model     model;
  private JFrame    frame;
  private JPanel    colorPanel;
  private JButton   redButton;
  private JButton   blackButton;
  private JButton   greenButton;
  private JButton   blueButton;
  private JButton   magentaButton;
  private JButton   clearButton;
  private PaintUtil canvas;

  /**
   * Initializes model and adds listener
   * 
   * @param model
   */
  View(Model model) {
    this.model = model;
    model.addListener(this);
  }

  /**
   * Creates the canvas, adds color panel and reset button
   */
  public void canvasLayout() {
    frame = new JFrame("Canvas");
    redButton = new JButton();
    redButton.setBackground(Color.RED);
    redButton.setBorderPainted(false);
    redButton.setOpaque(true);
    blackButton = new JButton();
    blackButton.setBackground(Color.BLACK);
    blackButton.setBorderPainted(false);
    blackButton.setOpaque(true);
    greenButton = new JButton();
    greenButton.setBackground(Color.GREEN);
    greenButton.setBorderPainted(false);
    greenButton.setOpaque(true);
    blueButton = new JButton();
    blueButton.setBackground(Color.BLUE);
    blueButton.setBorderPainted(false);
    blueButton.setOpaque(true);
    magentaButton = new JButton();
    magentaButton.setBackground(Color.MAGENTA);
    magentaButton.setBorderPainted(false);
    magentaButton.setOpaque(true);
    clearButton = new JButton("Reset");
    addListenerOnButtons();

    colorPanel = new JPanel();
    colorPanel.setLayout(new GridLayout(6, 1));
    colorPanel.add(redButton);
    colorPanel.add(magentaButton);
    colorPanel.add(blueButton);
    colorPanel.add(greenButton);
    colorPanel.add(blackButton);
    colorPanel.add(clearButton);
    canvas = new PaintUtil();
    frame.add(canvas, BorderLayout.CENTER);
    frame.getContentPane().add(colorPanel, BorderLayout.WEST);
    frame.pack();
    frame.setSize(650, 650);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }

  /**
   * Adds action listener on each button
   */
  private void addListenerOnButtons() {

    redButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        model.setColor(Color.RED);
      }
    });

    blackButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        model.setColor(Color.BLACK);
      }
    });

    blueButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        model.setColor(Color.BLUE);
      }
    });

    greenButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        model.setColor(Color.GREEN);
      }
    });

    magentaButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        model.setColor(Color.MAGENTA);
      }
    });

    clearButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        model.cleanAll();
      }
    });
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void resetCanvas() {
    canvas.clear();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void mouseClicked(int x, int y) {
    canvas.mouseIsClicked(x, y);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void mouseMovedOnCanvas(int x, int y) {
    canvas.mouseIsMoved(x, y);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setColor(Color color) {
    canvas.setColor(color);
  }

}
