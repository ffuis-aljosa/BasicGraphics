package basicgraphics;

import javax.swing.JFrame;

/**
 *
 * @author Korisnik
 */
public class Frame extends JFrame {

    public Frame() {
        add(new Board());
        pack();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Basic Graphics");
        
        setVisible(true);
    }
    
}
