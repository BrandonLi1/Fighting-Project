import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Luffy extends Character {
    Animation animation;
    private Animation animation2;
    private Animation animation3;
    private Animation animation4;
    private Animation temp;
    private Animation animation5;
    private Animation animation6;
    private int animationNum;
    private int failCheck;
    public int comboNum;
    private boolean ult;
    int energy;
    private boolean isAttacking;
    ArrayList<BufferedImage> images = new ArrayList<>();

    public Luffy() {
        super("Luffy", 500, 3, 200, 200, 5, 10, 3, 0.0, 300, 675, false, false, true, 10,600, 1000, 10);
        isAttacking = false;
        comboNum = 3;
        ArrayList<BufferedImage> images = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            String filename = "src\\Luffy\\Walk\\luffyrun00" + i + ".png";
            try {
                images.add(ImageIO.read(new File(filename)));
            }
            catch (IOException e) {
                System.out.println(e.getMessage() + filename);
            }
        }
        animation = new Animation(images,50, true);
        //Luffy.Saber.Walk.idle animation
        ArrayList<BufferedImage> images2 = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            String filename = "src\\Luffy\\Walk\\idle\\luffyidle00" + i + ".png";
            try {
                images2.add(ImageIO.read(new File(filename)));
            }
            catch (IOException e) {
                System.out.println(e.getMessage() + filename);
            }
        }
        animation2 = new Animation(images2,300, true);
        //luffy Luffy.Saber.Walk.jump animation
        ArrayList<BufferedImage> images3 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            String filename = "src\\Luffy\\Walk\\jump\\LuffyUp00" + i + ".png";
            try {
                images3.add(ImageIO.read(new File(filename)));
            }
            catch (IOException e) {
                System.out.println(e.getMessage() + filename);
            }
        }
        animation3 = new Animation(images3,50, true);
        //jump
        ArrayList<BufferedImage> images4 = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            String filename = "src\\Luffy\\Walk\\Heavy\\luffyheavy00" + i + ".png";
            try {
                images4.add(ImageIO.read(new File(filename)));
            }
            catch (IOException e) {
                System.out.println(e.getMessage() + filename);
            }
        }
        animation4 = new Animation(images4,75, false);
        temp = animation4;
        ArrayList<BufferedImage> images5 = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            String filename = "src\\Luffy\\Walk\\Attack\\luffyattack00" + i + ".png";
            try {
                images5.add(ImageIO.read(new File(filename)));
            }
            catch (IOException e) {
                System.out.println(e.getMessage() + filename);
            }
        }
        animation5 = new Animation(images5,100, false);
        ArrayList<BufferedImage> images6 = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            String filename = "src\\Luffy\\Walk\\Ult\\luffyult00" + i + ".png";
            try {
                images6.add(ImageIO.read(new File(filename)));
            }
            catch (IOException e) {
                System.out.println(e.getMessage() + filename);
            }
        }
        animation6 = new Animation(images6,100, false);        //attack

    }

    public Animation getAnimation3() {
        return animation3;
    }

    @Override
    public void setAnimationNum(int num){
        animationNum = num;
    }

    @Override
    public BufferedImage getPlayerImage() {
        if (isAttacking) {
            if (!animation4.isRunning() && !animation5.isRunning()) {
                isAttacking = false;
                if(ult == true){
                    setyCoord((int) (yCoord + 300));
                    failCheck = 0;
                }
                ult = false;
                setHeight(200);
                setWidth(200);
                animationNum = 2;
            } else {
                return animation4.getActiveFrame();
            }
        }

        if (animationNum == 1) {
            return animation.getActiveFrame();  // updated
        } else if (animationNum == 3) {
            return animation3.getActiveFrame();
        } else if (animationNum == 4){
            return animation4.getActiveFrame();
        } else if(animationNum == 5) {
            return animation5.getActiveFrame();
        }else{
            return animation2.getActiveFrame();
        }
    }

    @Override
    public Rectangle attack() {
        animation4=animation5;
        if (!isAttacking) {
            isAttacking = true;
            animation5.reset(); //change to 6
            animation5.resume();
        }
        setAnimationNum(5);
        if (facingRight) {
            return new Rectangle((int) (xCoord+width), (int) (yCoord), aWidth, aHeight);
        }
        return new Rectangle((int) (xCoord-width+100), (int) (yCoord), aWidth, aHeight);
    }
    @Override
    public Rectangle heavyAttack() { //ani 6
        animation4 = temp;
        setAttack(100, height);
        if (!isAttacking) {
            isAttacking = true;
            animation4.reset(); //change to 6
            animation4.resume();
        }
        setAnimationNum(4);
        if (facingRight) {
            return new Rectangle((int) (xCoord+width), (int) (yCoord), aWidth, aHeight);
        }
        return new Rectangle((int) (xCoord-width + 100), (int) (yCoord), aWidth, aHeight);
    }
    public Rectangle special1() { //ani 7
        if(meter >= 3){
            ult=true;
//        setAttack(500, 500);
            animation4=animation6;
            if (!isAttacking && meter >= 3) {
                isAttacking = true;
                animation6.reset(); //change to 6
                animation6.resume();
            }
            setAnimationNum(6);
            if (failCheck==0) {
                setyCoord((int) (yCoord - 300));
                failCheck++;
            }
            System.out.println("fly");
            setWidth(1000);
            setHeight(500);
            if (facingRight) {
                return new Rectangle((int) (xCoord), (int) (yCoord), width, height);
            }
            return new Rectangle((int) (xCoord), (int) (yCoord), width, height);
        }
        return(new Rectangle(0,0,0,0));
    }
    public Rectangle hitbox() { //change cus character is small
        return new Rectangle((int) xCoord+85, (int) yCoord, 50, height);
    }
    /*  public Rectaqngle attack() {
        setAnimation(attackAnimation);
        setAttack(30, height);
        attackAnimation.resume();
        if (facingRight) {
            return new Rectangle((int) (xCoord+width), (int) (yCoord), -aWidth, -aHeight);
        }
        return new Rectangle((int) (xCoord-width), (int) (yCoord), aWidth, aHeight);
    }

    public Rectangle hitbox() { //change cus character is small
        if (facingRight) {
            return new Rectangle((int) xCoord, (int) yCoord, width, height);
        }
        return new Rectangle((int) xCoord, (int) yCoord, -width, height);
    }*/
}

