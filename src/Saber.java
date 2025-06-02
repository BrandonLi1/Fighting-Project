import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Saber extends Character{
    private Animation idleAnimation;
    private Animation jumpAnimation;
    public Saber() {
        super("Luffy", 500, 3, 150, 150, 10, 10, 3, 0, 300, 675, false, false, true, 10);
        ArrayList<BufferedImage> images = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            String filename = "src\\Saber\\Idle\\idle" + i + ".png";
            try {
                images.add(ImageIO.read(new File(filename)));
            }
            catch (IOException e) {
                System.out.println(e.getMessage() + filename);
            }
        }
        idleAnimation = new Animation(images,50, true);
        for (int i = 1; i < 3; i++) {
            String filename = "src\\Saber\\Jump\\jump" + i + ".png";
            try {
                images.add(ImageIO.read(new File(filename)));
            }
            catch (IOException e) {
                System.out.println(e.getMessage() + filename);
            }
        }
        jumpAnimation = new Animation(images,50, true);
    }

   /* public Rectangle attack() {

    }

    public Rectangle hitbox() {

    }*/
}
