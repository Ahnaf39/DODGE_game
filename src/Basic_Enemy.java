import java.awt.*;
import java.util.Random;

public class Basic_Enemy extends GameObject {
    private int count = 0;

    public Basic_Enemy(float x, float y, ID id) {
        super(x,y,id);
        Random r = new Random();
        velX = -1 * (r.nextInt(9) + 3);
        velY = r.nextInt(5) + 3;
    }


    public Rectangle getBounds(){
        return new Rectangle((int)x,(int)y,16,16);
        //16 is from width and height below
        // needs better implementation and less hardcody
    }


    public void tick() {
        x += velX;
        y += velY;

        if (y <= 0 || y >= Game.HEIGHT-32){
            //y = storeY; //this y is for spam from one location
            velY *= -1; //this y is rebound
            count++;
        }
        if (x <= 0 || x >= Game.WIDTH-16){
            //x = storeX; //this x is for spam from one location

            velX *= -1; //this x is rebound
            count++;
        }
    }


    public int getCount(){
        return count;
    }


    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillOval((int)x,(int)y,16, 16);
    }
}
