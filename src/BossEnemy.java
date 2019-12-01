import java.awt.*;
import java.util.Random;

public class BossEnemy extends GameObject {

    private Random r = new Random();
    public static int PHASE_1 = 0;
    public static int PHASE_2 = 1;
    public static int PHASE_3 = 2;
    public static int PHASE_DMG = 99;
    public boolean isVulnerable = false;
    public int bossHeath = 100;

    private int bossPhase = PHASE_1;

    public BossEnemy(int x, int y, ID id) {
        super(x,y,id);
        velX = -1 * (r.nextInt(9) + 1);
        velY = r.nextInt(5);
    }

    public Rectangle getBounds(){
        return new Rectangle((int)x,(int)y,48,48);
        //16 is from width and height below
        // needs better implementation and less hardcody
    }

    public void tick() {
        if (bossPhase < PHASE_3) {
            velX = 0;
            velY = 0;
        } else {
            x += velX;
            y += velY;

            if (y <= 0 || y >= Game.HEIGHT-32){
                velY *= -1; //this y is rebound
            }
            if (x <= 0 || x >= Game.WIDTH-16){
                velX *= -1; //this x is rebound
            }
        }
    }



    public void render(Graphics g) {
        g.setColor(Color.magenta);
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

    public int getBossPhase() {
        return bossPhase;
    }

    public void setBossPhase(int bossPhase) {
        this.bossPhase = bossPhase;
    }

    public boolean getIsVulnerable() {
        return isVulnerable;
    }

    public void setIsVulnerable(boolean isVulnerable) {
        this.isVulnerable = isVulnerable;
    }
}
