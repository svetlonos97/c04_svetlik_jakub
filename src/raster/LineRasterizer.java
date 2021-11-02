package raster;


import model.Line;
import model.Point;
import model.Polygon;
import model.Triangle;

import java.util.List;


public abstract class LineRasterizer {
    protected Raster raster;
    public void rasterize(int x1, int y1, int x2, int y2){
        rasterize(x1,y1,x2,y2, 0xffff00);
    }

  /*  public void rasterize(Point a, Point b){

    }*/

    public void rasterize(Polygon polygon){
        List<Point> points = polygon.getPoints();
        for (int i=0;i< points.size();i++){
            if(i==points.size()-1) {
                rasterize(points.get(i).getX(),points.get(i).getY(), points.get(0).getX(),points.get(0).getY());
            }
            else{
                rasterize(points.get(i).getX(),points.get(i).getY(), points.get(i + 1).getX(), points.get(i + 1).getY());
            }
        }
    }



    public void rasterize(Line line){
        rasterize(line.getX1(), line.getY1(),line.getX2(),line.getY2());
    }

    public void rasterize(Line line, int color){
        rasterize(line.getX1(), line.getY1(),line.getX2(),line.getY2(),color);
    }

    public void rasterize(Point a, Point b){
        rasterize(a.getX(),a.getY(),b.getX(),b.getY());
    }

    public void rasterize(Triangle triangle){
        rasterize(new Polygon(triangle.getA(), triangle.getB(), triangle.getC()));
    }

   /* public void rasterize(Polyline polyline){

    }*/

    public void rasterize(int x1, int y1, int x2, int y2, int color){

    }

}
