package ru.firsaalex.DinamicConnectivity;

public class Main {



    public static void main(String[] args) {

        System.out.println("Hello World!");
        QuickFindUF qf = new QuickFindUF(10);
        WeightedQuickUnionUF wqu = new WeightedQuickUnionUF(10);


        qf.union(0, 4);
        qf.union(6, 5);
        qf.union(7, 2);
        qf.union(3, 9);
        qf.union(3, 8);
        qf.union(5, 0);

        qf.toString(qf.getId());



        wqu.union(7, 5);
        wqu.union(8, 2);
        wqu.union(3, 9);
        wqu.union(2, 6);
        wqu.union(5, 3);
        wqu.union(6, 1);
        wqu.union(2, 0);
        wqu.union(9, 6);
        wqu.union(4, 2);

        wqu.toString(wqu.getId());

        // wqu.getId();
    }
}
