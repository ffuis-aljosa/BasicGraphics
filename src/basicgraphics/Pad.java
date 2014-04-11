/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basicgraphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author Korisnik
 */
public class Pad extends Rectangle.Double implements GameObject {

    enum MovingState { STANDING, MOVING_LEFT, MOVING_RIGHT }
    
    private int w = 100;
    private int h = 20;
    
    private int dx = 10;
    
    private Board board;
    private MovingState state;
    
    private Color fillColor = Color.BLACK;
    
    public Pad(Board board, int x, int y) {
        this.board = board;
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.state = MovingState.STANDING;
    }
    
    public void moveRight() {
        state = MovingState.MOVING_RIGHT;
    }
    
    public void moveLeft() {
        state = MovingState.MOVING_LEFT;
    }
    
    public void stopMoving() {
        state = MovingState.STANDING;
    }
    
    @Override
    public void move() {
        if (state == MovingState.MOVING_RIGHT)
            x += dx;
        else if (state == MovingState.MOVING_LEFT)
            x -= dx;
        
        if (x < 0)
            x = 0;
        else if (x + w > board.PANEL_WIDTH)
            x = board.PANEL_WIDTH - w;
    }
    
    @Override
    public void draw(Graphics2D g2) {
        g2.setPaint(fillColor);
        g2.fill(this);
    }
}
