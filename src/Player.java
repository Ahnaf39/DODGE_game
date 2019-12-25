import java.awt.*;

public class Player extends GameObject{
    static int PLAYER_ALIVE = 1;
    static int PLAYER_DEAD = 0;

    private int state;

    private int PLAYER_SIZE = 26;
    private int ARC_SIZE = 8;
    private int PLAYER_LOCATION_BOUND = 32;

    private int deathEffectCount = 0;

    /**
     * Create a new Player object that can be controlled with keyboard inputs (arrow keys)
     * @param x initial x-coordinate starting position of the player
     * @param y initial y-coordinate starting position of the player
     * @param id ID attached to player object
     */
    Player(int x, int y, ID id) {
        super(x, y, id);
        state = PLAYER_ALIVE;
    }

    /**
     * Produce the effective area where collisions with enemies cause the player damage
     * @return a Rectangle object to define the bounds of collision for the player
     */
    public Rectangle getBounds(){
        return new Rectangle((int) x, (int) y, PLAYER_SIZE, PLAYER_SIZE);
    }

    /**
     * Control the player movement for each tick, prevent player from crossing
     * screen boundaries.
     */
    public void tick() {
        x += velX;
        y += velY;

        if (x >= Game.WIDTH - PLAYER_LOCATION_BOUND){
            x = Game.WIDTH - PLAYER_LOCATION_BOUND;
        }
        else if (x <= 0){
            x = 0;
        }
        if (y >= Game.HEIGHT - 2 * PLAYER_LOCATION_BOUND){
            y = Game.HEIGHT - 2 * PLAYER_LOCATION_BOUND;
        }
        else if (y <= 0){
            y = 0;
        }

        collision();
    }

    /**
     * Deal with player collisions with enemies in the game.
     * There are two effective modes which are dealt with using branches:
     *      - Easy Mode: Player gets a health buffer to allow for some sustained damage
     *      - Hard Mode: One hit KO from enemies
     *
     * All enemies provide the same amount of damage for each unit of time spent in contact
     * with the player. Continuous contact is essentially emulated by continuous collisions and
     * damage afflicted.
     *
     * Note that the boss also has a health bar and is subject to a 'vulnerable' phase and is
     * dealt damage instead of the player at this time, which requires a separate check.
     */
    private void collision(){
        for (int i = 0; i < Handler.object.size(); i++) {

            GameObject tempObject = Handler.object.get(i);

            if (tempObject.getId() != ID.BossEnemy && tempObject.getId() != ID.Player) {

                /* Same damage from all non-boss enemies at all times */

                if(getBounds().intersects(tempObject.getBounds())){
                    if (!Menu.isHardMode) {
                        HUD.HEALTH--;
                    } else {
                        HUD.HEALTH -= 100;
                    }
                }

            } else if (tempObject.getId() == ID.BossEnemy) {

                /*
                 * Boss has a vulnerable phase, must check for that to see if
                 * the boss takes damage or if the player takes damage
                 */

                if(getBounds().intersects(tempObject.getBounds())){
                    if (Handler.bossEnemy != null) {
                        if (Handler.bossEnemy.getIsVulnerable()) {
                            Boss_HUD.HEALTH--;
                            if (Boss_HUD.HEALTH == 0){
                                Handler.object.clear();
                                Handler.initialEnemy = null;
                                Game.gameState = Game.STATE.Victory;
                            }
                        } else {
                            if (!Menu.isHardMode) {
                                HUD.HEALTH--;
                            } else {
                                HUD.HEALTH -= 100;
                            }
                        }
                    }
                }
            }

        }
    }

    /**
     * Set player object state indicating dead/alive status
     * @param state the player state
     */
    void setPlayerState(int state) {
        this.state = state;
    }

    /**
     * Get player object state indicating dead/alive status
     * @return (int) the player state
     */
    int getPlayerState() {
        return this.state;
    }

    /**
     * Render the player at all ticks
     * @param g Graphics object used to render the player
     */
    public void render(Graphics g) {

        g.setColor(Color.white);
        g.fillRoundRect((int)x,(int)y,PLAYER_SIZE,PLAYER_SIZE,ARC_SIZE,ARC_SIZE);

    }

    /**
     * Animate player death and reset all game parameters for restart
     * @param g Graphics object used to render objects
     */
    void playerDeath(Graphics g) {
        if (deathEffectCount == 0) {

            g.setColor(Color.YELLOW);
            g.fillRoundRect((int)x, (int)y, PLAYER_SIZE, PLAYER_SIZE, ARC_SIZE * 2, ARC_SIZE * 2);
            deathEffectCount++;

        } else if (deathEffectCount >= 1 && deathEffectCount <= 500) {

            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    float xPos = x + (i * ((int) (deathEffectCount / 100)) * 25) + 16 - 5;
                    float yPos = y + (j * ((int) (deathEffectCount / 100)) * 25) + 16 - 5;

                    if (!(i == 0 && j == 0)) {
                        g.setColor(Color.YELLOW);
                        g.fillRoundRect((int) xPos, (int) yPos, PLAYER_SIZE / 2, PLAYER_SIZE / 2, ARC_SIZE, ARC_SIZE);
                    }
                }
            }

            deathEffectCount ++;
            g.setColor(Color.black);
            g.fillRoundRect((int) x, (int) y, PLAYER_SIZE, PLAYER_SIZE,ARC_SIZE,ARC_SIZE);

        } else {

            g.setColor(Color.black);
            g.fillRoundRect((int) x, (int) y, PLAYER_SIZE, PLAYER_SIZE,ARC_SIZE,ARC_SIZE);
            Game.gameState = Game.STATE.Menu;
            Handler.object.clear();
            HUD.HEALTH = 100;
            Handler.initialEnemy = null;
            Handler.bossEnemy = null;

        }
    }
}
