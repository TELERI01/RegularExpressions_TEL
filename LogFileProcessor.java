/**
* LogFileProcessor class
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogFileProcessor {
    private final String filename;
    private final int printFlag;
    private final Map<String, Integer> ipAddresses;
    private final Map<String, Integer> usernames;
    /*
     * Constructor
     */
    public LogFileProcessor(String filename, int printFlag) {
        this.filename = filename;
        this.printFlag = printFlag;
        this.ipAddresses = new HashMap<>();
        this.usernames = new HashMap<>();
    }
    //Process File method
    public void processLogFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                processLine(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processLine(String line) {
        Pattern ipPattern = Pattern.compile("\\b(?:\\d{1,3}\\.){3}\\d{1,3}\\b");//RegexPattern for IP
        Pattern usernamePattern = Pattern.compile("\\b[A-Za-z0-9_-]+\\b");//Pattern for usernames
        Matcher ipMatcher = ipPattern.matcher(line);//Mathes the string you pass through the method
        Matcher usernameMatcher = usernamePattern.matcher(line);// same for usernames

        //While loops for both ip's and usernames

        while (ipMatcher.find()) {
            String ipAddress = ipMatcher.group();
            ipAddresses.put(ipAddress, ipAddresses.getOrDefault(ipAddress, 0) + 1);
        }

        while (usernameMatcher.find()) {
            String username = usernameMatcher.group();
            usernames.put(username, usernames.getOrDefault(username, 0) + 1);
        }
    }

    public int getUniqueIPCount() {
        return ipAddresses.size();
    }

    public int getUniqueUserCount() {
        return usernames.size();
    }
    //Printing outputs
    public void printOutput() {
        System.out.println("arg[1] == " + printFlag);

        if (printFlag == 1) {
            ipAddresses.forEach((ip, count) -> System.out.println(ip + ": " + count));
        } else if (printFlag == 2) {
            usernames.forEach((user, count) -> System.out.println(user + ": " + count));
        }

        System.out.println(getUniqueIPCount() + " unique IP addresses in the log.");
        System.out.println(getUniqueUserCount() + " unique users in the log.");
    }
    //=======================================================================================
    //Print Statements
    //=======================================================================================
    

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java LogFileProcessor <filename> <printFlag>");
            return;
        }

        String filename = args[0];
        int printFlag = Integer.parseInt(args[1]);

        LogFileProcessor processor = new LogFileProcessor(filename, printFlag);
        processor.processLogFile();
        processor.printOutput();
    }
}
