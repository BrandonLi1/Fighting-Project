import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Saber extends Character{
    private Animation animation, animation1, animation2, animation3, animation4;

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
        animation2 = new Animation(images,50, true);
        for (int i = 1; i < 3; i++) {
            String filename = "src\\Saber\\Jump\\jump" + i + ".png";
            try {
                images.add(ImageIO.read(new File(filename)));
            }
            catch (IOException e) {
                System.out.println(e.getMessage() + filename);
            }
        }
        animation3 = new Animation(images,50, true);
    }

    @Override
    public void setAnimationNum(int num){
        animationNum = num;
    }
    @Override
    public BufferedImage getPlayerImage() {
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

   /* public Rectangle attack() {

    }

    public Rectangle hitbox() {

    }*/
}
