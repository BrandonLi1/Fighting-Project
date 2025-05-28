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
    int health, basicChain, height, width, speed, comboCounter;
    double jumpHeight, xCoord, yCoord;;
    double meter;
    boolean stunned, IFrames, isGrounded, blocking;
    private Animation animation;
    private BufferedImage temp;
    private Timer timer;
    private Timer timer2;
    private int countdown, countdown2, aWidth, aHeight, attackDamage;
    double temp2;



    public Character(String name, int health, int basicChain,
                     int height, int width, int speed, int jumpHeight,
                      int comboCounter, double meter, int xCoord, int yCoord,
                     boolean stunned, boolean IFrames, boolean isGrounded,
                     int attackDamage) {
            timer = new Timer(1,this);
            timer2 = new Timer(1,this);
        timer = new Timer(1,this);
        timer2 = new Timer(1,this);
        countdown = 100;
        countdown2 = 100;
        facingRight = true;
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
        temp2=jumpHeight;
        this.attackDamage = attackDamage;
        try {
            temp = ImageIO.read(new File("src\\marioright.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ArrayList<BufferedImage> images = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            String filename = "src\\Luffy\\Walk\\luffy00" + i + ".png";
            try {
                images.add(ImageIO.read(new File(filename)));
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        animation = new Animation(images,50);
    }


    public Rectangle hitBox() {
        Rectangle rect = new Rectangle((int) xCoord, (int) yCoord, width, height);
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

    public void setName(String name) {
        this.name = name;
    }

    public void moveRight() {
        if (xCoord + speed <= 1860) {
            xCoord += speed;
        }
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    public BufferedImage getPlayerImage() {
        return animation.getActiveFrame();  // updated
    }


    public void moveLeft() {
        if (xCoord - speed >= 0) {
            xCoord -= speed;
        }
    }

    public void jump() {
        if(isGrounded){
            timer.start();
            timer2.start();
        }
    }

    public void setAttack(int width, int height) {
        aHeight = height;
        aWidth = width;
    }

    public Rectangle attack (int locationX, int locationY, boolean direction) {
        if (!direction) {
            return new Rectangle(locationX, locationY, -aWidth, -aHeight);
        }
        return new Rectangle(locationX, locationY, aWidth, aHeight);
    }

    public void block() {
        if (isGrounded) {
            blocking=true;
        }
    }

    public void checkGrounded() {
        if (yCoord>=700) {
            isGrounded=true;
        }
    }


    public void faceRight() {
        facingRight = true;
    }


    public void faceLeft() {
        facingRight = false;
    }


    // newly added, used for right-clicking to turn
    public void turn() {
        if (facingRight) {
            faceLeft();
        } else {
            faceRight();
        }
    }
    public double getxCoord() {
        if (facingRight) {
            return xCoord;
        } else {
            return (xCoord + 150);
        }
    } public int getWidth() {
        if (facingRight) {
            return 150;
        } else {
            return 150*-1;
        }
    }





    @Override
    public void actionPerformed(ActionEvent e) {
        double x=5;
        if (e.getSource()==timer) {
            yCoord-=x;
            isGrounded = false;
            yCoord-=jumpHeight;
            countdown-=2;
            if(countdown <= 0){
                timer.stop();
                //jumpHeight=temp2;
            }
            jumpHeight-= 0.2;
        } else if (e.getSource() == timer2 && !timer.isRunning()) {
            yCoord += jumpHeight + 0.2;
            countdown2 -= 2;
            if(countdown2 <= 0){
                timer2.stop();
                jumpHeight=temp2;
                countdown2=100;
                countdown= 100;
                isGrounded = true;
            }
            jumpHeight += 0.2;
        }

    }


    public int getAttackDamage() {
        return attackDamage;
    }

    //merge force
    //left right make player have to manually face enemy cus less code
}
