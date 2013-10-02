import java.io.*;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: eprib
 * Date: 12.09.13
 * Time: 21:03
 * To change this template use File | Settings | File Templates.
 */
public class Brute {
    public static void main(String[] args) throws IOException {

        // read in the input
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Point[] points = new Point[N];

        StdDraw.setXscale(0, 32767);
        StdDraw.setYscale(0, 32767);

        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
            points[i].draw();
        }


        Arrays.sort(points);

        for(int i = 0; i < points.length; i++) {
            for(int j = i + 1; j < points.length; j++) {
                for(int k = j + 1; k < points.length; k++) {
                    for(int l = k + 1; l < points.length; l++) {
                        if ((points[i].slopeTo(points[j]) == points[i].slopeTo(points[k])) &&
                           (points[i].slopeTo(points[j]) == points[i].slopeTo(points[l]))) {
                                System.out.println(points[i].toString() + " -> " + points[j].toString()+ " -> " + points[k].toString() + " -> " + points[l].toString());
                                points[i].drawTo(points[l]);
                        }
                    }
                }
            }
        }

    }
}
