import fill.ScanLineFiller;
import model.Line;
import model.Point;
import model.Polyline;
import raster.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * trida pro kresleni na platno: zobrazeni pixelu, ovladani mysi
 *
 * @author PGRF FIM UHK
 * @version 2020
 */
public class CanvasLine {

    private JPanel panel;
    private Raster raster;
    private List<Line> lines = new ArrayList();
    private List<Line> borderLines = new ArrayList();
    private int x1,y1;
    LineRasterizer lineRasterizer;
    ScanLineFiller scanLineFiller;
    private List<Point> points = new ArrayList();
    public CanvasLine(int width, int height) {
        raster = new RasterBufferedImage(width, height);
        JFrame frame = new JFrame();
        lineRasterizer = new FilledLineRasterizer(raster);
        scanLineFiller = new ScanLineFiller(raster);
        frame.setLayout(new BorderLayout());
        frame.setTitle("UHK FIM PGRF : " + this.getClass().getName());
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        panel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                present(g);
            }
        };
        panel.setPreferredSize(new Dimension(width, height));

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    x1 = e.getX();
                    y1 = e.getY();
                    raster.setPixel(e.getX(), e.getY(), 0xffff00);
                    borderLines.clear();
                }
                if (e.getButton() == MouseEvent.BUTTON2) {
                    scanLineFiller.setPolyline(new Polyline(borderLines));
                    scanLineFiller.fill();
                    System.out.println("spsadne toooo");
                }

                panel.repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    Line line = new Line(x1, y1, e.getX(), e.getY());
                    lines.add(line);
                    for (int i = 0; i<lines.size();i++){
                        Line firstLine = lines.get(i);
                        System.out.println("Rovnice primky je"+firstLine.getK()+"x+"+firstLine.getQ());
                        for (int j = 0; j<lines.size();j++){
                            if (i != j){
                                Line secondLine = lines.get(j);

                                float x = (secondLine.getQ() - firstLine.getQ()) / (firstLine.getK() - secondLine.getK());
                                float y = (secondLine.getK()* x+secondLine.getQ());

                                if(y>Math.min(firstLine.getY1(), firstLine.getY2()) && y<Math.max(firstLine.getY1(), firstLine.getY2())
                                && x>Math.min(secondLine.getX1(), secondLine.getX2()) && x<Math.max(secondLine.getX1(), secondLine.getX2())){
                                    System.out.println("Prusecik je"+(int)x+"a y je"+(int)y);
                                    raster.setPixel((int)x, (int)y, 0xff0000);
                                    points.add(new Point((int)x, (int)y));
                                }

                            }
                        }
                        if(points.size()>1){
                            for (int p = 0; p<points.size()-1; p++){
                                int x1 = points.get(p).getX();
                                int x2 = points.get(p+1).getX();
                                int y1 = points.get(p).getY();
                                int y2 = points.get(p+1).getY();
                                lineRasterizer.rasterize(x1, y1, x2, y2, 0xff0000);
                                borderLines.add(new Line(x1, y1, x2, y2));
                            }
                        }
                        points.clear();

                    }


                    //lineRasterizer.rasterize(line);

                }

                panel.repaint();
            }
        });
        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                clear();
                redraw();
                lineRasterizer.rasterize(x1, y1, e.getX(), e.getY());
            }
        });
    }

    public void clear() {
    raster.clear();
    }

    public void redraw(){

        for (int i = 0; i<lines.size();i++){
          lineRasterizer.rasterize(lines.get(i));
        }
        panel.repaint();
    }

    public void present(Graphics graphics) {
        graphics.drawImage(((RasterBufferedImage)raster).getImg(), 0, 0, null);
    }

    public void start() {
        clear();
       // img.getGraphics().drawString("Use mouse buttons", 5, img.getHeight() - 5);
        panel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CanvasLine(800, 600).start());
    }

}