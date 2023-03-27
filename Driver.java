import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Driver {

    public static int[] sections;

    public static void main (String[] args) throws FileNotFoundException {
        parseArgs(args);
        testRun(sections);
    }

    public static void parseArgs (String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(args[0]));

        // the input should only be one continuous line of values, separated by spaces
        String[] line = sc.nextLine().split(" ");
        sections = new int[line.length];
        sc.close();

        for (int i = 0; i < line.length; i++) {
            sections[i] = Integer.parseInt(line[i]);
        }

    }

    public static void testRun (int[] cities) {
        Program3 pa3 = new Program3();

        long start = System.currentTimeMillis();
        int opt = pa3.maxFoodCount(sections);
        long end = System.currentTimeMillis();

        System.out.println("Optimal amount of food: " + opt);
        System.out.println("Completed in " + (end - start) + " milliseconds");
    }
}
