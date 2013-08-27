

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        QuickFindUF qf = new QuickFindUF(10);
        WeightedQuickUnionUF wqu = new WeightedQuickUnionUF(10);

        qf.union(9,0);
        qf.union(5,6);
        qf.union(2,1);
        qf.union(2,9);
        qf.union(7,9);
        qf.union(1,5);


        qf.connected1(1,2);

        wqu.union(6, 4);
        wqu.union(9,6);
        wqu.union(9,8);
        wqu.union(7,2);
        wqu.union(5,1);
        wqu.union(5,2);
        wqu.union(4,7);
        wqu.union(3,9);
        wqu.union(2,0);

       // wqu.getId();

    }
}
