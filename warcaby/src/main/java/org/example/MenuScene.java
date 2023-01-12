package org.example;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class MenuScene extends SceneParent implements Runnable
{
  VBox vbox;
  Label waitLabel;    
  Button game1Button;
  Button game2Button;
  Button game3Button;
  App app;

  MenuScene(App a)
  {
    app = a;

    th = new Thread(this);
    th.start();
  }
 
  public VBox getPane()
  {
    return vbox;
  }
  public void waitingForPlayer()
  {
    waitLabel.setText("Waiting for another player");
    game1Button.setDisable(true);
    game2Button.setDisable(true);
    game3Button.setDisable(true);
  }

  public void run()
  {
    vbox = new VBox(HEIGHT/10);
    vbox.setAlignment(Pos.CENTER);
    
    scene = new Scene(vbox, WIDTH, HEIGHT);
    
    waitLabel = new Label("");
    waitLabel.setPrefHeight(HEIGHT/10);
    waitLabel.setPrefWidth(WIDTH);
    waitLabel.setAlignment(Pos.CENTER);
    waitLabel.setFont(new Font(HEIGHT/15));
    vbox.getChildren().add(waitLabel);

    game1Button = new Button("Game1");
    game1Button.setPrefHeight(HEIGHT/6);
    game1Button.setPrefWidth(WIDTH/2);
    
    game1Button.setOnMouseEntered(new EventHandler<MouseEvent>() 
    {
      public void handle(MouseEvent me) {
        scene.setCursor(Cursor.HAND);
      }
    });
    game1Button.setOnMouseExited(new EventHandler<MouseEvent>() 
    {
      public void handle(MouseEvent me) 
      {
        scene.setCursor(Cursor.CROSSHAIR);
      }
    });
    game1Button.setOnAction(new EventHandler<ActionEvent>() 
    {
        public void handle(ActionEvent event)
        {
          app.startGame("GAME1");
        }
    });
    vbox.getChildren().add(game1Button);

    game2Button = new Button("Game2");
    game2Button.setPrefHeight(HEIGHT/6);
    game2Button.setPrefWidth(WIDTH/2);
    
    game2Button.setOnMouseEntered(new EventHandler<MouseEvent>() 
    {
      public void handle(MouseEvent me) {
          scene.setCursor(Cursor.HAND);
      }
    });
    game2Button.setOnMouseExited(new EventHandler<MouseEvent>() 
    {
      public void handle(MouseEvent me) 
      {
          scene.setCursor(Cursor.CROSSHAIR);
      }
    });
    game2Button.setOnAction(new EventHandler<ActionEvent>() 
    {
        public void handle(ActionEvent event)
        {
          app.startGame("GAME2");
        }
    });
    vbox.getChildren().add(game2Button);

    game3Button = new Button("Game3");
    game3Button.setPrefHeight(HEIGHT/6);
    game3Button.setPrefWidth(WIDTH/2);
    
    game3Button.setOnMouseEntered(new EventHandler<MouseEvent>() 
    {
      public void handle(MouseEvent me) {
          scene.setCursor(Cursor.HAND);
      }
    });
    game3Button.setOnMouseExited(new EventHandler<MouseEvent>() 
    {
      public void handle(MouseEvent me) 
      {
          scene.setCursor(Cursor.CROSSHAIR);
      }
    });
    game3Button.setOnAction(new EventHandler<ActionEvent>() 
    {
        public void handle(ActionEvent event)
        {
          app.startGame("GAME3");
        }
    });
    vbox.getChildren().add(game3Button);
  }
}
