package org.example;

import javafx.scene.Scene;

public abstract class SceneParent 
{
  static float WIDTH;  
  static float HEIGHT;
  static float SIZE;
  static int FIELDS;
  
  Thread th;
  Scene scene;

  public static void setSize(float w, float h)
  {
    WIDTH = w;
    HEIGHT = h;
  }

  public static void setSize(float w, float h, float s, int f)
  {
    WIDTH = w;
    HEIGHT = h;
    SIZE = s;
    FIELDS = f;
  }
  public Scene getScene()
  {
    return scene;
  }
  public void join()
  {
    try {
      th.join();
    } catch (InterruptedException e) {
      System.out.print("thread error");
    }
  }
}
