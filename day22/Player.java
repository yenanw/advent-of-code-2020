import java.util.LinkedList;

public class Player {
    private String name;
    private LinkedList<Integer> hand;

    public Player (String name, LinkedList<Integer> hand) {
        this.name = name;
        this.hand = hand;
    }

    public String getName() {
        return name;
    }

    public LinkedList<Integer> getHand() {
        return new LinkedList<>(hand);
    }

    public LinkedList<Integer> getHand(int size) {
        LinkedList<Integer> nhand = getHand();
        while (nhand.size() > size) {
            nhand.removeLast();
        }
        return nhand;
    }

    public int draw() {
        return hand.poll();
    }

    public void addLast(int card) {
        hand.addLast(card);
    }

    public void addFirst(int card) {
        hand.addFirst(card);
    }

    public void newHand(LinkedList<Integer> hand) {
        this.hand = hand;
    }

    public int handSize() {
        return hand.size();
    }

    public boolean hasCardLeft() {
        return !hand.isEmpty();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Player))
            return false;

        Player p = (Player)obj;
        return getName().equals(p.getName());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append("'s deck: ").append(hand.toString());
        return sb.toString();
    }
}
