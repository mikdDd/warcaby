package org.example;
//wzorzec factory
public class GameFactory implements GameFactoryInterface {
    @Override
    public Game createGame(String gameType) {
        if(gameType == null){
            return null;
        }
        if(gameType.equalsIgnoreCase("POLISH")){
            return new PolishCheckers();

        } else if(gameType.equalsIgnoreCase("ENGLISH")){
            return new EnglishCheckers();

        } else if(gameType.equalsIgnoreCase("THAI")){
            return new ThaiCheckers();
        }

        return null;
    }
}
