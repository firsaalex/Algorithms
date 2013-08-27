package ru.firsaalex.DinamicConnectivity;

/**
 * Created with IntelliJ IDEA.
 * User: firsaalex
 * Date: 25.08.13
 * Time: 12:06
 * To change this template use File | Settings | File Templates.
 */

public class QuickFindUF {

    public int[] id;

    public  QuickFindUF (int N) {

        id = new int[N];

        for(int i = 0; i < N; i++) {
            id[i] = i;
        }
    }

    public boolean connected7 (int p, int q) {

        return (id[p] == id[q]);
    }

    public void union (int p, int q) {

        int pid = id[p];
        int qid = id[q];

        for(int i = 0; i < id.length; i++) {
            if(id[i] == pid) id[i] = qid;
        }
    }

    public boolean connected1 (int p, int q) {

        return (id[p] == id[q]);
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


