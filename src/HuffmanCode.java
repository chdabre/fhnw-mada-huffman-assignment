import java.io.File;
import java.util.HashMap;

public class HuffmanCode {
    /**
     * Use the generated huffman table to encode the source string
     * @return String of encoded bits.
     */
    public static String encodeHuffmanString(String s, String[] huffmanTable) {
        String outString = "";

        for (char c : s.toCharArray()) {
            if (c < 256) { // Ignore non-ascii chars
                outString += huffmanTable[c]; // the huffmanTable array is a lookup table, where each index represents the decimal representation of the corresponding char
            }
        }

        return outString;
    }

    /**
     * Decode a huffman-encoded string to its original form using the corresponding huffman table.
     * @return The original decoded string
     */
    public static String decodeHuffmanString(String s, String[] huffmanTable) {
        // Convert the table to a HashMap for easier lookup
        HashMap<String, Character> tableMap = new HashMap<>();
        for (char c = 0; c < huffmanTable.length; c++) {
            if (huffmanTable[c] != null) {
                tableMap.put(huffmanTable[c], c);
            }
        }

        String codeChunk = "";
        String decoded = "";
        for (char c : s.toCharArray()) {
            codeChunk += c;
            if (tableMap.get(codeChunk) != null) { // Try to find a sequence of bits which match a key from the table
                decoded += tableMap.get(codeChunk); // Get the corresponding char
                codeChunk = "";
            }
        }

        return decoded;
    }

    /**
     * Store a generated huffman Table to a file in the format char1:code1-char2:code2-etc.
     */
    public static void storeHuffmanTable(String[] huffmanTable, String fileName) {
        String tableAsString = "";
        for (char c = 0; c < huffmanTable.length; c++) {
            if (huffmanTable[c] != null) {
                tableAsString += (int) c + ":" + huffmanTable[c] + "-";
            }
        }
        tableAsString = tableAsString.substring(0, tableAsString.length() - 1); // Remove last unneccessary dash

        FileUtilities.saveStringToFile(tableAsString, fileName);
    }

    /**
     * Read a stringified huffman Table to a file in the format char1:code1-char2:code2-etc.
     */
    public static String[] readHuffmanTable(String fileName) {
        String encodedTable = FileUtilities.readStringFromFile(fileName);
        String[] tableElements = encodedTable.split("-");

        String[] huffmanTable = new String[256];
        for (String s : tableElements) {
            String[] entry = s.split(":");
            huffmanTable[Integer.parseInt(entry[0])] = entry[1];
        }

        return huffmanTable;
    }
}
