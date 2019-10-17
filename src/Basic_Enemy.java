import java.awt.*;
import java.util.Random;

public class Basic_Enemy extends GameObject {
    private int storeX, storeY;

    Random r = new Random();
    public Basic_Enemy(int x, int y, ID id) {
        super(x,y,id);
        storeX = x;
        storeY=y;
        velX =-1*(r.nextInt(9)+1);
        velY = r.nextInt(5);

    }

    public Rectangle getBounds(){
        return new Rectangle(x,y,16,16);//16 is from width and height below
        // needs better implementation and less hardcody
    }
    public void tick() {

        x+=velX;
        y+=velY;

        if (y<=0 || y>=Game.HEIGHT || x<=0 || x>=Game.WIDTH){
            x = storeX;
            y = storeY;
        }
        //implement a rebound function
    }

    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillOval(x,y,16, 16);
    }
}
