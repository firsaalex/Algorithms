import java.util.TreeSet;

/**
 * Created with IntelliJ IDEA.
 * User: firsa_as
 * Date: 08.10.13
 * Time: 10:55
 * To change this template use File | Settings | File Templates.
 */
public class PointSET {

    private TreeSet<Point2D> set;


    // construct an empty set of points
    public PointSET() {
        set = new TreeSet<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return set.isEmpty();
    }

    // number of points in the set
    public int size() {
        return set.size();
    }

    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p) {
        set.add(p);
    }

    // does the set contain the point p?
    public boolean contains(Point2D p) {
        return set.contains(p);
    }

    // draw all of the points to standard draw
    public void draw() {
        for (Point2D p: set) {
            StdDraw.point(p.x(),p.y());
        }
    }

    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        Stack<Point2D> stackOfPoints = new Stack<Point2D>();

        for (Point2D p: set) {
            if (rect.contains(p)) {
                stackOfPoints.push(p);
            }
        }

        return stackOfPoints;
    }

    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p) {
        if (isEmpty()) return null;

        Point2D nearest = set.first();

        for (Point2D current: set) {
            if (nearest.distanceTo(p) > current.distanceTo(p)) {
                nearest = current;
            }
        }

        return nearest;
    }
}
