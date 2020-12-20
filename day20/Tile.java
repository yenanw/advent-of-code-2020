import java.util.ArrayList;
import java.util.List;

public class Tile {
    public Tile top = null;
    public Tile bottom = null;
    public Tile left = null;
    public Tile right = null;

    private int[][] defaultTile;

    private int[][] tile;
    private int id;

    private List<int[][]> arrangements;

    public Tile(int[][] tile, int id) {
        arrangements = initArrangements(tile);
        this.tile = tile;
        defaultTile = copy(tile);
        this.id = id;
    }

    public int getID() {
        return id;
    }

    public void setTile(int[][] tile) {
        this.tile = tile;
    }

    public int[][] getTile() {
        return tile;
    }

    public List<int[][]> getArrangements() {
        return arrangements;
    }
   
    public boolean matchBottom(Tile t) {
        int[][] tM = t.getTile();
        for (int i = 0; i < tile[0].length; i++) {
            if (tile[tile.length-1][i] != tM[0][i])
                return false;
        }
        return true;
    }

    public boolean matchTop(Tile t) {
        int[][] tM = t.getTile();
        for (int i = 0; i < tile[0].length; i++) {
            if (tile[0][i] != tM[tM.length-1][i])
                return false;
        }
        return true;
    }

    public boolean matchLeft(Tile t) {
        int[][] tM = t.getTile();
        for (int i = 0; i < tile.length; i++) {
            if (tile[i][0] != tM[i][tM[0].length-1])
                return false;
        }
        return true;
    }

    public boolean matchRight(Tile t) {
        int[][] tM = t.getTile();
        for (int i = 0; i < tile.length; i++) {
            if (tile[i][tile[0].length-1] != tM[i][0])
                return false;
        }
        return true;
    }

    public void reset() {
        top = null;
        bottom = null;
        left = null;
        right = null;
        tile = copy(defaultTile);
    }

    public boolean isReady() {
        return top != null && bottom != null &&
               left != null && right != null;
    }

    public boolean isFree() {
        return top == null && bottom == null &&
               left == null && right == null;
    }

    public boolean isCorner() {
        int count = 0;
        if (top != null)
            count++;
        if (bottom != null)
            count++;
        if (left != null)
            count++;
        if (right != null)
            count++;

        return count == 2;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Tile: ").append(id).append("\n");
        for (int[][] t : arrangements) {
            for (int[] row : t) {
                sb.append(readRow(row)).append("\n");
            }
            sb.append("\n");
        }
        sb.append("End of tile ").append(id).append("\n");
        return sb.toString();
    }

    private List<int[][]> initArrangements(int[][] tile) {
        List<int[][]> arr = new ArrayList<>();

        int[][] b1 = copy(tile);
        // add the 4 rotations of the default matrix
        arr.add(b1);
        for (int i = 0; i < 3; i++) {
            b1 = rotateL(b1);
            arr.add(b1);
        }

        int[][] b2 = copy(tile);
        flipH(b2);
        // add the 4 rotations of the horizontally flipped matrix
        arr.add(b2);
        for (int i = 0; i < 3; i++) {
            b2 = rotateL(b2);
            arr.add(b2);
        }

        // the rest of the arrangements are all duplicates of each other so
        // they are dismissed
        return arr;
    }

    private String readRow(int[] row) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < row.length; i++) {
            if (row[i] <= 0)
                sb.append('.');
            else
                sb.append('#');
        }
        return sb.toString();
    }

    //----------------------------Auxiliary methods-----------------------------
    private int[][] copy(int[][] m) {
        int[][] copy = new int[m.length][m[0].length];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                copy[i][j] = m[i][j];
            }
        }
        return copy;
    }

    private void flipH(int[][] m) {
        for (int i = 0; i < m.length; i++) {
            reverse(m[i]);
        }
    }

    public void flipV(int[][] m) {
        int cLength = m[0].length;
        for (int i = 0; i < cLength; i++) {
            for (int o = 0; o < m.length / 2; o++) {
                int tmp = m[o][i];
                m[o][i] = m[cLength-(o+1)][i];
                m[cLength-(o+1)][i] = tmp;
            }
        }
    }

    private void reverse(int[] arr) {
        for (int i = 0; i < arr.length / 2; i++) {
            int tmp = arr[i];
            arr[i] = arr[arr.length-(i+1)];
            arr[arr.length-(i+1)] = tmp;
        }
    }

    private int[][] rotateL(int[][] m) {
        int[][] t = transpose(m);
        flipH(t);
        return t;
    }

    private int[][] transpose(int[][] m) {
        int[][] tM = new int[m[0].length][m.length];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                tM[j][i] = m[i][j];
            }
        }
        return tM;
    }
}
