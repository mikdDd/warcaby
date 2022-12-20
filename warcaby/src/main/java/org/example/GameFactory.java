package org.example;

public interface GameFactory {
    GameController createGame(String gameType);
}
