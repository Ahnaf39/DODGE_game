import java.awt.*;
import java.util.LinkedList;

public class Handler {
    static LinkedList<GameObject> object = new LinkedList<GameObject>();
    static Basic_Enemy initialEnemy = null;
    static BossEnemy bossEnemy = null;

    public void tick(){
        for (int i=0; i<object.size(); i++){
            GameObject tempObject = object.get(i);
            tempObject.tick();
            if (tempObject.id == ID.Player) {
                if (HUD.HEALTH == 0) {
                    ((Player) tempObject).setPlayerState(Player.PLAYER_DEAD);
                }

            }

            if (initialEnemy == null) {
                if (tempObject.id == ID.Basic_Enemy) {
                    initialEnemy = (Basic_Enemy) tempObject;
                }
            }

            if (bossEnemy == null) {
                if (tempObject.id == ID.BossEnemy) {
                    bossEnemy = (BossEnemy) tempObject;
                }
            }

            if (tempObject.id == ID.Basic_Enemy) {
                Basic_Enemy check_death = (Basic_Enemy) tempObject;
                if (check_death.getCount() >= 5) {
                    removeObject(tempObject);
                }
            }

            if (tempObject.id == ID.SmartEnemy) {
                SmartEnemy check_death = (SmartEnemy) tempObject;
                if (check_death.getTime() == 1000) {
                    removeObject(tempObject);
                }
            }

        }
    }

    public void render (Graphics g){
        for(int i=0; i<object.size();i++){
            GameObject tempObject = object.get(i);
            tempObject.render(g);

            if (tempObject.getId() == ID.Player && ((Player) tempObject).getPlayerState()==Player.PLAYER_DEAD) {
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
