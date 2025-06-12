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
        super("Glorp", 500, 3, 150, 150, 6, 10, 3, 0, 300, 675, false, false, true, 10,60, 800);
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
            return animation.getActiveFrame();  // updated
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

}

