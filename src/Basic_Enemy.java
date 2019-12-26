import java.awt.*;
import java.util.Random;

/**
 * Handles everything regarding the Basic Enemy object
 * Uses GameObject to get Basic Enemy's initial position and to get its ID associated to it
 */
public class Basic_Enemy extends GameObject {
    private int count = 0;
    private int BASIC_ENEMY_SIZE = 16;

    /**
     * Create a new Basic Enemy object that can be moves in a fixed direction and then rebounds off the boundaries
     * @param x initial x-coordinate of the Basic Enemy
     * @param y initial y-coordinate of the Basic Enemy
     * @param id ID attached to basic enemy object
     */
    public Basic_Enemy(float x, float y, ID id) {
        super(x,y,id);
        Random r = new Random();
        velX = -1 * (r.nextInt(9) + 3);
        velY = r.nextInt(5) + 3;
    }

    /**
     * Produce of the effective area for the Basic Enemy to collide with the player and deal damage
     * @return rectangle that is the collision area of the Basic Enemy
     */
    public Rectangle getBounds(){

        return new Rectangle( (int)x, (int)y, BASIC_ENEMY_SIZE, BASIC_ENEMY_SIZE);
    }

    /**
     * Defines the Basic Enemy movement at each tick
     * Rebound off the boundaries
     */
    public void tick() {
        x += velX;
        y += velY;

        if (y <= 0 || y >= Game.HEIGHT - BASIC_ENEMY_SIZE * 2){

            velY *= -1; //this y is rebound
            count++;
        }
        if (x <= 0 || x >= Game.WIDTH - BASIC_ENEMY_SIZE){

            velX *= -1; //this x is rebound
            count++;
        }
    }

    /**
     * Count the number of times the Basic Enemy rebounded off the boundaries
     * @return the number of rebounds
     */
    public int getCount(){
        return count;
    }

    /**
     * Renders the Basic Enemy at all ticks
     * @param g Graphic object to render the Basic Enemy
     */
    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillOval((int)x,(int)y, BASIC_ENEMY_SIZE, BASIC_ENEMY_SIZE);
    }
}
