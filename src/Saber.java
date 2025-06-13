import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Saber extends Character {
    Animation animation, animation2, animation3, animation4, animation5, animation6, heavyAttack, attack1, attack2;
    private int basicCount;
    int energy;
    private int failCheck = 0;
    public int comboNum;
    private boolean ult;

    public Saber() {
        super("Saber", 500, 4, 150, 150, 8, 10, 0, 0.0, 300, 675, false, false, true, 10, 500, 1500, 10);
        isAttacking = false;

        comboNum = 5;
        ArrayList<BufferedImage> images = new ArrayList<>();
        for (int i = 1; i < 9; i++) {
            String filename = "src\\Saber\\Walk\\walk" + i + ".png";
            try {
                images.add(ImageIO.read(new File(filename)));
            } catch (IOException e) {
                System.out.println(e.getMessage() + filename);
            }
        }
        animation = new Animation(images, 50, true);
        images = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            String filename = "src\\Saber\\Idle\\idle" + i + ".png";
            try {
                images.add(ImageIO.read(new File(filename)));
            } catch (IOException e) {
                System.out.println(e.getMessage() + filename);
            }
        }
        animation2 = new Animation(images, 100, true);
        images = new ArrayList<>();
        for (int i = 1; i < 3; i++) {
            String filename = "src\\Saber\\Jump\\jump" + i + ".png";
            try {
                images.add(ImageIO.read(new File(filename)));
            } catch (IOException e) {
                System.out.println(e.getMessage() + filename);
            }
        }
        animation3 = new Animation(images, 200, false);
        images = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            String filename = "src\\Saber\\Attack\\attack" + i + ".png";
            try {
                images.add(ImageIO.read(new File(filename)));
            } catch (IOException e) {
                System.out.println(e.getMessage() + filename);
            }
        }
        attack1 = new Animation(images, 81, false);
        animation4 = attack1;
        images = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            String filename = "src\\Saber\\Attack\\attack" + i + ".png";
            try {
                images.add(ImageIO.read(new File(filename)));
            } catch (IOException e) {
                System.out.println(e.getMessage() + filename);
            }
        }
        try {
            images.add(ImageIO.read(new File("src\\Saber\\Attack\\attack5.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        heavyAttack = new Animation(images, 120, false);
        images = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            String filename = "src\\Saber\\Attack\\attack2-" + i + ".png";
            try {
                images.add(ImageIO.read(new File(filename)));
            } catch (IOException e) {
                System.out.println(e.getMessage() + filename);
            }
        }
        attack2 = new Animation(images, 36, false);
        images = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            String filename = "src\\Saber\\Block\\block1.png";
            try {
                images.add(ImageIO.read(new File(filename)));
            } catch (IOException e) {
                System.out.println(e.getMessage() + filename);
            }
        }
        animation5 = new Animation(images, 50, true);
        images = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            String filename = "src\\Saber\\Special\\spec" + i + ".png";
            try {
                images.add(ImageIO.read(new File(filename)));
            } catch (IOException e) {
                System.out.println(e.getMessage() + filename);
            }
        }
        try {
            images.add(ImageIO.read(new File("src\\Saber\\Special\\spec4.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        animation6 = new Animation(images, 200, false);
        animation6.stop();
    }

    @Override
    public void setAnimationNum(int num) {
        animationNum = num;
    }

    @Override
    public BufferedImage getPlayerImage() {
        if (isAttacking) {
            if (!animation4.isRunning() && !animation6.isRunning()) {
                isAttacking = false;
                if (ult == true) {
                    setyCoord((int) (yCoord + 300));
                    failCheck = 0;
                }
                ult = false;
                animationNum = 2;
                setHeight(150);
                setWidth(150);
            } else {
                return animation4.getActiveFrame();
            }
        }
        if (animationNum == 1) {
            return animation.getActiveFrame();  // updated
        } else if (animationNum == 3) {
            return animation3.getActiveFrame();
        } else if (animationNum == 4) {
            return animation4.getActiveFrame();
        } else if (animationNum == 5) {
            return animation5.getActiveFrame();
        } else if (animationNum == 6) {
            return animation6.getActiveFrame();
        } else {
            return animation2.getActiveFrame();
        }
    }

    @Override
    public Rectangle attack() {
        setAttack(100, height);
        if (Math.random() <= .5) {
            animation4 = attack1;
        } else {
            animation4 = attack2;
        }
        if (!isAttacking) {
            isAttacking = true;
            animation4.reset();
            animation4.resume();
        }

        setAnimationNum(4);
        if (facingRight) {
            return new Rectangle((int) (xCoord + width), (int) (yCoord), aWidth, aHeight);
        }
        return new Rectangle((int) (xCoord) - aWidth, (int) (yCoord), aWidth, aHeight);
    }

    public Rectangle heavyAttack() { //ani 6
        setAttack(100, height);
        animation4 = heavyAttack;
        if (!isAttacking) {
            isAttacking = true;
            animation4.reset(); //change to 6
            animation4.resume();
        }
        setAnimationNum(4);
        if (facingRight) {
            return new Rectangle((int) (xCoord + width), (int) (yCoord), aWidth, aHeight);
        }
        return new Rectangle((int) (xCoord) - aWidth, (int) (yCoord), aWidth, aHeight);
    }

    public Rectangle special1() {
        //ani 7
        if (meter >= 3) {
            ult = true;
            setAttack(1000, 1000);
            animation4 = animation6;
            if (!isAttacking) {
                isAttacking = true;
                animation6.reset(); //change to 6
                animation6.resume();
            }
            setAnimationNum(6);
            if (failCheck == 0) {
                setyCoord((int) (yCoord - 300));
                failCheck++;
            }
            System.out.println("fly");
            setWidth(1000);
            setHeight(400);
            if (facingRight) {
                return new Rectangle((int) (xCoord), (int) (yCoord), width, height);
            }
            return new Rectangle((int) (xCoord), (int) (yCoord), width, height);
        }
        return new Rectangle(0,0,0,0);
    }
    public Rectangle hitbox() { //change cus character is small
        return new Rectangle((int) xCoord+85, (int) yCoord, 50, height);
   }

   /* public Rectangle hitbox() {

    }*/
}
