import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HuffmanCodeTest {

    @Test
    public void encodeDecodeHuffman() {
        String inString = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.";

        // Generate the Huffman Tree
        HuffmanTreeNode tree = HuffmanTree.generateHuffmanTree(inString);

        String[] huffmanTable = new String[256];
        HuffmanTree.generateHuffmanTable(huffmanTable, tree, "");

        String encoded = HuffmanCode.encodeHuffmanString(inString, huffmanTable);
        String decoded = HuffmanCode.decodeHuffmanString(encoded, huffmanTable);

        assertEquals(inString, decoded, "The Decoded string should equal the original string");
    }

    @Test
    public void encodeDecodeHuffmanWithFileIO() {
        String inString = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.";

        // Generate the Huffman Tree
        HuffmanTreeNode tree = HuffmanTree.generateHuffmanTree(inString);

        // Generate the Huffman Table and encode the string
        String[] huffmanTable = new String[256];
        HuffmanTree.generateHuffmanTable(huffmanTable, tree, "");
        String encoded = HuffmanCode.encodeHuffmanString(inString, huffmanTable);

        // Store both the huffman Table and the encoded String to files
        HuffmanCode.storeHuffmanTable(huffmanTable, "dec_tab.txt");
        FileUtilities.dumpBitStringToFile(encoded, "output.dat");


        // Read them back in and decode
        String[] newHuffmanTable = HuffmanCode.readHuffmanTable("dec_tab.txt");
        String newEncoded = FileUtilities.readBitStringFromFile("output.dat");
        String decoded = HuffmanCode.decodeHuffmanString(newEncoded, newHuffmanTable);

        assertEquals(inString, decoded, "The Decoded string should equal the original string, even if it was saved and loaded from files");
    }

    @Test
    public void DecodeAssignmentFile() {
        // Read the given files and decode
        String[] newHuffmanTable = HuffmanCode.readHuffmanTable("dec_tab-mada.txt");
        String newEncoded = FileUtilities.readBitStringFromFile("output-mada.dat");
        String decoded = HuffmanCode.decodeHuffmanString(newEncoded, newHuffmanTable);

        System.out.println(decoded);
        assertTrue(decoded.length() > 0);
    }

    @Test
    public void testCompressionFactor() {
        String longText = FileUtilities.readStringFromFile("text.txt");

        // Generate the Huffman Tree
        HuffmanTreeNode tree = HuffmanTree.generateHuffmanTree(longText);

        // Generate the Huffman Table and encode the string
        String[] huffmanTable = new String[256];
        HuffmanTree.generateHuffmanTable(huffmanTable, tree, "");
        String encoded = HuffmanCode.encodeHuffmanString(longText, huffmanTable);

        // Store both the huffman Table and the encoded String to files
        HuffmanCode.storeHuffmanTable(huffmanTable, "dec_tab_long.txt");
        FileUtilities.dumpBitStringToFile(encoded, "output_long.dat");

        File sourceFile = new File("text.txt");
        File tableFile = new File("dec_tab_long.txt");
        File encodedFile = new File("output_long.dat");

        System.out.println("Source file size: " + sourceFile.length() + " bytes");
        System.out.println("Compressed file size: " + (tableFile.length() + encodedFile.length()) + " bytes");

        assertTrue(sourceFile.length() > tableFile.length() + encodedFile.length(), "The huffman code should actually make this text smaller.");
    }
}