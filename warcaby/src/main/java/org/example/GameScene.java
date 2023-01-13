package org.example;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

/**
 * Klasa obslugujaca scene gry.
 */
public class GameScene extends SceneParent implements Runnable
{
  GridPane gridPane;
  Label tourLabel;    

  /**
   * Konstruktor.
   */
  public GameScene()
  {
    th = new Thread(this);
    th.start();
  }
  
  /**
   * Ustawienie gornego tekstu.
   * @param s text
   */
  public void setLabel(String s)
  {
    Platform.runLater(() -> tourLabel.setText(s));
  }

  /**
   * Tworzenie planszy.
   */
  public void run()
  {
    gridPane = new GridPane();
    scene = new Scene(gridPane, WIDTH, HEIGHT);
    scene.setCursor(Cursor.CROSSHAIR);

    tourLabel = new Label("PLACEHOLDER");
    tourLabel.setFont(new Font(SIZE/1.5));
    tourLabel.setPrefWidth(WIDTH);
    tourLabel.setPrefHeight(SIZE);
    tourLabel.setAlignment(Pos.CENTER);
    
    gridPane.add(tourLabel, 0, 0, FIELDS, 1);
  }

  /**
   * Getter GridPane
   * @return gridPane
   */
  public GridPane getPane()
  {
    return gridPane;
  }
}
