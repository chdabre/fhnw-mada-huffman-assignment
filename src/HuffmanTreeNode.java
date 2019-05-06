public class HuffmanTreeNode implements Comparable<HuffmanTreeNode> {
    private final char leafChar;
    private final int frequency;
    private final HuffmanTreeNode leftChild;
    private final HuffmanTreeNode rightChild;

    public HuffmanTreeNode(char leafChar, int frequency, HuffmanTreeNode leftChild, HuffmanTreeNode rightChild) {
        this.leafChar = leafChar;
        this.frequency = frequency;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    public boolean isLeafNode() {
        return (leftChild == null) && (rightChild == null);
    }

    public char getLeafChar() {
        return leafChar;
    }

    public int getFrequency() {
        return frequency;
    }

    public HuffmanTreeNode getLeftChild() { return leftChild; }

    public HuffmanTreeNode getRightChild() { return rightChild; }

    public int compareTo(HuffmanTreeNode that) {
        return this.frequency - that.frequency;
    }
}
