import java.awt.*;
import java.util.Random;

/**
 * Handles everything regarding the Basic Enemy Spam object
 * Uses GameObject to get Basic Enemy Spam's initial position and to get its ID associated to it
 */
public class BasicEnemySpam extends GameObject {
    private int storeX, storeY;
    private Random r = new Random();
    private int BASIC_ENEMY_SPAM_SIZE = 16;

    /**
     * Create the Basic Enemy Spam object that is a barrage of basic enemies shooting at the player
     * @param x the initial x-coordinate of the Basic Enemy Spam
     * @param y the initial y-coordinate of the Basic Enemy Spam
     * @param id the ID attached to the Basic Enemy Spam object
     */
    public BasicEnemySpam(int x, int y, ID id) {

        super(x,y,id);
        storeX = x;
        storeY = y;
        velX = -1 * (r.nextInt(5) + 1);
        velY = r.nextInt(4);

    }

    /**
     * Produce the effect area of the Basic Enemy Spam object where the player will take damage
     * @return a rectangular area that defines the collision area
     */
    public Rectangle getBounds(){

        return new Rectangle((int)x,(int)y,BASIC_ENEMY_SPAM_SIZE,BASIC_ENEMY_SPAM_SIZE);
    }

    /**
     * Defines the Basic Enemy Spam movement at each tick
     * Repeat from the initial position once Basic Enemy Spam objects go out of bounds
     */
    public void tick() {

        x += velX;
        y += velY;

        if (y <= 0 || y >= Game.HEIGHT || x <= 0 || x >= Game.WIDTH){
            x = storeX;
            y = storeY;
        }
    }

    /**
     * Render the Basic Enemy Spam at every tick
     * @param g Graphics object to render the Basic Enemy Spam
     */
    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillOval((int)x,(int)y,BASIC_ENEMY_SPAM_SIZE, BASIC_ENEMY_SPAM_SIZE);
    }
}
