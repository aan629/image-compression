package haarHuffman;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.TreeMap;

/* Huffman encode*/

public class HuffmanEncode {
	
	//Public Pointers
	public static String codeword;
	public static String huffmanTree;
	public static ArrayList<String> test;
	
	//Private Pointers
    private static PriorityQueue<NodeEncode> nodes = new PriorityQueue<>((o1, o2) -> (o1.value < o2.value) ? -1 : 1);
    private static TreeMap<Character, String> codes = new TreeMap<>();
    private static String text = "";
    
    private static int ASCII[] = new int[128];
    
    public HuffmanEncode(String str) {
    	huffmanTree = "";
    	text = str;
    	handleNewText();
    }

    private static void handleNewText() {
        	test = new ArrayList<String>();
            ASCII = new int[128];
            nodes.clear();
            codes.clear();
            codeword = "";
            calculateCharIntervals(nodes);
            buildTree(nodes);
            generateCodes(nodes.peek(), "");
            saveTree();
            encodeText();
    }
    
    private static void encodeText() {
        for (int i = 0; i < text.length(); i++) {
        	test.add(codes.get(text.charAt(i)));
        	//codeword += codes.get(text.charAt(i));
            //System.out.println(i + "/"+ (text.length()-1));
        }
    }

    private static void buildTree(PriorityQueue<NodeEncode> vector) {
        while (vector.size() > 1)
            vector.add(new NodeEncode(vector.poll(), vector.poll()));
    }

    private static void saveTree() {
        codes.forEach((k, v) -> huffmanTree+= k+"  "+v+"\n");
        //codes.forEach((k, v) -> System.out.println("'" + k + "' : " + v));
    }

    private static void calculateCharIntervals(PriorityQueue<NodeEncode> vector) {
        for (int i = 0; i < text.length(); i++)
            ASCII[text.charAt(i)]++;

        for (int i = 0; i < ASCII.length; i++)
            if (ASCII[i] > 0) {
                vector.add(new NodeEncode(ASCII[i] / (text.length() * 1.0), ((char) i) + ""));
            }
    }

    private static void generateCodes(NodeEncode node, String s) {
        if (node != null) {
            if (node.right != null)
                generateCodes(node.right, s + "1");

            if (node.left != null)
                generateCodes(node.left, s + "0");

            if (node.left == null && node.right == null)
                codes.put(node.character.charAt(0), s);
        }
    }
}

class NodeEncode {
    NodeEncode left, right;
    double value;
    String character;

    public NodeEncode(double value, String character) {
        this.value = value;
        this.character = character;
        left = null;
        right = null;
    }

    public NodeEncode(NodeEncode left, NodeEncode right) {
        this.value = left.value + right.value;
        character = left.character + right.character;
        if (left.value < right.value) {
            this.right = right;
            this.left = left;
        } else {
            this.right = left;
            this.left = right;
        }
    }
}