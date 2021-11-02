import model.Line;
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
public class CanvasDottedLine {

    private JPanel panel;
    private Raster raster;
    private List<Line> lines = new ArrayList();
    private int x1,y1;
    LineRasterizer lineRasterizer;

    public CanvasDottedLine(int width, int height) {
        raster = new RasterBufferedImage(width, height);
        JFrame frame = new JFrame();
        lineRasterizer = new DottedLineRasterizer(raster);
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
                }

                panel.repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    Line line = new Line(x1, y1, e.getX(), e.getY());
                    lines.add(line);
                    lineRasterizer.rasterize(line);

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
        SwingUtilities.invokeLater(() -> new CanvasDottedLine(800, 600).start());
    }

}