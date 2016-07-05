/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package convexhull;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

/**
 *
 * @author Admin
 */
public class ConvexHullGui extends Application {
    
    Canvas canvas = new Canvas(500, 500);
    Button btn = new Button("go");
    GraphicsContext g = canvas.getGraphicsContext2D();
    ArrayList<Point> l = new ArrayList<>();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println(System.getProperty("user.dir"));
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        FlowPane rootNode = new FlowPane(10, 10);
        rootNode.setAlignment(Pos.BOTTOM_CENTER);
        Scene scene = new Scene(rootNode, 500, 500);
        
        rootNode.getChildren().addAll(canvas);
        primaryStage.setScene(scene);
        scene.setOnMouseClicked((e) -> go(e.getX(), e.getY()));
        scene.setOnMouseExited((e) -> {
            l.clear();
            clear();
        });
        primaryStage.show();
    }
    
    private void go(double x, double y) {
        clear();
        g.fillOval(x, y, 10, 10);
        l.add(new Point((int) x, (int) y));
        if (l.size() < 3) {
            return;
        }
        System.out.println(l.size());
        ConvexHull h = new ConvexHull(new ArrayList<>(l));
        h.findConvexHullPoints();
        ArrayList<Point> drawMe = h.getAns();
        for (int i = 0; i < l.size(); i++) {
            Point p1 = l.get(i);
            g.fillOval(p1.getX(), p1.getY(), 10, 10);
        }
        for (int i = 0; i < drawMe.size() - 1; i++) {
            g.strokeLine(drawMe.get(i).getX(), drawMe.get(i).getY(), drawMe.get(i + 1).getX(), drawMe.get(i + 1).getY());
        }
         g.strokeLine(drawMe.get(0).getX(), drawMe.get(0).getY(), drawMe.get(drawMe.size()-1).getX(), drawMe.get(drawMe.size()-1).getY());
    }
    
    private void clear() {
        g.clearRect(0, 0, 500, 500);
    }
    
}
