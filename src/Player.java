import java.awt.*;

public class Player extends GameObject{
    public Player(int x, int y, ID id) {
        super(x, y, id);
        //velX=1;
    }

    public void tick() {
        x+=velX;
        y+=velY;
    }

    public void render(Graphics g) {
        g.setColor(Color.black);
        g.fillRoundRect(x,y,32,32,10,10);
    }
}
