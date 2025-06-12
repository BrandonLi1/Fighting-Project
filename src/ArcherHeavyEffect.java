import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ArcherHeavyEffect {
    private double x, y;
    private boolean facingRight;
    private Animation animation;
    private boolean expired = false;
    public int owner;
    public boolean hasHit = false;

    public ArcherHeavyEffect(double x, double y, boolean facingRight, ArrayList<BufferedImage> frames) {
        this.x = x;
        this.y = y;
        this.facingRight = facingRight;
        this.owner = 0;
        this.animation = new Animation(frames, 50, false);
        animation.reset();
        animation.resume();
    }

    public void update() {
        if (!animation.isRunning()) {
            expired = true;
        }
    }

    public void draw(Graphics g) {
        BufferedImage frame = animation.getActiveFrame();
        if (frame != null) {
            if (facingRight) {
                g.drawImage(frame, (int) x, (int) y, null);
            } else {
                g.drawImage(frame, (int) x + frame.getWidth(), (int) y, -frame.getWidth(), frame.getHeight(), null);
            }
        }
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public Rectangle hitbox() {
        BufferedImage frame = animation.getActiveFrame();
        if (frame != null) {
            return new Rectangle((int) x, (int) y, frame.getWidth(), frame.getHeight());
        }
        return new Rectangle(0, 0, 0, 0);
    }
}
