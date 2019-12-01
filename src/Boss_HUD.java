import java.awt.*;

public class Boss_HUD {
    public static int HEALTH = 100;
    private int greenValue = 0;
    private int redValue = 225;


    public void tick(){
        if (HEALTH <= 0){
            HEALTH = 0;
        }

        if (greenValue <= 0){
            greenValue = 0;
        } else if (greenValue >= 255){
            greenValue = 255;
        }

        if(redValue >= 100){
            redValue = 100;
        }
        greenValue = 100 - HEALTH;
        redValue = HEALTH * 2;
    }

    public void render(Graphics g){
        if(Game.gameState== Game.STATE.FifthStage) {
            g.setColor(Color.gray);
            g.fillRect(420, 5, 200, 32);//200 is a hardcode and needs to be updated
            g.setColor(new Color(redValue, greenValue, 0));
            g.fillRect(420, 5, HEALTH * 2, 32);
            g.setColor(Color.white);
            g.drawRect(420, 5, 200, 32); //again hardcoded 200
        }
    }
}
