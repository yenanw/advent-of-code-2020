import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day20 {
    public static Tile[] parseTiles(String[] input) {
        // parse the input, please make sure there is two empty lines at the
        // end, else it won't be pretty
        List<Tile> tiles = new ArrayList<>();
        List<int[]> matrix = new ArrayList<>();
        int cid = -1;
        for (String line : input) {
            if (!line.isBlank()) {
                if (line.startsWith("Tile")) {
                   cid = Integer.parseInt(line.split(" ")[1].split(":")[0]);
                } else {
                    int[] r = new int[line.length()];
                    for (int i = 0; i < r.length; i++) {
                        if (line.charAt(i) == '#')
                            r[i] = 1;
                    }
                    matrix.add(r);
                }
            } else {
                int[][] m = matrix.toArray(int[][]::new);
                tiles.add(new Tile(m, cid));
                matrix.clear();
                cid = -1;
            }
        }
        return tiles.toArray(Tile[]::new);
    }

    public static void main(String[] args) {
        try {
            String fileName = args[0];
            String[] lines = Files.lines(Paths.get(fileName))
                             .toArray(String[]::new);

           TileManager tm = new TileManager(parseTiles(lines));
           tm.arrange();

           //----------------PART 1----------------
           Tile[] corners = tm.findCorners();
           long prod = 1;
           for (Tile cornerTile : corners) {
               prod *= cornerTile.getID();
           }
           System.out.println("The product of the corners is " + prod);
           
           //----------------PART 2----------------
           // wtf
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
