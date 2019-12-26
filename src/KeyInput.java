import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Class for handling inputs from the user
 */
public class KeyInput extends KeyAdapter {
    private boolean uP = false;
    private boolean dP = false;
    private boolean lP = false;
    private boolean rP = false;

    /**
     * Handle key pressed events for continuous player object motion
     * @param e KeyEvent object
     */
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        //System.out.println(key);
        for (int i = 0; i< Handler.object.size(); i++){
            GameObject tempObject = Handler.object.get(i);
            if (tempObject.getId()==ID.Player){
                //key events for player 1
                if (((Player) tempObject).getPlayerState() == Player.PLAYER_ALIVE) {
                    if (key==KeyEvent.VK_UP){
                        uP = true;
                        tempObject.setVelY(-5);
                    }
                    if (key==KeyEvent.VK_DOWN){
                        dP = true;
                        tempObject.setVelY(5);
                    }
                    if (key==KeyEvent.VK_RIGHT){
                        rP = true;
                        tempObject.setVelX(5);
                    }
                    if (key==KeyEvent.VK_LEFT){
                        lP = true;
                        tempObject.setVelX(-5);
                    }
                    if (key==KeyEvent.VK_SPACE){
                        ((Player) tempObject).setPlayerState(Player.PLAYER_DEAD);
                        HUD.HEALTH  =0;
                    }
                } else {
                    // don't do anything
                    tempObject.setVelX(0);
                    tempObject.setVelY(0);
                }
            }
        }
    }

    /**
     * Handle key released events to stop player motion in a given direction
     * @param e KeyEvent object
     */
    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();

        for (int i = 0; i < Handler.object.size(); i++){
            GameObject tempObject = Handler.object.get(i);
            if (tempObject.getId()==ID.Player){
                //key events for player 1

                if (key == KeyEvent.VK_UP) {
                    uP = false;
                    if (dP) {
                        tempObject.setVelY(5);
                    } else {
                        tempObject.setVelY(0);
                    }
                }
                if (key == KeyEvent.VK_DOWN) {
                    dP = false;
                    if (uP) {
                        tempObject.setVelY(-5);
                    } else {
                        tempObject.setVelY(0);
                    }
                }
                if (key == KeyEvent.VK_RIGHT) {
                    rP = false;
                    if (lP) {
                        tempObject.setVelX(-5);
                    } else {
                        tempObject.setVelX(0);
                    }
                }
                if (key == KeyEvent.VK_LEFT) {
                    lP = false;
                    if (rP) {
                        tempObject.setVelX(5);
                    } else {
                        tempObject.setVelX(0);
                    }
                }
            }
        }
    }
}
