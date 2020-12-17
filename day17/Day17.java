import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day17 {
    private static final String Set = null;

    public static List<Coord> parseCoords(String[] input) {
        List<Coord> activeCoords = new ArrayList<>();
        for (int y = 0; y < input.length; y++) {
            for (int x = 0; x < input[0].length(); x++) {
                if (input[y].charAt(x) == '#') {
                    Coord coord = new Coord(x, y, 0, 0);
                    activeCoords.add(coord);
                }
            }
        }
        return activeCoords;
    }

    public static void main(String[] args) {
        try {
            String fileName = args[0];
            String[] lines = Files.lines(Paths.get(fileName))
                             .toArray(String[]::new);

            InfiniteCube ic = new InfiniteCube(parseCoords(lines));

            for (int i = 0; i < 6; i++) {
                ic.cycle();
            }
            // lmao this is some ugly ass code
            System.out.println(ic.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
