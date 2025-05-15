import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GraphicsPanel extends JPanel implements ActionListener, KeyListener {
    private JButton startButton;
    private BufferedImage background;
    private BufferedImage startBackground;
    private Timer timer;
    private Timer roundTimer;
    private Character p1;
    private Character p2;
    int countdown;
    boolean startWindow;

    public GraphicsPanel() {
        timer = new Timer(1, this);
        roundTimer = new Timer(1000, this);
        startWindow=true;
        countdown=180;
        try {
            background = ImageIO.read(new File("src/background.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        p1 = new Character();
        p2 = new Character();
        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (startWindow) {
            startButton=new JButton("Start");
            startButton.setBackground(Color.BLACK);
            startButton.setForeground(Color.WHITE);
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object source =  e.getSource();
        if (source==roundTimer) {
            countdown--;
        }
        if (source==startButton) {
            timer.start();
            roundTimer.start();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
