import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: eprib
 * Date: 08.09.13
 * Time: 18:11
 * Subset.java takes a command-line integer k, reads in a sequence of N strings
 * from standard input using StdIn.readString(), and prints out exactly k of them,
 * uniformly at random. Each item from the sequence can be printed out at most once.
 */
public class Subset {


    public static void main (String[] args) {

        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        int N = 0;
        int k = Integer.decode(args[0]);
        String string;

        if(k < 0)throw new IllegalArgumentException("Number of items must be greater then zero!");

        while (!StdIn.isEmpty()) {
            string = StdIn.readString();
            queue.enqueue(string);
            N++;
        }

        if(k > N) throw new IllegalArgumentException("Number of items in subset must be less then number in queue");


        for(int i = 0; i < k; i++) {
            System.out.println(queue.dequeue());
            //System.out.println(queue.toString());
        }
    }
}
