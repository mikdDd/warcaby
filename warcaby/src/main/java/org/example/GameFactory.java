package org.example;

/**Klasa konkretna factory obiektow klasy Game
 *
 */
public class GameFactory implements GameFactoryInterface {
    @Override
    public Game createGame(final String gameType) {
        if(gameType == null){
            return null;
        }
        if("POLISH".equalsIgnoreCase(gameType)){
            return new PolishCheckers();

        } else if("ENGLISH".equalsIgnoreCase(gameType)){
            return new EnglishCheckers();

        } else if("THAI".equalsIgnoreCase(gameType)){
            return new ThaiCheckers();
        }

        return null;
    }
}
