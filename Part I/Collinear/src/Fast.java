import java.io.*;
import java.util.Arrays;


/**
 * Created with IntelliJ IDEA.
 * User: eprib
 * Date: 12.09.13
 * Time: 22:03
 * To change this template use File | Settings | File Templates.
 */
public class Fast {


    public static  void main (String[] args) throws IOException {

        // read in the input
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();

        Point[] points = new Point[N];
        Point[] tempPoints = new Point[N];
        Point[] out = new Point[N];
        Point[] startPoint = new Point[N];
        Point[] endPoint = new Point[N];
        int pointCntr = 0;
        boolean flag = false;

        double[] slope = new double[N];


        int cntr = 0;

        // draw all points
        StdDraw.setXscale(0, 32767);
        StdDraw.setYscale(0, 32767);

        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
            points[i].draw();
        }

        // copy data to temp array
        for(int  i = 0; i < points.length ;  i++) {
            tempPoints[i] = points[i];
        }

        for(int i = 0; i < points.length; i++) {
            // sort points by slope
            Arrays.sort(points, 0, points.length, tempPoints[i].SLOPE_ORDER);
            cntr=0;
            // generate slope array
            slope[0] = points[0].slopeTo(points[0]);
            for (int l = 1; l < slope.length; l++) {

                slope[l] = points[0].slopeTo(points[l]);

                // check if slopes are same
                if (slope[l-1] == slope[l]) {
                    cntr++;
                    // if last element in array and cntr >=3
                    if((l == points.length-1) && (cntr>=2)){

                        // copy points in out array to sort them
                        for(int cc = 0; cc < cntr+1; cc++) {
                            out[cc]=points[l-cntr+cc];
                        }
                        out[cntr+1]=points[0];
                        // sort out array
                        Arrays.sort(out,0,cntr+2);


                        flag = false;
                        for(int cc = 0; cc < pointCntr; cc++) {
                            if((startPoint[cc] == out[0]) && (endPoint[cc] == out[cntr+1])) {
                                flag = true;
                                break;
                            }
                        }

                        if (!flag) {
                            // draw line
                            out[0].drawTo(out[cntr+1]);
                            // print results
                            for(int cc = 0; cc < cntr+2; cc++) {
                                System.out.print(out[cc].toString());
                                if(cc == cntr+1) System.out.print("\r\n");
                                else System.out.print(" -> ");
                            }

                            startPoint[pointCntr]= out[0];
                            endPoint[pointCntr]=out[cntr+1];
                            pointCntr++;
                        }
                    }
                }
                else {
                    if(cntr >= 2){
                        for(int cc = 0; cc < cntr+1; cc++) {
                            out[cc]=points[l-1-cntr+cc];
                        }
                        out[cntr+1]=points[0];

                        Arrays.sort(out,0,cntr+2);

                        flag = false;
                        for(int cc = 0; cc < pointCntr; cc++) {
                            if((startPoint[cc] == out[0]) && (endPoint[cc] == out[cntr+1])) {
                                flag = true;
                                break;
                            }
                        }

                        if (!flag) {
                            out[0].drawTo(out[cntr+1]);
                            for(int cc = 0; cc < cntr+2; cc++) {
                                System.out.print(out[cc].toString());
                                if(cc == cntr+1) System.out.print("\r\n");
                                else System.out.print(" -> ");
                            }
                            startPoint[pointCntr]= out[0];
                            endPoint[pointCntr]=out[cntr+1];
                            pointCntr++;
                        }
                    }
                    cntr = 0;
                }

            }

        }

    }
}
