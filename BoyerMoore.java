import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;

public class BoyerMoore {
    static int numberOfOccurrence;
    static int numberOfComparison;
    static double runningTime;

    public static void main(String[] args) throws IOException {
        StringBuilder text = new StringBuilder();
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
        HashMap<Character, Integer> badSymbol = Tables.constructBadTable(pattern);
        HashMap<Integer, Integer> goodSuffix = Tables.constructGoodSuffix(pattern);
       // long start = System.nanoTime();
        Instant start = Instant.now();
        applyBoyerMoore(badSymbol, goodSuffix, html, pattern);
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
       // long end = System.nanoTime();
        System.out.println("time is " + (timeElapsed) + " ms");
        System.out.println("Boyer Moore Occurrence is " + BoyerMoore.numberOfOccurrence);
        System.out.println("Boyer Moore comparison is " + BoyerMoore.numberOfComparison);

    }


    // Boyer Moore
    public static void applyBoyerMoore(HashMap<Character, Integer> badSymbol, HashMap<Integer, Integer> goodSuffix, String text, String pattern) {
        int texLen = text.length();
        int patLen = pattern.length();

        int texIndex = patLen - 1;
        int patIndex = patLen - 1;
        while (texIndex < texLen) {
            if (pattern.charAt(patIndex) == text.charAt(texIndex)) {

                // This is the case where all chars matches
                if (patIndex == 0) {
                    // TODO: match
                    // System.out.println("EŞLEME OLDU");
                    //System.out.println(texIndex);
                    numberOfOccurrence++;
                    texIndex += patLen - 1;
                    texIndex += goodSuffix.get(patLen);
                    patIndex = patLen - 1;
                } else {
                    patIndex--;
                    texIndex--;
                }

            }
            // No matches
            else {
                // That means first index is not a match, so we will shift using bad symbol method
                if (patIndex == patLen - 1) {
                    if (badSymbol.get(text.charAt(texIndex)) != null) {
                        texIndex += badSymbol.get(text.charAt(texIndex));
                    } else {
                        texIndex += badSymbol.get('*');
                    }

                } else {
                    int k = (patLen - 1) - patIndex;
                    int t1 = (badSymbol.get(text.charAt(texIndex)) != null) ? badSymbol.get(text.charAt(texIndex)) : badSymbol.get('*');
                    int d1 = Math.max(t1 - k, 1);
                    int d2 = goodSuffix.get(k);
                    texIndex += k;
                    texIndex += Math.max(d1, d2);
                    patIndex = (patLen - 1);
                }
                numberOfComparison++;
            }
        }
        //  System.out.println("finished searching");


    }

    private static String extractBody(String result) {
        int firstIndex = result.indexOf("<body>") + 6;
        int lastIndex = result.indexOf("</body>");
        return result.substring(firstIndex, lastIndex);
    }
}


