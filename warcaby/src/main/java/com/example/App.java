package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static int WIDTH = 800;
    private static int HEIGHT = 600;
    private static int FIELDS = 10;
    private static int SIZE = Math.min(WIDTH, HEIGHT)/FIELDS;
    
    private static int PLAYER = 1; //white
    /* (non-Javadoc)
     * @see javafx.application.Application#start(javafx.stage.Stage)
     */
    @Override
    public void start(Stage stage)
    {
      stage.setTitle("Client");

      GridPane gridPane = new GridPane();

      Rectangle[][] rect = new Rectangle[FIELDS][FIELDS];


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

      stage.setScene(new Scene(gridPane, WIDTH, HEIGHT));
      stage.show();

    }


    public static void main(String[] args) {
        launch();
    }

}