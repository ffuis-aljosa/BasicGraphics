package basicgraphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author Korisnik
 */
public class Board extends JPanel implements Runnable {

    final int PANEL_WIDTH = 800;
    final int PANEL_HEIGHT = 600;
    
    final Color BACKGROUND_COLOR = Color.LIGHT_GRAY;
    final Thread runner;
    
    // "Kistovi" koje ćemo da koristimo
    float[] dash = {10f, 5f, 2f, 5f};
    
    final BasicStroke STROKE_DEFAULT = new BasicStroke(2);
    final BasicStroke STROKE_TICK = new BasicStroke(5);
    final BasicStroke STROKE_DASHED = 
            new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 1,
                dash, 0f);
    
    // Pravougaonik koji šeta po ekranu
    int x = 0;
    int y = 0;
    final int w = 10;
    final int h = 10;
    int dx = 5;
    int dy = 3;
    
    public Board() {
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBackground(BACKGROUND_COLOR);
        runner = new Thread(this);
        runner.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        Graphics2D g2 = (Graphics2D) g;
        
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        
        Rectangle2D.Double rect = new Rectangle2D.Double(x, y, w, h);
        g2.fill(rect);
    }
    
    private void update() {
        x += dx;
        y += dy;
        
        if (x + w >= PANEL_WIDTH || x <= 0)
            dx = -dx;
        
        if (y + h >= PANEL_HEIGHT || y <= 0)
            dy = - dy;
    }

    @Override
    public void run() {
        while(true) {
            update();
            repaint();
            
            try {
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
