import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;

public class Archer extends Character{
    private Animation attackAnimation;
    private Animation runAnimation;
    private Animation deathAnimation;
    private Animation hurtAnimation;
    private Animation idleAnimation;
    private int animationNum;
    ArrayList<BufferedImage> images = new ArrayList<>();

    public Archer() {
        super("Archer", 350, 1, 130, 150, 4, 8, 1, 0, 300, 675, false, false, true, 6);

        //Archer attack
        for (int i = 11; i < 22; i++) {
            String filename = "src\\Archer\\Attack\\Archer0" + i + ".png";
            try {
                images.add(ImageIO.read(new File(filename)));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        attackAnimation = new Animation(images, 50, false);
        images = new ArrayList<>();

        //Archer run
        for (int i = 22; i < 30; i++) {
            String filename = "src\\Archer\\Run\\Archer0" + i + ".png";
            try {
                images.add(ImageIO.read(new File(filename)));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        runAnimation = new Animation(images, 50, false);
        images = new ArrayList<>();

        //Archer idle
        for (int i = 0; i < 5; i++) {
            String filename = "src\\Archer\\Idle\\Archer00" + i + ".png";
            try {
                images.add(ImageIO.read(new File(filename)));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        idleAnimation = new Animation(images, 50, true);
        images = new ArrayList<>();

        //Archer death
        for (int i = 44; i < 50; i++) {
            String filename = "src\\Archer\\Death\\Archer0" + i + ".png";
            try {
                images.add(ImageIO.read(new File(filename)));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        deathAnimation = new Animation(images, 50, false);
        images = new ArrayList<>();

        //Archer hurt
        for (int i = 33; i < 38; i++) {
            String filename = "src\\Archer\\Hurt\\Archer0" + i + ".png";
            try {
                images.add(ImageIO.read(new File(filename)));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        hurtAnimation = new Animation(images, 50, false);

        //animation.stop();
        attackAnimation.stop();
    }

    public Rectangle attack() {
        setAnimation(attackAnimation);
        setAttack(30, height);
        attackAnimation.resume();
        if (facingRight) {
            return new Rectangle((int) (xCoord + width), (int) (yCoord), -aWidth, -aHeight);
        }
        return new Rectangle((int) (xCoord + width), (int) (yCoord), aWidth, aHeight);
    }

    @Override
    public void setAnimationNum(int num){
        animationNum = num;
    }

    @Override
    public BufferedImage getPlayerImage() {
        if (animationNum == 1) {
            return runAnimation.getActiveFrame();
        } else if (animationNum == 3) {
            return idleAnimation.getActiveFrame();
        } else if (animationNum == 4) {
            return attackAnimation.getActiveFrame();
        } else {
            return idleAnimation.getActiveFrame();
        }
    }

    public BufferedImage deathAnimation() {
        return deathAnimation.getActiveFrame();
    }
}
