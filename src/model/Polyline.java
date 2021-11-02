package model;

import java.util.ArrayList;
import java.util.List;

public class Polyline {
    private List<Line> lines;

    public Polyline(List<Line> lines) {
        this.lines = lines;
    }

    public Line getLine(int i) {
        return lines.get(i);
    }

    public int getSize(){
        return lines.size();
    }

}
