/**
 * Created with IntelliJ IDEA.
 * User: firsaalex
 * Date: 24.08.13
 * Time: 22:02
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

    public boolean connected (int p, int q) {

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
}

