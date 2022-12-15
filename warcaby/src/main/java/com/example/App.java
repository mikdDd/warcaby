package com.example;

import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
     Label msg;
    Label output;
    TextField input;
     Button send;
    public Text t;
    //TODO: posprzatac, zrobic klasy
    //TODO: wysylanie info
    //TODO: synchronizacja

    private static int WIDTH = 800;
    private static int HEIGHT = 600;
    private static int FIELDS = 10;
    private static int SIZE = Math.min(WIDTH, HEIGHT)/FIELDS;


     Socket socket ;
     PrintWriter out ;
     BufferedReader in;


     private int player;

    public final static int PLAYER1 = 1;
    public final static int PLAYER2 = 2;

    public final static int ACTIVE = 0;
    public final static int NONACTIVE = 1;
    private  static int actualPlayer = PLAYER1;

    private static int showing = ACTIVE;


    /* (non-Javadoc)
     * @see javafx.application.Application#start(javafx.stage.Stage)
     */
    @Override
    public void start(Stage stage)
    {


        stage.setTitle("Client");

        GridPane gridPane = new GridPane();
        Label l = new Label();
        Rectangle[][] rect = new Rectangle[FIELDS][FIELDS];

        msg = new Label();
        input = new TextField();
        output = new Label("OUTPUT");

        //get board from server
        char[][] board = new char[FIELDS][FIELDS];

        for (int i=0; i<FIELDS; i++)
        {
            for (int j=0; j<FIELDS; j++)
            {
                board[i][j]='0';
            }
        }
        board[1][0]='B';
        board[3][0]='B';
        board[5][0]='B';
        board[7][0]='B';
        board[9][0]='B';
        board[0][1]='B';
        board[2][1]='B';
        board[4][1]='B';
        board[6][1]='B';
        board[8][1]='B';

        board[1][8]='W';
        board[3][8]='W';
        board[5][8]='W';
        board[7][8]='W';
        board[9][8]='W';
        board[0][9]='W';
        board[2][9]='W';
        board[4][9]='W';
        board[6][9]='W';
        board[8][9]='W';

        //draw board
        for (int i=0; i<FIELDS; i++)
        {
            for (int j=0; j<FIELDS; j++)
            {
                rect[i][j] = new Rectangle();
                rect[i][j].setWidth(SIZE);
                rect[i][j].setHeight(SIZE);
                if((i+j) % 2 == 1 )
                {
                    rect[i][j].setFill(Color.BROWN);
                }
                else
                {
                    rect[i][j].setFill(Color.YELLOW);
                }
                gridPane.add(rect[i][j], j, i);
            }
        }

        //draw pawns
        for (int i=0; i<FIELDS; i++)
        {
            for (int j=0; j<FIELDS; j++)
            {
                if(board[i][j]!='0')
                {
                    Circle cir = new Circle();
                    cir.setRadius(SIZE/2);
                    if(board[i][j]=='W')
                    {
                        cir.setFill(Color.WHITE);
                    }
                    if(board[i][j]=='B')
                    {
                        cir.setFill(Color.BLACK);
                    }
                    gridPane.add(cir, i, j);
                }
            }
        }

        //temp
        t = new Text(10, 50, "This is a test");
        t.setFont(new Font(20));



        send = new Button("SEND");
        send.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event)
            {
                send();
            }
        });

        gridPane.add(send,20,20);
        gridPane.add(msg,20,21);
        gridPane.add(output,20,22);
        gridPane.add(input,20,23);
        gridPane.add(t,10,0);
        stage.setScene(new Scene(gridPane, WIDTH, HEIGHT));
        stage.show();

        this.listenSocket();
        this.receiveInitFromServer();
        this.startThread();
    }


    private void startThread() {




                Thread gTh = new Thread(this);
                gTh.start();


    }

    @Override
    public void run() {


        if (player==PLAYER1) {
            f1();
        }
        else{
            f2();
        }


        // Mozna zrobic w jednej metodzie. Zostawiam
        // dla potrzeb prezentacji
        // f(player);
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

    private void send()
    {
        // Wysylanie do serwera
        out.println(input.getText());
        msg.setText("OppositeTurn");
        send.setDisable(true);
         input.setText("");
         input.requestFocus();
         showing = ACTIVE;
        actualPlayer = player;
    }
    private void receive(){
        try {
            // Odbieranie z serwera
            String str = in.readLine();
            //System.out.println(str);
            output.setText(str);
            msg.setText("My turn");
            send.setDisable(false);
            input.setText("");
            input.requestFocus();

        }
        catch (IOException e) {
            System.out.println("Read failed"); System.exit(1);}

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
    private void receiveInitFromServer() {
        try {
            player = Integer.parseInt(in.readLine());
            if (player== PLAYER1) {
                msg.setText("My Turn");
            } else {
                msg.setText("Opposite turn");
                send.setDisable(true);
            }
        } catch (IOException e) {
            System.out.println("Read failed");
            System.exit(1);
        }
    }

//     // Jedna metoda dla kazdego Playera
//     void f(int iPlayer){
//         while(true) {
//             synchronized (this) {
//                 if (actualPlayer== iPlayer) {
//                     try {
//                         wait(10);
//                     } catch (InterruptedException e) {
//                     }
//                 }
//                 if (showing ==ACTIVE){
//                     receive();
//                     showing =NONACTIVE;
//                 }
//                 notifyAll();
//             }
//         }
//     }




    public static void main(String[] args) {


        //listenSocket();
        //receiveInitFromServer();
        //startThread();
        launch(args);
    }

}
