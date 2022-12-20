package org.example;

public class GameControllerFactory implements GameFactory{
    @Override
    public GameController createGame(String gameType) {
        if(gameType == null){
            return null;
        }
        if(gameType.equalsIgnoreCase("POLISH")){
            return new PolishCheckers();

        } else if(gameType.equalsIgnoreCase("TYPE2")){
            return null;

        } else if(gameType.equalsIgnoreCase("TYPE3")){
            return null;
        }

        return null;
    }
}
