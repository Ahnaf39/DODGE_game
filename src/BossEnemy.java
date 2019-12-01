import java.awt.*;
import java.util.Random;

public class BossEnemy extends GameObject {
    private int storeX, storeY;
    private Random r = new Random();
    public static int PHASE_1 = 0;
    public static int PHASE_2 = 1;

    private int bossPhase = PHASE_1;

    public BossEnemy(int x, int y, ID id) {
        super(x,y,id);
        storeX = x;
        storeY=y;
        velX = -1 * (r.nextInt(9) + 1);
        velY = r.nextInt(5);
    }

    public Rectangle getBounds(){
        return new Rectangle((int)x,(int)y,16,16);
        //16 is from width and height below
        // needs better implementation and less hardcody
    }

    public void tick() {
        velX = 0;
        velY = 0;
    }

    public void render(Graphics g) {
        g.setColor(Color.magenta);
        g.fillOval((int)x,(int)y,16, 16);
        g.fillRoundRect((int)x,(int)y,32,32,32,32);
    }

    public int getBossPhase() {
        return bossPhase;
    }

    public void setBossPhase(int bossPhase) {
        this.bossPhase = bossPhase;
    }
}
