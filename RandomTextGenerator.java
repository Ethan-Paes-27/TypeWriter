import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

public class RandomTextGenerator {

    public static String generateText(int k, int length, String sourceName, String outputName) {
        String source = "";
        Scanner scanner;
        PrintWriter output;
        
        //START OF VALIDATION

        try {
            scanner = new Scanner(new File(sourceName));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                source += line;
            }
            System.out.println(source);
            scanner.close();
        }
        catch (Exception e) {
            System.err.println("Source passed does not exist or is not available!");
            return null;
        }
        try {
            output = new PrintWriter(new File(outputName));
        }
        catch (Exception e) {
            System.err.println("Output passed does not exist or is not available!");
            return null;
        }
        if (k < 0) {
            System.err.println("K is negative!");
            output.close();
            return null;
        }
        if (source.length() < length) {
            System.err.println("Source length is less than the passed length!");
            output.close();
            return null;
        }

        //END OF VALIDATION

        int sourceLen = source.length();

        int random = (int)(Math.random() * sourceLen);

        String before = source.substring(random, random + k);

        String ret = "" + before;

        for (int i = k; i < length; i++) {
            HashMap<Character, Integer> chars = new HashMap<>();
            int total = 0;

            for (int j = k; j < sourceLen; j++) {
                char c = source.charAt(j);

                if (!source.substring(j - k, j).equals(before)) {
                    continue;
                }

                if (!chars.containsKey(c)) {
                    chars.put(c, 1);
                } else {
                    chars.put(c, chars.get(c) + 1);
                }
                total++;
            }

            if (chars.isEmpty()) {
                System.out.println("empty");
                random = (int)(Math.random() * sourceLen);
                before = source.substring(random, random + k);
                i--;
                continue;
            }

            int rand = (int) (Math.random() * (total));

            for (char key : chars.keySet()) {
                rand -= chars.get(key);

                if (rand <= 0) {
                    ret += before.charAt(0);
                    before = before.substring(1);
                    before += key;
                    break;
                }
            }
        }
        output.print(ret);
        output.close();
        return ret;
    }


    public static void main(String[] args) {
        generateText(10, 1000, "GreatGatsby.txt", "RecreatedGatsby.txt");
    }
}
