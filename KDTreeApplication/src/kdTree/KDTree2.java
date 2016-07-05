/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kdTree;

 enum SplitLine {
    HORIZONTAL, VERTICAL;

    public SplitLine next() {
        if (this.equals(HORIZONTAL)) {
            return VERTICAL;
        }
        return HORIZONTAL;
    }
}

 enum whereToGo {
    LEFT_BOTTOM, RIGHT_UP, STAY
}

 class Node {

    Point point;
    SplitLine splitLine;
    Node leftBottom;
    Node rightUp;

    public Node(Point point, SplitLine splitLine) {
        this.point = point;
        this.splitLine = splitLine;
    }
}

public class KDTree2 {

    public Node root;

    public void put(Point point) {
        root = put(root, point, SplitLine.VERTICAL);
    }

    private Node put(Node root, Point point, SplitLine splitLine) {
        if (root == null) {
            return new Node(point, splitLine);
        }
        whereToGo destination = findDestination(root, point, splitLine);
        if (destination == whereToGo.LEFT_BOTTOM) {
            root.leftBottom = put(root.leftBottom, point, splitLine.next());
        } else if (destination == whereToGo.RIGHT_UP) {
            root.rightUp = put(root.rightUp, point, splitLine.next());
        } else {
            return root;
        }
        return root;

    }

    private whereToGo findDestination(Node root, Point point, SplitLine splitLine) {
		if (splitLine == SplitLine.VERTICAL) {
			if (point.getX() - root.point.getX() <= 0) {
				return whereToGo.LEFT_BOTTOM;
			} else /* (point.getX() - root.point.getX() > 0) */ {
				return whereToGo.RIGHT_UP;
			}
			// if(point.getY() - root.point.getY() )
			// return whereToGo.STAY;
		} else {
			if (point.getY() - root.point.getY() <= 0) {
				return whereToGo.LEFT_BOTTOM;
			} else /* if (point.getY() - root.point.getY() > 0) */ {
				return whereToGo.RIGHT_UP;
			}
			// return whereToGo.STAY;
		}
	}

    public Point nearestPoint(Point point) {

        return nearestPoint(root, point, Double.MAX_VALUE);
    }

    private Point nearestPoint(Node root, Point point, double radius) {
		if (root == null) {
			return null;
		}
		double newRadius = point.distanceTo(root.point);
		if (newRadius > radius) {
			if (findDestination(root, point, root.splitLine) == whereToGo.LEFT_BOTTOM) {
				return nearestPoint(root.leftBottom, point, radius);
			} else {
				return nearestPoint(root.rightUp, point, radius);
			}
		} else {
			if (findDestination(root, point, root.splitLine) == whereToGo.LEFT_BOTTOM) {
				Point tmp = nearestPoint(root.leftBottom, point, newRadius);
				Point tmp2 = null;
				if (tmp != null) {
					if (intersectWithSplittingLine(tmp, point, root)) {
						tmp2 = nearestPoint(root.rightUp, point, newRadius);
					}
				} else {
					return root.point;
				}
				return tmp2 != null ? tmp2 : tmp;
			} else {
				Point tmp = nearestPoint(root.rightUp, point, newRadius);
				Point tmp2 = null;
				if (tmp != null) {
					if (intersectWithSplittingLine(tmp, point, root)) {
						tmp2 = nearestPoint(root.leftBottom, point, newRadius);
					}
				} else {
					tmp = root.point;
				}
				return tmp2 != null ? tmp2 : tmp;
			}
		}
	}

    private boolean intersectWithSplittingLine(Point tmp, Point point, Node root) {
        if (root.splitLine == SplitLine.VERTICAL) {
            double differenceInX = tmp.getX() - root.point.getX();
            differenceInX = differenceInX > 0 ? differenceInX : -differenceInX;
            if (differenceInX > tmp.distanceTo(point)) {
                return false;
            }
            return true;

        } else {
            double differenceInY = tmp.getY() - root.point.getY();
            differenceInY = differenceInY > 0 ? differenceInY : -differenceInY;
            if (differenceInY > tmp.distanceTo(point)) {
                return false;
            }
            return true;

        }
    }
}
