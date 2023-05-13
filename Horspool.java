import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;


public class Horspool {
    static int numberOfOccurence;
    static int numberOfComparison;

    static double runningTime;

    public static void main(String[] args) throws FileNotFoundException {
        // long start = System.nanoTime();
        String pattern = "1101";
        StringBuilder text = new StringBuilder();
        File file = new File("bits.html");
        FileReader reader = new FileReader(file);
        BufferedReader reader1 = new BufferedReader(reader);
        String line;
        while (true) {
            try {
                if ((line = reader1.readLine()) == null) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            text.append(line);
        }
        String bodyText = extractBody(String.valueOf(text));

       // long start = System.nanoTime();
        Instant start = Instant.now();
        applyHorspool(Tables.constructBadTable(pattern), pattern, bodyText);
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
       // long end = System.nanoTime();

        System.out.println("time is " + (timeElapsed)  + "ms");
        System.out.println("HorsPool  Occurrence is " + Horspool.numberOfOccurence);
        System.out.println("HorsPool Total comparison is " + Horspool.numberOfComparison);


    }

    public static void applyHorspool(HashMap<Character, Integer> badTable, String key, String s) {
        char lastC = key.charAt(key.length() - 1);
        int length = key.length();
        int index = length - 1;
       // int noOfOccur = 0;
        while (true) {
            if (lastC == s.charAt(index)) {
                boolean check = checkFullMatch(index, key, s);
                index += badTable.get(s.charAt(index));
                if (check)
                    numberOfOccurence++;
                numberOfComparison++;
            } else {
                if (badTable.get(s.charAt(index)) == null)
                    index += badTable.get('*');
                else
                    index += badTable.get(s.charAt(index));

                numberOfComparison++;
            }
            if (index > s.length() - 1)
                break;
        }

    }

    private static boolean checkFullMatch(int len, String key, String s) {
        boolean check = true;
        int k = key.length() - 1;
        for (int i = len; i >= 0; i--) {
            if (key.charAt(k) != s.charAt(i)) {
                check = false;
                numberOfComparison++;
                break;
            }
            k--;
            if (k < 0)
                break;
        }

        return check;
    }

    private static String extractBody(String result) {
        int firstIndex = result.indexOf("<body>") + 6;
        int lastIndex = result.indexOf("</body>");
        return result.substring(firstIndex, lastIndex);
    }


}
