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
    ArrayList<BufferedImage> images = new ArrayList<>();

    public Glorp() {
        super("Luffy", 500, 3, 150, 150, 10, 10, 3, 0, 300, 675, false, false, true, 10,2, 10);
        isAttacking = false;

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
        } else {
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
        return new Rectangle((int) (xCoord), (int) (yCoord), aWidth, aHeight);
    }
    public Rectangle hitbox() { //change cus character is small
        return new Rectangle((int) xCoord+85, (int) yCoord, 50, height);
    }

}

