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
    private Animation attackAnimation;
    private int animationNum;
    ArrayList<BufferedImage> images = new ArrayList<>();

    public Luffy() {
        super("Luffy", 500, 3, 150, 150, 5, 10, 3, 0, 300, 675, false, false, true, 10);
        ArrayList<BufferedImage> images = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            String filename = "src\\Luffy\\Walk\\luffy00" + i + ".png";
            try {
                images.add(ImageIO.read(new File(filename)));
            }
            catch (IOException e) {
                System.out.println(e.getMessage() + filename);
            }
        }
        animation = new Animation(images,50, true);
        //Luffy.Walk.idle animation
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
        //luffy Luffy.Walk.jump animation
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
        if (animationNum == 1) {
            return animation.getActiveFrame();  // updated
       } else if (animationNum == 3 ) {
            return animation3.getActiveFrame();
        } else {
            return animation2.getActiveFrame();
        }
    }
    public Rectangle attack() {
        setAnimation(attackAnimation);
        setAttack(30, height);
        attackAnimation.resume();
        if (facingRight) {
            return new Rectangle((int) (xCoord+width), (int) (yCoord), -aWidth, -aHeight);
        }
        return new Rectangle((int) (xCoord-width), (int) (yCoord), aWidth, aHeight);
    }
}

