import java.util.*;

/**
 * The class which manages almost all the data and processes going on in the game of UNO
 * @author Roham Hayedi
 */
public class Round {

//    An ArrayList for players
    private final ArrayList<Player> players;
//    An ArrayList for all yet-to-be-played the cards
    private final ArrayList<Card> deck;
//    An ArrayList for all played cards
    private final ArrayList<Card> playedCards;
//    Scanner for input
    private final Scanner reader = new Scanner(System.in);
//    The random used for randomizing the choices and shuffles
    private final Random rand = Randomizer.getRandom();
//    The single instance of this class we are going to use
    private static Round newRound = null;
//    The constructor of this class
//    in which deck will be filled and shuffled
    private Round(){
        players = new ArrayList<>();
        deck = new ArrayList<>();
        playedCards = new ArrayList<>();
        initializeDeck();
        shuffleDeck();
    }

    /**
     * @return the single instance of the class
     */
    public static Round getNewRound() {
        if (newRound == null)
            newRound = new Round();
        return newRound;
    }

    /**
     * Used for AI to make decisions according to other players number of cards
     * @return players list
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Adds the given player to the player's list
     * @param player the player to be added
     */
    public void addPlayer(Player player){ players.add(player); }


//      Creates all 108 cards consisting of
//      4 of WildCards and 4 of WildCard4+ and
//      every color of : zero, the 18 NumberedCards beyond zero and every pair of SpecialCards
    private void initializeDeck(){
//        to make 4 of every special card;
        for (int i = 0; i < 4; i++) {
            char color = 'R';
            if (i == 1)
                color = 'G';
            else if (i == 2)
                color = 'B';
            else if (i == 3)
                color = 'Y';
            Card skipCard = new SkipCard(color);
            Card reverseCard = new ReverseCard(color);
            Card draw2Plus = new Draw2Plus(color);
            Card wildCard = new WildCard();
            Card wildCard4Plus = new WildCard4Plus();
            deck.add(skipCard);
            deck.add(skipCard);
            deck.add(reverseCard);
            deck.add(reverseCard);
            deck.add(draw2Plus);
            deck.add(draw2Plus);
            deck.add(wildCard);
            deck.add(wildCard4Plus);
        }
//        to make 4 of zero cards and 18 greater than 0 number cards
        for (int i = 1; i <= 19; i++) {
            for (int j = 0; j < 4; j++) {
                char color = 'R';
                if (j == 1)
                    color = 'G';
                else if (j == 2)
                    color = 'B';
                else if (j == 3)
                    color = 'Y';
                Card card = new NumberedCard(i % 10, color);
                deck.add(card);
            }
        }
    }

//      Shuffles the deck
    private void shuffleDeck(){
        Collections.shuffle(deck, rand);
    }

//     Deals 7 cards to every player and one at a time
    private void dealCards(){
        for (int i = 0, j = 0; i < players.size() * 7; i++, j++) {
            j %= players.size();
            Player playerToBeDealt = players.get(j);
            Card cardToDeal = deck.get(0);
            playerToBeDealt.addCard(cardToDeal);
            deck.remove(cardToDeal);
        }
    }

//     the last played card on board, which at beginning is the first card of the deck
    private Card getTopCard(){
            return playedCards.get(playedCards.size() - 1);
    }

    /**
     * The method where almost everything regarding the game happens
     * @throws InterruptedException
     */
    public void play() throws InterruptedException {

        boolean hasDrawn = false;
        dealCards();
        playedCards.add(deck.get(0));
        deck.remove(0);
        Order.chooseStarter(players);
        ArrayList<Card> toDraw = new ArrayList<>();

        for(int i = 0; !roundOver(); i += Order.getGameWise()) {

//            if hasDrawn, the index has to go a step back, to check whether the player can play the new card or not
            if (hasDrawn)
                i -= Order.getGameWise();
            int cardNumber = -1;
            checkDeck();
//            if in Anti-Clockwise index goes below zero, it is incremented by the number of players, to get the index of the last player
            if (i < 0)
                i += players.size();
            Player currentPlayer = players.get(i % players.size());
            currentPlayer.clearValidMove();
            UI.gameWiseDisplay();
            UI.cardDisplay(getTopCard());
            UI.boardDisplay(players, currentPlayer);
//            to make sure the top card at the beginning has its effect
            if (i == 0){
                getTopCard().effect(currentPlayer);
                Thread.sleep(1500);
                if (getTopCard() instanceof SkipCard)
                    i += Order.getGameWise();
                else if (getTopCard() instanceof WildCard4Plus) {
                    toDraw.add(getTopCard());
                }
                else if (getTopCard() instanceof Draw2Plus) {
                    toDraw.add(getTopCard());
                }
                if (drawPlus(toDraw, currentPlayer)) {
                    toDraw.clear();
                    Thread.sleep(1800);
                    continue;
                }
                if (!(getTopCard() instanceof ReverseCard) && !(getTopCard() instanceof NumberedCard))
                    continue;
            }
            addValidMoves(currentPlayer, getTopCard());
            if (drawPlus(toDraw, currentPlayer)) {
                toDraw.clear();
                Thread.sleep(1800);
                continue;
            }

            if (hasDrawn){
                System.out.println(currentPlayer + " Drew a card!");
            }
//            Asks for input, to choose a card or to draw one, while invalid
            do {
                if (currentPlayer.getValidMove().size() == 0 && hasDrawn) {
                    hasDrawn = false;
                    UI.drawAndPass();
                    Thread.sleep(1800);
                    break;
                }
                if (currentPlayer instanceof HumanPlayer)
                    UI.askForNextMove();
                cardNumber = playACard(currentPlayer);

            } while ((cardNumber > currentPlayer.getHand().size() || cardNumber < 0) ||
                    !checkIfValid(currentPlayer, cardNumber));
//            Either put a card on board or draw one, according to the players choice
            if (cardNumber > 0) {
                int cardIndex = cardNumber - 1;
                playedCards.add(currentPlayer.getHand().get(cardIndex));
                currentPlayer.getHand().remove(cardIndex);
                hasDrawn = false;
            }else if (cardNumber == 0){
                drawFromDeck(currentPlayer);
                hasDrawn = true;
            }
//            Top card affects the game, and draw cards will be added to the toDraw list
            if (!getTopCard().getEffectMade()) {
                getTopCard().effect(currentPlayer);
                Thread.sleep(1500);
                if (getTopCard() instanceof SkipCard)
                    i += Order.getGameWise();
                if (getTopCard() instanceof WildCard4Plus) {
                    if (toDraw.size() != 0 && !toDraw.contains(getTopCard()))
                        toDraw.clear();
                    toDraw.add(getTopCard());
                }
                if (getTopCard() instanceof Draw2Plus) {
                    if (toDraw.size() != 0 && !toDraw.contains(getTopCard()))
                        toDraw.clear();
                    toDraw.add(getTopCard());
                }
            }
        }
    }
//    The method used for Draw2+ and WildCard 4+, with which the number of times to draw is calculated
//    and if needed, amplified
    private boolean drawPlus(ArrayList<Card> toDraw, Player player){
        if (toDraw.size() != 0) {
            Card card = toDraw.get(0);
            int maxDraw = 0;
            if (!player.getHand().contains(card)) {
                if (card instanceof WildCard4Plus) {
                    maxDraw = 4;
                    System.out.println(maxDraw * toDraw.size() + " Cards will be drawn due to WildCard4+'s effect for " + player);
                } else if (card instanceof Draw2Plus) {
                    maxDraw = 2;
                    System.out.println(maxDraw * toDraw.size() + " Cards will be drawn due to Draw2+'s effect for " + player);
                }
                for (int i = 0; i < maxDraw * toDraw.size(); i++) {
                    drawFromDeck(player);
                }
                return true;
            }
            return false;
        }
        return false;
    }
//    Checks if the deck is empty, if so the cards piled up on board
//    will be collected and shuffled
    private void checkDeck(){
        if (deck.size() == 0){
            Card topCard = getTopCard();
            playedCards.remove(topCard);
            deck.addAll(playedCards);
            playedCards.clear();
            playedCards.add(topCard);
            shuffleDeck();
        }
    }
//    Iterates the given players hand,
//    and checks whether they are valid to be put on top of the current top card
    private void addValidMoves(Player player, Card topCard){
        for (Card card : player.getHand()){
            if (card.rulesCheck(topCard, player))
                player.addValidMove(card);
        }
    }
//    If the current player is a Human, he will be asked for input
//    if not, AIPlayers method will be called to make a decision based on his hand
    private int playACard(Player player){
        if (player instanceof HumanPlayer){
            return reader.nextInt();
        }else {
            return AIPlayer.cardPlay((AIPlayer) player);
        }
    }
//    The method used to draw a card from the deck for the current player
    private void drawFromDeck(Player player){
        player.addCard(deck.get(0));
        deck.remove(0);
    }
//    Checks whether the chosen card is valid or not
    private boolean checkIfValid(Player player, int cardNumber){
        if (cardNumber == 0){
            if (player.getValidMove().size() == 0)
                return true;
            else {
                UI.warnForDraw();
            }
        }else {
            Card cardToPlay = player.getHand().get(cardNumber - 1);
            for (Card card : player.getValidMove())
                if (cardToPlay.equals(card))
                    return true;
        }
        return false;
    }
//    At each turn, it's checked whether a player has played all his cards
    private boolean roundOver(){
        for (Player player : players) {
            if (player.getHand().size() == 0) {
                UI.winnerDisplay(player);
                scoreboardDisplay();
                return true;
            }
        }
        return false;
    }
//    The logic of displaying scoreboard, which collaborates with UI to display the actual scoreboard
    private void scoreboardDisplay() {
        TreeMap<Integer, Player> scoreList = new TreeMap<>();
        for (Player player : players){
            ArrayList<Card> hand = player.getHand();
            int score = 0;
            if (hand.size() != 0){
                for (Card card : hand){
                    if (card instanceof WildCard)
                        score += 50;
                    else if (card instanceof SpecialCard)
                        score += 20;
                        else if (card instanceof NumberedCard)
                            score += ((NumberedCard) card).getNumber();
                }
            }
            scoreList.put(score, player);
        }
        UI.scoreboardDisplay(scoreList);
    }
}
