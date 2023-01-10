package org.example;

public class SceneFactory 
{
  public void createFactory(String type)
  {
    if (type=="MENU")
    {
      return new MenuScene();
    }
    else if (type=="GAME")
    {
      return new GameScene();
    }
  }
}
