package model;

public class Triangle {
    private Point a, b, c;

    public Triangle(){
    }

    public void setA(Point a){
        this.a = a;
    }

    public void setB(Point b){
        this.b = b;
    }
    public void setC(Point c){
        this.c = c;
    }
    public Point getA(){
        return a;
    }
    public Point getB(){
        return b;
    }
    public Point getC(){
        return c;
    }
    public Point calculateC(Point mouse){
        double midPointX = (getA().getX() + getB().getX())/2;
        double midPointY = (getA().getY() + getB().getY())/2;
        double vectorX = mouse.getX() - midPointX;
        double vectorY = mouse.getY() - midPointY;
        double angle = Math.atan(vectorY/vectorX);
        double vectorASX = midPointX - getA().getX();
        double vectorASY = midPointY - getA().getY();
        double r = Math.sqrt(vectorASX*vectorASX+vectorASY*vectorASY);
        double cX = midPointX + r * Math.cos(angle);
        double cY = midPointY + r * Math.sin(angle);
        return new Point((int)cX, (int)cY);
    }

  }
