package com.example;

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


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
    //TODO: wysylanie info
    //TODO: synchronizacja

    
    private static int WIDTH = 800;
    private static int HEIGHT = 600;
    private static int FIELDS = 10;
    private static int PAWNS = 10;
    private static int SIZE = Math.min(WIDTH, HEIGHT)/FIELDS;

    Stage stage;
    //game
    GridPane gridPane = new GridPane();
    Scene gameScene = new Scene(gridPane, WIDTH, HEIGHT);
    Button tourButton;
    Label tourLabel;    
    Board board;
   
    //menu
    VBox vbox;
    Scene menuScene;
    Button game1Button;


    Bridge bridge;
    


    public final static int PLAYER1 = 1;
    public final static int PLAYER2 = 2;

    public final static int ACTIVE = 0;
    public final static int NONACTIVE = 1;
    private int actualPlayer = PLAYER1;
    private int player = 1;
    private static int showing = ACTIVE;


    /* (non-Javadoc)
     * @see javafx.application.Application#start(javafx.stage.Stage)
     */
    @Override
    public void start(Stage stage)
    {
      this.stage=stage;
      stage.setTitle("Client");

      buildMenuScene();
      
      stage.setScene(menuScene);
      stage.show();
    }
    public void buildMenuScene()
    {
      vbox = new VBox();
      menuScene = new Scene(vbox, WIDTH, HEIGHT);

      game1Button = new Button("Game1");
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
            buildGameScene();
            stage.show();
            startThread();
          }
      });
      
      vbox.getChildren().add(game1Button);
    }

    public void buildGameScene()
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
      try 
      {
        player = bridge.receiveInitFromServer();
      } 
      catch (IOException e) 
      {
        System.out.println("Read failed");
        System.exit(1);
      }

      Pawn.setSize(SIZE);
      Tile.setSize(SIZE);
      board = new Board(FIELDS, PAWNS);
      board.addToScene(gridPane);
      board.addEvents(player, gameScene);

      gameScene.setCursor(Cursor.CROSSHAIR);

      input = new TextField();
      output = new Label("OUTPUT");
      board = new Board(FIELDS, PAWNS);
      board.setDefaultPosition();
      board.addEvents(player, gameScene);
      board.addToScene(gridPane);
      
      tourLabel = new Label("");
      tourLabel.setFont(new Font(SIZE/2));
      tourLabel.setPrefWidth(WIDTH-HEIGHT);
      tourLabel.setAlignment(Pos.CENTER);
      gridPane.add(tourLabel,10,1);

      tourButton = new Button("END TOUR");
      tourButton.setMinWidth((WIDTH-HEIGHT)/1.5);
      GridPane.setHalignment(tourButton, HPos.CENTER);

      tourButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
        public void handle(MouseEvent me) {
            gameScene.setCursor(Cursor.HAND);
        }
      });
      tourButton.setOnMouseExited(new EventHandler<MouseEvent>() {
        public void handle(MouseEvent me) {
            gameScene.setCursor(Cursor.CROSSHAIR);
        }
      });
      tourButton.setOnAction(new EventHandler<ActionEvent>() {

          public void handle(ActionEvent event)
          {
            bridge.send("Button clicked ");
          }
      });


      gridPane.add(tourButton,10,2);
      gridPane.add(output,10,5);
      gridPane.add(input,10,6);

      stage.setScene(gameScene);
    }

    
    private void send(String text)
    {
      bridge.send(text);
      showing = ACTIVE;
      actualPlayer = player;
    }

    @Override
    public void run() 
    {
      if (player==PLAYER1) 
      {
          f1();
      }
      else
      {
          f2();
      }
    }

    /// Metoda uruchamiana w run dla PLAYER1
    void f1()
    {
      while(true) 
      {
        synchronized (this) 
        {
          if (actualPlayer== PLAYER1) 
          {
            try 
            {
                wait(10);
            } catch (InterruptedException e) 
            {
            }
          }
          if (showing == ACTIVE)
          {
            try 
            {
              // Odbieranie z serwera
              String message = bridge.receive();
              input.setText(message);
              showing = NONACTIVE;
            }
            catch (IOException e) 
            {
              System.out.println("Read failed"); 
              System.exit(1);
            }
          }
          notifyAll();
        }
      }
    }

  /// Metoda uruchamiana w run dla PLAYER2
  void f2()
  {
    while(true) 
    {
      synchronized (this) 
      {
        if (actualPlayer== PLAYER2) 
        {
          try 
          {
            wait(10);
          } 
          catch (InterruptedException e) 
          {
          }
        }
        if (showing == ACTIVE)
        {
          try 
          {
            // Odbieranie z serwera
            bridge.receive();
            showing = NONACTIVE;
          }
          catch (IOException e) 
          {
            System.out.println("Read failed"); 
            System.exit(1);
          }
        }
        notifyAll();
      }
    }
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
