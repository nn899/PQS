package nyu.edu.cs.pqs.impl;

public class InitCanvasApp {

  public static void main(String[] args) {

    Model model = Model.getInstance();
    // 4 canvases show up on top of each other
    View view1 = new View(model);
    View view2 = new View(model);
    View view3 = new View(model);
    View view4 = new View(model);
    view1.canvasLayout();
    view2.canvasLayout();
    view3.canvasLayout();
    view4.canvasLayout();
  }

}
