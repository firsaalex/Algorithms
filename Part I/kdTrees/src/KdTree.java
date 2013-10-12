/**
 * Created with IntelliJ IDEA.
 * User: firsa_as
 * Date: 08.10.13
 * Time: 10:55
 * To change this template use File | Settings | File Templates.
 */
public class KdTree {

    private final static boolean HORIZONTAL = false;
    private final static boolean VERTICAL = true;

    private Node root;
    private int N;

    // Linked list helper class
    private static class Node {
        private Point2D point;      // the point
        private Node lb;            // the left/bottom subtree
        private Node rt;            // the right/top subtree
        private boolean direction;  // true if vertical, false if horizontal

        private Node (Point2D p, boolean dir) {
            this.point = p;
            this.lb = null;
            this.rt = null;
            this.direction = dir;

        }
    }

    // construct an empty set of points
    public KdTree() {
        N = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set
    public int size() {
        return N;
    }

    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (isEmpty()) {
            root = new Node(p, VERTICAL);
            N++;
        }
        else if (!contains(p)){
            double delta = 0;
            Node curNode = root;
            boolean dir;

            while (true) {
                dir = curNode.direction;
                if (dir == VERTICAL) delta = p.x() - curNode.point.x();
                else if (dir == HORIZONTAL) delta = p.y() - curNode.point.y();

                if (delta < 0) {
                    if (curNode.lb == null) {
                        curNode.lb = new Node(p, !dir);
                        break;
                    }
                    else curNode = curNode.lb;
                }
                else {
                    if (curNode.rt == null) {
                        curNode.rt = new Node(p, !dir);
                        break;
                    }
                    else curNode = curNode.rt;
                }
            }
            N++;
        }

    }



    // does the set contain the point p?
    public boolean contains(Point2D p) {
        if (isEmpty()) return false;

        Node x = root;
        while (x != null) {
            double cmp = 0;
            if (x.direction == VERTICAL) cmp = p.x() - x.point.x();
            else if (x.direction == HORIZONTAL) cmp = p.y() - x.point.y();

            if (cmp < 0)x = x.lb;
            else if (cmp == 0) {
                if((x.point.x() == p.x()) && (x.point.y() == p.y())) return true;
                else x = x.rt;
            }
            else x = x.rt;

        }
        return false;
    }

    // draw all of the points to standard draw
    public void draw() {

    }

    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        Stack<Point2D> stackOfPoints = new Stack<Point2D>();

        checkNode(root, rect, stackOfPoints);


        return stackOfPoints;
    }

    private void checkNode(Node node, RectHV rect, Stack<Point2D> stack) {
        if (node != null) {
            if (rect.contains(node.point)) {
                stack.push(node.point);
            }


            if (node.direction == VERTICAL) {

                if ((node.point.x() - rect.xmax()) > 0) {
                    // no need to search right
                    if (node.lb != null) checkNode(node.lb, rect, stack);
                }
                else if ((node.point.x() - rect.xmin()) < 0) {
                    // no need to search right
                    if (node.rt != null) checkNode(node.rt, rect, stack);
                }
                else {
                    if (node.lb != null) checkNode(node.lb, rect, stack);
                    if (node.rt != null) checkNode(node.rt, rect, stack);
                }
            }
            else {
                if ((node.point.y() - rect.ymax()) > 0) {
                    // no need to search top
                    if (node.lb != null) checkNode(node.lb, rect, stack);
                }
                else if ((node.point.y() - rect.ymin()) < 0) {
                    // no need to search right
                    if (node.rt != null) checkNode(node.rt, rect, stack);
                }
                else {
                    if (node.lb != null) checkNode(node.lb, rect, stack);
                    if (node.rt != null) checkNode(node.rt, rect, stack);
                }

            }
        }

    }

    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D target) {
        if (isEmpty()) return null;

        return nearest(root, root.point, target);

    }

    private Point2D nearest( Node curNode,  Point2D curLeader,  Point2D target) {
        double curDist;
        double minDist = target.distanceTo(curLeader);
        double separateAxisCoord;
        double targetAxisCoord;

        if (curNode.direction == VERTICAL) {
            separateAxisCoord = curNode.point.x();
            targetAxisCoord = target.x();
        }
        else {
            separateAxisCoord = curNode.point.y();
            targetAxisCoord = target.y();
        }

        if (minDist > (curDist = target.distanceTo(curNode.point))) {
            curLeader = curNode.point;
            minDist = curDist;
        }

        Point2D nearestLB = curLeader;
        Point2D nearestRT = curLeader;


        if (targetAxisCoord + minDist > separateAxisCoord) {
            if (curNode.rt != null) nearestRT = nearest(curNode.rt, curLeader, target);
        }
        if (targetAxisCoord - minDist <= separateAxisCoord) {
            if (curNode.lb != null) nearestLB = nearest(curNode.lb, curLeader, target);
        }




        if (target.distanceTo(nearestLB) < target.distanceTo(nearestRT)) return nearestLB;
        else return nearestRT;
    }

    private boolean greaterDistance(final Point2D c, final Node n, final Point2D pt) {
        return c.distanceTo(pt) > n.point.distanceTo(pt);
    }


    public static void main(String[] args) {

        KdTree tree = new KdTree();

        tree.insert(new Point2D(0.35, 0.4));
        tree.insert(new Point2D(0.5, 0.3));
        tree.insert(new Point2D(0.15, 0.5));
        tree.insert(new Point2D(0.1, 0.1));
        tree.insert(new Point2D(0.05, 0.4));
        tree.insert(new Point2D(0.25, 0.65));
        tree.insert(new Point2D(0.4, 0.25));
        tree.insert(new Point2D(0.7, 0.7));
        tree.insert(new Point2D(0.55, 0.65));
        tree.insert(new Point2D(0.5, 0.1));
        tree.insert(new Point2D(0.65, 0.2));


        StdOut.println("Contains = "+ tree.contains(new Point2D(0.9,0.5)) + "\n");
        StdOut.println(tree.nearest(new Point2D(0.27,0.65)).toString());
    }
}
