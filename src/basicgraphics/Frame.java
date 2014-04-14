package basicgraphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author Korisnik
 */
public class Frame extends JFrame {

    Board board = new Board();
    
    public Frame() {
        add(board);
        pack();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Basic Graphics");
        
        setJMenuBar(initMenu());
        
        setVisible(true);
    }
    
    JMenuBar initMenu() {
        JMenuBar menu = new JMenuBar();
        
        JMenuItem newGame = new JMenuItem("New game");
        newGame.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                board.startGame();
            }
        });
        
        menu.add(newGame);
        
        return menu;
    }
    
}
