/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Arrays;
import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // compare points by slope
    public  final Comparator<Point> SLOPE_ORDER = new SlopeOrder();       // YOUR DEFINITION HERE

    private  class SlopeOrder implements Comparator<Point>
    {
        public int compare(Point a, Point b) {
            if (Point.this.slopeTo(a) < Point.this.slopeTo(b)) return -1;
            if (Point.this.slopeTo(a) > Point.this.slopeTo(b)) return +1;
            return 0;
        }
    }



    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        // slope point to itself
        if ((this.x == that.x) && (this.y == that.y)) return Double.NEGATIVE_INFINITY;

        double dx = that.x - this.x;
        double dy = that.y - this.y;

        // if horizontal
        if ((dy == 0.0) || (dy == -0.0)) return +0.0;
        // if vertical
        if ((dx == 0.0) || (dx == -0.0)) return Double.POSITIVE_INFINITY;
        // normal slope
        return dy/dx;
}

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        if (this.y < that.y) return -1;
        if (this.y > that.y) return +1;
        if (this.x < that.x) return -1;
        if (this.x > that.x) return +1;
        return 0;
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
        Point[] points = new Point[17];
        double[] slope = new double[17];
        int k = 0;

        points[0] = new Point(0, 0);
        points[1] = new Point(1, 0);
        points[2] = new Point(2, 0);
        points[3] = new Point(3, 0);
        points[4] = new Point(4, 0);
        points[5] = new Point(0, 1);
        points[6] = new Point(4, 1);
        points[7] = new Point(0, 2);
        points[8] = new Point(2, 2);
        points[9] = new Point(4, 2);
        points[10] = new Point(0, 3);
        points[11] = new Point(4, 3);
        points[12] = new Point(0, 4);
        points[13] = new Point(1, 4);
        points[14] = new Point(2, 4);
        points[15] = new Point(3, 4);
        points[16] = new Point(4, 4);

        for (int i = 0; i < points.length; i++) {
            slope[k] = points[1].slopeTo(points[i]);
            System.out.print(slope[k] + " ");
            k++;

        }

        System.out.println("By coordinates:");
        Arrays.sort(points);
        for (int ll = 0; ll < points.length; ll++) {
            System.out.print(points[ll] + " ");
        }
        System.out.print("\r\n");

        System.out.println("By slope 0 :");
        Arrays.sort(points,points[0].SLOPE_ORDER);
        for (int ll = 0; ll < points.length; ll++) {
            System.out.print(points[ll] + " ");
        }
        System.out.print("\r\n");

        System.out.println("By slope 1 :");
        Arrays.sort(points,points[1].SLOPE_ORDER);
        for (int ll = 0; ll < points.length; ll++) {
            System.out.print(points[ll] + " ");
        }
        System.out.print("\r\n");

    }
}
