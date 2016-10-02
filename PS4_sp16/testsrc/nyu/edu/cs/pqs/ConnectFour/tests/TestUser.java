package nyu.edu.cs.pqs.ConnectFour.tests;

import nyu.edu.cs.pqs.ConnectFour.api.IGameModel;
import nyu.edu.cs.pqs.ConnectFour.impl.User;

public class TestUser extends User {

  public String name;

  TestUser(IGameModel model, String name) {
    super(model, name);
    this.name = name;
  }

  public String getName() {
    return name;
  }

}
