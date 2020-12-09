import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

class SlopeProblem {

    static int countTrees(int startX, int startY, int dx, int dy, String[] map) {
        int x = startX;
        int count = 0;
        
        for (int y = startY; y < map.length; y += dy) {
            if (map[y].charAt(x) == '#') {
                count++;
            }

            x = (x+dx) % map[y].length();
        }

        return count;
    }

    static String repeat(String str, int times) {
        StringBuilder sb = new StringBuilder(str);

        for (int i = 0; i <= times; i++) {
            sb.append(str);
        }

        return sb.toString();
    }

    public static void main(String args[]) {
        try {
            String input = args[0];

            List<String> map = Files.lines(Paths.get(input))
                                .collect(Collectors.toList());

            String[] mapArray = new String[map.size()];
            map.toArray(mapArray);

            int treeCounts[] = new int[] {
                countTrees(0, 0, 1, 1, mapArray),
                countTrees(0, 0, 3, 1, mapArray),
                countTrees(0, 0, 5, 1, mapArray),
                countTrees(0, 0, 7, 1, mapArray),
                countTrees(0, 0, 1, 2, mapArray)
            };
           
            int prod = 1;
            for (int i : treeCounts) {
                System.out.println("Tree count: " + i);
                prod *= i;
            }

            System.out.println("Answer: " + prod);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}