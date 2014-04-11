package basicgraphics;

import java.awt.Graphics2D;

/**
 *
 * @author Korisnik
 */
public interface GameObject {
    public abstract void move();
    public abstract void draw(Graphics2D g2);
}
