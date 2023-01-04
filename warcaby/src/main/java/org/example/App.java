package org.example;

import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.css.Size;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.channels.FileLock;

/**
 * JavaFX App
 */
public class App extends Application implements Runnable
{
  //temp
  Label output;
  TextField input;
  
  //TODO: posprzątać
  //TODO: zwiecha po wyborze
  
  private static float WIDTH = 600;
  private static int FIELDS = 10;
  private static float SIZE = WIDTH/FIELDS;
  private static float HEIGHT = SIZE * (FIELDS+1);
  private static int PAWNS = 10;

  Stage stage;
  //game
  GridPane gridPane = new GridPane();
  Scene gameScene;
  Label tourLabel;    
  BoardFX board;
  
  //menu
  VBox vbox;
  Scene menuScene;
  Button game1Button;
  Button game2Button;
  Button game3Button;
  
  
  Bridge bridge;
  
  public final static int PLAYER1 = 1;
  public final static int PLAYER2 = 2;

  public final static int ACTIVE = 0;
  public final static int NONACTIVE = 1;
  private int actualPlayer = PLAYER1;
  private int player = 1;


  /* (non-Javadoc)
    * @see javafx.application.Application#start(javafx.stage.Stage)
    */
  @Override
  public void start(Stage stage)
  {
    this.stage=stage;
    stage.setTitle("Client");

    buildMenuScene();
    
    
  }
  
  public void buildMenuScene()
  {
    
    vbox = new VBox(HEIGHT/10);
    vbox.setAlignment(Pos.CENTER);
    
    menuScene = new Scene(vbox, WIDTH, HEIGHT);
    
    game1Button = new Button("Game1");
    game1Button.setPrefHeight(HEIGHT/6);
    game1Button.setPrefWidth(WIDTH/2);
    
    game1Button.setOnMouseEntered(new EventHandler<MouseEvent>() 
    {
      public void handle(MouseEvent me) {
          menuScene.setCursor(Cursor.HAND);
      }
    });
    game1Button.setOnMouseExited(new EventHandler<MouseEvent>() 
    {
      public void handle(MouseEvent me) 
      {
          menuScene.setCursor(Cursor.CROSSHAIR);
      }
    });
    game1Button.setOnAction(new EventHandler<ActionEvent>() 
    {
        public void handle(ActionEvent event)
        {
          startGame("GAME1");
        }
    });
    vbox.getChildren().add(game1Button);

    game2Button = new Button("Game2");
    game2Button.setPrefHeight(HEIGHT/6);
    game2Button.setPrefWidth(WIDTH/2);
    
    game2Button.setOnMouseEntered(new EventHandler<MouseEvent>() 
    {
      public void handle(MouseEvent me) {
          menuScene.setCursor(Cursor.HAND);
      }
    });
    game2Button.setOnMouseExited(new EventHandler<MouseEvent>() 
    {
      public void handle(MouseEvent me) 
      {
          menuScene.setCursor(Cursor.CROSSHAIR);
      }
    });
    game2Button.setOnAction(new EventHandler<ActionEvent>() 
    {
        public void handle(ActionEvent event)
        {
          startGame("GAME2");
        }
    });
    vbox.getChildren().add(game2Button);

    game3Button = new Button("Game3");
    game3Button.setPrefHeight(HEIGHT/6);
    game3Button.setPrefWidth(WIDTH/2);
    
    game3Button.setOnMouseEntered(new EventHandler<MouseEvent>() 
    {
      public void handle(MouseEvent me) {
          menuScene.setCursor(Cursor.HAND);
      }
    });
    game3Button.setOnMouseExited(new EventHandler<MouseEvent>() 
    {
      public void handle(MouseEvent me) 
      {
          menuScene.setCursor(Cursor.CROSSHAIR);
      }
    });
    game3Button.setOnAction(new EventHandler<ActionEvent>() 
    {
        public void handle(ActionEvent event)
        {
          startGame("GAME3");
        }
    });
    vbox.getChildren().add(game3Button);

    stage.setScene(menuScene);
    stage.show();
  }

  public void startGame(String game)
  {
    try 
    {
      bridge = new Bridge();
    } 
    catch (UnknownHostException e) 
    {
      System.out.println("Unknown host: localhost");
      System.exit(1);
    } 
    catch (IOException e) 
    {
      System.out.println("No I/O");
      System.exit(1);
    }
    bridge.send(game);
    buildGameScene();
  }
  public void buildGameScene()
  {  
    player = Integer.parseInt(bridge.receive());
    FIELDS = Integer.parseInt(bridge.receive());
    SIZE = WIDTH/FIELDS;
    HEIGHT = SIZE * (FIELDS+1);

    System.out.println(WIDTH);
    System.out.println(HEIGHT);
    System.out.println(SIZE);

    PAWNS = Integer.parseInt(bridge.receive());
    
    gameScene = new Scene(gridPane, WIDTH, HEIGHT);
    
    PawnFX.setSize(SIZE);
    TileFX.setSize(SIZE);
    
    board = new BoardFX(FIELDS, PAWNS, player, gridPane, bridge);
    board.setPosition(bridge.receive());
    board.addEvents(gameScene);
    
    gameScene.setCursor(Cursor.CROSSHAIR);

    tourLabel = new Label("");
    tourLabel.setFont(new Font(SIZE/1.5));
    tourLabel.setPrefWidth(WIDTH);
    tourLabel.setPrefHeight(SIZE);
    tourLabel.setAlignment(Pos.CENTER);
    gridPane.add(tourLabel, 0, 0, FIELDS, 1);

    stage.setScene(gameScene);
    stage.show();
    startThread();
  }
  
  @Override
  public void run() 
  {
    while(actualPlayer > 0)
    {
      if (actualPlayer == PLAYER1) 
      {
        if (player == PLAYER1)
        {
          Platform.runLater(new Runnable() 
          {
            public void run()
            {
              tourLabel.setText("Your turn");
            }  
          });
          
          board.enableWhite();
          String posibleMoves = bridge.receive();
          board.disableWhite();
          board.enableTiles(posibleMoves);
          board.setPosition(bridge.receive());
          board.disableTiles(posibleMoves);
          actualPlayer = Integer.parseInt(bridge.receive());
        }
        else
        {
          Platform.runLater(new Runnable() 
          {
            public void run()
            {
              tourLabel.setText("Enemy's turn");
            }  
          });
          board.setPosition(bridge.receive());
          actualPlayer = Integer.parseInt(bridge.receive());
        }
      }
      else
      {
        if (player == PLAYER2)
        {
          Platform.runLater(new Runnable() 
          {
            public void run()
            {
              tourLabel.setText("Your turn");
            }  
          });
          
          board.enableBlack();
          String posibleMoves = bridge.receive();
          board.disableBlack();
          board.enableTiles(posibleMoves);
          board.setPosition(bridge.receive());
          board.disableTiles(posibleMoves);
          actualPlayer = Integer.parseInt(bridge.receive());
        }
        else
        {
          Platform.runLater(new Runnable() 
          {
            public void run()
            {
              tourLabel.setText("Enemy's turn");
            }  
          });
          board.setPosition(bridge.receive());
          actualPlayer = Integer.parseInt(bridge.receive());
        }
      }
    }
    System.out.println("END");
    Platform.runLater(new Runnable() 
    {
      public void run()
      {
        if (actualPlayer == 0)
          tourLabel.setText("DRAW!");
        else if (actualPlayer == -1)
          tourLabel.setText("WHITE WON!");
        else if (actualPlayer == -2)
          tourLabel.setText("BLACK WON!");

      }  
    });
  }

  private void startThread() 
  {
    Thread gTh = new Thread(this);
    gTh.start();
  }
  
  public static void main(String[] args) 
  {
    launch(args);
  }
}

