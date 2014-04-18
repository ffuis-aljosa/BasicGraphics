package basicgraphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
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
        setJMenuBar(initMenu());
        
        pack();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Basic Graphics");
        
        setVisible(true);
    }
    
    final JMenuBar initMenu() {
        // Napravimo liniju menija
        JMenuBar menuBar = new JMenuBar();
        
        // Mapravimo meni
        JMenu gameMenu = new JMenu("Game");
        
        // Napravimo stavku za meni
        JMenuItem newGame = new JMenuItem("New game");
        newGame.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                board.startGame();
            }
        });
        
        // Dodamo stavku u meni
        gameMenu.add(newGame);
        
        // Dodamo meni u liniju menija
        menuBar.add(gameMenu);
        
        return menuBar;
    }
    
}
