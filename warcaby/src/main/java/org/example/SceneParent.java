package org.example;

import javafx.scene.Scene;

/**
 * Abstakcyjna klasa sceny.
 */
public abstract class SceneParent 
{
  static float WIDTH;  
  static float HEIGHT;
  static float SIZE;
  static int FIELDS;
  
  Thread th;
  Scene scene;

  /**
   * Statyczne ustawienie wymiarów.
   * @param w szerokosc
   * @param h wysokosc
   */
  public static void setSize(float w, float h)
  {
    WIDTH = w;
    HEIGHT = h;
  }

  /**
   * Statyczne ustawienie wymiarów.
   * @param w szerokosc
   * @param h wysokosc
   * @param s rozmiar pola
   * @param f liczba pol
   */
  public static void setSize(float w, float h, float s, int f)
  {
    WIDTH = w;
    HEIGHT = h;
    SIZE = s;
    FIELDS = f;
  }
  /**
   * Getter sceny
   * @return scena
   */
  public Scene getScene()
  {
    return scene;
  }
  /**
   * Synchronizacja watkow
   */
  public void join()
  {
    try {
      th.join();
    } catch (InterruptedException e) {
      System.out.print("thread error");
    }
  }
}
