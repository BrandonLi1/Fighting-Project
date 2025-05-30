import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class Kali extends Character {
    private Animation attackAnimation;
    private Animation idle;
    ArrayList<BufferedImage> images = new ArrayList<>();

    public Kali() {
        super("Kali", 500, 3, 500, 500, 2, 10, 3, 0, 300, 675, false, false, true, 10);

        for (int i = 1; i < 6; i++) {
            String filename = "src\\Kali\\Attack" + i + ".png";
                try {
                images.add(ImageIO.read(new File(filename)));
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        //get actual idle animatio wtf man
        try {
            idle = new Animation(ImageIO.read(new File("src\\Kali\\Idle\\kaliIdle.png")), 100, false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        attackAnimation = new Animation(images,50, false);
        attackAnimation.stop();
        setAnimation(idle);
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

    public Rectangle hitbox() { //change cus character is small
        if (facingRight) {
            return new Rectangle((int) xCoord, (int) yCoord, width, height);
        }
        return new Rectangle((int) xCoord, (int) yCoord, -width, height);
    }
}
