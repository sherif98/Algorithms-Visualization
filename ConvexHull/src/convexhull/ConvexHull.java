/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package convexhull;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public final class ConvexHull {

    private ArrayList<Point> points;
    private ArrayList<Point> ans;
    private Point minimum;

    public ConvexHull(ArrayList<Point> points) {
        if (points == null) {
            throw new NullPointerException("no points available");
        }
        this.points = points;
        minimum = Collections.min(points);
        points.remove(minimum);
        findConvexHullPoints();
    }

    public void findConvexHullPoints() {
        Point.CompareByPolarOrder comparator = new Point.CompareByPolarOrder(minimum);
        Collections.sort(points, comparator);
        ans = solve();
    }

    private ArrayList<Point> solve() {
        ArrayDeque<Point> stack = new ArrayDeque<Point>();
        stack.push(minimum);
        stack.push(points.get(0));
        for (int i = 1; i < points.size(); i++) {
            Point tmp = stack.pop();
            while (ccw(stack.peek(), tmp, points.get(i)) <= 0) {
                tmp = stack.pop();
            }
            stack.push(tmp);
            stack.push(points.get(i));

        }
        ArrayList<Point> l = new ArrayList<Point>(stack);
        return l;
    }

    private int ccw(Point a, Point b, Point c) {
        int direction1 = (b.getX() - a.getX()) * (c.getY() - a.getY());
        int direction2 = (b.getY() - a.getY()) * (c.getX() - a.getX());
        return direction1 - direction2;
    }

    public ArrayList<Point> getAns() {
        return ans;
    }

}

class Point implements Comparable<Point> {

    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getY() {
        return this.y;
    }

    public int getX() {
        return this.x;
    }

    @Override
    public int compareTo(Point that) {
        if (this.y == that.y) {
            return that.x - this.x;
        }
        return this.y - that.y;
    }

    @Override
    public String toString() {
        return "x = " + x + " y = " + y + "\n";
    }

    public static class CompareByPolarOrder implements Comparator<Point> {

        Point referencePoint;

        public CompareByPolarOrder(Point referencePoint) {
            this.referencePoint = referencePoint;
        }

        @Override
        public int compare(Point arg0, Point arg1) {
            double slope1 = getSlope(referencePoint, arg0);
            double slope2 = getSlope(referencePoint, arg1);
            double angle1 = slope1 <= 0 ? 180 + Math.toDegrees(Math.atan(slope1)) : Math.toDegrees(Math.atan(slope1));
            double angle2 = slope2 <= 0 ? 180 + Math.toDegrees(Math.atan(slope2)) : Math.toDegrees(Math.atan(slope2));
            if (angle1 - angle2 > 0) {
                return 1;
            }
            if (angle1 - angle2 < 0) {
                return -1;
            }

            if (arg0.x == arg1.x) {
                return arg0.y - arg1.y;
            }
            return arg0.x - arg1.x;
        }

        private double getSlope(Point one, Point two) {
            double numerator = one.y - two.y;
            double denomerator = one.x - two.x;
            if (denomerator == 0) {
                return Double.MAX_VALUE;
            }
            return numerator / denomerator;
        }

    }

}
