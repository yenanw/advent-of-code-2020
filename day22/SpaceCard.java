import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class SpaceCard {
    private Player player1;
    private Player player2;
    
    public SpaceCard(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public Player playCombat() {
        int round = 1;
        Player winner = null;
        while (winner == null) {
            System.out.println("-- Round " + round + " --");
            round++;

            System.out.println(player1);
            System.out.println(player2);

            int card1 = player1.draw();
            int card2 = player2.draw();

            System.out.println(player1.getName() + " plays: " + card1);
            System.out.println(player2.getName() + " plays: " + card2);

            if (card1 > card2) {
                System.out.println(player1.getName() + " wins the round!");
                player1.addLast(card1);
                player1.addLast(card2);
            } else if (card2 > card1) {
                System.out.println(player2.getName() + " wins the round!");
                player2.addLast(card2);
                player2.addLast(card1);
            } else {
                // negligible, there are no identical cards in the deck
                System.out.println("It's a draw!");
            }
            System.out.println();
            if (!player1.hasCardLeft())
                winner = player2;
            else if (!player2.hasCardLeft())
                winner = player1;
        }
        System.out.println(winner.getName() + " wins the game!");
        return winner;
    }

    public Player playRecursiveCombat() {
       return playRecursiveCombat(player1, player2);
    }

    private Player playRecursiveCombat(Player p1, Player p2) {
        Set<LinkedList<Integer>> hands1 = new HashSet<>();
        Set<LinkedList<Integer>> hands2 = new HashSet<>();
        Player winner = null;
        while (winner == null) {
            if (containsHand(hands1, p1.getHand()) ||
                containsHand(hands2, p2.getHand())) {
                    winner = p1;
                    break;
            } else {
                hands1.add(p1.getHand());
                hands2.add(p2.getHand());
            }

            int card1 = p1.draw();
            int card2 = p2.draw();
            Player roundWinner = null;

            if (card1 <= p1.handSize() && card2 <= p2.handSize()) {
                Player newP1 = new Player(p1.getName(), p1.getHand(card1));
                Player newP2 = new Player(p2.getName(), p2.getHand(card2));

                Player ngWinner = playRecursiveCombat(newP1, newP2);

                if (ngWinner.equals(p1))
                    roundWinner = p1;
                else
                    roundWinner = p2;
            } else {
                if (card1 > card2)
                    roundWinner = p1;
                else if (card2 > card1)
                    roundWinner = p2;
            }

            if (roundWinner == null) {
                System.out.println("Round winner is null");
            } else if (roundWinner.equals(p1)) {
                p1.addLast(card1);
                p1.addLast(card2);
            } else if (roundWinner.equals(p2)) {
                p2.addLast(card2);
                p2.addLast(card1);
            }

            if (!p1.hasCardLeft())
                winner = p2;
            else if (!p2.hasCardLeft())
                winner = p1;
        }
        return winner;
    }

    public boolean containsHand(Set<LinkedList<Integer>> hands,
                                LinkedList<Integer> hand) {
        for (LinkedList<Integer> h : hands) {
            if (hand.containsAll(h) && h.size() == hand.size())
                return true;
        }
        return false;
    }

    public int score(Player player) {
        int score = 0;
        LinkedList<Integer> hand = new LinkedList<>();

        for (int i = player.handSize(); i > 0; i--) {
            int card = player.draw();
            score += i * card;
            hand.addFirst(card);
        }

        player.newHand(hand);
        return score;
    }
    
}
