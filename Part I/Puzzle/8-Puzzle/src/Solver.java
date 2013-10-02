/**
 * Created with IntelliJ IDEA.
 * User: firsa_as
 * Date: 01.10.13
 * Time: 10:41
 * To change this template use File | Settings | File Templates.
 */
public class Solver {

    private SearchNode topSearchNode;

    private final class SearchNode implements Comparable<Board>{
        int priority;
        int moves;
        int manhattan;
        Board currentBoard;
        SearchNode prevBoard;

        public SearchNode (Board curBoard) {
            currentBoard = curBoard;
            manhattan = currentBoard.manhattan();
            moves = prevBoard.moves + 1;

        }


        public int compareTo(Board that) {
            if (this.priority > that.priority) return 1;
            if (this.priority < that.priority) return -1;
            return 0;
        }



    }

    // Find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        // top node initialization
        topSearchNode.currentBoard = initial;
        topSearchNode.moves = 0;
        topSearchNode.prevBoard = null;
        topSearchNode.manhattan = topSearchNode.currentBoard.manhattan();
        topSearchNode.priority = topSearchNode.manhattan + topSearchNode.moves;

        MinPQ<SearchNode> pq = new MinPQ<SearchNode>();
        pq.insert(new SearchNode(initial, null));
    }
    // Is the initial board solvable?
    public boolean isSolvable() {
        return false;
    }
    // Min number of moves to solve initial board; -1 if no solution
    public int moves() {
        return 0;
    }
    // Sequence of boards in a shortest solution; null if no solution
    public Iterable<Board> solution() {
        Stack<Board> solution = new Stack<Board>();

        return  solution;
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        StdOut.print(initial.toString());
        StdOut.println("Hamming = " + initial.hamming());
        StdOut.println("Manhattan = " + initial.manhattan());
        StdOut.println("isGoal = " + initial.isGoal());

        StdOut.println("Twin:\n" + initial.twin().toString());

        StdOut.println("Neighbors:");
        for(Board board: initial.neighbors()) {
            StdOut.print(board.toString());
        }

        /*
        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
        */
    }
}
