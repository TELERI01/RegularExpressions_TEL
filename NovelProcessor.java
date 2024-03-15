/**
* NovelProcessor Class
* @author Tyler Lericos
* @version 1.0
* Assignment 4
* CS322 - Compiler Construction
**/

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class NovelProcessor {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java NovelProcessor <novel_file> <pattern_file>");
            return;
        }

        String novelFileName = args[0];
        String patternFileName = args[1];

        try {
            // Read patterns from the pattern file
            List<String> patterns = readPatterns(patternFileName);

            // Count occurrences of each pattern in the novel file
            Map<String, Integer> patternCounts = countPatternOccurrences(novelFileName, patterns);

            // Write results to output file
            String outputFileName = generateOutputFileName(novelFileName);
            writeResults(outputFileName, patternCounts);
        } catch (IOException e) {
            e.printStackTrace(); //Prints stack in case of error
        }
    }
    //List of strings for read pattern
    private static List<String> readPatterns(String patternFileName) throws IOException {
        List<String> patterns = new ArrayList<>(); 
        BufferedReader reader = new BufferedReader(new FileReader(patternFileName));
        String line;
        //While loop that reads line by line and adds it to the String line
        while ((line = reader.readLine()) != null) {
            patterns.add(line);
        }
        reader.close();
        return patterns;
    }
    
    //
    private static Map<String, Integer> countPatternOccurrences(String novelFileName, List<String> patterns) throws IOException {
        Map<String, Integer> patternCounts = new HashMap<>();
        BufferedReader reader = new BufferedReader(new FileReader(novelFileName));
        StringBuilder novelTextBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            novelTextBuilder.append(line).append("\n");
        }
        reader.close();
        String novelText = novelTextBuilder.toString();

        for (String pattern : patterns) {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(novelText);
            int count = 0;
            while (m.find()) {
                count++;
            }
            patternCounts.put(pattern, count);
        }
        return patternCounts;
    }

    private static String generateOutputFileName(String novelFileName) {
        String novelName = novelFileName.substring(0, novelFileName.lastIndexOf('.'));
        return novelName.toLowerCase() + "_wc.txt";
    }

    private static void writeResults(String outputFileName, Map<String, Integer> patternCounts) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName));
        for (Map.Entry<String, Integer> entry : patternCounts.entrySet()) {
            writer.write(entry.getKey() + "|" + entry.getValue() + "\n");
        }
        writer.close();
    }
}
