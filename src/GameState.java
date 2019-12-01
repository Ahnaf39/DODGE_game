import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.List;

public class GameState {
    private Handler handler;
    private int time;
    private int enemyWave = 0;
    private int duration = 100;
    private BossEnemy boss;
    private Random random = new Random();
    private boolean bossStarted = false;

    public GameState(Handler handler){
        this.handler=handler;
    }

    public void GState(){

        time = 0;

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

            bossStarted = false;
            Handler.object.clear();
            Handler.initialEnemy = null;
            boss = new BossEnemy(Game.WIDTH - 80, Game.HEIGHT / 2 - 16, ID.BossEnemy);
            handler.addObject(new Player(100, 100, ID.Player, handler));
            handler.addObject(boss);
            System.out.println(Handler.object);
            HUD.level = 5;
            System.out.println(Game.gameState);
        }
    }

    public void tick(){
        if (Game.gameState == Game.STATE.FirstStage) {
            time++;
            if (Handler.initialEnemy != null) {
                if (Handler.initialEnemy.getCount() == 5) {
                    Game.gameState = Game.STATE.SecondStage;
                    GState();
                }
            }
        } else if (Game.gameState == Game.STATE.SecondStage){
            time++;
            if(time >= 1000){
                Game.gameState = Game.STATE.ThirdStage;
                GState();
            }
        } else if (Game.gameState == Game.STATE.ThirdStage){
            time++;
            List<Integer> coords;
            if ((time % (2 * duration) == 0 && enemyWave < 10) || enemyWave == 0) {
                for (int k = 1; k <= enemyWave + 1; k++) {
                    handler.addObject(basicEnemyFromList(getSpawnCoords()));
                }
                enemyWave++;
                System.out.println(enemyWave);
                System.out.println("items in handler: " + Handler.object.size());
            }
            else if ((Handler.object.size() == 1 && Handler.object.get(0).id == ID.Player) && enemyWave == 10) {
                System.out.println("reached here");
                Game.gameState = Game.STATE.FourthStage;
                GState();
            }
        } else if (Game.gameState == Game.STATE.FourthStage){
            time++;
            if (time >= 2000){
                Game.gameState = Game.STATE.FifthStage;
                GState();
            }
        } else if (Game.gameState == Game.STATE.FifthStage) {
            time ++;
//            System.out.println(bossStarted);
            if (Handler.bossEnemy != null) {
                if (Handler.bossEnemy.getBossPhase() == BossEnemy.PHASE_1) {
                    if (((time % (2 * duration)) == 0 && time < 2000) || !bossStarted) {
                        // TODO figure out velocity and initial placement math
                        bossStarted = true;
                        handler.addObject(basicEnemyFromList(new ArrayList<Integer>(
                                                            Arrays.asList(
                                                                    (int) Handler.bossEnemy.getX() - 30,
                                                                    (int) Handler.bossEnemy.getY(),
                                                                    -5,
                                                                    0))));
                        handler.addObject(basicEnemyFromList(new ArrayList<Integer>(
                                                            Arrays.asList(
                                                                    (int) Handler.bossEnemy.getX() - 30 + 10,
                                                                    (int) Handler.bossEnemy.getY() + 17,
                                                                    -5,
                                                                    5))));
                        handler.addObject(basicEnemyFromList(new ArrayList<Integer>(
                                                            Arrays.asList(
                                                                    (int) Handler.bossEnemy.getX() - 30 + 10,
                                                                    (int) Handler.bossEnemy.getY() - 17,
                                                                    -5,
                                                                    -5))));
                        handler.addObject(basicEnemyFromList(new ArrayList<Integer>(
                                                            Arrays.asList(
                                                                    (int) Handler.bossEnemy.getX() - 30 + 10 + 10,
                                                                    (int) Handler.bossEnemy.getY() + 17 + 17,
                                                                    -5,
                                                                    5))));
                        handler.addObject(basicEnemyFromList(new ArrayList<Integer>(
                                                            Arrays.asList(
                                                                    (int) Handler.bossEnemy.getX() - 30 + 10 + 10,
                                                                    (int) Handler.bossEnemy.getY() - 17 - 17,
                                                                    -5,
                                                                    -5))));
                    } else if (Handler.object.size() == 2) {
                        // setting to phase 2
                        System.out.println("setting to phase 2");
                        time = 0;
                        Handler.bossEnemy.setBossPhase(BossEnemy.PHASE_2);
                    }

                } else if (Handler.bossEnemy.getBossPhase() == BossEnemy.PHASE_2) {
                    // TODO add phase 2 minions/mechanics
//                    System.out.println("set to phase 2; time = " + time);


//
//                    handler.addObject(basicEnemyFromList(new ArrayList<Integer>(Arrays.asList(1,2,4,5))));
//                    handler.addObject(basicEnemyFromList(new ArrayList<Integer>(Arrays.asList(1,2,4,5))));
//                    handler.addObject(basicEnemyFromList(new ArrayList<Integer>(Arrays.asList(1,2,4,5))));
//                    handler.addObject(basicEnemyFromList(new ArrayList<Integer>(Arrays.asList(1,2,4,5))));
//                    handler.addObject(basicEnemyFromList(new ArrayList<Integer>(Arrays.asList(1,2,4,5))));

                }
            } else {
//                System.out.println("here for some reason");
            }
        }
    }

    public void render(Graphics g){
        if (Game.gameState == Game.STATE.FirstStage) {
            if (time < duration) {
                Font fnt = new Font("helvetica", 1, 32);
                g.setFont(fnt);
                g.setColor(Color.white);
                g.drawString("Use arrow keys to dodge enemies", 50, 50);
            }
        } else if (Game.gameState == Game.STATE.SecondStage) {
            if (time < duration) {
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
        int spawnHeight = Game.HEIGHT - 40;
        int spawnWidth = Game.WIDTH - 40;
        switch (side) {
            // spawn on top
            case 0:
                y = 0;
                x = random.nextInt(spawnWidth);
                velx = random.nextInt(10) - 10;
                vely = random.nextInt(10);
                break;
            // spawn on bottom
            case 1:
                y = spawnHeight;
                x = random.nextInt(spawnWidth);
                velx = random.nextInt(20) - 10;
                vely = -1 * random.nextInt(10);
                break;
            // spawn on left
            case 2:
                y = random.nextInt(spawnHeight);
                x = 0;
                velx = random.nextInt(10);
                vely = random.nextInt(20) - 10;
                break;
            // spawn on right
            case 3:
                y = random.nextInt(spawnHeight);
                x = spawnWidth;
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

    private Basic_Enemy basicEnemyFromList(List<Integer> enemy_params) {
        Basic_Enemy enemy = new Basic_Enemy(enemy_params.get(0), enemy_params.get(1), ID.Basic_Enemy);
        enemy.setVelX(enemy_params.get(2));
        enemy.setVelY(enemy_params.get(3));

        return enemy;
    }

    private SmartEnemy smartEnemyFromList(List<Integer> enemy_params) {
        SmartEnemy enemy = new SmartEnemy(enemy_params.get(0), enemy_params.get(1), ID.SmartEnemy, handler);
        enemy.setVelX(enemy_params.get(2));
        enemy.setVelY(enemy_params.get(3));

        return enemy;
    }
}
