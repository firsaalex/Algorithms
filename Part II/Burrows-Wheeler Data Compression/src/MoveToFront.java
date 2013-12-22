import java.util.LinkedList;

/**
 * Created by firsaalex on 15.12.13.
 */
public class MoveToFront {

    private static final int R = 256;

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {

        String s = BinaryStdIn.readString();
        char[] input = s.toCharArray();
        int idx;

        // Store the list of chars.
        LinkedList<Integer> strList = new LinkedList<Integer>();
        for (int i = 0; i < R; i++) strList.add(i);

        // Check whether the char is in the list.
        for (int i = 0; i < input.length; i++) {

            idx = strList.indexOf((int) input[i]);
            BinaryStdOut.write((char) idx, 8);
            int obj = strList.remove(idx);
            strList.add(0, obj);

        }

        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {

        String s = BinaryStdIn.readString();
        char[] input = s.toCharArray();

        // Store the list of chars.
        LinkedList<Integer> list = new LinkedList<Integer>();
        for (int i = 0; i < R; i++) list.add(i);

        for (int i = 0; i < input.length; i++) {
            int obj = list.remove((int) input[i]);
            list.add(0, obj);
            BinaryStdOut.write((char) obj, 8);
        }

        // Total, worst, R*N, Best, N
        BinaryStdOut.close();

    }

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args) {

         if (args[0].equals("-")) encode();
         else if (args[0].equals("+")) decode();

    }

}
