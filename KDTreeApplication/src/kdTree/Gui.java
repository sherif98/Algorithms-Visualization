package kdTree;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Gui extends Application {

    Canvas canvas = new Canvas(500, 500);
    GraphicsContext g = canvas.getGraphicsContext2D();
    ArrayList<Point> list = new ArrayList<>();
    double prevX, prevY;
    boolean start = true;
    KDTree2 tree = new KDTree2();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FlowPane rootNode = new FlowPane(10, 10);
        Scene scene = new Scene(rootNode, 500, 500);
        rootNode.getChildren().add(canvas);
        primaryStage.setScene(scene);
        scene.setOnMouseClicked((e) -> {
            if (e.getButton() == MouseButton.SECONDARY) {
                colorNearest(e.getX(), e.getY());
            } else {
                printDot(e.getX(), e.getY());
            }
        });
        primaryStage.show();
    }

    private void colorNearest(double x, double y) {
        start = false;
        g.setFill(Color.BLACK);
        g.fillOval(prevX, prevY, 10, 10);
        g.setFill(Color.RED);
        Point tmp = tree.nearestPoint(new Point(x, y));
        System.out.println(tmp);
        g.fillOval(tmp.getX(), tmp.getY(), 10, 10);
        prevX = tmp.getX();
        prevY = tmp.getY();
    }

    private void printDot(double x, double y) {
        g.setFill(Color.BLACK);
        g.fillOval(x, y, 10, 10);
        tree.put(new Point(x, y));
        visualizeTree();
    }

    private void visualizeTree() {
        visualizeTree(tree.root, 500, 0, 0, 500);
    }

    private void visualizeTree(Node root, double oneX, double oneY,
            double twoX, double twoY) {
        if (root == null) {
            return;
        }
        if (root.splitLine.equals(SplitLine.VERTICAL)) {
            g.strokeLine(root.point.getX(), oneY, root.point.getX(), twoY);
            visualizeTree(root.leftBottom, root.point.getX(), oneY, twoX, twoY);
            visualizeTree(root.rightUp, oneX, oneY, root.point.getX(), twoY);
        } else {
            g.strokeLine(twoX, root.point.getY(), oneX, root.point.getY());
            visualizeTree(root.rightUp, oneX, root.point.getY(), twoX, twoY);
            visualizeTree(root.leftBottom, oneX, oneY, twoX, root.point.getY());
        }

    }
}
