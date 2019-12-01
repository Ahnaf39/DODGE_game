import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public class GameState {
    private Handler handler;
    private int time1 = 0;
    private int time2 = 0;
    private int time3 = 0;
    private int time4 = 0;
    private int enemyWave = 0;
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

        } else if (Game.gameState == Game.STATE.SecondStage) {

            Handler.object.clear();
            Handler.initialEnemy = null;
            handler.addObject(new Player(100, 100, ID.Player, handler));
            handler.addObject(new SmartEnemy(600, 50, ID.SmartEnemy,handler));
            HUD.level = 2;

        } else if (Game.gameState == Game.STATE.ThirdStage) {

            Handler.object.clear();
            Handler.initialEnemy = null;
            handler.addObject(new Player(100, 100, ID.Player, handler));
            HUD.level = 3;
            enemyWave = 0;

        } else if (Game.gameState == Game.STATE.FourthStage) {

            Handler.object.clear();
            Handler.initialEnemy = null;
            handler.addObject(new Player(100, 100, ID.Player, handler));
            handler.addObject(new SmartEnemy(random.nextInt(Game.WIDTH),random.nextInt(Game.HEIGHT),ID.SmartEnemy,handler));

            for (int j = 1; j < 20; j++) {
                handler.addObject(new BasicEnemySpam(600, 50 * (j / 5), ID.BasicEnemySpam));
            }

            HUD.level = 4;

        } else if (Game.gameState == Game.STATE.FifthStage) {

            Handler.object.clear();
            Handler.initialEnemy = null;
            handler.addObject(new Player(100, 100, ID.Player, handler));
            handler.addObject(new SmartEnemy(random.nextInt(Game.WIDTH),random.nextInt(Game.HEIGHT),ID.SmartEnemy,handler));
            HUD.level = 5;

        }
    }

    public void tick(){
        if (Game.gameState == Game.STATE.FirstStage) {
            time1++;
            if (Handler.initialEnemy != null) {
                if (Handler.initialEnemy.getCount() == 5) {
                    Game.gameState = Game.STATE.SecondStage;
                    GState();
                }
            }
        }
        if(Game.gameState == Game.STATE.SecondStage){
            time2++;
            if(time2 >= 1000){
                Game.gameState = Game.STATE.ThirdStage;
                GState();
            }
        }
        if(Game.gameState == Game.STATE.ThirdStage){
            time3++;
            List<Integer> coords;
            if ((time3 % (2 * duration) == 0 && enemyWave < 10) || enemyWave == 0) {
                for (int k = 1; k <= enemyWave + 1; k++) {
                    coords = getSpawnCoords();
                    Basic_Enemy enemy = new Basic_Enemy(coords.get(0), coords.get(1), ID.Basic_Enemy);
                    enemy.setVelX(coords.get(2));
                    enemy.setVelY(coords.get(3));
                    handler.addObject(enemy);
                }
                enemyWave++;
                System.out.println(enemyWave);
            }
            else if ((Handler.object.size() == 1 && Handler.object.get(0).id == ID.Player) && enemyWave == 10) {
                System.out.println("reached here");
                Game.gameState = Game.STATE.FourthStage;
                GState();
            }
        }
        if(Game.gameState == Game.STATE.FourthStage){
            time4++;
            if(time4 >= 2000){
                Game.gameState = Game.STATE.FifthStage;
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

    private List<Integer> getSpawnCoords() {
        List<Integer> vals = new ArrayList<>();
        int side = random.nextInt(3);
        int x = 0, y = 0;
        int velx = 0, vely = 0;
        switch (side) {
            // spawn on top
            case 0:
                y = 0;
                x = random.nextInt(Game.WIDTH);
                velx = random.nextInt(20) - 10;
                vely = random.nextInt(10);
                break;
            // spawn on bottom
            case 1:
                y = Game.HEIGHT;
                x = random.nextInt(Game.WIDTH);
                velx = random.nextInt(20) - 10;
                vely = -1 * random.nextInt(10);
                break;
            // spawn on left
            case 2:
                y = random.nextInt(Game.HEIGHT);
                x = 0;
                velx = random.nextInt(10);
                vely = random.nextInt(20) - 10;
                break;
            // spawn on right
            case 3:
                y = random.nextInt(Game.HEIGHT);
                x = Game.WIDTH;
                velx = -1 * random.nextInt(10);
                vely = random.nextInt(20) - 10;
                break;
        }

        if (velx > -3 && velx <= 0) velx = -3;
        if (velx < 3 && velx >= 0) velx = 3;
        if (vely > -3 && vely <= 0) vely = -3;
        if (vely < 3 && vely >= 0) vely = 3;

        vals.add(x); vals.add(y); vals.add(velx); vals.add(vely);

        return vals;
    }
}
