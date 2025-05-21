import java.awt.*;

public class Character {
    String name;
    int health;
    int basicChain;
    int height;
    int width;
    int speed;
    int jumpHeight;
    int comboCounter;
    double meter;
    int xCoord;
    int yCoord;
    boolean stunned;
    boolean IFrames;


    public Character(String name, int health, int basicChain,
                     int height, int width, int speed, int jumpHeight,
                      int comboCounter, double meter, int xCoord, int yCoord,
                     boolean stunned, boolean IFrames) {
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

    }
    public void moveLeft() {

    }

    public void jump() {

    }

    public void block() {

    }
}
