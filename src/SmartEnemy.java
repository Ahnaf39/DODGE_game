import java.awt.*;
import java.util.Random;

public class SmartEnemy extends GameObject {
    private int storeX, storeY;
    //private int count=0;
   // private Spawn spawn;
    private Handler handler;
    private GameObject player;
    Random r = new Random();
    public SmartEnemy(int x, int y, ID id, Handler handler) {
        super(x,y,id);
        this.handler = handler;

        for (int i=0;i<handler.object.size();i++) {
            if(handler.object.get(i).getId()==ID.Player){
                player=handler.object.get(i);
            }
        }

        storeX = x;
        storeY = y;
        velX = -1 *(r.nextInt(9) + 1);
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

        float diffX = x - player.getX();
        float diffY = y - player.getY();
        float distance = (float) Math.sqrt((x - player.getX()) * (x - player.getX()) +
                                           (y - player.getY()) * (y - player.getY()));

        velX=((-1 / distance) * diffX * 4);
        velY=((-1 / distance) * diffY * 4);

        if(((Player) player).getPlayerState()==Player.PLAYER_DEAD){
            velX=0;
            velY=0;
        }

        /*if (y<=0 || y>=Game.HEIGHT-32){
            //y = storeY; //this y is for spam from one location
            velY*=-1; //this y is rebound

        }
        if (x<=0 || x>=Game.WIDTH-16){
            //x = storeX; //this x is for spam from one location

            velX*=-1; //this x is rebound


        }*/
    }

    public void render(Graphics g) {
        g.setColor(Color.pink);
        g.fillOval((int)x,(int)y,16, 16);
    }
}
