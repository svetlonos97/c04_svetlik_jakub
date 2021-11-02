package fill;

import raster.Raster;

public class SeedFiller extends Filler {

    private int seedX;
    private int seedY;

    private int background;

    public SeedFiller(Raster raster) {
        super(raster);
    }

    private boolean isInside(int x, int y){
        return raster.getPixel(x,y)==background;

    }
    private void seedFill(int x, int y, int color){
        if(isInside(x, y)){
            raster.setPixel(x, y, color);
            seedFill(x+1, y, color);
            seedFill(x-1,y, color);
            seedFill(x,y+1, color);
            seedFill(x,y-1, color);

        }
    }

    public void setSeedX(int seedX) {
        this.seedX = seedX;
    }

    public void setSeedY(int seedY) {
        this.seedY = seedY;
    }

    @Override
    public void fill() {
        background = raster.getPixel(seedX,seedY);
        seedFill(seedX, seedY, fillColor);

    }


}
