import java.util.Random;
import java.util.Scanner;

/**
 * The model of wildcard, when played, it lets the players choose the next color
 * @author Roham Hayedi
 */
public class WildCard extends Card {

//    the next color
    private char nextColor;

    /**
     * The constructor of the class
     */
    public WildCard(){ };

    /**
     * @param card which {@code this} will be checked with
     * @param player who is going to play a card
     * @return {@code true}, meaning it's always playable
     */
    @Override
    public boolean rulesCheck(Card card, Player player) {
        return true;
    }

    /**
     * @return the chosen color
     */
    public char getNextColor() {
        return nextColor;
    }

    /**
     * @return the cards name
     */
    @Override
    public String getCardName() {
        return "WildCard";
    }

    /**
     * The effect of wildcard,
     * which lets the player choose a color
     * @param player who needs to choose a color
     */
    @Override
    public void effect(Player player) {
        this.effectMade = true;
        Random rand = Randomizer.getRandom();
        Scanner reader = new Scanner(System.in);
        int colorNumber;
        if (player instanceof HumanPlayer) {
            do {
                UI.wildCardNote();
                colorNumber = reader.nextInt();
            } while (colorNumber > 4 || colorNumber < 0);
        }
        else{
            colorNumber = AIPlayer.nextColorPreference(player);
        }
        System.out.println(colorNumber);
        switch (colorNumber){
            case 1:
                this.nextColor = 'R';
                break;
            case 2:
                this.nextColor = 'G';
                break;
            case 3:
                this.nextColor = 'B';
                break;
            case 4:
                this.nextColor = 'Y';
                break;
        }
    }

}
