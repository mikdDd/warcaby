package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

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

  public String receive()
  {
    try
    {
      return in.readLine();
    }
    catch(Exception e)
    {
      System.out.print("ereor: " + e);
    }
    return "";
  }

}
