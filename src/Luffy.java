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
    private Animation animation5;
    private int animationNum;
    public int comboNum;
    private boolean isAttacking;
    ArrayList<BufferedImage> images = new ArrayList<>();

    public Luffy() {
        super("Luffy", 500, 3, 150, 150, 5, 10, 3, 0, 300, 675, false, false, true, 60,600, 1000);
        isAttacking = false;
        comboNum = 3;
        ArrayList<BufferedImage> images = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            String filename = "src\\Luffy\\Walk\\luffy00" + i + ".png";
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
        for (int i = 0; i < 7; i++) {
            String filename = "src\\Luffy\\Walk\\idle\\luffyidle00" + i + ".png";
            try {
                images2.add(ImageIO.read(new File(filename)));
            }
            catch (IOException e) {
                System.out.println(e.getMessage() + filename);
            }
        }
        animation2 = new Animation(images2,50, true);
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
        for (int i = 0; i < 6; i++) {
            String filename = "src\\Luffy\\Walk\\Attack\\luffyAttack00" + i + ".png";
            try {
                images4.add(ImageIO.read(new File(filename)));
            }
            catch (IOException e) {
                System.out.println(e.getMessage() + filename);
            }
        }
        animation4 = new Animation(images4,50, false);
        //attack
        ArrayList<BufferedImage> images5 = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            String filename = "src\\Luffy\\Walk\\Heavy\\luffyheavy00" + i + ".png";
            try {
                images5.add(ImageIO.read(new File(filename)));
            }
            catch (IOException e) {
                System.out.println(e.getMessage() + filename);
            }
        }
        animation5 = new Animation(images4,50, false);
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
            if (!animation4.isRunning()) {
                isAttacking = false;
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
        if (!isAttacking) {
            isAttacking = true;
            animationNum = 4;
            animation4.reset();
            animation4.resume();
        }

        setAttack(100, height);
        if (facingRight) {
            return new Rectangle((int) (xCoord+width), (int) (yCoord), aWidth, aHeight);
        }
        return new Rectangle((int) (xCoord-width+50), (int) (yCoord), aWidth, aHeight);
    }
    public Rectangle hitbox() { //change cus character is small
        return new Rectangle((int) xCoord+85, (int) yCoord, 50, height);
    }
    @Override
    public Rectangle heavyAttack() { //ani 6
        setAttack(100, height);
        if (!isAttacking) {
            isAttacking = true;
            animation5.reset(); //change to 6
            animation5.resume();
        }
        setAnimationNum(4);
        if (facingRight) {
            return new Rectangle((int) (xCoord+width), (int) (yCoord), aWidth, aHeight);
        }
        return new Rectangle((int) (xCoord)-aWidth, (int) (yCoord), aWidth, aHeight);
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

