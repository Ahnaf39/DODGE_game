import java.awt.*;
import java.util.Random;

/**
 *
 */
public class SmartEnemy extends GameObject {

    private GameObject player;
    private int time;
    private Random r = new Random();

    private int SMART_ENEMY_SIZE = 16;

    /**
     * Create a new Smart Enemy object that follows the player
     * @param x initial x-coordinate of the Smart Enemy
     * @param y initial y-coordinate of the Smart Enemy
     * @param id ID attached to smart enemy object
     */
    public SmartEnemy(int x, int y, ID id) {
        super(x,y,id);
        time = 0;

        for (int i = 0; i < Handler.object.size(); i++) {
            if(Handler.object.get(i).getId() == ID.Player){
                player = Handler.object.get(i);
            }
        }


    }

    /**
     * Produce of the effective area for the Smart Enemy to collide with the player and deal damage
     * @return rectangle that is the collision area of the Smart Enemy
     */
    public Rectangle getBounds(){
        return new Rectangle((int)x,(int)y,SMART_ENEMY_SIZE,SMART_ENEMY_SIZE);
    }

    /**
     * Defines the Smart Enemy movement at each tick
     * Follow player based on distance to player position
     */
    public void tick() {

        x += velX;
        y += velY;

        float diffX = x - player.getX();
        float diffY = y - player.getY();
        float distance = (float) Math.sqrt((x - player.getX()) * (x - player.getX()) +
                                           (y - player.getY()) * (y - player.getY()));

        velX = ((-1 / distance) * diffX * 3);
        velY = ((-1 / distance) * diffY * 3);

        if(((Player) player).getPlayerState() == Player.PLAYER_DEAD){
            velX = 0;
            velY = 0;
        }

        time++;
    }

    /**
     * Keep track of number of ticks the smart enemy has
     * been alive for to provide information to Handler
     * @return
     */
    public int getTime() {
        return this.time;
    }

    /**
     * Renders the Smart Enemy at all ticks
     * @param g Graphic object to render the Smart Enemy
     */
    public void render(Graphics g) {
        g.setColor(Color.pink);
        g.fillOval((int)x,(int)y,SMART_ENEMY_SIZE, SMART_ENEMY_SIZE);
    }
}
