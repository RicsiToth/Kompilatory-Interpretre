package main;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Turtle {

    private Canvas canvas;
    private GraphicsContext gc;
    private double x ;
    private double y;
    private double angle;

    public Turtle(Canvas canvas, double startX, double startY, double angle) {
        this.canvas = canvas;
        this.x = startX;
        this.y = startY;
        this.angle = angle;
        gc = this.canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
    }

    public void forward(double length) {
        double nextX = x + length * Math.sin(Math.PI * 2 * angle / 360);
        double nextY = y - length * Math.cos(Math.PI * 2 * angle / 360);
        gc.strokeLine(x, y, nextX, nextY);
        x = nextX;
        y = nextY;
    }

    public void turnRight(double angle) {
        this.angle += angle;
    }

    public void turnLeft(double angle) {
        this.angle -= angle;
    }

    public void drawSquare(double length) {
        for(int i = 0; i < 4; i++) {
            forward(length);
            turnRight(90);
        }
    }

    public void drawTriangle(double length) {
        forward(length);
        turnRight(120);
        forward(length);
        turnRight(120);
        forward(length);
    }

    public void draw(String commands, double angle, double length, double change) {
        if(length < 1) {
            return;
        }
        for(char command : commands.toCharArray()) {
            switch (command) {
                case 'd':
                    forward(length);
                    break;
                case 'p':
                    turnRight(angle);
                    break;
                case 'l':
                    turnLeft(angle);
                    break;
                case '*':
                    double oldX = x;
                    double oldY = y;
                    double oldAngle = this.angle;
                    draw(commands, angle, length * change, change);
                    this.x = oldX;
                    this.y = oldY;
                    this.angle = oldAngle;
                    break;
                default:
                    break;
            }
        }
    }
    
    public void clean() {
    	gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    	x = canvas.getWidth() / 2;
    	y = canvas.getHeight() / 2;
    	angle = 0;
    }
    
    public void setStroke(Integer red, Integer green, Integer blue) {
    	gc.setStroke(Color.rgb(red, green, blue));
    	gc.setFill(Color.rgb(red, green, blue));
    }
    
    public void drawDot(Integer radius) {
    	gc.fillOval(x - (radius / 2), y - (radius / 2), radius, radius);
    }
}
