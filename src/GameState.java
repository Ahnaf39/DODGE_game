import java.awt.*;
import java.util.Random;

public class GameState {
    private Handler handler;
    private int time1 = 0;
    private int time2 = 0;
    private int time3 = 0;
    private int time4 = 0;
    private int enemycount = 0;
    private int duration = 100;
    private Random random = new Random();

    public GameState(Handler handler){
        this.handler=handler;
    }

    public void GState(){
        if (Game.gameState == Game.STATE.Menu || Game.gameState==Game.STATE.FirstStage) {
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
            enemycount++;
        }
        if (Game.gameState == Game.STATE.FourthStage) {
            Handler.object.clear();
            Handler.initialEnemy = null;
            handler.addObject(new Player(100, 100, ID.Player, handler));
            handler.addObject(new SmartEnemy(random.nextInt(Game.WIDTH),random.nextInt(Game.HEIGHT),ID.SmartEnemy,handler));
            for (int i = 0; i < 5; i++) {
                for (int j = 1; j < 5; j++) {
                    handler.addObject(new BasicEnemySpam(600, 50 * j, ID.BasicEnemySpam));
                }
            }
            HUD.level = 4;
        }
        if (Game.gameState == Game.STATE.FifthStage) {
            Handler.object.clear();
            Handler.initialEnemy = null;
            handler.addObject(new Player(100, 100, ID.Player, handler));
            handler.addObject(new SmartEnemy(random.nextInt(Game.WIDTH),random.nextInt(Game.HEIGHT),ID.SmartEnemy,handler));
            HUD.level = 5;
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
        if(Game.gameState==Game.STATE.ThirdStage){
            time3++;
            if (time3 % (2 * duration) == 0 && enemycount<=10) {
                for (int k=0;k<enemycount;k++) {
                    handler.addObject(new Basic_Enemy(Math.abs(random.nextInt(Game.WIDTH)),
                            Math.abs(random.nextInt(Game.HEIGHT)),
                            ID.Basic_Enemy));
                }
                enemycount++;
            }
            else if (Handler.object.size() == 1 && enemycount>=10) {
                Game.gameState=Game.STATE.FourthStage;
                GState();
            }
        }
        if(Game.gameState==Game.STATE.FourthStage){
            time4++;
            if(time4>=2000){
                Game.gameState=Game.STATE.FifthStage;
                GState();
            }
        }
    }

    public void render(Graphics g){
        if (Game.gameState == Game.STATE.FirstStage) {
            if (time1 < duration) {
                Font fnt = new Font("helvetica", 1, 32);
                g.setFont(fnt);
                g.setColor(Color.white);
                g.drawString("Use arrow keys to dodge enemies", 50, 50);
            }
        }
        if (Game.gameState == Game.STATE.SecondStage) {
            if (time2 < duration) {
                Font fnt = new Font("helvetica", 1, 28);
                g.setFont(fnt);
                g.setColor(Color.white);
                g.drawString("Press SPACE to do something super cool", 50, 50);
            }
        }
    }
}
