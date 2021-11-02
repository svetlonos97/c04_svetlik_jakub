package raster;

import model.Line;

public class LineRasterizerTrivial extends LineRasterizer{

    public LineRasterizerTrivial(Raster raster) {
        super.raster = raster;

    }

    public void setRaster(Raster raster) {
        super.raster = raster;
    }

    public void rasterize(Line line){
        rasterize(line.getX1(), line.getY1(),line.getX2(),line.getY2());
    }

    @Override
    public void rasterize(int x1, int y1, int x2, int y2, int color) {

        float k = (float)(y2 -y1)/(x2 -x1);
        float q = y1 -(x1*k);

        for(int i = x1; i<= x2; i++){
            int y = (int)(k*i+q);
            raster.setPixel(i, y, color);

        }
    }
}
