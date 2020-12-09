import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Sum {

    static int[] convertToIntArray(String arr[]) {
        int intArr[] = new int[arr.length];

        for (int i = 0; i < arr.length; i++) {
            intArr[i] = Integer.parseInt(arr[i]);
        }

        return intArr;
    }

    // find a pair of int that sums to sum
    static int[] findPair(int input[], int sum) {
        for (int i = 0; i < input.length; i++) {
            for (int j = i + 1; j < input.length; j++) {
                if (input[i] + input[j] == sum) {
                    int pair[] = new int[] {
                        input[i], input[j]
                    };
                    return pair;
                }
            }
        }

        return null;
    }

    // find a triple of int that sums to sum
    static int[] findTriple(int input[], int sum) {
        for (int i = 0; i < input.length; i++) {
            for (int j = i + 1; j < input.length; j++) {
                for (int o = j + 1; o < input.length; o++) {
                    if (input[i] + input[j] + input[o] == sum) {
                        int triple[] = new int[] {
                            input[i], input[j], input[o]
                        };
                        return triple;
                    }
                }
            }
        }

        return null;
    }


    public static void main(String args[]) {
        try {
            File file = new File(args[0]);
            ArrayList<String> strings = new ArrayList<>();

            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String str = reader.nextLine();
                strings.add(str);
                System.out.println(str);
            }
            reader.close();

            if (strings.size() == 0) {
                System.out.println("wait, that's illegal");
            } else {
                String strArr[] = new String[strings.size()]; 
                strings.toArray(strArr);

                int arr[] = convertToIntArray(strArr);
                int answer[] = findTriple(arr, 2020);

                if (answer == null) {
                    System.out.println("no answer found -.-");
                } else {
                    System.out.print("The answer is ");
                    System.out.print(answer[0] + ", " + answer[1] + " and " + answer[2]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
