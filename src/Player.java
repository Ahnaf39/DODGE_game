import java.awt.*;

public class Player extends GameObject{
    public Player(int x, int y, ID id) {
        super(x, y, id);
        //velX=1;

    }

    public void tick() {
        x+=velX;
        y+=velY;

        //Player not going out of room
        //the -32 and -62 here is from fillRoundRect width and height(trial and error)
        // and needs to be updated to be less hardcoded
        if (x>=Game.WIDTH-32){
            x=Game.WIDTH-32;
        }
        else if (x<=0){
            x=0;
        }
        if (y>=Game.HEIGHT-62){
            y=Game.HEIGHT-62;
        }
        else if (y<=0){
            y=0;
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.white);
        g.fillRoundRect(x,y,32,32,10,10);
    }
}
