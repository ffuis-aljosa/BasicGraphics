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
    private int dx;
    private int dy;
    
    private Board board;
    private Ellipse2D.Double ellipseForDrawing;
    
    private Color fillColor = Color.RED;
    private Color borderColor = Color.BLACK;

    public Ball(Board board) {
        x = board.PANEL_HEIGHT/2;
        y = board.PANEL_WIDTH/2;
        dx = 5;
        dy = 5;
        width = w;
        height = h;
        this.board = board;
    }
    
    public void bouceHorizontal() {
        dx = -dx;
    }
    
    public void bouceVertical() {
        dy = - dy;
    }
    
    private void reset() {
        x = board.PANEL_HEIGHT/2;
        y = board.PANEL_WIDTH/2;
    }
    
    @Override
    public void move() {
        x += dx;
        y += dy;
        
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
    
    @Override
    public void draw(Graphics2D g2) {
        ellipseForDrawing = new Ellipse2D.Double(x, y, w, h);
        
        g2.setPaint(fillColor);
        g2.fill(ellipseForDrawing);
        
        g2.setPaint(borderColor);
        g2.draw(ellipseForDrawing);
    }
}
