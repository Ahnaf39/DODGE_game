import java.awt.*;
import java.util.Random;

/**
 * Boss enemy is a class,
 */
public class BossEnemy extends GameObject {

    private Random r = new Random();
    public boolean isVulnerable = false;
    public int bossHeath = 100;

    /**
     * Enum to keep track of the various boss phases
     */
    public enum PHASES {
        PHASE_1,
        PHASE_2,
        PHASE_3,
        PHASE_DMG
    }

    private BossEnemy.PHASES bossPhase = PHASES.PHASE_1;

    /**
     * Constructor for boss enemy class
     * @param x initial x-coordinate of the boss
     * @param y initial y-coordinate of the boss
     * @param id ID attached to the boss object
     */
    public BossEnemy(int x, int y, ID id) {
        super(x,y,id);
        velX = -1 * (r.nextInt(9) + 1);
        velY = r.nextInt(5);
    }

    /**
     * Bounds of intersection for the boss to deal or receive damage
     * @return a rectangle object that provides bounds of intersection of the boss
     */
    public Rectangle getBounds(){
        return new Rectangle((int)x+12,(int)y+10,60,60);
    }

    /**
     * Boss tick to check for updates in movement
     */
    public void tick() {
        if (bossPhase != PHASES.PHASE_3) {
            velX = 0;
            velY = 0;
        } else {
            x += velX;
            y += velY;

            if (y <= 0 || y >= Game.HEIGHT - 32){
                velY *= -1; //this y is rebound
            }
            if (x <= 0 || x >= Game.WIDTH - 16){
                velX *= -1; //this x is rebound
            }
        }
    }

    /**
     * Render boss object (with superficial spikes for design)
     * @param g Graphics object to render on the canvas
     */
    public void render(Graphics g) {
        if (isVulnerable) {
            g.setColor(Color.ORANGE);
        } else {
            g.setColor(Color.magenta);
        }
        int[] xpoints0 = {(int) x,      (int) x,      (int) x - 12};
        int[] ypoints0 = {(int) y + 20, (int) y + 30, (int) y + 25};
        int[] xpoints1 = {(int) x + 15, (int) x + 31, (int) x + 23};
        int[] ypoints1 = {(int) y,      (int) y,      (int) y - 10};
        int[] xpoints2 = {(int) x + 15, (int) x + 31, (int) x + 23};
        int[] ypoints2 = {(int) y + 50, (int) y + 50, (int) y + 60};
        int[] xpoints3 = {(int) x + 50, (int) x + 50, (int) x + 62};
        int[] ypoints3 = {(int) y + 20, (int) y + 30, (int) y + 25};
        g.fillOval((int) x,(int) y,48, 48);
        g.fillPolygon(xpoints0,ypoints0,3);
        g.fillPolygon(xpoints1,ypoints1,3);
        g.fillPolygon(xpoints2,ypoints2,3);
        g.fillPolygon(xpoints3,ypoints3,3);
    }

    public BossEnemy.PHASES getBossPhase() {
        return bossPhase;
    }

    public void setBossPhase(BossEnemy.PHASES bossPhase) {
        this.bossPhase = bossPhase;
    }

    public boolean getIsVulnerable() {
        return isVulnerable;
    }

    public void setIsVulnerable(boolean isVulnerable) {
        this.isVulnerable = isVulnerable;
    }
}
