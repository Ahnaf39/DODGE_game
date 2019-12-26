import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.Serializable;
import java.util.Random;

/**
 * Game class used to house all the game components
 * including the game loop and Window object used as a display
 */
public class Game extends Canvas implements Runnable,Serializable{
    private static final long serialVersionUID = 2733916649114610736L;

    /* Define game proportions */
    public static final int WIDTH = 640, HEIGHT = (WIDTH / 12) * 9;

    private Thread thread;
    private boolean running = false;

    private Handler handler;
    private HUD hud;
    private Boss_HUD boss_hud;
    private Menu menu;
    private GameState gstate;

    /**
     * Enum to describe all possible states that the game can be in
     * The game transitions are modelled as a finite state machine
     */
    public enum STATE{
        Menu,
        FirstStage,
        SecondStage,
        ThirdStage,
        FourthStage,
        FifthStage,
        Victory
    }

    /* Static gameState variable to inform other objects about the game state */
    public static STATE gameState = STATE.Menu;

    /**
     * Constructor for the game object initializes the game loop
     * by running the game thread
     */
    public Game(){
        handler = new Handler();
        menu = new Menu(this, handler);
        gstate = new GameState(handler);
        this.addKeyListener(new KeyInput());
        this.addMouseListener(menu);
        new Window(WIDTH,HEIGHT,"DODGE!",this);

        hud = new HUD();
        boss_hud = new Boss_HUD();
    }

    /**
     * Start method for the game object to start the game thread
     */
    public synchronized void start(){
        thread = new Thread(this);
        thread.start();

        running = true;
    }

    /**
     * Stop method for the game object to stop the game thread
     */
    public synchronized void stop(){
        try{
            thread.join();
            running = false;
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Game loop for the thread runnable
     */
    public void run(){
        long lastTime = System.nanoTime();
        double amountofTicks = 60.0;
        double ns = 1000000000/amountofTicks;
        double delta =0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        this.requestFocus();
        while(running){
            long now = System.nanoTime();
            delta+=(now-lastTime)/ns;
            lastTime=now;
            while(delta>=1){
                tick();
                delta--;
            }
            if(running){
                render();
            }
            frames++;
            if(System.currentTimeMillis()-timer>1000){
                timer+=1000;
                //System.out.println("FPS: "+ frames);

                frames =0;
            }
        }
        stop();
    }

    /**
     * tick method to update at every iteration of the game loop
     */
    private void tick(){
        handler.tick();
        if(gameState != STATE.Menu) {
            if (gameState != STATE.Victory) {
                hud.tick();
            }
            gstate.tick();
            if(gameState==STATE.FifthStage){
                boss_hud.tick();
            }

        }
        else {
            menu.tick();
        }
    }

    private void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.black);
        g.fillRect(0,0,WIDTH,HEIGHT);

        handler.render(g);
        if (gameState != STATE.Menu ) {
            if (gameState != STATE.Victory) {
                hud.render(g);
            }
            gstate.render(g);
            if(gameState==STATE.FifthStage){
                boss_hud.render(g);
            }
        }
        else {
            menu.render(g);
        }
        g.dispose();
        bs.show();
    }

    /**
     * Main method to run the game window.
     * @param args input args
     */
    public static void main(String args[]){
        new Game();
    }

}