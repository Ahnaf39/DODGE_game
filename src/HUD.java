import java.awt.*;

public class HUD {
    public static int HEALTH = 100;
    private int greenValue = 255;
    private int redValue = 0;
    public static int level = 1;

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
        greenValue = HEALTH * 2;
        redValue = 100 - HEALTH;
    }

    public void render(Graphics g){
        if (Menu.isHardMode) {
            Font fnt = new Font("helvetica", 1, 15);
            g.setFont(fnt);
            g.setColor(Color.gray);
            g.drawString("Don't get hit. You have only one chance", 5, 25);
        } else {
            g.setColor(Color.gray);
            g.fillRect(5, 5, 200, 32);//200 is a hardcode and needs to be updated
            g.setColor(new Color(redValue, greenValue, 0));
            g.fillRect(5, 5, HEALTH * 2, 32);
            g.setColor(Color.white);
            g.drawRect(5, 5, 200, 32); //again hardcoded 200
        }

        g.drawString("Level: " + level, 5, 50);
    }
}
