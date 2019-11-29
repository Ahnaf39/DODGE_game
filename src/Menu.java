import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Menu extends MouseAdapter{
    private Game game;
    private Handler handler;
    private GameState gameState;
    private Random r = new Random();
    public Menu(Game game,Handler handler){
        this.game=game;
        this.handler=handler;
    }
    public void mousePressed(MouseEvent e){
        int mx = e.getX();
        int my = e.getY();
        gameState = new GameState(handler);
        //Start button
        if(mouseOver(mx,my,100,100,100,64)){ //xywidthheight taken from Start rectangle. Need better code
            gameState.GState();

        }
        if (mouseOver(mx,my,100,200,100,64)){
            System.exit(1);
        }
    }
    public void mouseReleased(MouseEvent e){

    }
    private boolean mouseOver(int mx, int my,int x, int y, int width, int height){
        if (mx>x && mx<x+width){
            if (my>y&&my<y+height){
                return true;
            }
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
    }
}