import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

public class RandomTextGenerator {

    public static void generateText(int k, int length, String sourceName, String outputName) {
        String source = "";
        Scanner scanner;
        PrintWriter output;

        //START OF VALIDATION

        try {
            source = new Scanner(new File(sourceName)).useDelimiter("\\Z").next();
        }
        catch (Exception e) {
            System.err.println("Source passed does not exist or is not available!");
            return;
        }
        try {
            output = new PrintWriter(outputName);
        }
        catch (Exception e) {
            System.err.println("Output passed does not exist or is not available!");
            return;
        }
        if (k < 0) {
            System.err.println("K is negative!");
            output.close();
            return;
        }
        if (source.length() < length) {
            System.err.println("Source length is less than the passed length!");
            output.close();
            return;
        }

        //END OF VALIDATION

        int sourceLen = source.length();

        int random = (int)(Math.random() * (sourceLen - length));

        String before = source.substring(random, random + k);

        String ret = "";

        for (int i = 0; i < length; i++) {
            HashMap<Character, Integer> chars = new HashMap<>();
            int total = 0;

            if (k == 0) {
                int randomPos = (int)(Math.random() * sourceLen);
                ret += source.charAt(randomPos);
                continue;
            }

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
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("What book do you want?");
        System.out.println("1. Frankenstein");
        System.out.println("2. GreatGatsby");
        System.out.println("3. Odyssey");
        System.out.println("4. Sherlock Holmes");
        System.out.println("5. Wizard Of Oz");
        int choice = scan.nextInt();

        String source = "";

        if (choice < 1 || choice > 5) {
            System.out.println("Choice not valid!");
            return;
        }

        if (choice == 1) {
            source = "Frankenstein.txt";
        }
        if (choice == 2) {
            source = "GreatGatsby.txt";
        }
        if (choice == 3) {
            source = "Odyssey.txt";
        }
        if (choice == 4) {
            source = "SherlockHolmes.txt";
        }
        if (choice == 5) {
            source = "WizardOfOz.txt";
        }

//        System.out.print("What file should I put to: " );
//        String output = scan.next();
//        System.out.println();

        System.out.print("What k should I use: " );
        int k = scan.nextInt();
        System.out.println();

        System.out.print("What length should I print: " );
        int len = scan.nextInt();
        System.out.println();

        generateText(k, len, source, "Output.txt");
    }
}

