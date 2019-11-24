import java.awt.*;
import java.util.LinkedList;

public class Handler {
    LinkedList<GameObject> object = new LinkedList<GameObject>();

    public int playerState = 1;
    public void tick(){
        for (int i=0; i<object.size(); i++){
            GameObject tempObject = object.get(i);
            tempObject.tick();
            if (tempObject.id == ID.Player) {
                if (HUD.HEALTH == 0) {
                    ((Player) tempObject).setPlayerState(Player.PLAYER_DEAD);
                }
            }
        }
    }
    public void render (Graphics g){
        for(int i=0; i<object.size();i++){
            GameObject tempObject = object.get(i);
            tempObject.render(g);

            if (tempObject.getId() == ID.Player && HUD.HEALTH == 0) {
                ((Player)tempObject).playerDeath(g);
            }
        }
    }
    public void addObject(GameObject object){
        this.object.add(object);
    }
    public void removeObject (GameObject object){
        this.object.remove(object);
    }
}
