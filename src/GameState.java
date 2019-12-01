import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.List;

import static java.lang.Math.*;

public class GameState {
    private Handler handler;
    private int time;
    private int enemyWave = 0;
    private int duration = 100;
    private BossEnemy boss;
    private Random random = new Random();
    private boolean phase1Started = false;
    private boolean phase2Started = false;
    private boolean phase3Started = false;
    private int numPhase2Waves = 0;

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
            handler.addObject(new SmartEnemy(600, 50, ID.SmartEnemy));
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
            handler.addObject(new SmartEnemy(random.nextInt(Game.WIDTH), random.nextInt(Game.HEIGHT), ID.SmartEnemy));

            for (int j = 1; j < 18; j++) {
                handler.addObject(new BasicEnemySpam(600, (50 * j) % Game.HEIGHT, ID.BasicEnemySpam));
            }

            HUD.level = 4;

        } else if (Game.gameState == Game.STATE.FifthStage) {

            phase1Started = false;
            phase2Started = false;
            Handler.object.clear();
            Handler.initialEnemy = null;
            boss = new BossEnemy(Game.WIDTH - 80, Game.HEIGHT / 2 - 16, ID.BossEnemy);
            boss.setBossPhase(BossEnemy.PHASE_1);
            handler.addObject(new Player(100, 100, ID.Player, handler));
            handler.addObject(boss);
            HUD.level = 5;
        }
    }

    public void tick(){
        switch (Game.gameState) {
            case FirstStage:
                firstStageLogic();
                break;

            case SecondStage:
                secondStageLogic();
                break;

            case ThirdStage:
                thirdStageLogic();
                break;

            case FourthStage:
                fourthStageLogic();
                break;

            case FifthStage:
                fifthStageLogic();
                break;
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

    private void firstStageLogic() {
        time++;
        if (Handler.initialEnemy != null) {
            if (Handler.initialEnemy.getCount() == 5) {
                Game.gameState = Game.STATE.SecondStage;
                GState();
            }
        }
    }

    private void secondStageLogic() {
        time++;
        if(time >= 1000){
            Game.gameState = Game.STATE.ThirdStage;
            GState();
        }
    }

    private void thirdStageLogic() {
        time++;
        if ((time % (2 * duration) == 0 && enemyWave < 10) || enemyWave == 0) {
            for (int k = 1; k <= enemyWave + 1; k++) {
                handler.addObject(basicEnemyFromList(getSpawnCoords()));
            }
            enemyWave++;
        }
        else if ((Handler.object.size() == 1 && Handler.object.get(0).id == ID.Player) && enemyWave == 10) {
            Game.gameState = Game.STATE.FourthStage;
            GState();
        }
    }

    private void fourthStageLogic() {
        time++;
        boolean isSmartPresent = false;
        for (GameObject temp:
                Handler.object) {
            if (temp.id == ID.SmartEnemy) {
                isSmartPresent = true;
                break;
            }
        }
        if (time >= 2000 || !isSmartPresent){
            Game.gameState = Game.STATE.FifthStage;
            GState();
        }
    }

    private void fifthStageLogic() {
        time ++;
        if (Handler.bossEnemy != null) {
            if (Handler.bossEnemy.getBossPhase() == BossEnemy.PHASE_1) {
                if (((time % (duration)) == 0 && time < 2000) || !phase1Started) {
                    phase1Started = true;
                    spawnBossBasicEnemies();

                } else if (Handler.object.size() == 2) {
                    time = 0;
                    Handler.bossEnemy.setBossPhase(BossEnemy.PHASE_2);
                }

            } else if (Handler.bossEnemy.getBossPhase() == BossEnemy.PHASE_2) {

                if ((((time % (duration * 0.5)) == 0 && time < 2000) ) || !phase2Started) {

                    spawnBossBasicEnemies();

                    if (((time % (6 * duration)) == 0 && numPhase2Waves < 4) || !phase2Started) {
                        handler.addObject(smartEnemyFromList(new ArrayList<Integer>(
                                Arrays.asList(
                                        (int) Handler.bossEnemy.getX() - 30,
                                        (int) Handler.bossEnemy.getY(),
                                        -5,
                                        0))));
                        numPhase2Waves++;
                    }
                    phase2Started = true;

                } else if (Handler.object.size() == 2) {
                    // setting to phase 2
                    time = 0;
                    numPhase2Waves = 0;
                    Handler.bossEnemy.setBossPhase(BossEnemy.PHASE_3);
                }
            } else if (Handler.bossEnemy.getBossPhase() == BossEnemy.PHASE_3) {
                Handler.bossEnemy.setVelX(0);
                if (Handler.bossEnemy.getVelY() == 0) {
                    Handler.bossEnemy.setVelY(-4);
                }

                if ((((time % (duration * 3)) == 0 && time < 2000) ) || !phase3Started) {

                    spawnBossBasicEnemies();

                    if (((time % (6 * duration)) == 0 && numPhase2Waves < 4) || !phase3Started) {
                        handler.addObject(smartEnemyFromList(new ArrayList<Integer>(
                                Arrays.asList(
                                        (int) Handler.bossEnemy.getX() - 30,
                                        (int) Handler.bossEnemy.getY(),
                                        -5,
                                        0))));
                        numPhase2Waves++;
                    }
                    phase3Started = true;

                } else if (Handler.object.size() == 2) {
                    time = 0;
                    Handler.bossEnemy.setBossPhase(BossEnemy.PHASE_3);
                }
            }
        } else {
//                System.err.println("Should not be here. Kill program");
        }
    }

    private List<Float> getSpawnCoords() {
        List<Float> vals = new ArrayList<>();
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

        vals.add((float) x); vals.add((float) y); vals.add((float) velx); vals.add((float) vely);

        return vals;
    }

    private Basic_Enemy basicEnemyFromList(List<Float> enemy_params) {
        Basic_Enemy enemy = new Basic_Enemy(enemy_params.get(0), enemy_params.get(1), ID.Basic_Enemy);
        enemy.setVelX(enemy_params.get(2));
        enemy.setVelY(enemy_params.get(3));

        return enemy;
    }

    private SmartEnemy smartEnemyFromList(List<Integer> enemy_params) {
        SmartEnemy enemy = new SmartEnemy(enemy_params.get(0), enemy_params.get(1), ID.SmartEnemy);
        enemy.setVelX(enemy_params.get(2));
        enemy.setVelY(enemy_params.get(3));

        return enemy;
    }

    private void spawnBossBasicEnemies() {
        int radius = 30;
        handler.addObject(basicEnemyFromList(new ArrayList<Float>(
                Arrays.asList(
                        Handler.bossEnemy.getX() - 30 - radius,
                        Handler.bossEnemy.getY(),
                        (float) -5,
                        (float )0))));
        handler.addObject(basicEnemyFromList(new ArrayList<Float>(
                Arrays.asList(
                        Handler.bossEnemy.getX() - 30 - (float) (radius * cos(PI/6)),
                        Handler.bossEnemy.getY() + (float) (radius * sin(PI/6)),
                        (float)-5*(float)cos(PI/6) ,
                        (float) 5*(float)sin(PI/6)))));
        handler.addObject(basicEnemyFromList(new ArrayList<Float>(
                Arrays.asList(
                        Handler.bossEnemy.getX() - 30 - (float) (radius * cos(PI/6)),
                        Handler.bossEnemy.getY() - (float) (radius * sin(PI/6)),
                        (float) -5*(float)cos(PI/6),
                        (float) -5*(float)sin(PI/6)))));
        handler.addObject(basicEnemyFromList(new ArrayList<Float>(
                Arrays.asList(
                        Handler.bossEnemy.getX() - 30 - (float) (radius * cos(PI/3)),
                        Handler.bossEnemy.getY() + (float) (radius * sin(PI/3)),
                        (float)-5*(float)cos(PI/3) ,
                        (float) 5*(float)sin(PI/3)))));
        handler.addObject(basicEnemyFromList(new ArrayList<Float>(
                Arrays.asList(
                        Handler.bossEnemy.getX() - 30 - (float) (radius * cos(PI/3)),
                        Handler.bossEnemy.getY() - (float) (radius * sin(PI/3)),
                        (float) -5*(float)cos(PI/3),
                        (float) -5*(float)sin(PI/3)))));
    }
}
