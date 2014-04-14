package basicgraphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Korisnik
 */
public class Board extends JPanel implements Runnable {

    /**
     * Širina table
     */
    public final int PANEL_WIDTH = 600;
    /**
     * Visina table
     */
    public final int PANEL_HEIGHT = 600;
    
    final Color BACKGROUND_COLOR = Color.LIGHT_GRAY;
    final Thread runner;
    
    // Bodovi u igri
    
    int upperScore, lowerScore;
    
    Boolean inGame;
    
    // Brojanje udara lopte zbog ubrzavanja
    
    int hitCounter;
    
    // Objekti u igri
    
    Ball ball;
    Pad upper, lower;
    
    String winMessage;
    
    /**
     * Podrazumjevani konstruktor. Postavlja veličinu table, boju pozadine i font,
     * inicijalizuje početni rezultat, te objekte u igri. Inicijalizuje i pokreće
     * radnu nit.
     */
    public Board() {
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBackground(BACKGROUND_COLOR);
        setFocusable(true);
        setFont(getFont().deriveFont(Font.BOLD, 18f));
        setDoubleBuffered(true);
        
        upperScore = lowerScore = hitCounter = 0;
        inGame = false;
        
        ball = new Ball(this);
        upper = new Pad(this, PANEL_WIDTH/2 - Pad.w/2, 0);
        lower = new Pad(this, PANEL_WIDTH/2 - Pad.w/2, PANEL_HEIGHT - Pad.h);
        
        addKeyListener(new GameKeyAdapter());
        
        runner = new Thread(this);
    }
    
    /**
     * Dodaje bod donjem igraču
     */
    public void lowerScored() {
        lowerScore++;
        hitCounter = 0;
        
        if (lowerScore == 5)
            stopGame("Lower wins!");
    }
    
    /**
     * Dodaje bod gornjem igraču
     */
    public void upperScored() {
        upperScore++;
        hitCounter = 0;
        
        if (upperScore == 5)
            stopGame("Upper wins!");
    }

    public void startGame() {
        inGame = true;
        runner.start();
    }
    
    public void stopGame(String message) {
        inGame = false;
        winMessage = message;
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        Graphics2D g2 = (Graphics2D) g;
        
        // Savjeti pri iscrtavanju
        
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Iscrtaj teren
        
        g2.drawRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
        g2.drawLine(0, PANEL_HEIGHT/2, PANEL_WIDTH, PANEL_HEIGHT/2);
        
        // Iscrtaj sve objekte
        
        ball.draw(g2);
        upper.draw(g2);
        lower.draw(g2);
        
        // Iscrtaj rezultat
        
        g2.drawString("" + upperScore, PANEL_WIDTH/6, PANEL_HEIGHT*2/6);
        g2.drawString("" + lowerScore, PANEL_WIDTH/6, PANEL_HEIGHT*4/6);
        
        // Sinhronizovanje sa grafičkom kartom
        Toolkit.getDefaultToolkit().sync();
        
        // Optimizacija upotrebe RAM-a
        g.dispose();
    }
    
    private void update() {
        ball.move();
        upper.move();
        lower.move();
    }
    
    private void detectCollision() {
        if (ball.intersects(upper) || ball.intersects(lower)) {
            ball.bouceVertical();
            hitCounter++;
            
            // Svaki peti udar lopta se ubrzava
            if (hitCounter % 5 == 0)
                ball.speedUp();
        }
    }

    long counter = 0;
    
    @Override
    public void run() {        
        while(true) {
            if (inGame) {
                counter++;
                update();
                detectCollision();
                repaint();

                try {
                    Thread.sleep(20);
                } catch (InterruptedException ex) {
                    System.out.println(ex.toString());
                }
            } else {
                JOptionPane.showMessageDialog(this, winMessage);
                runner.stop();
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
