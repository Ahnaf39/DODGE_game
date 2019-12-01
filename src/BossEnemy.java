import java.awt.*;
import java.util.Random;

public class BossEnemy extends GameObject {
    private int storeX, storeY;
    private Random r = new Random();
    public static int PHASE_1 = 0;
    public static int PHASE_2 = 1;
    public static int PHASE_3 = 2;

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
        if (bossPhase < PHASE_3) {
            velX = 0;
            velY = 0;
        } else {
            x += velX;
            y += velY;

            if (y <= 0 || y >= Game.HEIGHT-32){
                //y = storeY; //this y is for spam from one location
                velY *= -1; //this y is rebound
            }
            if (x <= 0 || x >= Game.WIDTH-16){
                //x = storeX; //this x is for spam from one location

                velX *= -1; //this x is rebound
            }
        }
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
