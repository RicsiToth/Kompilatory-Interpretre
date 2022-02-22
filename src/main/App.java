package main;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

public class App extends Application {

    public void start(Stage stage) {
        Group group = new Group();
        // create a scene
        Scene scene = new Scene(group, 600, 400);
        Canvas canvas = new Canvas(600, 400);
        group.getChildren().add(canvas);
        // set the scene
        stage.setScene(scene);

        Turtle turtle = new Turtle(canvas, 400, 400, 0);
        //turtle.drawSquare(50);
        //turtle.drawTriangle(100);
        //Fraktal zo zadania uhol musi byt pre turtle 0
        turtle.draw("dl*pp*lz", 45, 100, 0.5);
        //Sierpinského trojuholník pociatocny uhol musi byt 90
        //turtle.draw("*d*dpd*dpdd", 120, 225, 0.5);

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
