import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

public class BruteForce {

    static int numberOfOccurence = 0;
    static int numberOfComparison = 0;

    public static void main(String[] args) throws IOException {
        StringBuilder text = new StringBuilder();
        ArrayList<int[]> list = new ArrayList<>();
        String pattern = "1101";
        File file = new File("bits.html");
        FileReader reader = new FileReader(file);
        BufferedReader reader1 = new BufferedReader(reader);
        String line;
        while (((line = reader1.readLine()) != null)) {
            text.append(line);
        }
        String html = text.toString();
        html = extractBody(html);

// CODE HERE
        Instant start = Instant.now();
        //  double start = System.nanoTime();
        applyBruteForce(html, pattern, list);
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        //  double end = System.nanoTime();
        // Brute force algorithm
        System.out.println("Brute Force Occurrence is " + BruteForce.numberOfOccurence);
        System.out.println("Brute Force Total comparison is " + BruteForce.numberOfComparison);
        System.out.println("Brute Force running time is " + (timeElapsed) + " milliseconds");

    }

    public static void applyBruteForce(String bodyPart, String pattern, ArrayList<int[]> indexLists) {
        // GLOBAL OLARAK TANIMLA
        int lengthOfResult = bodyPart.length();
        int lengthOfPattern = pattern.length();

        for (int i = 0; i <= lengthOfResult - lengthOfPattern; i++) {

            int j;

            for (j = 0; j < lengthOfPattern; j++) {

                if (pattern.charAt(j) != bodyPart.charAt(j + i)) {
                    numberOfComparison++;
                    break;
                }
                numberOfComparison++;
            }

            // number of pattern occurrence in the text
            if (j == lengthOfPattern) {
                //indexList.add(i);
                if (numberOfOccurence == 0) {
                    indexLists.add(new int[]{i, lengthOfPattern});
                } else {
                    int last = indexLists.size() - 1;
                    int[] lastElement = indexLists.get(last);
                    int prevIndex = lastElement[0];
                    if (i - prevIndex < lastElement[1]) {
                        lastElement[1] += lengthOfPattern - 1;
                    } else {
                        indexLists.add(new int[]{i, lengthOfPattern});
                    }
                }
                numberOfOccurence++;
            }

        }
        // index listi update etmemiz gerekiyor.


    }

    private static String extractBody(String result) {
        int firstIndex = result.indexOf("<body>") + 6;
        int lastIndex = result.indexOf("</body>");
        return result.substring(firstIndex, lastIndex);
    }
}
