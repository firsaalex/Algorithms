import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: firsaalex
 * Date: 23.11.13
 * Time: 19:35
 * To change this template use File | Settings | File Templates.
 */
public class BaseballElimination {

    private int N;                          //  number of teams
    private String[] name;                  // team names
    private int[] w, l, r;                  // win , lost , remaining games
    private int[][] g;                      // all remaining games

    private double outputCap = 0;           // output capacity
    private FordFulkerson ff;               // caching object
    private ArrayList<String> certificate;  // certificate of elimination
    private int targetIndex = 0;            // Index of team checking for elimination
    private int lastLayerCnt;               // # of items in last layer (N - 1)
    private int firstLayerCnt;              // # of items in first layer (# of 2-combinations)

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        In in = new In(filename);

        N = in.readInt();

        name = new String[N];

        w = new int[N];
        l = new int[N];
        r = new int[N];

        g = new int[N][N];

        for (int i = 0; i < N; i++) {

            name[i] = in.readString();

            w[i] = in.readInt();
            l[i] = in.readInt();
            r[i] = in.readInt();

            for (int j = 0; j < N; j++) {
                g[i][j] = in.readInt();
            }
        }


    }

    // number of teams
    public int numberOfTeams() {
        return N;
    }

    // all teams
    public Iterable<String> teams() {

        Queue<String> queue = new Queue<String>();
        for (int i = 0; i < N; i++) {
            queue.enqueue(name[i]);
        }
        return queue;
    }

    // number of wins for given team
    public int wins(String team) {
        for (int i = 0; i < N; i++) {

            if (name[i].equals(team)) return w[i];
        }

        throw new IllegalArgumentException("No such team");

    }

    // number of losses for given team
    public int losses(String team) {

        for (int i = 0; i < N; i++) {

            if (name[i].equals(team)) return l[i];
        }

        throw new IllegalArgumentException("No such team");

    }

    // number of remaining games for given team
    public int remaining(String team) {

        for (int i = 0; i < N; i++) {

            if (name[i].equals(team)) return r[i];
        }

        throw new IllegalArgumentException("No such team");

    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {

        int indexI = 0;
        int indexJ = 0;

        boolean[] indexFounded = new boolean[2];

        for (int i = 0; i < N; i++) {

            if (name[i].equals(team1)) {
                indexI = i;
                indexFounded[0] = true;
            }
            if (name[i].equals(team2)) {
                indexJ = i;
                indexFounded[1] = true;
            }

            if (indexFounded[0] && indexFounded[1]) break;
        }

        if (indexFounded[0] && indexFounded[1]) return g[indexI][indexJ];

        throw new IllegalArgumentException("No such team");

    }


    // is given team eliminated?
    public boolean isEliminated(String team) {
        targetIndex = findTargetIndex(team);
        ff = buildGraph(targetIndex);
        certificate = new ArrayList<String>();

        if (!trivialEliminated(targetIndex)) return nonTrivialEliminated(ff, outputCap);
        return true;
    }

    private int findTargetIndex(String team) {

        int ti = 0;
        boolean contains = false;

        for (int i = 0; i < N; i++) {

            if (name[i].equals(team)) {
                contains = true;
                ti = i;
                break;
            }
        }
        if (!contains) throw new IllegalArgumentException("No such team");
        return ti;
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {

        if (!isEliminated(team)) return null;

        for (int i = firstLayerCnt + 1; i < firstLayerCnt + N; i++) {

            if (ff.inCut(i)) {
                String s = name[getIndex(i - firstLayerCnt - 1, targetIndex)];
                if (!certificate.contains(s)) certificate.add(s);
            }

        }
        return certificate;
    }


    private boolean trivialEliminated(int ti) {

        int sum = w[ti] + r[ti];

         for (int  i = 0; i < N; i++) {
             if (w[i] > sum) {
                 certificate.add(name[i]);
                 return true;
             }
         }

         return false;
    }

    private FordFulkerson buildGraph(int targetindex) {
        lastLayerCnt = N - 1;
        firstLayerCnt = (N - 1)*(N - 2) / 2;
        int vertexNum = 2 + firstLayerCnt + lastLayerCnt;

        int curV = 1;
        int curWeigh = 0;

        outputCap = 0;

        FlowNetwork fn = new FlowNetwork(vertexNum);

        // build weighed-edge digraph
        for (int i = 0; i < N - 1; i++) {
            for (int j = 0; j < i; j++) {
                //StdOut.println("[" + j + "," + i + "]");

                // S --> first layer vertexes
                curWeigh = g[getIndex(j, targetindex)][getIndex(i, targetindex)];
                outputCap += curWeigh;
                fn.addEdge(new FlowEdge(0, curV, curWeigh));

                // first layer vertexes --> second layer vertexes
                fn.addEdge(new FlowEdge(curV, firstLayerCnt + 1 + i, Double.POSITIVE_INFINITY));
                fn.addEdge(new FlowEdge(curV, firstLayerCnt + 1 + j, Double.POSITIVE_INFINITY));

                curV++;
            }
            curWeigh = w[targetindex] + r[targetindex] - w[getIndex(i, targetindex)];
            if (curWeigh < 0) curWeigh = 0;

            // second layer vertexes --> T
            fn.addEdge(new FlowEdge(firstLayerCnt + 1 + i, vertexNum - 1, curWeigh));
        }
        //StdOut.println(fn.toString());

        ff = new FordFulkerson(fn, 0, vertexNum - 1);
        return ff;
    }

    private boolean nonTrivialEliminated(FordFulkerson fordF, double oc) {

        return (fordF.value() != oc);
    }

    private static int getIndex(int j, int targetIndex) {

        if (j < targetIndex) return j;
        else return  j + 1;

    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);

        for (String team : division.teams()) {

            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team))
                    StdOut.print(t + " ");
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }

    }

}
