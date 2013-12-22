import java.util.*;

/**
 * Created by firsaalex on 07.12.13.
 */
public class BoggleDFS {
    private int rows;
    private int cols;
    private Digraph digraph;
    private String[] fullBoard;
    private String word;
    private ArrayList<String> allWords;
    private TST<Integer>[] tst;

    private boolean[] marked;  // marked[v] = true if v is reachable
    // from source (or sources)

    // single-source reachability
    public BoggleDFS(Digraph G, BoggleBoard board, TST<Integer> fullDictionary) {


        char curChar;
        digraph = G;
        rows = board.rows();
        cols = board.cols();
        HashSet<Character> set = new HashSet<Character>();
        int difChars = 0;
        char cur = 0;

        for (int  i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                cur = board.getLetter(i,j);
                if (!set.contains(cur)) {
                    set.add(cur);
                    tst[difChars] = new TST<Integer>();
                    difChars++;

                }

            }
        }

        fullBoard = new String[rows * cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                 curChar = board.getLetter(i,j);
                 if (curChar == 'Q')
                     fullBoard[i * cols + j] = "QU";
                 else
                     fullBoard[i * cols + j] = String.format("%c", curChar);

            }
        }

        word = new String();
        allWords = new ArrayList<String>();
        marked = new boolean[digraph.V()];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                dfs(digraph, i * cols + j);
            }
        }
    }


    private void dfs(Digraph G, int v) {

        word = word + fullBoard[v];



        if (tst.prefixMatch(word).iterator().hasNext()){
            if ((tst.contains(word) && (!allWords.contains(word))) && (word.length() >= 3)) {
                allWords.add(word);
            }

            marked[v] = true;

             for (int w : G.adj(v)) {
                if (!marked[w]) dfs(G, w);
             }
        }
         marked[v] = false;

        if (fullBoard[v] == "QU") word = word.substring(0,word.length() - 2);
        else word = word.substring(0,word.length() - 1);

    }

    // is there a directed path from the source (or sources) to v?
    public boolean marked(int v) {
        return marked[v];
    }

    public ArrayList<String> getAllWords() {
        return allWords;
    }


}
