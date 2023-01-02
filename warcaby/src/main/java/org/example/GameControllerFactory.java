package org.example;

public class GameControllerFactory implements GameFactory{
    @Override
    public GameController createGame(String gameType) {
        if(gameType == null){
            return null;
        }
        if(gameType.equalsIgnoreCase("GAME1")){
            return new PolishCheckers();

        } else if(gameType.equalsIgnoreCase("GAME2")){
            return new EnglishCheckers();

        } else if(gameType.equalsIgnoreCase("GAME3")){
            return new ThaiCheckers();
        }

        return null;
    }
}
