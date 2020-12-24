import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class TileHandler {
    private Tile center;
    private Set<Tile> blackTiles;
    private Tile[][] tileMatrix;
    private String[] instructions;

    public TileHandler(String[] instructions, int size) {
        this.blackTiles = new HashSet<>();
        this.instructions = instructions;
        // i'm just gonna say that i have ascended with this
        this.tileMatrix = initTiles(size);
    }

    public void updateTiles() {
        Set<Tile> toBeFlipped = new HashSet<>();
        // the tiles that needs to be checked are the black tiles and all
        // neighbors of the black tiles, any other tiles are white and have
        // 0 black neighbor, so they don't need to be considered
        Set<Tile> tilesToCheck = new HashSet<>(blackTiles);
        tilesToCheck.addAll(getNeighborsOfBlackTiles());

        for (Tile tile : tilesToCheck) {
            int blackCount = 0;
            for (Tile neighbor : getNeighbors(tile).values()) {
                if (!neighbor.isWhite)
                    blackCount++;
            }
            // apply the art display rules here
            if (!tile.isWhite) {
                if (blackCount == 0 || blackCount > 2)
                    toBeFlipped.add(tile);
            } else {
                if (blackCount == 2)
                    toBeFlipped.add(tile);
            }
        }
        // flip all tiles that needs to be flipped
        for (Tile tile : toBeFlipped) {
            flipTile(tile);
        }
    }

    public void renovate() {
        for (String ins : instructions) {
            parseIns(ins);
        }
    }

    public void parseIns(String ins) {
        Tile tile = center;
        Queue<Character> chrs = strToQueue(ins);

        while (!chrs.isEmpty()) {
            char ch = chrs.poll();
            String dir =  Character.toString(ch);

            if (ch == 's' || ch == 'n') {
                dir += chrs.poll();
            } 

            tile = moveTile(tile, dir);
        }

        flipTile(tile);
    }

    public int countBlackTiles() {
        return blackTiles.size();
    }

    private Queue<Character> strToQueue(String str) {
        Queue<Character> chrQueue = new LinkedList<>();
        for (char ch : str.toCharArray()) {
            chrQueue.add(ch);
        }
        return chrQueue;
    }
    
    private Tile moveTile(Tile tile, String dir) {
        return getNeighbors(tile).get(dir);
    }

    private Set<Tile> getNeighborsOfBlackTiles() {
        Set<Tile> neighbors = new HashSet<>();
        for (Tile black : blackTiles) {
            neighbors.addAll(getNeighbors(black).values());
        }
        return neighbors;
    }

    private Map<String,Tile> getNeighbors(Tile tile) {
        Map<String,Tile> neighbors = new HashMap<>();
        int r = tile.row, c = tile.col;
        
        neighbors.put("e", tileMatrix[r][c+1]);
        neighbors.put("w", tileMatrix[r][c-1]);

        if ((r & 1) == 0) {
            neighbors.put("ne", tileMatrix[r-1][c]);
            neighbors.put("se", tileMatrix[r+1][c]);
            neighbors.put("nw", tileMatrix[r-1][c-1]);
            neighbors.put("sw", tileMatrix[r+1][c-1]);
        } else {
            neighbors.put("ne", tileMatrix[r-1][c+1]);
            neighbors.put("se", tileMatrix[r+1][c+1]);
            neighbors.put("nw", tileMatrix[r-1][c]);
            neighbors.put("sw", tileMatrix[r+1][c]);
        }

        return neighbors;
    }

    private void flipTile(Tile tile) {
        if (!tile.isWhite) {
            blackTiles.remove(tile);
            tile.isWhite = true;
        } else {
            blackTiles.add(tile);
            tile.isWhite = false;
        }
    }

    private Tile[][] initTiles(int size) {
        Tile[][] m = new Tile[size][size];
        for (int r = 0; r < m.length; r++) {
            for (int c = 0; c < m[0].length; c++) {
                m[r][c] = new Tile(r, c);
            }
        }
        center = m[size/2][size/2];
        return m;
    }

    private class Tile {        
        boolean isWhite = true;
        int row;
        int col;

        public Tile(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Tile))
                return false;

            Tile tile = (Tile)obj;
            return this.row == tile.row && this.col == tile.col;
        }
        
        @Override
        public int hashCode() {
            return row * col + col;
        }
    }
}
