package basicgraphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;

/**
 *
 * @author Korisnik
 */
public class Ball extends Rectangle.Double implements GameObject {
    private final int w = 20;
    private final int h = 20;
    
    // Minimalni, maksimalni intenzitet brzine lopte i korak ubrzanja
    private final int MIN_DX = 4;    
    private final int MIN_DY = 4;
    private final int MAX_DX = 20;    
    private final int MAX_DY = 20;
    
    private final int STEP = 3;

    // Predstavljaju intenzitet brzine po x i po y koordinati
    private int dx;
    private int dy;
    
    // Predstavljaju smjer brzine po x i po y koordinati
    private int directionX;
    private int directionY;
    
    // Kako imamo posebno smjer, posebno intenzitet brzine, lako
    // možemo ubrzavati/usporavati i odbijati loptu
    
    private Board board;
    private Ellipse2D.Double ellipseForDrawing;
    
    private Color fillColor = Color.RED;
    private Color borderColor = Color.BLACK;

    /**
     * Inicijalizuje loptu na tabli.
     * Postavlja je na sredinu table i postavlja brzinu na minimum.
     * 
     * @param board Tabla kojoj lopta pripada.
     */
    public Ball(Board board) {
        this.board = board;
        reset();
        directionX = 1;
        directionY = 1;
        width = w;
        height = h;
    }
    
    /**
     * Mijenja smjer lopte po x osi.
     */
    public void bouceHorizontal() {
        directionX = -directionX;
    }
    
    /**
     * Mijenja smijer lopte po y osi.
     */
    public void bouceVertical() {
        directionY = -directionY;
    }
    
    private void reset() {
        x = board.PANEL_WIDTH/2 - w/2;
        y = board.PANEL_HEIGHT/2 - h/2;
        dx = MIN_DX;
        dy = MIN_DY;
    }
    
    /**
     * Ubrzava loptu, pazeći na gornu granicu.
     */
    public void speedUp() {
        dx += STEP;
        dy += STEP;
        
        if (dx > MAX_DX)
            dx = MAX_DX;
        if (dy > MAX_DY)
            dy = MAX_DY;
    }
    
    /**
     * Vrši pomjeranje lopte.
     */
    @Override
    public void move() {
        x += dx * directionX;
        y += dy * directionY;
        
        if (x + w >= board.PANEL_WIDTH || x <= 0)
            bouceHorizontal();
        
        if (y <= 0) {
            board.lowerScored();
            reset();
        }
        else if (y + w >= board.PANEL_HEIGHT) {
            board.upperScored();
            reset();
        }
    }
    
    /**
     * Vrši iscrtavanje lopte na tabli.
     * @param g2 Graphics2D objekat na kojem se vrši iscrtavanje.
     */
    @Override
    public void draw(Graphics2D g2) {
        ellipseForDrawing = new Ellipse2D.Double(x, y, w, h);
        
        g2.setPaint(fillColor);
        g2.fill(ellipseForDrawing);
        
        g2.setPaint(borderColor);
        g2.draw(ellipseForDrawing);
    }
}
