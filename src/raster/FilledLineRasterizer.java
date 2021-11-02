package raster;

import model.Line;
import raster.LineRasterizer;

public class FilledLineRasterizer extends LineRasterizer {

    public FilledLineRasterizer(Raster raster) {
        super.raster = raster;
    }

    public void rasterize(Line line){
        rasterize(line.getX1(), line.getY1(),line.getX2(),line.getY2());
    }

    @Override
    public void rasterize(int x1, int y1, int x2, int y2, int color) {

        float k = (float)(y2 -y1)/(x2 -x1);
        float g,h;

        float x = x1;
        float y = y1;
        if (Math.abs((x2-x1))>Math.abs((y2-y1))){
            if (x1>x2) {
                g = -1;
                h = -k;
            }
            else {
                g = 1;
                h = k;
            }
        }
        else {
            if (y1>y2){
                g = -(1/k);
                h= -1;
            }
            else{
                g = 1/k;
                h = 1;
            }

        }
        for (int i = 0; i <= Math.max((Math.abs(x2 - x1)), (Math.abs(y2 - y1))); i++) {

            raster.setPixel((int) x, (int) y, color);
            x = x + g;
            y = y + h;
        }

    }

}
