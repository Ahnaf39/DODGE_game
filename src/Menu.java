import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.Random;

public class Menu extends MouseAdapter{
    private Handler handler;
    private Random r = new Random();
    public static boolean isHardMode = false;

    public Menu(Game game,Handler handler){
        this.handler=handler;
    }

    public void mousePressed(MouseEvent e){
        int mx = e.getX();
        int my = e.getY();
        GameState gameState = new GameState(handler);
        //Start button
        if(mouseOver(mx,my,100,100,100,64)){ //xywidthheight taken from Start rectangle. Need better code
            gameState.GState();

        }
        else if (mouseOver(mx,my,0,10,100,64)){
            Game.gameState = Game.STATE.FirstStage;
            gameState.GState();
        }
        else if (mouseOver(mx,my,0,80,100,64)){
            Game.gameState = Game.STATE.SecondStage;
            gameState.GState();
        }
        else if (mouseOver(mx,my,0,150,100,64)){
            Game.gameState = Game.STATE.ThirdStage;
            gameState.GState();
        }
        else if (mouseOver(mx,my,0,220,100,64)){
            Game.gameState = Game.STATE.FourthStage;
            gameState.GState();
        }
        else if (mouseOver(mx,my,0,300,100,64)){
            Game.gameState = Game.STATE.FifthStage;
            gameState.GState();
        }

        else if (mouseOver(mx,my,300,140,180,35)){
            isHardMode = false;
        }

        else if (mouseOver(mx,my,300,200,180,35)){
            isHardMode = true;
        }
    }

    public void mouseReleased(MouseEvent e){

    }

    private boolean mouseOver(int mx, int my,int x, int y, int width, int height){
        if (mx>x && mx<x+width){
            return my > y && my < y + height;
        }
        return false;
    }

    public void tick(){

    }

    public void render(Graphics g){
        Font fnt = new Font("helvetica", 1, 32);
        g.setFont(fnt);
        g.setColor(Color.white);
        g.drawString("Start",110,140);
        g.drawRect(100,100,100,64);
        g.drawString("Quit",110,240);
        g.drawRect(100,200,100,64);

        Font fnt1 = new Font("helvetica", 1, 24);
        g.setFont(fnt1);
        g.drawString("Level 1",10,40);
        g.drawRect(0,10,100,64);
        g.drawString("Level 2",10,120);
        g.drawRect(0,80,100,64);
        g.drawString("Level 3",10,200);
        g.drawRect(0,150,100,64);
        g.drawString("Level 4",10,280);
        g.drawRect(0,220,100,64);
        g.drawString("Level 5",10,360);
        g.drawRect(0,300,100,64);

        g.drawString("Easy Mode",345,165);
        g.drawOval(300,140,30,30);

        g.drawString("Hard Mode",345,225);
        g.drawOval(300,200,30,30);

        if (!isHardMode) {
            Graphics2D g2d = (Graphics2D)g;
            // Assume x, y, and diameter are instance variables.
            Ellipse2D.Double circle = new Ellipse2D.Double(302, 142, 28, 28);
            g2d.fill(circle);
        } else {
            Graphics2D g2d = (Graphics2D)g;
            // Assume x, y, and diameter are instance variables.
            Ellipse2D.Double circle = new Ellipse2D.Double(302, 202, 28, 28);
            g2d.fill(circle);
        }
    }
}