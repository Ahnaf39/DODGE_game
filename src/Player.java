import java.awt.*;
import java.util.Random;

public class Player extends GameObject{
    Handler handler;
    public static int PLAYER_ALIVE = 1;
    public static int PLAYER_DEAD = 0;

    public int state;

    private int deathEffectCount = 0;

    public Player(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        //velX=1;
        state = PLAYER_ALIVE;
        this.handler=handler;
    }
    public Rectangle getBounds(){
        return new Rectangle((int)x,(int)y,32,32);//32 is from width and height below
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
            if (tempObject.getId()==ID.Basic_Enemy||tempObject.getId()==ID.SmartEnemy||tempObject.getId()==ID.BasicEnemySpam){
                if(getBounds().intersects(tempObject.getBounds())){
                    HUD.HEALTH--;
                }
            }

        }
    }

    public void setPlayerState(int state) {
        this.state = state;
    }
    public int getdeathEffectCount(){return deathEffectCount;}

    public int getPlayerState() {
        return this.state;
    }

    public void render(Graphics g) {
        g.setColor(Color.white);
        g.fillRoundRect((int)x,(int)y,32,32,10,10);

    }

    public void playerDeath(Graphics g) {
        if (deathEffectCount == 0) {

            g.setColor(Color.YELLOW);
            g.fillRoundRect((int)x, (int)y, 32, 32, 20, 20);
            deathEffectCount++;
        } else if (deathEffectCount >= 1 && deathEffectCount <= 500) {

            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    float xcoord = x + (i * (deathEffectCount / 100) * 25) + 16 - 5;
                    float ycoord = y + (j * (deathEffectCount / 100) * 25) + 16 - 5;

                    if (!(i == 0 && j == 0)) {
                        g.setColor(Color.YELLOW);
                        g.fillRoundRect((int)xcoord,(int) ycoord, 15, 15, 10, 10);
                    }
                }
            }

            deathEffectCount ++;
            g.setColor(Color.black);
            g.fillRoundRect((int)x,(int)y,32,32,10,10);
        } else {
            g.setColor(Color.black);
            g.fillRoundRect((int)x,(int)y,32,32,10,10);
            Game.gameState = Game.STATE.Menu;
            Handler.object.clear();
            HUD.HEALTH = 100;
        }
    }
}
