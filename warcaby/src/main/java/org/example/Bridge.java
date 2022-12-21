package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.RecursiveAction;

import javafx.application.Platform;

public class Bridge 
{
  
  Socket socket ;
  PrintWriter out ;
  BufferedReader in;

  public Bridge() throws UnknownHostException, IOException
  {
    socket = new Socket("localhost", 4444);
    // Inicjalizacja wysylania do serwera
    out = new PrintWriter(socket.getOutputStream(), true);
    // Inicjalizacja odbierania z serwera
    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
  }

  public void send(String text)
  {
    // Wysylanie do serwera
    // out.println(input.getText());
    out.println(text);

      
      
  }

  public String receive() throws IOException
  {
    String str = in.readLine();
    return str;
  }

    
  public int receiveInitFromServer() throws IOException
  {
    int input = Integer.parseInt(in.readLine());
    if (input == 1) 
      {
        //tourLabel.setText("My Turn");
        System.out.println("Player 1");
      } 
      else 
      {
        // tourLabel.setText("Opposite turn");
        // tourButton.setDisable(true);
        System.out.println("Player 2");
      }
    
    return input;
  }

}
