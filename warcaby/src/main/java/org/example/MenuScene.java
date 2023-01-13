package org.example;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.net.URISyntaxException;
import java.util.Objects;


/**
 * Klasa obslugujaca scene menu.
 */
public class MenuScene extends SceneParent implements Runnable
{
  VBox vbox;
  Label waitLabel;    
  Button game1Button;
  Button game2Button;
  Button game3Button;
  App app;

  /**
   * Konstruktor.
   * @param a aplikacja
   */
  MenuScene(App a)
  {
    app = a;

    th = new Thread(this);
    th.start();
  }

  /**
   * Ustawienie tekstu oczekiwania.
   */
  public void waitingForPlayer()
  {
    waitLabel.setText("Waiting for another player");
    waitLabel.setFont(Font.font("Garamond",37));
    game1Button.setDisable(true);
    game2Button.setDisable(true);
    game3Button.setDisable(true);
  }

  /**
   * Tworzenie sceny menu.
   */
  public void run()
  {
    vbox = new VBox(HEIGHT/40);
    vbox.setAlignment(Pos.CENTER);
    vbox.setBackground(new Background(new BackgroundFill(Color.SIENNA, CornerRadii.EMPTY, Insets.EMPTY)));
    scene = new Scene(vbox, WIDTH, HEIGHT);
    waitLabel = new Label("");
    waitLabel.setPrefHeight(HEIGHT/11);
    waitLabel.setPrefWidth(WIDTH);
    waitLabel.setAlignment(Pos.CENTER);
    waitLabel.setFont(new Font(HEIGHT/15));

    Image image;
    try {
      image = new Image(Objects.requireNonNull(getClass().getResource("logoszachy2.png")).toURI().toString());
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
    ImageView imageView = new ImageView(image);
    imageView.autosize();
    imageView.setPreserveRatio(true);
    imageView.setFitHeight(200);
    vbox.getChildren().add(imageView);
    vbox.getChildren().add(waitLabel);
    game1Button = new Button("Polish Checkers");
    game1Button.setPrefHeight(HEIGHT/7);
    game1Button.setPrefWidth(WIDTH/2);

    game1Button.setStyle("-fx-background-color: beige");
    game1Button.setFont(Font.font("Garamond", 30));
    
    game1Button.setOnMouseEntered(me -> scene.setCursor(Cursor.HAND));
    game1Button.setOnMouseExited(me -> scene.setCursor(Cursor.CROSSHAIR));
    game1Button.setOnAction(event -> app.startGame("GAME1"));
    vbox.getChildren().add(game1Button);

    game2Button = new Button("English Checkers");
    game2Button.setPrefHeight(HEIGHT/7);
    game2Button.setPrefWidth(WIDTH/2);
    game2Button.setStyle("-fx-background-color: beige");
    game2Button.setFont(Font.font("Garamond",30));
    game2Button.setOnMouseEntered(me -> scene.setCursor(Cursor.HAND));
    game2Button.setOnMouseExited(me -> scene.setCursor(Cursor.CROSSHAIR));
    game2Button.setOnAction(event -> app.startGame("GAME2"));
    vbox.getChildren().add(game2Button);

    game3Button = new Button("Thai Checkers");
    game3Button.setPrefHeight(HEIGHT/7);
    game3Button.setPrefWidth(WIDTH/2);
    game3Button.setStyle("-fx-background-color: beige");
    game3Button.setFont(Font.font("Garamond",30));
    
    game3Button.setOnMouseEntered(me -> scene.setCursor(Cursor.HAND));
    game3Button.setOnMouseExited(me -> scene.setCursor(Cursor.CROSSHAIR));
    game3Button.setOnAction(event -> app.startGame("GAME3"));
    vbox.getChildren().add(game3Button);
  }
}
