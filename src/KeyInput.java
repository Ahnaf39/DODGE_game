import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {
    private Handler handler;
    private boolean uP = false;
    private boolean dP = false;
    private boolean lP = false;
    private boolean rP = false;
    public KeyInput(Handler handler){
        this.handler=handler;
    }
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        //System.out.println(key);
        for (int i=0; i< handler.object.size(); i++){
            GameObject tempObject = handler.object.get(i);
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
                    }
                } else {
                    // don't do anything
                    tempObject.setVelX(0);
                    tempObject.setVelY(0);
                }
            }
        }
    }
    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();

        for (int i=0; i< handler.object.size(); i++){
            GameObject tempObject = handler.object.get(i);
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
