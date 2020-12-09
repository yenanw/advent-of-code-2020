import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PasswordChecker {

    static int[] parseInterval(String interval) {
        // assuming interval looks like this "number-number"
        String[] strs = interval.split("-");

        int[] r = new int[] {
            Integer.parseInt(strs[0]),
            Integer.parseInt(strs[1])
        };

        return r;
    }


    static List<String> getCorrectPasswordOldPolicy(List<String[]> passConditions) {

        List<String> corruptPasswords = new ArrayList<>();

        for (String[] strs : passConditions) {
            int[] interval = parseInterval(strs[0]);
            char charToBeRepeated = strs[1].charAt(0);

            int count = 0;
            for (char c : strs[2].toCharArray()) {
                if (c == charToBeRepeated) {
                    count++;
                }
            }

            if (count < interval[0] || count > interval[1]) {
                corruptPasswords.add(strs[2]);
            }
        }

        return corruptPasswords;
    }

    static List<String> getCorrectPasswordNewPolicy(List<String[]> passConditions) {

        List<String> corruptPasswords = new ArrayList<>();

        for (String[] strs : passConditions) {
            int[] interval = parseInterval(strs[0]);
            char charToBeRepeated = strs[1].charAt(0);

            int count = 0;
            String pass = strs[2];

            if (pass.charAt(interval[0]-1) == charToBeRepeated) {
                count++;
            }

            if (pass.charAt(interval[1]-1) == charToBeRepeated) {
                count++;
            }

            if (count != 1) {
                corruptPasswords.add(strs[2]);
            }
        }

        return corruptPasswords;
    }

    public static void main(String args[]) {

        String fileName = args[0];
        List<String[]> passwords;

        try {
            passwords = Files.lines(Paths.get(fileName))
                            .map(str -> str.split(" "))
                            .collect(Collectors.toList());

            // passwords collects String[] with a length of 3,
            // where String[0] = repetition allowed for a given character
            //       String[1] = the character to be repeated
            //       String[2] = the password


            List<String> corruptPasswords = getCorrectPasswordNewPolicy(passwords);
            System.out.println(corruptPasswords.size() + " passwords are corrupt");
            System.out.println((passwords.size() - corruptPasswords.size()) + " passwrods are valid");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}