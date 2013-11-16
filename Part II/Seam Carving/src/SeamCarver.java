import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: firsa_as
 * Date: 15.11.13
 * Time: 10:57
 * To change this template use File | Settings | File Templates.
 */
public class SeamCarver {
    /*
        pixel(I,J): In image processing I-index refers to column
                                        J-index refers to row
     */

    private int width;
    private int height;

    // RGB representation
    private int[][] r;
    private int[][] g;
    private int[][] b;

    // Energy
    private double[][] energy;
    private boolean transposed;
    private final double borderEnergy = 195075;

    // seam
    private int[] verticalSeam;
    private int[] horizontalSeam;
    private double totalEnergy;


    public SeamCarver(Picture picture) {
        width = picture.width();
        height = picture.height();

        r = new int[width][height];
        g = new int[width][height];
        b = new int[width][height];

        // fill rgb arrays
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {

                Color color = picture.get(i,j);
                r[i][j] = color.getRed();
                g[i][j] = color.getGreen();
                b[i][j] = color.getBlue();

            }

        }

        // compute energy martix
        energy = new double[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                energy[i][j] = energy(i,j);
            }
        }

        transposed = false;

        // seam init
        horizontalSeam = new int[width];
        verticalSeam = new int[height];
    }

    // current picture
    public Picture picture() {
        Picture pic = new Picture(width,height);


        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Color c = new Color(r[i][j], g[i][j], b[i][j]);
                pic.set(i, j, c);
            }
        }

        return pic;
    }

    // width of current picture
    public int width() {
        return width;
    }

    // height of current picture
    public int height() {
        return height;
    }

    // energy of pixel at column x and row y
    public  double energy(int x, int y) {
        if ((x < 0) || (x >= width)) throw new IndexOutOfBoundsException(String.format("x = %d argument is out of bounds",x));
        if ((y < 0) || (y >= height)) throw new IndexOutOfBoundsException(String.format("y = %d argument is out of bounds",y));

        if ((x == 0) || (x == width - 1)) return borderEnergy;
        if ((y == 0) || (y == height - 1)) return borderEnergy;

        int rx,gx,bx,ry,gy,by;

        rx = r[x + 1][y] - r[x - 1][y];
        gx = g[x + 1][y] - g[x - 1][y];
        bx = b[x + 1][y] - b[x - 1][y];

        ry = r[x][y + 1] - r[x][y - 1];
        gy = g[x][y + 1] - g[x][y - 1];
        by = b[x][y + 1] - b[x][y - 1];

        return (Math.pow(rx, 2) + Math.pow(bx, 2) + Math.pow(gx, 2) + Math.pow(ry, 2) + Math.pow(gy, 2) + Math.pow(by, 2));
    }

    private int minIndex(double[] a) {
        double min = a[0];
        int index = 0;

        for (int i = 0; i < a.length; i++) {
            if (a[i] < min) {
                min = a[i];
                index = i;
            }
        }
        return index;
    }

    private void pixelHandler (int x, int y) {

        if (!transposed) {
            horizontalSeam[x] = y;
            totalEnergy += energy[x][y];

            if (x < width - 1) {

                double[] adj = new double[3];

                if (y == 0) adj[0] = Double.POSITIVE_INFINITY;
                else        adj[0] = energy[x + 1][y - 1];

                if (y == height - 1) adj[2] = Double.POSITIVE_INFINITY;
                else                 adj[2] = energy[x + 1][y + 1];

                adj[1] = energy[x + 1][y];

                pixelHandler(x + 1, minIndex(adj) + y - 1);
            }

        }
        else {
            verticalSeam[y] = x;
            totalEnergy += energy[x][y];

            if (y < height - 1) {

                double[] adj = new double[3];

                if (x == 0) adj[0] = Double.POSITIVE_INFINITY;
                else        adj[0] = energy[x - 1][y + 1];

                if (x == width - 1) adj[2] = Double.POSITIVE_INFINITY;
                else                 adj[2] = energy[x + 1][y + 1];

                adj[1] = energy[x][y + 1];

                pixelHandler(minIndex(adj) + x - 1, y + 1);
            }


        }




    }

    // sequence of indices for horizontal seam
    public   int[] findHorizontalSeam() {

        double curMinEnergy = Double.POSITIVE_INFINITY;
        int[] curSeamPath;
        totalEnergy = 0.0;

        if (transposed) {
            curSeamPath = new int[height];
            for (int i = 0; i < width; i++) {
                totalEnergy = 0;
                pixelHandler(i,0);
                if (totalEnergy < curMinEnergy) {
                    curMinEnergy = totalEnergy;
                    System.arraycopy(verticalSeam, 0, curSeamPath, 0, height);
                }
            }

        }
        else {
            curSeamPath = new int[width];
            for (int i = 0; i < height; i++) {
                totalEnergy = 0;
                pixelHandler(0,i);
                if (totalEnergy < curMinEnergy) {
                    curMinEnergy = totalEnergy;
                    System.arraycopy(horizontalSeam, 0, curSeamPath, 0, width);
                }
            }

        }



        return curSeamPath;

    }

    // sequence of indices for vertical seam
    public   int[] findVerticalSeam() {
        transposed = true;
        verticalSeam = findHorizontalSeam();
        transposed = false;

        return verticalSeam;
    }

    // remove horizontal seam from picture
    public    void removeHorizontalSeam(int[] a) {

        r = resizeHorizontalInt(r, a);
        g = resizeHorizontalInt(g, a);
        b = resizeHorizontalInt(b, a);

        energy = resizeHorizontaldouble(energy, a);

        height -= 1;

    }




    // remove vertical seam from picture
    public    void removeVerticalSeam(int[] a) {

        r = resizeVerticalInt(r, a);
        g = resizeVerticalInt(g, a);
        b = resizeVerticalInt(b, a);

        energy = resizeVerticalDouble(energy, a);

        width -= 1;

    }

    private int[][] resizeHorizontalInt(int[][] arr, int a[]) {
        int[][] tmp = new int[width][height - 1];
        if (a.length != width) throw new RuntimeException();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height - 1; j++) {
                if (j < a[i]) {
                    tmp[i][j] = arr[i][j];
                }
                else {
                    tmp[i][j] = arr[i][j + 1];
                }

            }
        }
        return tmp;
    }

    private double[][] resizeHorizontaldouble(double [][] arr, int a[]) {
        double [][] tmp = new double[width][height - 1];
        if (a.length != width) throw new RuntimeException();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height - 1; j++) {
                if (j < a[i]) {
                    tmp[i][j] = arr[i][j];
                }
                else {
                    tmp[i][j] = arr[i][j + 1];
                }

            }
        }
        return tmp;
    }

    private int[][] resizeVerticalInt(int[][] arr, int a[]) {
        int[][] tmp = new int[width - 1][height];
        if (a.length != height) throw new RuntimeException();

        for (int i = 0; i < width - 1; i++) {
            for (int j = 0; j < height; j++) {
                if (i < a[j]) {
                    tmp[i][j] = arr[i][j];
                }
                else {
                    tmp[i][j] = arr[i + 1][j];
                }

            }
        }
        return tmp;
    }

    private double[][] resizeVerticalDouble(double [][] arr, int a[]) {
        double [][] tmp = new double[width - 1][height];
        if (a.length != height) throw new RuntimeException();

        for (int i = 0; i < width - 1; i++) {
            for (int j = 0; j < height; j++) {
                if (i < a[j]) {
                    tmp[i][j] = arr[i][j];
                }
                else {
                    tmp[i][j] = arr[i + 1][j];
                }

            }
        }
        return tmp;
    }



}