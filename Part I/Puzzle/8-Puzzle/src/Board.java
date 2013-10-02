import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: firsa_as
 * Date: 01.10.13
 * Time: 10:41
 * To change this template use File | Settings | File Templates.
 */
public class Board {

    private int N;  // board size
    private int[][] puzzle;



    // construct a board from an N-by-N array of blocks (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        N = blocks[0].length;
        puzzle = multyDimArrayCopy(blocks);
    }


    // board dimension N
    public int dimension() {
        return N;
    }


    // number of blocks out of place
    public int hamming() {
        int wrongPlacedBlockCounter = 0;
        for(int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                // miss last block cause we don't have to count zero block
                if(!((i == N-1) && (j == N-1))) {
                    if (puzzle[i][j] != (i*N+j+1)) wrongPlacedBlockCounter++;
                }
            }
        }
       return wrongPlacedBlockCounter;
    }


    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int manhattanDistancesSum = 0;
        int goalRow;
        int goalColumn;

        for(int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                // miss last block cause we don't have to count zero block
                if(!((i == N-1) && (j == N-1))) {
                    // if block is zero, we exactly know goal position (last position)
                    if(puzzle[i][j] == 0) {
                        goalColumn = N - 1;
                        goalRow = N - 1;
                    }
                    else {
                        goalRow = (puzzle[i][j] - 1) / N;
                        goalColumn = puzzle[i][j] - N*goalRow - 1;
                    }

                    manhattanDistancesSum += (Math.abs(i - goalRow) + Math.abs(j - goalColumn));
                }
            }
        }
        return manhattanDistancesSum;
    }


    // is this board the goal board?
    public boolean isGoal() {
        int[][] goal = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if((i == N - 1) && (j == N - 1)) goal[i][j] = 0;
                else goal[i][j] = i*N + j +1;
            }
        }
        Board goalBoard = new Board(goal);
        return equals(goalBoard);
    }


    // a board obtained by exchanging two adjacent blocks in the same row
    public Board twin() {
        int i = 0;
        int j = 0;
        int[][] tmp = new int[N][N];

        for (int ii = 0; ii < N; ii++) {
            for (int jj = 0; jj < N; jj++) {
                tmp[ii][jj] = puzzle[ii][jj];
            }
        }

        while (true) {
             i = (int) Math.floor(Math.random()*N);
             j = (int) Math.floor(Math.random()*(N-1));

             if((puzzle[i][j] != 0) && (puzzle[i][j+1] != 0)) {

                 tmp[i][j] = puzzle[i][j+1];
                 tmp[i][j+1] = puzzle[i][j];
                 break;
             }
         }
        return new Board(tmp);
    }


    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return  false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;

        for(int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (this.puzzle[i][j] != that.puzzle[i][j]) return false;
            }
        }
        return true;
    }

    // copy 2dim array
    private int[][] multyDimArrayCopy(int [][] that) {
        int[][] result = new int[N][N];
        for(int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                result[i][j] = that[i][j];
            }
        }
        return result;
    }


    // all neighboring boards
    public Iterable<Board> neighbors() {
        int zeroI = 0;
        int zeroJ = 0;
        int[][] val = new int[N][N];
        Stack<Board> neighborsStack = new Stack<Board>();

        //find zero block coordinates: i and j
        for(int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if(puzzle[i][j] == 0) {
                    zeroI = i;
                    zeroJ = j;
                }
                val[i][j]=puzzle[i][j];
            }
        }

        if(zeroI > 0) {
            val[zeroI - 1][zeroJ] = 0;
            val[zeroI][zeroJ] = puzzle[zeroI - 1][zeroJ];
            neighborsStack.push(new Board(val));
            val = multyDimArrayCopy(puzzle);
        }

        if(zeroI < (N-1)) {
            val[zeroI + 1][zeroJ] = 0;
            val[zeroI][zeroJ] = puzzle[zeroI + 1][zeroJ];
            neighborsStack.push(new Board(val));
            val = multyDimArrayCopy(puzzle);
        }

        if(zeroJ > 0) {
            val[zeroI][zeroJ - 1] = 0;
            val[zeroI][zeroJ] = puzzle[zeroI][zeroJ - 1];
            neighborsStack.push(new Board(val));
            val = multyDimArrayCopy(puzzle);
        }

        if(zeroJ < (N-1)) {
            val[zeroI][zeroJ + 1] = 0;
            val[zeroI][zeroJ] = puzzle[zeroI][zeroJ + 1];
            neighborsStack.push(new Board(val));
        }

        return neighborsStack;
    }


    // string representation of the board (in the output format specified below)
    public String toString() {
       StringBuilder sb = new StringBuilder();

        sb.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sb.append(String.format("%3d", puzzle[i][j]));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}
