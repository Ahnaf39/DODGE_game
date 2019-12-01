import java.awt.*;
import java.util.Random;

public class BasicEnemySpam extends GameObject {
    private int storeX, storeY;
    private Random r = new Random();

    public BasicEnemySpam(int x, int y, ID id) {
        super(x,y,id);
        storeX = x;
        storeY = y;
        velX = -1 * (r.nextInt(9) + 1);
        velY = r.nextInt(5);

    }

    public Rectangle getBounds(){
        return new Rectangle((int)x,(int)y,16,16);
        //16 is from width and height below
        // needs better implementation and less hardcody
    }


    public void tick() {

        x += velX;
        y += velY;

        if (y<=0 || y>=Game.HEIGHT || x<=0 || x>=Game.WIDTH){
            x = storeX;
            y = storeY;
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillOval((int)x,(int)y,16, 16);
    }
}
