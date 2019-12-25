import java.awt.*;
import java.awt.Rectangle;

public abstract class GameObject {
    protected float x,y;
    protected ID id;
    protected float velX, velY;

    /**
     * Constructor for general game object base class for Player and enemies.
     *
     * @param x initial x-coordinate starting position of the object
     * @param y initial y-coordinate starting position of the object
     * @param id ID attached with the object
     */
    public GameObject (float x, float y, ID id){
        this.x =x;
        this.y=y;
        this.id=id;
    }

    // ********** Abstract methods for velocity objects ************* //
    public abstract void tick();
    public abstract void render(Graphics g);
    public abstract Rectangle getBounds();

    // ********** Getters and setters for game object ************ //
    float getX(){
        return x;
    }

    float getY(){
        return y;
    }

    public ID getId() {
        return id;
    }

    public float getVelX(){
        return velX;
    }

    void setVelX(float velX){
        this.velX = velX;
    }

    public float getVelY(){
        return velY;
    }

    public void setVelY(float velY){
        this.velY = velY;
    }

}
