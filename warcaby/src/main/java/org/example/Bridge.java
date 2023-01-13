package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Klasa wysyłająca i odbierająca
 * informacje z serwera.
 */
public class Bridge 
{
  Socket socket ;
  PrintWriter out ;
  BufferedReader in;

  /**
   * Konstruktor
   * @throws UnknownHostException
   * @throws IOException
   */
  public Bridge() throws UnknownHostException, IOException
  {
    socket = new Socket("localhost", 4444);
    // Inicjalizacja wysylania do serwera
    out = new PrintWriter(socket.getOutputStream(), true);
    // Inicjalizacja odbierania z serwera
    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
  }

  /**
   * Wysyłanie informacji na serwer
   * @param text informacja
   */
  public void send(String text)
  {
    out.println(text);
  }

  /**
   * Odbieranie informacji na serwer
   * @return informacja
   */
  public String receive()
  {
    try
    {
      return in.readLine();
    }
    catch(Exception e)
    {
      System.out.print("error: " + e);
    }
    return "";
  }

}
