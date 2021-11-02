package fill;

import model.Line;
import model.Polyline;
import raster.Raster;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


public class ScanLineFiller  extends Filler{
    Polyline polyline;
    int ymin = Integer.MAX_VALUE;
    int ymax;
    ArrayList<Line> helpLine = new ArrayList<>();
    ArrayList<Integer> prus = new ArrayList<>();


    public ScanLineFiller(Raster raster) {
        super(raster);
    }

    public void setPolyline(Polyline polyline){
        this.polyline = polyline;
    }
    public Polyline getPolyline(){
        return polyline;
    }
    @Override
    public void fill() {

        for(int i=0;i<polyline.getSize();i++) {
            if (polyline.getLine(i).getY1() != polyline.getLine(i).getY2()) {    // není vodorovná ?
                Line u = new Line(polyline.getLine(i));

                u.prohod();
                u.vypocti();
                u.zkrat();
                helpLine.add(u);
                if (ymin > u.getY1()) {
                    ymin = u.getY1();
                }
                if (ymax < u.getY2()) {
                    ymax = u.getY2();
                }
            }
        }
        for(int y=ymin; y<ymax;y++){
            prus.clear();
            for(Line helpLine : helpLine){
                if (helpLine.jePrusecik(y)){
                    prus.add(helpLine.prusecik(y));
                }
            }

            Collections.sort(prus);

            int i = 1;
            while(i<prus.size()){
                for(int x = prus.get(i-1); x<= prus.get(i);x++){
                    int k = (x % 40);
                    int m = (y % 40);
                    int barva = vzor(k,m);
                    raster.setPixel(x, y, 0x000000FF);
                }
                i+=2;
            }

        }


        //nalez. min. a max y
    }

    public int vzor(int i, int j){
        if (j <= 5 && i >5 && i<50 ){
            return new Color(100,42,42).getRGB();
        }

        else{
            return 0x000000FF;
        }
    }


}
