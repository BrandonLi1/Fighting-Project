import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;



public class Character implements ActionListener {
    String name;
    int health, basicChain, height, width, speed, jumpHeight, comboCounter, xCoord, yCoord;
    double meter;
    boolean stunned, IFrames, isGrounded, blocking;
    private Animation animation;
    private BufferedImage temp;
    private Timer timer;
    private int countdown;



    public Character(String name, int health, int basicChain,
                     int height, int width, int speed, int jumpHeight,
                      int comboCounter, double meter, int xCoord, int yCoord,
                     boolean stunned, boolean IFrames, boolean isGrounded) {
        timer = new Timer(1,this);
        countdown = 100;
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
        try {
            temp = ImageIO.read(new File("src\\marioright.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        /*ArrayList<BufferedImage> images = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            String filename = "src\\tile00" + i + ".png";
            try {
                images.add(ImageIO.read(new File(filename)));
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        animation = new Animation(images,50);*/
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
        if (xCoord + speed <= 920) {
            xCoord += speed;
        }
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    public BufferedImage getPlayerImage() {
        return temp;  // updated
    }

    public void moveLeft() {
        if (xCoord - speed >= 0) {
            xCoord -= speed;
        }
    }

    public void jump() {
        if(isGrounded){
            timer.start();
            if(countdown == 0){
                timer.stop();
            }
        }
    }

    public void block() {
        if (isGrounded) {
            blocking=true;
        }
    }

    public void setGrounded(boolean grounded) {
        isGrounded = grounded;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==timer) {
            yCoord-=5;
            countdown-=5;
        }
        if(countdown == 0){
            timer.stop();
        }
        if (!timer.isRunning()) {
            //fall
        }
    }

    public void attackRect(int width, int height, int x, int y) {

    }
}
