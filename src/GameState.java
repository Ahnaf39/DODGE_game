import java.awt.*;
public class GameState {
    private Handler handler;
    private int time =0;
    private int duration = 100;
    public GameState(Handler handler){
        this.handler=handler;
    }
    public void GState(){
        if (Game.gameState == Game.STATE.Menu) {
            Game.gameState = Game.STATE.FirstStage;
            handler.addObject(new Player(100, 100, ID.Player, handler));

            handler.addObject(new Basic_Enemy(600, 50, ID.Basic_Enemy));
            handler.addObject(new SmartEnemy(600,400,ID.SmartEnemy,handler));
            handler.addObject(new BossEnemy(600, 100, ID.BossEnemy));
        }
    }
    public void tick(){
        time++;
    }
    public void render(Graphics g){
        if (Game.gameState == Game.STATE.FirstStage) {
            if (time<duration) {
                Font fnt = new Font("helvetica", 1, 32);
                g.setFont(fnt);
                g.setColor(Color.white);
                g.drawString("Use arrow keys to dodge enemies", 50, 50);
            }
        }
    }
}
