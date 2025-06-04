import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animation implements ActionListener {
    private ArrayList<BufferedImage> frames;
    private Timer timer;
    private int currentFrame;
    private boolean loop;
    private switchAttack handler;
    private String name;

    public Animation(ArrayList<BufferedImage> frames, int delay, boolean loop) {
        this.frames = frames;
        currentFrame = 0;
        timer = new Timer(delay, this);
        timer.start();
        this.loop=loop;
    }

    public Animation(ArrayList<BufferedImage> frames, int delay, boolean loop, switchAttack handler, String name) {
        this.frames = frames;
        this.handler = handler;
        this.name = name;
        currentFrame = 0;
        timer = new Timer(delay, this);
        timer.start();
        this.loop=loop;
    }

    public BufferedImage getActiveFrame() {
        return frames.get(currentFrame);
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public ArrayList<BufferedImage> getFrames() {
        return frames;
    }

    public void setCurrentFrame(int i) {
        currentFrame=i;
    }


    public void stop() {
        timer.restart();
        timer.stop();
    }

    public void resume() {
        timer.restart();
        timer.start();
    }

    public void reset() {
        currentFrame = 0;
    }

    public boolean isRunning() {
        return timer.isRunning();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof Timer) {
            //This advances the animation to the next frame
            //It also uses modulus to reset the frame to the beginning after the last frame
            //In other words, this allows our animation to loop
            if (name != null) {
                currentFrame = (currentFrame + 1) % frames.size();
            } else {
                currentFrame = (currentFrame + 1);
                if (currentFrame==frames.size()-1) {
                    handler.switchAttack();
                }
            }
        }

        if (currentFrame==frames.size()-1 && !loop){
            stop();
            System.out.println("stop");
        }
    }
}
