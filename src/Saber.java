import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Saber extends Character{
    private Animation animation, animation2, animation3, animation4, animation5, animation6, animation7;

    public Saber() {
        super("Saber", 500, 3, 150, 150, 10, 10, 3, 0, 300, 675, false, false, true, 10, 300, 1200);
        ArrayList<BufferedImage> images = new ArrayList<>();
        for (int i = 1; i < 9; i++) {
            String filename = "src\\Saber\\Walk\\walk" + i + ".png";
            try {
                images.add(ImageIO.read(new File(filename)));
            }
            catch (IOException e) {
                System.out.println(e.getMessage() + filename);
            }
        }
        animation = new Animation(images,50, true);
        images = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            String filename = "src\\Saber\\Idle\\idle" + i + ".png";
            try {
                images.add(ImageIO.read(new File(filename)));
            }
            catch (IOException e) {
                System.out.println(e.getMessage() + filename);
            }
        }
        animation2 = new Animation(images,100, true);
        images = new ArrayList<>();
        for (int i = 1; i < 3; i++) {
            String filename = "src\\Saber\\Jump\\jump" + i + ".png";
            try {
                images.add(ImageIO.read(new File(filename)));
            }
            catch (IOException e) {
                System.out.println(e.getMessage() + filename);
            }
        }
        animation3 = new Animation(images,200, false);
        images = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            String filename = "src\\Saber\\Attack\\attack" + i + ".png";
            try {
                images.add(ImageIO.read(new File(filename)));
            }
            catch (IOException e) {
                System.out.println(e.getMessage() + filename);
            }
        }
        animation4 = new Animation(images,50, true);
        images = new ArrayList<>();
        for (int i=0; i<2; i++) {
            String filename = "src\\Saber\\Block\\block1.png";
            try {
                images.add(ImageIO.read(new File(filename)));
            }
            catch (IOException e) {
                System.out.println(e.getMessage() + filename);
            }
        }
        animation5 = new Animation(images, 50, true);
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
        } else if (animationNum==5) {
            return animation5.getActiveFrame();
        }else {
            return animation2.getActiveFrame();
        }
    }
    @Override
   public Rectangle attack() {
       animationNum = 4;
       setAttack(100, height);
       animation4.resume();
       if (facingRight) {
           return new Rectangle((int) (xCoord+width), (int) (yCoord), aWidth, aHeight);
       }
       return new Rectangle((int) (xCoord), (int) (yCoord), aWidth, aHeight);
   }

   public Rectangle hitbox() { //change cus character is small
        return new Rectangle((int) xCoord+85, (int) yCoord, 50, height);
   }

   /* public Rectangle hitbox() {

    }*/
}
