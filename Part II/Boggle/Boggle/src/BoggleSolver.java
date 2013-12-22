import java.util.ArrayList;

/**
 * Created by firsaalex on 07.12.13.
 */
public class BoggleSolver {

    private TST<Integer> fullDict;
    private Digraph graph;
    private ArrayList<String> validList;
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        fullDict = new TST<Integer>();
        int pts;

        for (String s: dictionary) {
            if (s == null) continue;
            int l = s.length();

            fullDict.put(s, 1);
        }



    }


    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        validList = new ArrayList<String>();

        int rows = board.rows();
        int cols = board.cols();

        graph = new Digraph(rows * cols);
        // build graph
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {

                // vertical edges
                if (i > 0) graph.addEdge(xyTo1D(i, j, cols), xyTo1D(i - 1, j, cols));
                if (i < rows - 1) graph.addEdge(xyTo1D(i, j, cols),xyTo1D(i + 1, j, cols));

                // horizontal edges
                if (j > 0) graph.addEdge(xyTo1D(i, j, cols), xyTo1D(i, j - 1, cols));
                if (j < cols - 1) graph.addEdge(xyTo1D(i, j, cols), xyTo1D(i, j + 1, cols));

                // diagonal edges
                if ((i > 0) && (j > 0))
                    graph.addEdge(xyTo1D(i, j, cols), xyTo1D(i - 1, j - 1, cols));
                if ((i < rows - 1) && (j < cols - 1))
                    graph.addEdge(xyTo1D(i, j, cols), xyTo1D(i + 1, j + 1, cols));
                if ((i < rows - 1) && (j > 0))
                    graph.addEdge(xyTo1D(i, j, cols), xyTo1D(i + 1, j - 1, cols));
                if ((i > 0) && (j < cols - 1))
                    graph.addEdge(xyTo1D(i, j, cols), xyTo1D(i - 1, j + 1, cols));
            }
        }

        BoggleDFS dfs = new BoggleDFS(graph, board, fullDict);
        validList = dfs.getAllWords();



        return validList;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (word == null) throw new IllegalArgumentException();
        if (!validList.contains(word)) return 0;
        int l = word.length();

        if (l < 3) return 0;
        else if (l < 5) return 1;
        else if (l < 6) return 2;
        else if (l < 7) return 3;
        else if (l < 8) return 5;
        else  return 11;

    }

    private int xyTo1D(int i, int j, int cols) {
        return i * cols + j;
    }

    public static void main(String[] args) {

        BoggleSolver bs = new BoggleSolver((new Dictionary("test/dictionary-yawl.txt")).getAllWords());


        int sum = 0;
        int N = 0;

        for (String s: bs.getAllValidWords(new BoggleBoard())) {
            StdOut.println(s + " [" + bs.scoreOf(s) + "]");
            sum += bs.scoreOf(s);
            N++;
        }
        StdOut.println("Summ = " + sum + " N = " + N);

    }
}
