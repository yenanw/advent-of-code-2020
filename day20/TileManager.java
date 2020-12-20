import java.util.ArrayList;
import java.util.List;

public class TileManager {
    private Tile[] tiles;

    public TileManager(Tile[] tiles) {
        this.tiles = tiles;
    }

    public void arrange() {
        Tile[] corners = findCorners();
        for (int i = 0; i < tiles.length; i++) {
            Tile t1 = tiles[i];
            for (int j = 0; j < tiles.length; j++) {
                Tile t2 = tiles[j];

                if (i == j || t2.isReady())
                    continue;

                if (!t2.isFree()) {
                    matchTiles(t1, t2);
                } else {
                    for (int[][] t : t2.getArrangements()) {
                        t2.setTile(t);
                        if (matchTiles(t1, t2))
                            break;
                    }
                }

                if (t1.isReady())
                    break;
            }
        }
    }

    public Tile[] findCorners() {
        List<Tile> corners = new ArrayList<>();
        for (int i = 0; i < tiles.length; i++) {
            Tile t1 = tiles[i];
            for (int j = 0; j < tiles.length; j++) {
                if (i == j)
                    continue;
                Tile t2 = tiles[j];

                for (int[][] t : t2.getArrangements()) {
                    t2.setTile(t);
                    if (matchTiles(t1, t2))
                        break;
                }
                
                if (t1.isReady())
                    break;
            }

            if (t1.isCorner())
                corners.add(t1);
            // reset after to make sure no weird stuff is happening
            for (Tile tile : tiles) {
                tile.reset();
            }
        }
        return corners.toArray(Tile[]::new);
    }

    public int countCorners() {
        int count = 0;
        for (Tile tile : tiles) {
            if (tile.isCorner())
                count++;
        }
        return count;
    }

    private boolean matchTiles(Tile t1, Tile t2) {
        boolean isSet = false;
        if (t1.top == null && t1.matchTop(t2)) {
            t1.top = t2;
            t2.bottom = t1;
            isSet = true;
        } else if (t1.bottom == null && t1.matchBottom(t2)) {
            t1.bottom = t2;
            t2.top = t1;
            isSet = true;
        } else if (t1.left == null && t1.matchLeft(t2)) {
            t1.left = t2;
            t2.right = t1;
            isSet = true;
        } else if (t1.right == null && t1.matchRight(t2)) {
            t1.right = t2;
            t2.left = t1;
            isSet = true;
        }
        return isSet;
    }
    
}
