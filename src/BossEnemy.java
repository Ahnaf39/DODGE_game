import java.awt.*;
import java.util.Random;

public class BossEnemy extends GameObject {
    private int storeX, storeY;
    //private int count=0;
    //private Spawn spawn;

    Random r = new Random();
    public BossEnemy(int x, int y, ID id) {
        super(x,y,id);
        storeX = x;
        storeY=y;
        velX =-1*(r.nextInt(9)+1);
        velY = r.nextInt(5);

    }

    public Rectangle getBounds(){
        return new Rectangle((int)x,(int)y,16,16);//16 is from width and height below
        // needs better implementation and less hardcody
    }
    public void tick() {

        //x+=velX;
        //y+=velY;
        velX=0;
        velY=0;

        /*if (y<=0 || y>=Game.HEIGHT-32){
            y = storeY; //this y is for spam from one location
            //velY*=-1; //this y is rebound

        }
        if (x<=0 || x>=Game.WIDTH-16){
            x = storeX; //this x is for spam from one location

            //velX*=-1; //this x is rebound


        }*/
    }

    public void render(Graphics g) {
        g.setColor(Color.magenta);
        g.fillOval((int)x,(int)y,16, 16);
        g.fillRoundRect((int)x,(int)y,32,32,32,32);
    }
}
