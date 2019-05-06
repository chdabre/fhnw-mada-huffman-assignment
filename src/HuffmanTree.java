import java.util.PriorityQueue;

public class HuffmanTree {
    /**
     * Count the occurrences of a char in a String and output it as an int array
     * @param text The text to be analyzed
     * @return Frequency table where the index equals the decimal-representation of the char and the value the count.
     */
    private static int[] getFrequencyTable (String text) {
        int[] frequencyTable = new int[256];

        for(char c : text.toCharArray()) {
            frequencyTable[c]++;
        }

        return frequencyTable;
    }

    /**
     * Generate a Huffman Tree by first generating a Node for every occurring character in the frequency table,
     * then grouping them by frequency into a tree
     * @param frequencyTable int Array with ascii char decimal values as indices, and their corresponding frequencies as values
     */
    public static HuffmanTreeNode generateHuffmanTree(String text) {
        // Generate the frequency table
        int[] frequencyTable = getFrequencyTable(text);

        // Create Priority Queue of characters which appear in the frequency table
        PriorityQueue<HuffmanTreeNode> pq = new PriorityQueue<>();
        for (char c = 0; c < frequencyTable.length; c++) {
            if (frequencyTable[c] > 0) { // If this character appears in the table
                pq.add(new HuffmanTreeNode(c, frequencyTable[c], null, null));
            }
        }

        while (pq.size() > 1) { // While there is more then one node, combine them to a parent node with two children
            HuffmanTreeNode newLeftChild = pq.poll();
            HuffmanTreeNode newRightChild = pq.poll();

            pq.add(new HuffmanTreeNode(
                    (char) 0, // This is not a leaf node, so it can have any char associated with it
                    newLeftChild.getFrequency() + newRightChild.getFrequency(), // The 'Weight' of this node is the weight of both its children combined
                    newLeftChild,
                    newRightChild)
            );
        }

        return pq.poll(); // Return the last remaining node
    }

    /**
     * Recursively generate a Table of Huffman codes by traversing the tree and appending zeroes and ones to a string.
     * If a leaf node is detected, write the accumulated code to the String array at the index of the decimal representation of the corresponding char.
     * This results in a lookup table.
     * @param table The table to be created. Expects an empty String array of size 256.
     * @param topNode Start Node: The top node of the Huffman Tree
     * @param code The code to be generated, default: empty String
     */
    public static void generateHuffmanTable(String[] table, HuffmanTreeNode topNode, String code) {
        if (topNode.isLeafNode()) {
            table[topNode.getLeafChar()] = code; // Write the finished code to the lookup table
        } else {
            // Traverse further down the tree, splitting at the chlid nodes
            generateHuffmanTable(table, topNode.getLeftChild(), code + '0');
            generateHuffmanTable(table, topNode.getRightChild(), code + '1');
        }
    }
}