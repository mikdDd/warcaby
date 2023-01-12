package org.example;

import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
import javafx.application.Platform;
import javafx.stage.Stage;


import java.io.*;
import java.net.UnknownHostException;

/**
 * JavaFX App
 */
public class App extends Application implements Runnable
{
  MenuScene menuScene;
  GameScene gameScene;

  private static float WIDTH = 600;
  private static int FIELDS = 10;
  private static float SIZE = WIDTH/FIELDS;
  private static float HEIGHT = SIZE * (FIELDS+1);
  private static int PAWNS = 10;

  Stage stage;
  Bridge bridge;

  BoardFX board;
  App app = this;

  private final static int PLAYER1 = 1;
  private final static int PLAYER2 = 2;

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
    stage.setResizable(false);
  
    SceneParent.setSize(WIDTH, HEIGHT);
    menuScene = new MenuScene(this);

    menuScene.join();

    stage.setScene(menuScene.getScene());
    stage.show();
  }

  public void startGame(String game)
  {
    try 
    {
      bridge = new Bridge();
      bridge.send(game);
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
    menuScene.waitingForPlayer();

    new Thread(new Runnable() 
    {
      public void run()
      {
        player = Integer.parseInt(bridge.receive());
        FIELDS = Integer.parseInt(bridge.receive());
        SIZE = WIDTH/FIELDS;
        HEIGHT = SIZE * (FIELDS+1);
        PAWNS = Integer.parseInt(bridge.receive());
        
        Platform.runLater(new Runnable() 
        {
          public void run()
          {
            startGame2();
          }
        });
      }
    }).start();
    }
  public void startGame2()
  {
    SceneParent.setSize(WIDTH, HEIGHT, SIZE, FIELDS);
    gameScene = new GameScene();
    PawnFX.setSize(SIZE);
    TileFX.setSize(SIZE);
    gameScene.join();

    board = new BoardFX(FIELDS, PAWNS, player, gameScene.getPane(), bridge);
    
    board.setPosition(bridge.receive());
    board.addEvents(gameScene.getScene());

    stage.setScene(gameScene.getScene());
    stage.show();
    startThread();
  }
  public void startThread()
  {
    Thread gTh = new Thread(this);
    gTh.start();
  }
  public void run() 
  {
    while(actualPlayer > 0)
    {
      if (actualPlayer == PLAYER1) 
      {
        if (player == PLAYER1)
        {
          gameScene.setLabel("Your turn");
          
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
          gameScene.setLabel("Opponent's turn");
          board.setPosition(bridge.receive());
          actualPlayer = Integer.parseInt(bridge.receive());
        }
      }
      else
      {
        if (player == PLAYER2)
        {
          gameScene.setLabel("Your turn");
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
          gameScene.setLabel("Opponent's turn");
          board.setPosition(bridge.receive());
          actualPlayer = Integer.parseInt(bridge.receive());
        }
      }
    }

      if (actualPlayer == 0)
      {
        gameScene.setLabel("DRAW!");
      }
      else if (actualPlayer == -1)
      {
        gameScene.setLabel("WHITE WON!");
      } 
      else if (actualPlayer == -2)
      {
        gameScene.setLabel("BLACK WON!");
      }
  }

  public static void main(String[] args) 
  {
    launch(args);
  }
}

