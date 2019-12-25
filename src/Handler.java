import java.awt.*;
import java.util.LinkedList;

public class Handler {
    static LinkedList<GameObject> object = new LinkedList<GameObject>();
    static Basic_Enemy initialEnemy = null;
    static BossEnemy bossEnemy = null;

    /**
     * Tick to check updated objects list for the game and control logic
     * for Player death and enemy spawning
     */
    public void tick(){
        for (GameObject tempObject : object) {
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

    /**
     * Render each object including the player death
     * @param g Graphic object to render objects
     */
    public void render (Graphics g){
        for (GameObject tempObject : object) {
            tempObject.render(g);

            if (tempObject.getId() == ID.Player && ((Player) tempObject).getPlayerState() == Player.PLAYER_DEAD) {
                ((Player) tempObject).playerDeath(g);
            }
        }
    }

    void addObject(GameObject object){
        Handler.object.add(object);
    }

    private void removeObject(GameObject object){
        Handler.object.remove(object);
    }
}
