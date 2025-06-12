import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.*;

public class Character implements ActionListener {
    String name;
    int health, basicChain, height, width, speed, comboCounter, currentBasic, maxHealth, normalD, heavyD, heavyDamage;
    double jumpHeight, xCoord, yCoord, meter;
    boolean stunned, IFrames, isGrounded, blocking, facingRight, isAttacking;
    Animation animation;
    private Animation animation2;
    private Animation animation3;
    private Timer timer;
    int countdown, countdown2, aWidth, aHeight, attackDamage, animationNum;
    private double temp2;



    public Character(String name, int health, int basicChain,
                     int height, int width, int speed, int jumpHeight,
                      int comboCounter, double meter, int xCoord, int yCoord,
                     boolean stunned, boolean IFrames, boolean isGrounded,
                     int attackDamage, int normalD, int heavyD, int heavyDamage) {

        timer = new Timer(5,this);
        countdown = 50;
        countdown2 = 50;;
        maxHealth = health;
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
        this.normalD = normalD;
        this.heavyD = heavyD;
        this.heavyDamage = heavyDamage;
    }


    public Rectangle hitBox() {
        Rectangle rect = new Rectangle((int) xCoord, (int) yCoord, width, height);
        return rect;
    }
    public int getHealth(){
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
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

    public void setBlocking(boolean blocking) {
        this.blocking = blocking;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addMeter(double meter) {
        this.meter+=meter;
        if (this.meter>=7) {
            this.meter=7;
        }
    }

    public void moveRight() {
        if (xCoord + speed <= 1750 && !blocking && !stunned) {
            xCoord += speed;
        }
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    public BufferedImage getPlayerImage() {
//        if (animationNum == 1) {
//            return animation.getActiveFrame();  // updated
//        } else if (animationNum == 3 ) {
//            return animation3.getActiveFrame();
//        } else {
//            return animation2.getActiveFrame();
//        }
        return animation.getActiveFrame();
    }


    public void moveLeft() {
        if (xCoord - speed >= 0 && !blocking && !stunned) {
            xCoord -= speed;
        }
    }

    public void jump() {
        if(isGrounded){
            timer.start();
        }
    }

    public void setAttack(int width, int height) {
        aHeight = height;
        aWidth = width;
    }

    public Rectangle attack() throws IOException {
        setAnimationNum(4);
        setAttack(100, height);
        animation.resume();
        if (facingRight) {
            return new Rectangle((int) (xCoord+width), (int) yCoord, -aWidth, aHeight);
        }
        return new Rectangle((int) (xCoord-width), (int) yCoord, aWidth, aHeight);
    }

    public Rectangle heavyAttack() {
        return new Rectangle();
    }

    public Rectangle special1() {
        return new Rectangle();
    }


    public void block() {
        if (isGrounded && !stunned) {
            blocking=true;
            setAnimationNum(5);
        }
    }

    public void checkGrounded() {
        if (yCoord>=700) {
            isGrounded=true;
        }
    }

    public void setNormalD(int normalD) {
        this.normalD = normalD;
    }

    public void setAnimationNum(int num){
        animationNum = num;
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
            return (xCoord + width);
        }
    }
    public int getWidth() {
        if (facingRight) {
            return width;
        } else {
            return width*-1;
        }
    }





    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==timer && countdown>0) {
            isGrounded = false;
            yCoord-=jumpHeight;
            countdown-=3;
            jumpHeight-= 0.2;
        }
        if (countdown<=0) {
            yCoord += jumpHeight + 0.2;
            countdown2 -= 3;
            if(countdown2 <= 0){
                jumpHeight=temp2;
                countdown2=100;
                countdown= 100;
                isGrounded = true;
                timer.restart();
                timer.stop();
            }
            jumpHeight += 0.2;
        }
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Rectangle hitbox() {
        if (facingRight) {
            return new Rectangle((int) xCoord, (int) yCoord, width, height);
        }
        return new Rectangle((int) xCoord, (int) yCoord, -width, height);
    }

    public void setGlorpState(boolean x) {
        //nothing
    }


    public int getAttackDamage() {
        return attackDamage;
    }

    //merge force
    //left right make player have to manually face enemy cus less code
}
