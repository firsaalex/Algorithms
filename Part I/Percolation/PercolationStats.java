/**
 *PercolationStats class
 *
 * Created with DrJava
 * User: firsaalex
 * Date: 24.03.13
 * Time: 17:29
 */

//import java.lang.*;

public class PercolationStats {
    
   private double[] openedSiteCount;
   private int T;
   private int N;
   
   // perform T independent computational experiments on an N-by-N grid 
    public PercolationStats(int n, int t) {
        
        if ((n <= 0) || (t <= 0)) throw new IllegalArgumentException();
        T = t;
        N = n;
        openedSiteCount = new double[T];        
        int i, j = 0;
        
        for (int k = 0; k < T; k++) {
            
            Percolation prc = new Percolation(N);
            
            do {
                i = (int) Math.floor(Math.random()*N + 1);
                j = (int) Math.floor(Math.random()*N + 1);
                
                prc.open(i, j);    
            }
            while (!prc.percolates());

            for (int l = 1; l <= N; l++) {
                for (int m = 1; m <= N; m++) {
                    if (prc.isOpen(l, m))openedSiteCount[k]++;                    
                }
            }
            
            openedSiteCount[k] = (openedSiteCount[k] / (N*N));
        }
    }
    
    // get N
    private int getN() {
        return N;
    }
    // get T
    private int getT() {
        return T;
    }
   
    
   // sample mean of percolation threshold 
    public double mean() {
        
        double sum = 0;
        for (int i = 0; i < T; i++) {
            sum += openedSiteCount[i];
        }
        return   (sum / T);
    }
    
   // sample standard deviation of percolation threshold 
    public double stddev() {
        
        double sum = 0;
        for (int i = 0; i < T; i++) {
            sum += Math.sqrt((openedSiteCount[i] - mean())*(openedSiteCount[i] - mean()));
        }
        return  sum /(T-1);
    }
    
   // returns lower bound of the 95% confidence interval 
    public double confidenceLo() {
        return mean() - 1.96*stddev() / Math.sqrt(T);
    }
    
   // returns upper bound of the 95% confidence interval 
    public double confidenceHi() {
         return mean() + 1.96*stddev() / Math.sqrt(T);
    }
    
   // test client, described below 
    public static void main(String[] args) {
        
        PercolationStats prcs = new PercolationStats(200, 1000);
        System.out.printf("Mean=%f \r\n", prcs.mean());
        System.out.printf("STD=%f \r\n", prcs.stddev());
        System.out.printf("95%% interval:[%f ; %f] \r\n", prcs.confidenceLo(), prcs.confidenceHi());
    }
}