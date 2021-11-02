package model;

import java.util.ArrayList;
import java.util.List;

public class Polygon {
    private List<Point> points;

    public Polygon(){
        this.points = new ArrayList<>();
    }

    public Polygon(Point a, Point b, Point c) {
        this.points = new ArrayList<>();
        points.add(a);
        points.add(b);
        points.add(c);
    }

    public void addPoint(Point point) {
        points.add(point);
    }
    public List<Point> getPoints(){
        return points;
    }

    public void deleteList(){
        points.clear();
    }
    public Point getPoint(int index){
        return points.get(index);
    }
    public Point getLastPoint(){
        return points.get(points.size()-1);
    }
}
