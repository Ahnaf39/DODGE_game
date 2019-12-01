import java.awt.*;
public class GameState {
    private Handler handler;
    private int time1 =0;
    private int duration = 100;
    private GameObject basicenemy;

    public GameState(Handler handler){
        this.handler=handler;
        for (int i=0;i<handler.object.size();i++) {
            if(handler.object.get(i).getId()==ID.Basic_Enemy){
                basicenemy=handler.object.get(i);
            }
        }
    }

    public void GState(){
        if (Game.gameState == Game.STATE.Menu) {
            Handler.object.clear();
            Game.gameState = Game.STATE.FirstStage;
            handler.addObject(new Player(100, 100, ID.Player, handler));
            handler.addObject(new Basic_Enemy(600, 50, ID.Basic_Enemy));
        }
        if (Game.gameState == Game.STATE.SecondStage) {
            Handler.object.clear();
            Handler.initialEnemy = null;
            handler.addObject(new Player(100, 100, ID.Player, handler));
            handler.addObject(new SmartEnemy(600, 50, ID.SmartEnemy,handler));
        }
    }

    public void tick(){
        time1++;
        if (Handler.initialEnemy != null) {
            if (Handler.initialEnemy.getCount() == 5) {
                Game.gameState = Game.STATE.SecondStage;
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
    }
}
