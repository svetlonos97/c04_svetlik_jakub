package model;

public class Line {

    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private Line line;
    private float k,q;

    public Line(int x1, int y1, int x2, int y2){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        k = (float)(y2 -y1)/(x2 -x1);
        q = y1 -(x1*k);
    }

    public Line(Line line) {
        this(line.x1, line.y1, line.x2, line.y2);
        this.line = line;
    }

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }

    public float getK() {return k;}
    public float getQ() {return q;}

    public void prohod(){
        if(y1>y2){
            int supportY = y1;
            y1 = y2;
            y2 = supportY;
            int supportX = x1;
            x1 = x2;
            x2 = supportX;
        }
    }
    public void vypocti(){
        k = (float)(x2 -x1)/(y2 -y1);
        q = x1 - (k*y1);
    }
    public void zkrat(){
        y2--;
    }
    public boolean jePrusecik(int y){
        return (y>y1 && y<y2);
    }
    public int prusecik(int y){
        float lol = k*y+q;
        return (int)lol;
    }

}
