import java.util.ArrayList;

/**
 * The AI's basic logic at choosing cards has been implemented here
 * @author Roham Hayedi
 */
public class AIPlayer extends Player {
    /**
     * The constructor for this class
     */
    public AIPlayer(){
        super();
    }

    /**
     * @param player who has to choose a card from his hand
     * @return The number of the chosen card, or 0 when there are no playable ones
     */
    public static int cardPlay(AIPlayer player){
        Card chosenCard = null;
        if (player.getValidMove().size() != 0) {
            chosenCard = choosePreference(player);
            ArrayList<Card> hand = player.getHand();
            for (int i = 0, handSize = hand.size(); i < handSize; i++) {
                Card card = hand.get(i);
                if (chosenCard.equals(card))
                    return i + 1;
            }
        }
        return 0;
    }
//    The basic logic of the AI, to drop the cards with highest score when loss is imminent
    private static Card choosePreference(AIPlayer player){
        Round round = Round.getNewRound();
        for (Player currentPlayer : round.getPlayers()){
            if (!currentPlayer.equals(player)){
                if (currentPlayer.getHand().size() < 3){
                    if (player.getValidMove().contains(new WildCard4Plus()) || player.getValidMove().contains(new WildCard())){
                        ArrayList<Card> possibleMove = player.getValidMove();
                        for (int i = 0; i < possibleMove.size(); i++) {
                            if (possibleMove.get(i).equals(new WildCard()) || possibleMove.get(i).equals(new WildCard4Plus()))
                                return player.getValidMove().get(i);
                        }
                    }
                    for (Card card : player.getValidMove()){
                        if (card instanceof SpecialCard){
                            return card;
                        }
                    }
                }else{
                    NumberedCard maxNumber = new NumberedCard(0, 'R');
                    boolean cardFound = false;
                    for (Card card : player.getValidMove()) {
                        if (card instanceof NumberedCard && ((NumberedCard) card).getNumber() > maxNumber.getNumber()) {
                            maxNumber = (NumberedCard) card;
                            cardFound = true;
                        }
                    }
                    if (cardFound)
                        return maxNumber;
                }
            }
        }
        return player.getValidMove().get(Randomizer.getRandom().nextInt(player.getValidMove().size()));
    }

    /**
     * The logic of choosing a color when a wildCard is played
     * @param player who has to choose
     * @return the number of the color, which WildCard class uses
     */
    public static int nextColorPreference(Player player){
        int redCards = 0;
        int greenCards = 0;
        int blueCards = 0;
        int yellowCards = 0;
        for (Card card : player.getHand()){
            if (card instanceof ColoredCard) {
                if (((ColoredCard) card).getColor() == 'R')
                    redCards++;
                if (((ColoredCard) card).getColor() == 'G')
                    greenCards++;
                if (((ColoredCard) card).getColor() == 'B')
                    blueCards++;
                if (((ColoredCard) card).getColor() == 'Y')
                    yellowCards++;
            }
        }
        int highestCount = Math.max(Math.max(redCards, greenCards),Math.max(blueCards, yellowCards));
        if (highestCount == redCards)
            return 1;
        else if (highestCount == greenCards)
            return 2;
        else if (highestCount == blueCards)
            return 3;
        else
            return 4;
    }
}
