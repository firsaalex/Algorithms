/**
 * Created with IntelliJ IDEA.
 * User: firsaalex
 * Date: 24.08.13
 * Time: 22:29
 * To change this template use File | Settings | File Templates.
 */

package ru.firsaalex.DinamicConnectivity;

public class QuickUnionUF {

    private int[] id;

    public  QuickUnionUF (int N) {

        id = new int[N];

        for(int i = 0; i < N; i++) {
            id[i] = i;
        }
    }

    private int root (int p) {

        while(id[p] != p) p = id[p];

        return p;
    }

    public boolean connected (int p, int q) {

        return (root(p) == root(q));
    }

    public void union (int p, int q) {

        id[root(p)] = root(q);
    }

    public int[] getId() {
        return id;
    }

    public void toString(int [] id) {

        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < id.length; i++ ) {
            sb.append(id[i]);
            sb.append(" ");
        }
        sb.append("\r\n");

        System.out.println(sb.toString());
    }
}
