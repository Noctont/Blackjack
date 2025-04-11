import java.util.*;

public class Blackjack {
    public static void main(String[] args) {
        // Set up Scanner to read player input
        Scanner scanner = new Scanner(System.in);

        // Create and shuffle the deck of cards
        List<String> deck = createDeck();
        Collections.shuffle(deck);

        // These hold the player's and dealer's cards
        List<String> playerHand = new ArrayList<>();
        List<String> dealerHand = new ArrayList<>();

        // Deal 2 cards to the player and 2 to the dealer
        playerHand.add(deck.remove(0));
        playerHand.add(deck.remove(0));
        dealerHand.add(deck.remove(0));
        dealerHand.add(deck.remove(0));

        // PLAYER TURN: Keep asking if they want to hit or stay
        while (true) {
            // Show player's current hand and total value
            System.out.println("\nYour hand: " + playerHand);
            System.out.println("Total: " + getHandValue(playerHand));
            System.out.println("Dealer Shows Hand: " + dealerHand.get(0));
            System.out.print("Hit or Stay? ");
            String input = scanner.nextLine().toLowerCase();
            if (input.equals("hit")) {
                playerHand.add(deck.remove(0));

                // If player total goes over 21, they bust (lose automatically)
                if (getHandValue(playerHand) > 21) {
                    System.out.println("You BUSTED!");
                    break; //Adding a retry system in the future
                }
            } 
            // If player stays mamke dealer's turn
            else if (input.equals("stay")) {
                break;
            } 
            // Catch invalid input
            else {
                System.out.println("Please type 'hit' or 'stay'.");
            }
        }

        // DEALER TURN: Only runs if player didn't bust
        while (getHandValue(dealerHand) < 16) {
            dealerHand.add(deck.remove(0));
        }

        // Show everyone's hands and totals
        int playerTotal = getHandValue(playerHand);
        int dealerTotal = getHandValue(dealerHand);
        System.out.println("\nDealer hand: " + dealerHand + " (Total: " + dealerTotal + ")");
        System.out.println("Your hand: " + playerHand + " (Total: " + playerTotal + ")");

        // WIN LOSE TIE LOGIC
        if (playerTotal > 21) {
            System.out.println("You lose! (You busted)");
        } else if (dealerTotal > 21 || playerTotal > dealerTotal) {
            System.out.println("You win!");
        } else if (playerTotal < dealerTotal) {
            System.out.println("You lose!");
        } else {
            System.out.println("It's a tie!");
        }
    }

    // This method makes a full deck of 52 cards
    public static List<String> createDeck() {
        String[] suits = {" Of Spades", " Of Hearts", " Of Diamonds", " Of Clubs"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
        List<String> deck = new ArrayList<>();

        for (String suit : suits) {
            for (String rank : ranks) {
                deck.add(rank + suit);
            }
        }

        return deck;
    }

    // This method adds up the value of all cards in a hand
    public static int getHandValue(List<String> hand) {
        int value = 0;     // total value of the hand
        int aces = 0;      // count how many Aces we have

        for (String card : hand) {
            String rank = card.replaceAll("[^0-9JQKA]", "");
            if (rank.matches("[JQK]")) {
                value += 10;
            }

            else if (rank.equals("A")) {
                value += 11;
                aces++;
            }
                
            else {
                value += Integer.parseInt(rank);
            }
        }

        // If we bust and have any Aces, turn them from 11 to 1 until we're safe
        while (value > 21 && aces > 0) {
            value -= 10;
            aces--;
        }

        return value;
    }
}
