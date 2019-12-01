import java.awt.*;
public class GameState {
    private Handler handler;
    private int time1 =0;
    private int time2 =0;
    private int duration = 100;

    public GameState(Handler handler){
        this.handler=handler;
    }

    public void GState(){
        if (Game.gameState == Game.STATE.Menu) {
            Handler.object.clear();
            Game.gameState = Game.STATE.FirstStage;
            handler.addObject(new Player(100, 100, ID.Player, handler));
            handler.addObject(new Basic_Enemy(600, 50, ID.Basic_Enemy));
            HUD.level = 1;
        }
        if (Game.gameState == Game.STATE.SecondStage) {
            Handler.object.clear();
            Handler.initialEnemy = null;
            handler.addObject(new Player(100, 100, ID.Player, handler));
            handler.addObject(new SmartEnemy(600, 50, ID.SmartEnemy,handler));
            HUD.level = 2;
        }
        if (Game.gameState == Game.STATE.ThirdStage) {
            Handler.object.clear();
            Handler.initialEnemy = null;
            handler.addObject(new Player(100, 100, ID.Player, handler));
            handler.addObject(new Basic_Enemy(600, 50, ID.Basic_Enemy));
            HUD.level = 3;
        }
    }

    public void tick(){
        if (Game.gameState==Game.STATE.FirstStage) {
            time1++;
            if (Handler.initialEnemy != null) {
                if (Handler.initialEnemy.getCount() == 5) {
                    Game.gameState = Game.STATE.SecondStage;
                    GState();
                }
            }
        }
        if(Game.gameState==Game.STATE.SecondStage){
            time2++;
            if(time2>=1000){
                Game.gameState=Game.STATE.ThirdStage;
                GState();
            }
        }
    }

    public void render(Graphics g){
        if (Game.gameState == Game.STATE.FirstStage) {
            if (time1<duration) {
                Font fnt = new Font("helvetica", 1, 32);
                g.setFont(fnt);
                g.setColor(Color.white);
                g.drawString("Use arrow keys to dodge enemies", 50, 50);
            }
        }
        if (Game.gameState == Game.STATE.SecondStage) {
            if (time2<duration) {
                Font fnt = new Font("helvetica", 1, 28);
                g.setFont(fnt);
                g.setColor(Color.white);
                g.drawString("Press SPACE to do something super cool", 50, 50);
            }
        }
    }
}
