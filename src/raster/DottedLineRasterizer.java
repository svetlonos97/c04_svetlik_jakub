package raster;

import model.Line;
import raster.LineRasterizer;

public class DottedLineRasterizer extends LineRasterizer {

    public DottedLineRasterizer(Raster raster) {
        super.raster = raster;
    }

    public void rasterize(Line line){
        rasterize(line.getX1(), line.getY1(),line.getX2(),line.getY2());
    }

    @Override
    public void rasterize(int x1, int y1, int x2, int y2, int color) {
        int dotsShift = 10;
        float k = (float)(y2 -y1)/(x2 -x1);
        float g,h;

        float x = x1;
        float y = y1;
        if (Math.abs((x2-x1))>Math.abs((y2-y1))){
            if (x1>x2) {
                g = -dotsShift;
                h = -dotsShift*k;
            }
            else {
                g = dotsShift;
                h = dotsShift*k;
            }
        }
        else {
            if (y1>y2){
                g = -(dotsShift/k);
                h= -dotsShift;
            }
            else{
                g = dotsShift/k;
                h = dotsShift;
            }

        }
        for (int i = 0; i <= Math.max((Math.abs(x2 - x1)/dotsShift), (Math.abs(y2 - y1)/dotsShift)); i++) {

            raster.setPixel((int) x, (int) y, color);
            System.out.println(k);
            System.out.println("G je"+g+"a H je "+h);
            x = x + g;
            y = y + h;
        }

    }

}
