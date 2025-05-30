import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class Kali extends Character {
    private Animation attackAnimation;
    ArrayList<BufferedImage> images = new ArrayList<>();

    public Kali() {
        super("Kali", 500, 3, 150, 150, 2, 10, 3, 0, 300, 675, false, false, true, 10);
        for (int i = 1; i < 6; i++) {
            String filename = "src\\Kali\\Attack\\Attack" + i + ".png";
                try {
                images.add(ImageIO.read(new File(filename)));
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
       //animation.stop();
        attackAnimation = new Animation(images,50, false);
        attackAnimation.stop();
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
