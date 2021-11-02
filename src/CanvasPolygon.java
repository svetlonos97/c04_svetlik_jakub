import model.Line;
import model.Point;
import model.Polygon;
import model.Triangle;
import raster.*;


import java.awt.*;
import java.awt.event.*;
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
public class CanvasPolygon {

    private JPanel panel;
    private Raster raster;
    private int x1,y1;
    private Point startPoint;
    private boolean isActivated;
    LineRasterizer lineRasterizer;
    Polygon polygon;
    Triangle triangle;
    List<Triangle> triangles;

    public CanvasPolygon(int width, int height) {
        raster = new RasterBufferedImage(width, height);
        JFrame frame = new JFrame();
        lineRasterizer = new FilledLineRasterizer(raster);

        frame.setLayout(new BorderLayout());
        frame.setTitle("UHK FIM PGRF : " + this.getClass().getName());
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        polygon = new Polygon();
        triangle = new Triangle();
        triangles = new ArrayList<>();
        panel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                present(g);
            }
        };
        panel.setPreferredSize(new Dimension(width, height));
        panel.setFocusable(true);
        panel.requestFocusInWindow();


        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    redraw();
                    if(isActivated == false) {
                        if (polygon.getPoints().isEmpty()) {
                            x1 = e.getX();
                            y1 = e.getY();
                            startPoint = new Point(x1, y1);
                            polygon.addPoint(startPoint);
                            raster.setPixel(e.getX(), e.getY(), 0xffff00);
                        }
                    }
                    else {
                        if(triangle.getA() == null) {
                            triangle.setA(new Point(e.getX(), e.getY()));
                        }
                    }


                }

                panel.repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(e.getX()<width && e.getY()<height && e.getX()>0 && e.getY()>0) {
                    if (e.getButton() == MouseEvent.BUTTON1) {

                        if (isActivated == false) { // Standardni mod, tedy Polygon
                            clear();
                           // redraw();
                            polygon.addPoint(new Point(e.getX(), e.getY()));
                            lineRasterizer.rasterize(polygon);
                        } else {
                            if(triangle.getB() == null) {
                                clear();
                                //redraw();
                                triangle.setB(new Point(e.getX(), e.getY()));
                                lineRasterizer.rasterize(triangle.getB().getX(), triangle.getB().getY(), triangle.getA().getX(), triangle.getA().getY(), 0xFF008000);
                            }
                            else {
                                triangle.setC(triangle.calculateC(new Point(e.getX(), e.getY())));
                                triangles.add(triangle);
                                lineRasterizer.rasterize(triangle);
                                triangle = new Triangle();
                            }
                        }
                        redraw();
                    }
                }

                panel.repaint();
            }
        });
        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                if(e.getX()<width && e.getY()<height && e.getX()>0 && e.getY()>0) {
                    if (isActivated == false) { // Standardni mod, tedy Polygon
                        clear();
                        redraw();
                        lineRasterizer.rasterize(e.getX(), e.getY(), x1, y1, 0xFFFF0000);
                        lineRasterizer.rasterize(e.getX(), e.getY(), polygon.getLastPoint().getX(), polygon.getLastPoint().getY(), 0xFFFF0000);
                        panel.repaint();
                    } else {
                        clear();
                        if(triangle.getB() == null) {
                            lineRasterizer.rasterize(triangle.getA(), new Point(e.getX(),e.getY()));

                        }
                        else{

                            lineRasterizer.rasterize(new Polygon(triangle.getA(),triangle.getB(),triangle.calculateC(new Point(e.getX(),e.getY()))));
                        }

                    }
                }
                redraw();
            }

        });

        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyChar() == 'c'){
                    clear();
                    panel.repaint();
                    polygon.deleteList();
                    triangles.clear();

                }
                if (e.getKeyChar() == 't'){
                    switchActivation();
                }


            }
        });
    }

    public boolean switchActivation(){
        return isActivated = !isActivated;
    }

    public void clear() {
        raster.clear();
    }

    public void redraw(){
        lineRasterizer.rasterize(polygon);
        panel.repaint();
        for (Triangle triangle : triangles) {
             lineRasterizer.rasterize(triangle);
        }
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
        SwingUtilities.invokeLater(() -> new CanvasPolygon(800, 600).start());
    }

}