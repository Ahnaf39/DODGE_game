import java.awt.*;

public class HUD {
    public static int HEALTH =100;

    public void tick(){
        HEALTH--;
        if (HEALTH<=0){
            HEALTH=0;
        }
    }
    public void render(Graphics g){
        g.setColor(Color.gray);
        g.fillRect(5,5,200,32);//200 is a hardcode and needs to be updated
        g.setColor(Color.green);
        g.fillRect(5,5,HEALTH*2,32);
        g.setColor(Color.white);
        g.drawRect(5,5,200,32); //again hardcoded 200
    }
}
