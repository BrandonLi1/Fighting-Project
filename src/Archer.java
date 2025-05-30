import org.w3c.dom.css.Rect;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Archer extends Character{
    private Animation attackAnimation;
    ArrayList<BufferedImage> images = new ArrayList<>();

    public Archer() {
        super("Sagittarii", 350, 1, 130, 150, 4, 13, 1, 0, 300, 675, false, false, true, 6);
        for (int i = 11; i < 22; i++) {
            String filename = "src\\Archer\\Fire\\Archer" + i + ".png";
            try {
                images.add(ImageIO.read(new File(filename)));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        animation.stop();
        attackAnimation = new Animation(images, 50, false);
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
}
