import java.awt.*;
import java.util.Random;

public class SmartEnemy extends GameObject {

    private GameObject player;
    private int time;
    private Random r = new Random();

    public SmartEnemy(int x, int y, ID id) {
        super(x,y,id);
        time = 0;

        for (int i = 0; i < Handler.object.size(); i++) {
            if(Handler.object.get(i).getId() == ID.Player){
                player = Handler.object.get(i);
            }
        }


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

        velX = ((-1 / distance) * diffX * 3);
        velY = ((-1 / distance) * diffY * 3);

        if(((Player) player).getPlayerState() == Player.PLAYER_DEAD){
            velX = 0;
            velY = 0;
        }

        time++;
    }

    public int getTime() {
        return this.time;
    }

    public void render(Graphics g) {
        g.setColor(Color.pink);
        g.fillOval((int)x,(int)y,16, 16);
    }
}
