package drawing;

import java.io.Serializable;

public class Tracker implements Serializable {

    double x;
    double y;
    String text;
    String shape;
    Double[] points;

    public Tracker(double x, double y, String text, String shape) {
        this.x = x;
        this.y = y;
        this.text = text;
        this.shape = shape;
    }

    public Tracker(Double[] points) {
        this.points = points;
    }

    public String getShape() {
        return shape;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public String getText() {
        return text;
    }
}
