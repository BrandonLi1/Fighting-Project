import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

//https://free-game-assets.itch.io/free-2d-robot-sprite/download/eyJleHBpcmVzIjoxNzQ5MjIwNDM2LCJpZCI6MjgxMjA2fQ%3d%3d.CcGd7nY0X9LHD%2fCnuVouVKzmy98%3d

//https://craftpix.net/freebies/free-sci-fi-antagonists-pixel-character-pack/

//https://craftpix.net/freebies/free-robot-pixel-art-sprite-sheets/

public class Glorp extends Character {
    // Animations:
    private Animation walkAnimation;     // Walking animation
    private Animation idleAnimation;     // Idle/standing animation
    private Animation jumpAnimation;     // Jumping animation
    private Animation attackAnimation;   // Attack animation

    private int animationNum;
    private boolean isAttacking;
    public boolean heavying;
    public int comboNum;
    public boolean isGlorpState = false;

    public Glorp() {
        super("Glorp", 500, 3, 150, 150, 6, 10, 3, 0.0, 300, 675, false, false, true, 10, 400, 160, 15);
        isAttacking = false;
        comboNum = 5;

        // Load walk animation frames
        ArrayList<BufferedImage> walkFrames = new ArrayList<>();
        for (int i = 1; i < 7; i++) {
            try {
                walkFrames.add(ImageIO.read(new File("src\\Glorp\\Walk_00" + i + ".png")));
            } catch (IOException e) {
                System.out.println(e.getMessage() + " Walk frame " + i);
            }
        }
        walkAnimation = new Animation(walkFrames, 50, true);

        // Load idle animation frames
        ArrayList<BufferedImage> idleFrames = new ArrayList<>();
        for (int i = 1; i < 9; i++) {
            try {
                idleFrames.add(ImageIO.read(new File("src\\Glorp\\idle_00" + i + ".png")));
            } catch (IOException e) {
                System.out.println(e.getMessage() + " Idle frame " + i);
            }
        }
        idleAnimation = new Animation(idleFrames, 50, true);

        // Load jump animation frames
        ArrayList<BufferedImage> jumpFrames = new ArrayList<>();
        for (int i = 2; i < 7; i++) {
            try {
                jumpFrames.add(ImageIO.read(new File("src\\Glorp\\Jump_00" + i + ".png")));
            } catch (IOException e) {
                System.out.println(e.getMessage() + " Jump frame " + i);
            }
        }
        jumpAnimation = new Animation(jumpFrames, 120, true);

        // Load attack animation frames
        ArrayList<BufferedImage> attackFrames = new ArrayList<>();
        for (int i = 6; i >= 1; i--) {
            try {
                attackFrames.add(ImageIO.read(new File("src\\Glorp\\Attack_00" + i + ".png")));
            } catch (IOException e) {
                System.out.println(e.getMessage() + " Attack frame " + i);
            }
        }
        attackAnimation = new Animation(attackFrames, 44, false);

        /*ArrayList<BufferedImage> attackFrames = new ArrayList<>();
        for (int i = 6; i >= 1; i--) {
            try {
                attackFrames.add(ImageIO.read(new File("src\\Glorp\\Attack_00" + i + ".png")));
            } catch (IOException e) {
                System.out.println(e.getMessage() + " Attack frame " + i);
            }
        }
        attackAnimation = new Animation(attackFrames, 44, false); */
    }

    @Override
    public void setGlorpState(boolean x) {
        isGlorpState = x;
    }

    public Animation getJumpAnimation() {
        return jumpAnimation;
    }

    public void heavying(boolean x) {
        heavying = x;
    }

    @Override
    public Rectangle heavyAttack() {
        if (facingRight) {
            return new Rectangle((int) (xCoord+width), (int) (yCoord), 150, 300);
        }
        return new Rectangle((int) (xCoord - 150), (int) (yCoord), 150, 300);
    }

    @Override
    public void setAnimationNum(int num) {
        animationNum = num;
    }

    @Override
    public BufferedImage getPlayerImage() {
        if (isGlorpState) {
            try {
                return ImageIO.read(new File("src\\Glorp\\GlorpState.png"));
            } catch (IOException e) {
                System.out.println(e.getMessage() + " Glorp state image");
            }
        }

        if (this.blocking) {
            try {
                return ImageIO.read(new File("src\\Glorp\\Sit_011.png"));
            } catch (IOException e) {
                System.out.println(e.getMessage() + " Glorp state image");
            }
        }

        if (isAttacking) {
            if (!attackAnimation.isRunning()) {
                animationNum = 2;
                isAttacking = false;
            } else {
                return attackAnimation.getActiveFrame();
            }
        }

            if (animationNum == 1) {
                return walkAnimation.getActiveFrame(); // updated
            } else if (animationNum == 3) {
                return jumpAnimation.getActiveFrame();
            } else if (animationNum == 4){
                return attackAnimation.getActiveFrame();
            /*} else if(animationNum == 5) {
                return animation5.getActiveFrame();
            */}else{
                return idleAnimation.getActiveFrame();
            }
    }

    @Override
    public Rectangle attack() throws IOException {
        if (!isAttacking) {
            isAttacking = true;
            animationNum = 4;
            attackAnimation.reset();
            attackAnimation.resume();
        }

        setAttack(100, height);

        if (facingRight) {
            return new Rectangle((int) (xCoord+width), (int) (yCoord), 150, 300);
        }
        return new Rectangle((int) (xCoord - 150), (int) (yCoord), 150, 300);
    }

    public Rectangle hitbox() {
        // Smaller hitbox due to small character size
        return new Rectangle((int)xCoord + 85, (int)yCoord, 50, height);
    }
}


/*public class Glorp extends Character {
    Animation animation;
    private Animation animation2;
    private Animation animation3;
    private Animation animation4;
    private int animationNum;
    private boolean isAttacking;
    public int comboNum;
    public boolean isGlorpState = false;
    ArrayList<BufferedImage> images = new ArrayList<>();

    public Glorp() {
        super("Glorp", 500, 3, 150, 150, 6, 10, 3, 0.0, 300, 675, false, false, true, 10,60, 800, 20);
        isAttacking = false;
        comboNum = 5;

        //walk
        ArrayList<BufferedImage> images = new ArrayList<>();
        for (int i = 1; i < 7; i++) {
            String filename = "src\\Glorp\\Walk_00" + i + ".png";
            try {
                images.add(ImageIO.read(new File(filename)));
            }
            catch (IOException e) {
                System.out.println(e.getMessage() + filename);
            }
        }
        animation = new Animation(images,50, true);
        //idle
        ArrayList<BufferedImage> images2 = new ArrayList<>();
        for (int i = 1; i < 9; i++) {
            String filename = "src\\Glorp\\idle_00" + i + ".png";
            try {
                images2.add(ImageIO.read(new File(filename)));
            }
            catch (IOException e) {
                System.out.println(e.getMessage() + filename);
            }
        }
        animation2 = new Animation(images2,50, true);
        //attack
        ArrayList<BufferedImage> images3 = new ArrayList<>();
        for (int i = 2; i < 7; i++) {
            String filename = "src\\Glorp\\Jump_00" + i + ".png";
            try {
                images3.add(ImageIO.read(new File(filename)));
            }
            catch (IOException e) {
                System.out.println(e.getMessage() + filename);
            }
        }
        animation3 = new Animation(images3,120, true);
        //jump
        ArrayList<BufferedImage> images4 = new ArrayList<>();
        for (int i = 6; i >= 1; i--) {
            String filename = "src\\Glorp\\Attack_00" + i + ".png";
            try {
                images4.add(ImageIO.read(new File(filename)));
            }
            catch (IOException e) {
                System.out.println(e.getMessage() + filename);
            }
        }
        animation4 = new Animation(images4,44, false);
    }

    @Override
    public void setGlorpState(boolean x) {
        isGlorpState = x;
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
        if (isGlorpState) {
            try {
            return ImageIO.read(new File("src\\Glorp\\GlorpState.png"));
            }
            catch (IOException e) {
                System.out.println(e.getMessage() + "src\\Glorp\\GlorpState.png");
            }
        }
        if (isAttacking) {
            if (!animation4.isRunning()) {
                animationNum = 2;
                isAttacking = false;
            } else {
                return animation4.getActiveFrame();
            }
        }


        if (animationNum == 1) {
            return animation.getActiveFrame();
        } else if (animationNum == 3) {
            return animation3.getActiveFrame();
        } else if (animationNum == 4){
            return animation4.getActiveFrame();
        } else {
            return animation2.getActiveFrame();
        }
    }

    @Override
    public Rectangle attack() throws IOException {
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
        return new Rectangle((int) (xCoord), (int) (yCoord), aWidth, aHeight);
    }
    public Rectangle hitbox() { //change cus character is small
        return new Rectangle((int) xCoord+85, (int) yCoord, 50, height);
    }

} */

