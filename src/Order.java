import java.util.ArrayList;
import java.util.Collections;

/**
 * The class used to provide control over the order of players
 * @author Roham Hayedi
 */
public class Order {
//    The number with which player list's index will be incremented with
    private static int gameWise = 1;

    /**
     * The constructor of the class
     */
    public Order(){
    }

    /**
     * When called, game wise will change
     */
    public static void change() {
        if (gameWise == 1)
            gameWise = -1;
        else if (gameWise == -1)
            gameWise = 1;
    }

    /**
     * @return the integer representing the game wise
     */
    public static int getGameWise() {
        return gameWise;
    }

    /**
     * The method used to arrange the player's list in a random manner and also to name the players after arrangement
     * @param players list to be arranged
     */
    public static void chooseStarter(ArrayList<Player> players){
        Collections.shuffle(players, Randomizer.getRandom());
        for (int i = 0; i < players.size(); i++) {
            players.get(i).setPlayerName("Player " + (i + 1));
        }
    }
}
