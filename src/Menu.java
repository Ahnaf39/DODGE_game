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
        if (mouseOver(mx,my,0,10,100,64)){
            Game.gameState = Game.STATE.FirstStage;
            gameState.GState();
        }
        if (mouseOver(mx,my,0,80,100,64)){
            Game.gameState = Game.STATE.SecondStage;
            gameState.GState();
        }
        if (mouseOver(mx,my,0,150,100,64)){
            Game.gameState = Game.STATE.ThirdStage;
            gameState.GState();
        }
        if (mouseOver(mx,my,0,220,100,64)){
            Game.gameState = Game.STATE.FourthStage;
            gameState.GState();
        }
        if (mouseOver(mx,my,0,300,100,64)){
            Game.gameState = Game.STATE.FifthStage;
            gameState.GState();
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

    }
}