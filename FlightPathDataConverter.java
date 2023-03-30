import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class FlightPathDataConverter {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter file name: ");

        String inputFileName = sc.nextLine();
        if (!inputFileName.contains(".csv")) {
            inputFileName += ".csv";
        }

        List<List<String>> dataList;
        try {
            dataList = FlightPathDataConverter.importCSVData(inputFileName);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            sc.close();
            return;
        }
        String outputFileName = inputFileName.replace(".csv", ".dot");

        FlightPathDataConverter.exportDOTData(outputFileName, dataList);

        sc.close();
    }

    /**
     * From P1 - Hashtable code
     * @param filePathToCSV
     * @return List of tokenized lines
     * @throws FileNotFoundException
     */
    public static List<List<String>> importCSVData(String filePathToCSV) throws FileNotFoundException {
        List<List<String>> dataList = new ArrayList<List<String>>();

        // try to reader the file using BufferedReader
        BufferedReader br;
        try {
            if (filePathToCSV == null) {
                throw new FileNotFoundException("Error: Cannot load file with path 'null'");
            }
            else {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(filePathToCSV), "UTF-8"));
            }
        }
        catch (FileNotFoundException e) {
            throw e;
        }
        catch (Exception e) {
            return null;
        }

        char delimiter = ',';
        
        // Tokenize first line without adding -- remove the first line.
        try {
            tokenizeCSVLine(br, delimiter);
        }
        catch (IOException e) {
            return null;
        }

        try {
            List<String> tokens;
            tokens = tokenizeCSVLine(br, delimiter);
            while (tokens != null && (tokens.size() == 0)) {
                tokens = tokenizeCSVLine(br, delimiter);
            }

            while (tokens != null) {
                try {
                    dataList.add(tokens);
                } 
                catch (Exception e) {
                    System.out.println("An unexpected error occurred.");
                }

                tokens = tokenizeCSVLine(br, delimiter);
                while (tokens != null && (tokens.size() == 0)) {
                    tokens = tokenizeCSVLine(br, delimiter);
                }
            }
        }
        catch (IOException e) {
            return null;
        }
        return dataList;
    }
    
    /**
     * From P1 - Hashtable code
     * @param rdr Reader
     * @param delimiter Delimiter to separate character
     * @return List of tokenized strings
     * @throws IOException
     */
    public static List<String> tokenizeCSVLine(Reader rdr, char delimiter) throws IOException {
        List<String> tokens = new ArrayList<String>();
        char nextChar = (char) rdr.read();
        if (nextChar == (char) -1) {
            return null;
        }
        String token = "";
        boolean betweenQuotes = false;
        boolean backslash = false;

        // process characters until new line or end of file
        while (nextChar != '\n' && nextChar != '\r' && nextChar != (char) -1) {
            // determine whether there is preceding singular backslash, as it takes first
            // priority
            if (backslash) {
                // if previous character was backslash, then there are special circumstances
                if (nextChar == '"' || nextChar == '\\') {
                    // quote or backslash after backslash should be treated as a
                    token = token + nextChar;
                }
            }
            else {
                if (nextChar == '"') {
                    // quote without preceding backslash should invert/flip whether currently
                    // reader is reading within quotes
                    // should not be included in token
                    betweenQuotes = !betweenQuotes;
                }
                else if (nextChar == '\\') {
                    // backslash without preceding backslash should be stored as information for
                    // when processing next character
                    // should not be included in token
                    backslash = true;
                }
                else if (nextChar == delimiter && !betweenQuotes) {
                    // character in delimiter and not between quotes signifies end of token
                    tokens.add(token.trim());
                    token = "";
                }
                else {
                    token = token + nextChar;
                }
            }

            // if the character being processed is not a backslash, then keep track of that
            // for when processing next character could have included this in
            if (nextChar != '\\') {
                backslash = false;
            }

            nextChar = (char) rdr.read();
        }
        if (!token.trim().equals("")) {
            tokens.add(token.trim());
        }
        return tokens;
    }

    public static void exportDOTData(String filePathToDot, List<List<String>> tokenizedData) {
        FileOutputStream outputFile;
        try {
            outputFile = new FileOutputStream(new File(filePathToDot));
        } 
        catch (Exception e) {
            e.printStackTrace();
            return;
        }

        Map<String, ArrayList<String>> airportMap = new HashMap<String, ArrayList<String>>();
        for (List<String> data : tokenizedData) {
            String state1 = data.get(2).trim();
            String state2 = data.get(6).trim();

            airportMap.putIfAbsent(state1, new ArrayList<String>());
            airportMap.putIfAbsent(state2, new ArrayList<String>());
            
            String storeStr1 = data.get(0) + "/" + data.get(1) + "/" + data.get(3);
            if (!airportMap.get(state1).contains(storeStr1)) {
                airportMap.get(state1).add(storeStr1);
            }

            String storeStr2 = data.get(4) + "/" + data.get(5) + "/" + data.get(7);
            if (!airportMap.get(state2).contains(storeStr2)) {
                airportMap.get(state2).add(storeStr2);
            }
        }

        try {
            writeLine(outputFile, "digraph flight_data {", 0);
            for (String state : airportMap.keySet()) {
                String stateProcessed = state.replace(" ", "").replace(".", "");
                writeLine(outputFile, "subgraph cluster_" + stateProcessed + " {", 1);
                writeLine(outputFile, "label = \"" + state + "\"", 2);
                for (String airport : airportMap.get(state)) {
                    String[] airportData = airport.split("/");
                    writeLine(outputFile, airportData[0] + " [comment = \"" + airportData[1] + "/" + airportData[2] + "\"]", 2);
                }
                writeLine(outputFile, "}", 1);
            }

            for (List<String> data : tokenizedData) {
                String srcDestStr = data.get(0) + " -> " + data.get(4);
                String edgeLabelStr = " [label = \"$" + data.get(9) + "/" + data.get(10) + "mi\"]";
                writeLine(outputFile, srcDestStr + edgeLabelStr, 1);
            }
            writeLine(outputFile, "}", 0);
        } 
        catch (Exception e) {
            e.printStackTrace();
            return;
        } 
        finally {
            try {
                outputFile.close();
            } 
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void writeLine(FileOutputStream fos, String writeStr, int depth) throws IOException {
        writeStr = "\t".repeat(depth) + writeStr + "\n";
        char[] byteArr = writeStr.toCharArray();
        for (int i = 0; i < byteArr.length; i++) {
            fos.write(byteArr[i]);
        }
    }
}