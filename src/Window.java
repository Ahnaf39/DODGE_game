import javax.swing.JFrame;
import java.awt.Canvas;
import java.io.Serializable;
import java.awt.Dimension;

/**
 * Sets up the window for the game
 */
public class Window extends Canvas implements Serializable {
    private static final long serialVersionUID = -8700227474256481031L;

    /**
     * Creates the window for the game
     * @param width the width of the window
     * @param height the height of the window
     * @param title the title of the window
     * @param game the game object that the window holds
     */
    public Window (int width, int height, String title, Game game){
        JFrame frame = new JFrame(title);
        frame.setPreferredSize(new Dimension(width,height));
        frame.setMaximumSize(new Dimension(width,height));
        frame.setMinimumSize(new Dimension(width,height));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(game);
        frame.setVisible(true);
        game.start();
    }

}