import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This Class is intended to simplify file load/save operations.
 */
public class FileUtilities {
    /**
     * Save a string to a File with the given name.
     */
    public static void saveStringToFile(String s, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read the contents of a File and return it as a String.
     */
    public static String readStringFromFile(String fileName) {
        try {
            byte[] encoded;
            encoded = Files.readAllBytes(Paths.get(fileName));
            return new String(encoded, Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Dump a string of bits to a file.
     */
    public static void dumpBitStringToFile(String s, String fileName) {
        s += '1'; // Add a 1 to make it easier to remove the padding when reading the file
        while (s.length() % 8 != 0) {
            s += '0'; // Ensure that the length of the string is divisible by 8
        }

        byte[] byteArray = new byte[s.length() / 8];
        for (int i = 0; i < s.length() / 8; i++) {
            String chunk = s.substring(i * 8, (i + 1) * 8);
            Integer binaryChunk = Integer.parseInt(chunk, 2);
            byteArray[i] = binaryChunk.byteValue();
        }

        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            fos.write(byteArray);
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read a file and return a string with the binary representation of its content.
     */
    public static String readBitStringFromFile(String fileName) {
        try {
            byte[] encoded;
            String outString = "";

            encoded = Files.readAllBytes(Paths.get(fileName));
            for (byte b : encoded) {
                // Ensure the bytes are zero-padded from the left
                // Source: https://stackoverflow.com/questions/12310017/how-to-convert-a-byte-to-its-binary-string-representation
                outString += Integer.toBinaryString((b & 0xFF) + 0x100).substring(1);
            }

            return outString.substring(0, outString.lastIndexOf('1')); // Crop off the added zeroes from the end
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
