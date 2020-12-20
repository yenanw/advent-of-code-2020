import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TileManager {
    private Tile[] tiles;
    private int size;

    public TileManager(Tile[] tiles) {
        this.tiles = tiles;
        this.size = (int)Math.sqrt(tiles.length);
    }

    public void arrange() {
        Tile[] corners = findCorners();
        arrange(corners[0], new HashSet<>());
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
                    if (matchTiles(t1, t2)) {
                        t2.reset();
                        break;
                    }
                }
                
                if (t1.isReady())
                    break;
            }

            if (t1.isCorner())
                corners.add(t1);

            // reset after to make sure no weird stuff is happening
            reset();
        }
        return corners.toArray(Tile[]::new);
    }

    public Tile concatTiles() {
        Tile topLeft = null;
        for (Tile tile : tiles) {
            if (tile.top == null && tile.left == null && tile.isCorner()) {
                topLeft = tile;
                break;
            }
        }

        int row = topLeft.getTile().length - 2;
        int col = topLeft.getTile()[0].length - 2;
        int[][] image = new int[size * row][size * col];

        concatTiles(topLeft, image, 0, 0, new HashSet<>());
        return new Tile(image, -1);
    }

    public int countMatchingPattern(Tile t, int[][] pattern) {
        int count = 0;
        List<int[][]> arrangements = t.getArrangements();

        int p = 0;
        int[][] tile = arrangements.get(p);

        while (count == 0) {
            for (int i = 0; i < tile.length - pattern.length; i++) {
                for (int j = 0; j < tile[0].length - pattern[0].length; j++) {
                    boolean match = true;
                    for (int r = 0; r < pattern.length; r++) {
                        for (int c = 0; c < pattern[0].length; c++) {
                            if (pattern[r][c] > 0 &&
                                pattern[r][c] != tile[i+r][j+c]) {
                                match = false;
                                break;
                            }
                        }   
                        
                        if (!match)
                            break;
                    }
                    
                    if (match)
                        count++;
                }
            }
            
            tile = arrangements.get(p++);
            if (p >= arrangements.size()) 
                break;
        }
        return count;
    }

    public int countCorners() {
        int count = 0;
        for (Tile tile : tiles) {
            if (tile.isCorner())
                count++;
        }
        return count;
    }

    public int countReady() {
        int count = 0;
        for (Tile tile : tiles) {
            if (tile.isReady())
                count++;
        }
        return count;
    }

    public int countEdge() {
        int count = 0;
        for (Tile tile : tiles) {
            if (tile.isEdge())
                count++;
        }
        return count;
    }

    public void reset() {
        for (Tile tile : tiles) {
            tile.reset();
        }
    }

     private void concatTiles(Tile current, int[][] imageTile,
                                int row, int col, Set<Tile> visited) {
        if (visited.contains(current))
            return;

        int[][] tile = current.stripBorder();
        visited.add(current);

        for (int r = 0; r < tile.length; r++) {
            for (int c = 0; c < tile[0].length; c++) {
                imageTile[row+r][col+c] = tile[r][c];
            }
        }
        
        int rowLength = current.getTile().length - 2;
        int colLength = current.getTile()[0].length - 2;

        if (current.right != null) {
            concatTiles(current.right, imageTile, 
                        row, col+colLength, visited);
        }

        if (current.bottom != null) {
            concatTiles(current.bottom, imageTile, 
                        row+rowLength, col, visited);
        }
    }

    private void arrange(Tile t1, Set<Tile> visited) {
        if (visited.contains(t1))
            return;
        visited.add(t1);

        for (Tile t2 : tiles) {
            if (t1.equals(t2) || t1.isReady())
                continue;

            if (t2.isFree()) {
                for (int[][] tArr : t2.getArrangements()) {
                    t2.setTile(tArr);
                    if (matchTiles(t1, t2))
                        break;
                }
            } else {
                matchTiles(t1, t2);
            }
        }

        if (t1.top != null) {
            arrange(t1.top, visited);
        }

        if (t1.bottom != null) {
            arrange(t1.bottom, visited);
        }

        if (t1.left != null) {
            arrange(t1.left, visited);
        }

        if (t1.right != null) {
            arrange(t1.right, visited);
        }
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
