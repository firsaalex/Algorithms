/**
 * Created with IntelliJ IDEA.
 * User: firsa_as
 * Date: 01.10.13
 * Time: 10:41
 * To change this template use File | Settings | File Templates.
 */
public class Solver {

    private SearchNode topSearchNode = new SearchNode();
    private SearchNode topSearchNodeTwin = new SearchNode();
    private SearchNode finalSN = new SearchNode();
    private int moves;
    private boolean solveable;

    private final class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private SearchNode prev;
        private int manhattan;
        private int moves;
        private int priority;


        public SearchNode() {

        }

        public SearchNode(Board b, SearchNode p) {
            board = b;
            prev = p;
            manhattan = b.manhattan();
            moves = p.moves + 1;
            priority = manhattan + moves;
        }


        public int compareTo(SearchNode that) {
            if (this.priority > that.priority) return 1;
            if (this.priority < that.priority) return -1;
            return 0;
        }

        public String toString() {
            return "manhattan = " + manhattan
                    + "\nmoves = " + moves
                    + "\npriority = " + priority
                    + "\n"
                    + board.toString();
        }



    }

    // Find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        SearchNode currentSN, currentSNT;

        // top node initialization
        topSearchNode.board = initial;
        topSearchNode.prev = null;
        topSearchNode.manhattan = initial.manhattan();
        topSearchNode.moves = 0;
        topSearchNode.priority = topSearchNode.manhattan;

        // top twin node initialization
        topSearchNodeTwin.board = initial.twin();
        topSearchNodeTwin.prev = null;
        topSearchNodeTwin.manhattan = initial.manhattan();
        topSearchNodeTwin.moves = 0;
        topSearchNodeTwin.priority = topSearchNode.manhattan;


        // A-star algorithm
        MinPQ<SearchNode> pq = new MinPQ<SearchNode>();
        MinPQ<SearchNode> pqt = new MinPQ<SearchNode>();
        pq.insert(topSearchNode);
        pqt.insert(topSearchNodeTwin);

        while (true) {
            currentSN = pq.delMin();
            currentSNT = pqt.delMin();

            if (currentSN.board.isGoal()) {
                finalSN = currentSN;
                solveable = true;
                break;
            }

            if (currentSNT.board.isGoal()) {
                solveable = false;
                break;
            }

            for (Board b: currentSN.board.neighbors()) {
                if ((currentSN.prev == null) || (!b.equals(currentSN.prev.board)))
                    pq.insert(new SearchNode(b, currentSN));
            }

            for (Board b: currentSNT.board.neighbors()) {
                if ((currentSNT.prev == null) || (!b.equals(currentSNT.prev.board)))
                    pqt.insert(new SearchNode(b, currentSNT));
            }
        }
        moves = currentSN.moves;
    }
    // Is the initial board solvable?
    public boolean isSolvable() {
        return solveable;
    }
    // Min number of moves to solve initial board; -1 if no solution
    public int moves() {
        if (!solveable) return -1;
        return moves;
    }
    // Sequence of boards in a shortest solution; null if no solution
    public Iterable<Board> solution() {
        if (!solveable) return null;

        SearchNode fsn = finalSN;
        Stack<Board> solution = new Stack<Board>();


        while (fsn != null) {
            solution.push(fsn.board);
            fsn = fsn.prev;
        }

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
        for (Board board: initial.neighbors()) {
            StdOut.print(board.toString());
        }



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

        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }


    }
}
