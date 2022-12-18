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

/**
 * JavaFX App
 */
public class App extends Application implements Runnable
{
    //temp
    Label output;
    TextField input;
    
    //TODO: wybor startowy 
    //TODO: wysylanie info
    //TODO: synchronizacja

    GridPane gridPane = new GridPane();
    Scene scene = new Scene(gridPane, WIDTH, HEIGHT);

    Button tourButton;
    Label tourLabel;

    

    private static int WIDTH = 800;
    private static int HEIGHT = 600;
    private static int FIELDS = 10;
    private static int PAWNS = 10;
    private static int SIZE = Math.min(WIDTH, HEIGHT)/FIELDS;

    Board board;
   
    Socket socket ;
     PrintWriter out ;
     BufferedReader in;


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
      
      
        Pawn.setSize(SIZE);
        Tile.setSize(SIZE);
        stage.setTitle("Client");
    
        scene.setCursor(Cursor.CROSSHAIR);

        input = new TextField();
        output = new Label("OUTPUT");
        board = new Board(FIELDS, PAWNS);
        board.setDefaultPosition();
        board.addEvents(player, scene);
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
              scene.setCursor(Cursor.HAND);
          }
        });
        tourButton.setOnMouseExited(new EventHandler<MouseEvent>() {
          public void handle(MouseEvent me) {
              scene.setCursor(Cursor.CROSSHAIR);
          }
        });
        tourButton.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event)
            {
                send("button pressed");
            }
        });

        // board.addToScene(gridPane);

        

        gridPane.add(tourButton,10,2);
        gridPane.add(output,10,5);
        gridPane.add(input,10,6);

        

        stage.setScene(scene);
        stage.show();

        listenSocket();
      receiveInitFromServer();
      
        startThread();

      
    }

    private void startThread() 
    {
      Thread gTh = new Thread(this);
      gTh.start();
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
    void f1(){
        while(true) {
            synchronized (this) {
                if (actualPlayer== PLAYER1) {
                    try {
                        wait(10);
                    } catch (InterruptedException e) {
                    }
                }
                if (showing ==ACTIVE){
                    receive();
                    showing =NONACTIVE;
                }
                notifyAll();
            }
        }
    }

    /// Metoda uruchamiana w run dla PLAYER2
    void f2(){
        while(true) {
            synchronized (this) {
                if (actualPlayer== PLAYER2) {
                    try {
                        wait(10);
                    } catch (InterruptedException e) {
                    }
                }
                if (showing ==ACTIVE){
                    receive();
                    showing =NONACTIVE;
                }
                notifyAll();
            }
        }
    }

    private void send(String text)
    {
        // Wysylanie do serwera
        // out.println(input.getText());
        out.println(text);

        Platform.runLater(() -> {

          tourLabel.setText("OppositeTurn");
          tourButton.setDisable(true);
          input.setText("");
          input.requestFocus();
          

        }
      );
        showing = ACTIVE;
        actualPlayer = player;
    }

    private void receive()
    {
      try 
      {
        // Odbieranie z serwera
        String str = in.readLine();
        Platform.runLater(() -> {
          output.setText(str);
          tourLabel.setText("My turn");
          tourButton.setDisable(false);
          input.setText("");
          input.requestFocus();

        });
      }
      catch (IOException e) 
      {
        System.out.println("Read failed"); 
        System.exit(1);
      }
    }

    public void listenSocket() {
        try {
            socket = new Socket("localhost", 4444);
            // Inicjalizacja wysylania do serwera
            out = new PrintWriter(socket.getOutputStream(), true);
            // Inicjalizacja odbierania z serwera
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (UnknownHostException e) {
            System.out.println("Unknown host: localhost");
            System.exit(1);
        } catch (IOException e) {
            System.out.println("No I/O");
            System.exit(1);
        }
    }
    
    private void receiveInitFromServer() 
    {
      try 
      {
        player = Integer.parseInt(in.readLine());
        if (player== PLAYER1) 
        {
          tourLabel.setText("My Turn");
        } 
        else 
        {
          tourLabel.setText("Opposite turn");
          tourButton.setDisable(true);
        }
      } 
      catch (IOException e) 
      {
        System.out.println("Read failed");
        System.exit(1);
      }
    }

    public static void main(String[] args) 
    {
      launch(args);
    }

}
