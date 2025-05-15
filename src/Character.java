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
    int meter;
    int xCoord;
    int yCoord;
    boolean stunned;
    boolean IFrames;


    public Character(String name, int health, int basicChain,
                     int height, int width, int speed, int jumpHeight,
                      int comboCounter, int meter, int xCoord, int yCoord,
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


}
