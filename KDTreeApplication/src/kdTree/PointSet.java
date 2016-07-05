package kdTree;

import edu.princeton.cs.algs4.Point2D;

public class PointSet {
	private KDTree2 tree;

	public PointSet() {
		tree = new KDTree2();
	}

//	public boolean isEmpty() {
//		return tree.isEmpty();
//	}

	// public int size() {
	//
	// }

	public void insert(Point point) {
		tree.put(point);
	}

//	public boolean contains(Point2D point) {
//		return tree.contains(point);
//	}

	public void draw() {
		
	}

//	public Iterable<Point2D> range(RectHV rect) {
//
//	}

	public Point nearest(Point p) {
		return tree.nearestPoint(p);
	}
}
