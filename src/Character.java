import java.awt.*;
import java.awt.image.BufferedImage;

public class Character {
    private final int MOVE_AMT = 3;
    String name;
    int health, basicChain, height, width, speed, jumpHeight, comboCounter, xCoord, yCoord;
    double meter;
    boolean stunned, IFrames, isGrounded;
    private Animation animation;


    public Character(String name, int health, int basicChain,
                     int height, int width, int speed, int jumpHeight,
                      int comboCounter, double meter, int xCoord, int yCoord,
                     boolean stunned, boolean IFrames, boolean isGrounded) {
        this.name=name;
        this.health=health;
        this.basicChain=basicChain;
        this.height=height;
        this.width=width;
        this.speed=speed;
        this.jumpHeight=jumpHeight;
        this.comboCounter=comboCounter;
        this.meter=meter;
        this.stunned=stunned;
        this.IFrames=IFrames;
        this.xCoord=xCoord;
        this.yCoord=yCoord;
        this.isGrounded=isGrounded;
    }


    public Rectangle hitBox() {
        Rectangle rect = new Rectangle(xCoord, yCoord, width, height);
        return rect;
    }

    public void setIFrames(boolean IFrames) {
        this.IFrames = IFrames;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setStunned(boolean stunned) {
        this.stunned = stunned;
    }

    public void setComboCounter(int comboCounter) {
        this.comboCounter = comboCounter;
    }

    public void setMeter(int meter) {
        this.meter = meter;
    }

    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
    }

    public void moveRight() {
        if (xCoord + MOVE_AMT <= 920) {
            xCoord += MOVE_AMT;
        }
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    public BufferedImage getPlayerImage() {
        return animation.getActiveFrame();  // updated
    }

    public void moveLeft() {
        if (xCoord - MOVE_AMT >= 0) {
            xCoord -= MOVE_AMT;
        }
    }

    public void jump() {

    }

    public void block() {

    }
}
