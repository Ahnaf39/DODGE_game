import java.awt.*;
import java.util.Random;

public class Player extends GameObject{
    Random r = new Random();
    Handler handler;
    public Player(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        //velX=1;
        this.handler=handler;
    }
    public Rectangle getBounds(){
        return new Rectangle(x,y,32,32);//32 is from width and height below
                                                    // needs better implementation and less hardcody
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

        collision();
    }
    private void collision(){
        for(int i=0; i<handler.object.size();i++){
            GameObject tempObject = handler.object.get(i);
            if (tempObject.getId()==ID.Basic_Enemy){
                if(getBounds().intersects(tempObject.getBounds())){
                    HUD.HEALTH--;
                }
            }

        }
    }

    public void render(Graphics g) {
        g.setColor(Color.white);
        g.fillRoundRect(x,y,32,32,10,10);
    }
}
