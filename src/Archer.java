import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Archer extends Character{
    private Animation attackAnimation;
    private Animation runAnimation;
    private Animation deathAnimation;
    private Animation hurtAnimation;
    private Animation idleAnimation;
    private BufferedImage shieldSprite;
    private int animationNum;
    private boolean isAttacking;
    public int comboNum;
    private boolean isHeavyAttacking;
    ArrayList<BufferedImage> images = new ArrayList<>();
    public ArrayList<BufferedImage> heavyFrames;
    public ArcherHeavyEffect pendingHeavyEffect = null;

    //https://craftpix.net/freebies/free-underwater-enemies-pixel-art-character-pack/

    public Archer() {
        super("Archer", 350, 1, 130, 150, 4, 8, 1, 0.0, 300, 675, false, false, true, 6, 2, 300, 2);
        isAttacking = false;
        comboNum = 9;
        isHeavyAttacking = false;

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
        runAnimation = new Animation(images, 50, true);
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
        images = new ArrayList<>();

        //Archer heavy attack
        heavyFrames = new ArrayList<>();
        for (int i = 1; i <=3; i++) {
            String filename = "src\\Archer\\Heavy\\heavy" + i + ".png";
            try {
                heavyFrames.add(ImageIO.read(new File(filename)));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        //Archer shield
        try {
            shieldSprite = ImageIO.read(new File("src\\Archer\\block.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void setAnimationNum(int num){
        animationNum = num;
    }

    @Override
    public BufferedImage getPlayerImage() {
        if (isAttacking) {
            if (!attackAnimation.isRunning()) {
                animationNum = 2;
            } else {
                return attackAnimation.getActiveFrame();
            }
        }

        if (animationNum == 1) {
            return runAnimation.getActiveFrame();
        } else if (animationNum == 3) {
            //needs a jump animation
            //using idle animation for now
            return idleAnimation.getActiveFrame();
        } else if (animationNum == 4) {
            return attackAnimation.getActiveFrame();
        } else {
            return idleAnimation.getActiveFrame();
        }
    }

    public BufferedImage getShieldSprite() {
        if (blocking) {
            return shieldSprite;
        }
        return null;
    }

    public Point getShieldPosition() {
        int shieldWidth = shieldSprite != null ? shieldSprite.getWidth() : 0;
        int offset = 80;

        int shieldX;
        if (facingRight) {
            shieldX = (int) xCoord + width - offset;
        } else {
            shieldX = (int) xCoord - shieldWidth + offset;
        }
        int shieldY = (int) yCoord + height / 4;
        return new Point(shieldX, shieldY);
    }

    public BufferedImage deathAnimation() {
        return deathAnimation.getActiveFrame();
    }

    public Rectangle attack() {
        if (!isAttacking) {
            isAttacking = true;
            animationNum = 4;
            attackAnimation.reset();
            attackAnimation.resume();
        }
        return null;
    }

    public Rectangle hitbox() {
        return new Rectangle((int) xCoord, (int) yCoord, width, height);
    }

    public boolean attackAnimationEnded() {
        return isAttacking && !attackAnimation.isRunning();
    }

    public Rectangle heavyAttack() {
        if (pendingHeavyEffect == null) {
            double fx = facingRight ? xCoord + width : xCoord - 60;
            double fy = yCoord;
            pendingHeavyEffect = new ArcherHeavyEffect(fx, fy, facingRight, heavyFrames);
        }
        return null;
    }

    public void setIsAttacking(boolean attacking) {
        isAttacking = attacking;
    }
}
