package basicgraphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author Korisnik
 */
public class Board extends JPanel implements Runnable {

    public final int PANEL_WIDTH = 800;
    public final int PANEL_HEIGHT = 600;
    
    final Color BACKGROUND_COLOR = Color.LIGHT_GRAY;
    final Thread runner;
    
    // "Kistovi" koje ćemo da koristimo
    float[] dash = {10f, 5f, 2f, 5f};
    
    final BasicStroke STROKE_DEFAULT = new BasicStroke(2);
    final BasicStroke STROKE_TICK = new BasicStroke(5);
    final BasicStroke STROKE_DASHED = 
            new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 1,
                dash, 0f);
    
    // Bodovi u igri
    
    int upperScore, lowerScore;
    
    // Objekti u igri
    
    Ball ball;
    Pad upper, lower;
    
    public Board() {
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBackground(BACKGROUND_COLOR);
        setFocusable(true);
        
        upperScore = lowerScore = 0;
        
        ball = new Ball(this);
        upper = new Pad(this, 0, 0);
        lower = new Pad(this, 0, PANEL_HEIGHT - 20);
        
        addKeyListener(new GameKeyAdapter());
        
        runner = new Thread(this);
        runner.start();
    }
    
    public void lowerScored() {
        lowerScore++;
    }
    
    public void upperScored() {
        upperScore++;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        Graphics2D g2 = (Graphics2D) g;
        
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Iscrtaj sve objekte
        
        ball.draw(g2);
        upper.draw(g2);
        lower.draw(g2);
        
        // Iscrtaj rezultat
        
        g2.drawString("" + upperScore, 150, 150);
        g2.drawString("" + lowerScore, 150, 450);
    }
    
    private void update() {
        ball.move();
        upper.move();
        lower.move();
    }
    
    private void detectCollision() {
        if (ball.intersects(upper) || ball.intersects(lower)) {
            ball.bouceVertical();
        }
    }

    @Override
    public void run() {
        while(true) {
            update();
            detectCollision();
            repaint();
            
            try {
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private class GameKeyAdapter extends KeyAdapter {
    
        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            
            if (keyCode == KeyEvent.VK_LEFT)
                lower.moveLeft();
            else if (keyCode == KeyEvent.VK_RIGHT)
                lower.moveRight();
            else if (keyCode == KeyEvent.VK_A)
                upper.moveLeft();
            else if (keyCode == KeyEvent.VK_D)
                upper.moveRight();
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int keyCode = e.getKeyCode();
            
            if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT)
                lower.stopMoving();
            else if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_D)
                upper.stopMoving();
        }

    }
}
