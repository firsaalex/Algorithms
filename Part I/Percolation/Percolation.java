/**
 *Percolation class is used to definite is system percolates or not 
 *
 * Created with DrJava
 * User: firsaalex
 * Date: 23.03.13
 * Time: 20:42
 */

public class Percolation {
    
    private static final boolean   BLOCKED = false;   //site is blocked
    private static final boolean   OPEN = true;       //site is open
    
    private int N;                 // grid size = N*N    
    private boolean[] siteState;   // site is Open or Blocked    
    private WeightedQuickUnionUF wqu;     // weighed  quick union 
    private WeightedQuickUnionUF wquShort;
    
       
    
   // create N-by-N grid, with all sites blocked 
    public Percolation(int n) {
        
        N = n; // set grid size
        
        wqu = new WeightedQuickUnionUF(N*N+2);        // general data structure
        wquShort = new WeightedQuickUnionUF(N*N+2);   // for backwash bug fix
        siteState = new boolean[N*N+2];
            
        //Block every site exepts virtual 
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                siteState[xyTo1D(i, j)] = BLOCKED;
            }
        }
        siteState[0] = OPEN; // open top virtual site
        siteState[1] = OPEN; // open bottom virtual site
             
        
            
    }
    
    //getSize
    
    private int getN() {
        return N;
    }
    
    
   // open site (row i, column j) if it is not already 
    public void open(int i, int j) {
        
        validateIndex(i, j);
        
        // open site
        if (!isOpen(i, j)) siteState[xyTo1D(i, j)] = OPEN;
        
        
        if (i == 1) {
            wqu.union(0, xyTo1D(i, j)); 
            wquShort.union(0, xyTo1D(i, j)); 
        }
        
        if (i == N) {
           wqu.union(1, xyTo1D(i, j));
        }
        //connect site with neighbours it they are opened
        if (i > 1) {
            // check top site
            if (isOpen(i-1, j)) {
              wqu.union(xyTo1D(i, j), xyTo1D(i-1, j)); 
              wquShort.union(xyTo1D(i, j), xyTo1D(i-1, j)); 
            }
        }
        if (i < N) {
             // check botoom site
          if (isOpen(i+1, j)) {
            wqu.union(xyTo1D(i, j), xyTo1D(i+1, j));
            wquShort.union(xyTo1D(i, j), xyTo1D(i+1, j));
          }
        }
        if (j > 1) {
             // check left site
            if (isOpen(i, j-1)) {
              wqu.union(xyTo1D(i, j), xyTo1D(i, j-1));
              wquShort.union(xyTo1D(i, j), xyTo1D(i, j-1));
            }
        }
        if (j < N) {
             // check right site
          if (isOpen(i, j+1)) {
            wqu.union(xyTo1D(i, j), xyTo1D(i, j+1));
            wquShort.union(xyTo1D(i, j), xyTo1D(i, j+1));
          }
        }
        
    }
    
    
    
    
    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        validateIndex(i, j);
        return siteState[xyTo1D(i, j)];
    }
    
    
    
    
    /*
     * Is site (row i, column j) full?
     * Site is full if it is opened and can be connected to top virtual site
     * Backwash: check connection in wquShort to exclude bottom virtual site
     */ 
        
    public boolean isFull(int i, int j) {
        validateIndex(i, j);
        
        if (isOpen(i, j))  return wquShort.connected(0, xyTo1D(i, j));    
        else return false;
    }
    
    
    
    
    /* Does the system percolate?
     * Check connection virtual top and bottom sites in general WQU to  minimize time
     */
    public boolean percolates() {
        return wqu.connected(0, 1);
    }
    
    
    // Validate Index
    private void validateIndex(int i, int j) {
        if ((i <= 0) || (j <= 0) || (i > N) || (j > N)) 
            throw new IndexOutOfBoundsException("row index i out of bounds");
    }

    
    
    // transform 2D coordinates to 1D
    /* Ex:
     *       0
     *    2  3  4
     *    5  6  7
     *    8  9  10
     *       1     
     * !!!!!!!!!!!!!!!!!!!!!!!!
     * 0 - virtual top site
     * 1-  virtual bottom site
     * !!!!!!!!!!!!!!!!!!!!!!!!
     * 
     * i - row index
     * j - column index
     * N - row size
     */
    
    private int xyTo1D(int i, int j) {
        validateIndex(i, j);
        return (N*(i-1)+(j-1)+2);
        
    }
    
    
    
    
    
    // visualite siteState as TRUE (OPEN) and FALSE  (BLOCKED) 
    private void visualizeGridState() {
        System.out.printf("\r\n             %b \r\n", siteState[0]); 
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                
               System.out.printf("%1b      ", siteState[xyTo1D(i+1, j+1)]); 
            }
            System.out.printf("\r\n");
        }
        System.out.printf("             %b \r\n", siteState[1]);
        System.out.printf("\r\n");
        
    }
    
   
    
    
    
    
   public static void main(String[] args) { 
        
        
        int k = 1;
        int l = 1;
        int siteCnt = 0;
        Percolation prc = new Percolation(10);
        
        do {
            try {
                
                k = (int) Math.floor(Math.random()*prc.N + 1);
                l = (int) Math.floor(Math.random()*prc.N + 1);
                
                prc.open(k, l);
  
            } 
            catch (NumberFormatException ex) {
                  System.out.println("Incorrect indexes!");
            }                               
        
        }
        while (!prc.percolates());
        
        // visualizer
        prc.visualizeGridState(); 
        
        // Open site counter 
        for (int i = 1; i <= prc.N; i++) {
            for (int j = 1; j <= prc.N; j++) {
               if (prc.isOpen(i, j))siteCnt++; 
            }
        }
        
        //Print result
        System.out.printf("\r\nResult=%d", siteCnt);
        
    } 
}

