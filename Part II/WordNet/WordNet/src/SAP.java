/**
 * Created with IntelliJ IDEA.
 * User: firsa_as
 * Date: 06.11.13
 * Time: 16:00
 * To change this template use File | Settings | File Templates.
 */
public class SAP {
    private Digraph digraph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        digraph = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {

        if ((v < 0) || (v >= digraph.V()))
            throw new IndexOutOfBoundsException();
        if ((w < 0) || (w >= digraph.V()))
            throw new IndexOutOfBoundsException();

        DeluxeBFS bfsV = new DeluxeBFS(digraph, v);
        DeluxeBFS bfsW = new DeluxeBFS(digraph, w);
        int anc = ancestor(v, w);

        if (anc == -1) return -1;
        return bfsV.distTo(anc) + bfsW.distTo(anc);

    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {

        if ((v < 0) || (v >= digraph.V()))
            throw new IndexOutOfBoundsException();
        if ((w < 0) || (w >= digraph.V()))
            throw new IndexOutOfBoundsException();


        int dist = Integer.MAX_VALUE;
        int curDist;
        int distV, distW;
        int anc = -1;
        int i;
        boolean[] markedV;
        boolean[] markedW;

        DeluxeBFS bfsV = new DeluxeBFS(digraph, v);
        DeluxeBFS bfsW = new DeluxeBFS(digraph, w);
        markedV = bfsV.getMarked();
        markedW = bfsW.getMarked();

        for (i = 0; i < digraph.V(); i++) {
            if (markedV[i] && markedW[i]) {
                distV = bfsV.distTo(i);
                distW = bfsW.distTo(i);

                if ((distV >= 0) && (distW >= 0)) {
                    curDist = distV + distW;
                    if (curDist < dist) {
                        dist = curDist;
                        anc = i;
                    }
                }
            }
        }

        return anc;

    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {

        for (int vv: v) {
            if ((vv < 0) || (vv >= digraph.V()))
                throw new IndexOutOfBoundsException();
        }

        for (int ww: w) {
            if ((ww < 0) || (ww >= digraph.V()))
                throw new IndexOutOfBoundsException();
        }

        DeluxeBFS bfsV = new DeluxeBFS(digraph, v);
        DeluxeBFS bfsW = new DeluxeBFS(digraph, w);
        int anc = ancestor(v, w);

        if (anc == -1) return -1;
        return bfsV.distTo(anc) + bfsW.distTo(anc);
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {

        for (int vv: v) {
            if ((vv < 0) || (vv >= digraph.V()))
                throw new IndexOutOfBoundsException();
        }

        for (int ww: w) {
            if ((ww < 0) || (ww >= digraph.V()))
                throw new IndexOutOfBoundsException();
        }


        int dist = Integer.MAX_VALUE;
        int curDist;
        int distV, distW;
        int anc = -1;
        int i;
        boolean[] markedV;
        boolean[] markedW;

        DeluxeBFS bfsV = new DeluxeBFS(digraph, v);
        DeluxeBFS bfsW = new DeluxeBFS(digraph, w);
        markedV = bfsV.getMarked();
        markedW = bfsW.getMarked();

        for (i = 0; i < digraph.V(); i++) {
            if (markedV[i] && markedW[i]) {
                distV = bfsV.distTo(i);
                distW = bfsW.distTo(i);

                if ((distV >= 0) && (distW >= 0)) {
                    curDist = distV + distW;
                    if (curDist < dist) {
                        dist = curDist;
                        anc = i;
                    }
                }
            }
        }

        return anc;
    }

    // for unit testing of this class (such as the one below)
    public static void main(String[] args) {


        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);



        int v = StdIn.readInt();
        int w = StdIn.readInt();

        int ancestor = sap.ancestor(v, w);
        int length   = sap.length(v, w);
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);

    }
}
