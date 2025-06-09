import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Arrow extends Character{
    private double distanceTraveled;
    private double maxDistance;
    private double arrowSpeed;
    private BufferedImage image;
    private boolean expired;

    public Arrow(double x, double y, boolean facingRight) {
        super("Arrow", 1, 0, 12, 50, 0, 0, 0, 0, (int) x, (int) y, false, false, true, 0, 0, 0);
        distanceTraveled = 0;
        maxDistance = 350;
        arrowSpeed = 15;
        expired = false;
        this.facingRight = facingRight;

        try {
            image = ImageIO.read(new File ("src\\Archer\\arrow.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void update() {
        double dx;
        if (facingRight) {
            dx = arrowSpeed;
        } else {
            dx = -arrowSpeed;
        }
        xCoord += dx;
        distanceTraveled += Math.abs(dx);
        if (distanceTraveled >= maxDistance) {
            expired = true;
        }
    }

    public void draw(Graphics g) {
        if (image != null) {
            if (facingRight) {
                g.drawImage(image, (int) xCoord, (int) yCoord, width, height, null);
            } else {
                g.drawImage(image, (int) xCoord + width, (int) yCoord, -width, height, null);
            }
        } else {
            g.setColor(Color.DARK_GRAY);
            g.fillRect((int) xCoord, (int) yCoord, width, height);
        }
    }

    public boolean isExpired() {
        return expired;
    }

    @Override
    public Rectangle hitbox() {
        return new Rectangle((int) xCoord, (int) yCoord, Math.abs(width), height);
    }
}
