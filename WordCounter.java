/**
* WordCounter File
* @author Tyler Lericos
* @version 1.0
* Assignment 4
* CS322 - Compiler Construction
**/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WordCounter {
    public static void main(String[] args) {
        // Create a hashmap to store regex pattern as key and count as value
        Map<String, Integer> patternCounts = new HashMap<>();

        // Iterate through each output file
        for (int i = 1; i <= 6; i++) {
            String fileName = "output" + i + "_wc.txt";
            processOutputFile(fileName, patternCounts);
        }

        // Output the final count for each pattern across all novels
        for (Map.Entry<String, Integer> entry : patternCounts.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    private static void processOutputFile(String fileName, Map<String, Integer> patternCounts) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Split the line into pattern and count
                String[] parts = line.split("\\|"); // Escaping the pipe character
                if (parts.length == 2) {
                    String pattern = parts[0];
                    int count = Integer.parseInt(parts[1].trim());
    
                    // Update the count for the pattern in the hashmap
                    patternCounts.put(pattern, patternCounts.getOrDefault(pattern, 0) + count);
                } else {
                    System.err.println("Invalid line format: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
}
