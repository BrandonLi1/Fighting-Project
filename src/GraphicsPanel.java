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
import java.util.TimerTask;

public class GraphicsPanel extends JPanel implements ActionListener, KeyListener {
    private JButton startButton, keybindsButton, backButton, kaliButton, saberButton, gonButton, luffyButton, glorpButton, bingusButton;
    private JTextArea p1Controls;
    private JTextArea p2Controls;
    private BufferedImage background;
    private BufferedImage startBackground;
    private BufferedImage selectionBackground;
    private Timer timer;
    private Timer roundTimer;
    private Character p1;
    private Character p2;
    int countdown;
    boolean startWindow;
    boolean keybindsWindow;
    boolean selectionScreen=false;
    boolean[] pressedKeys = new boolean[128];

    public GraphicsPanel() {
        startButton=new JButton("Start");
        keybindsButton=new JButton("Keybinds");
        backButton=new JButton("back");
        startButton.setFocusPainted(false);
        keybindsButton.setFocusPainted(false);
        timer = new Timer(1, this);
        roundTimer = new Timer(1000, this);
        startWindow=true;
        countdown=180;
        int jumpcountdown = 2;
        p1Controls=new JTextArea();
        p2Controls=new JTextArea();
        p1Controls.setEditable(false);
        p2Controls.setEditable(false);
        p1 = new Character("joe", 1, 1, 100,100 , 10,1 ,1 ,1 ,300, 700, false, false ,true);
        //this.setCursor(); - make a custom cursor(if time)
        try {
            background = ImageIO.read(new File("src/Backgrounds/background2.jpg"));
            startBackground= ImageIO.read(new File("src/StartScreen.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        kaliButton=new JButton();
        try {
            Image img = ImageIO.read(new File("src\\CharacterSelectionAssets\\kaliSelection.jpg"));
            kaliButton.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            System.out.println(e);
        }
        add(kaliButton);
        kaliButton.setVisible(false);
        backButton.setVisible(false);
        add(startButton);
        add(keybindsButton);
        add(backButton);
        add(p1Controls);
        add(p2Controls);
        startButton.addActionListener(this);
        keybindsButton.addActionListener(this);
        backButton.addActionListener(this);

        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (startWindow) {
            startButton.setBackground(Color.BLACK);
            startButton.setForeground(Color.WHITE);
            keybindsButton.setBackground(Color.BLACK);
            keybindsButton.setForeground(Color.WHITE);
            startButton.setLocation(400, 300);
            keybindsButton.setLocation(400, 400);
            startButton.setVisible(true);
            keybindsButton.setVisible(true);
        } else if (keybindsWindow) {
            System.out.println("called");
            keybindsButton.setVisible(false);
            startButton.setVisible(false);
            backButton.setVisible(true);
            backButton.setLocation(0, 0);
            p1Controls.setLineWrap(true);
            p2Controls.setLineWrap(true);
            p1Controls.setVisible(true);
            p2Controls.setVisible(true);
            p1Controls.setText("p1 controls");
            p2Controls.setText("p2 controls");
            p1Controls.setBounds(0, 50, 300, 500);
            p2Controls.setBounds(400, 50, 300, 500);
        } else if (selectionScreen) {
            try {
                selectionBackground= ImageIO.read((new File("src\\Backgrounds\\selectionBackground.jpg")));
            } catch (Exception e) {
                System.out.println(e);
            }
            g.drawImage(selectionBackground, 0, 0, null);
            kaliButton.setVisible(true);
            kaliButton.setLocation(300, 100);
        } else {
            g.drawImage(background, 0, 0, null);
            g.drawImage(p1.getPlayerImage(), p1.xCoord, p1.yCoord, p1.width, p1.height, null);

            //g.drawImage(p2.getPlayerImage(), p2.xCoord, p2.yCoord, p2.width, p2.height, null);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString(String.valueOf(countdown), 450, 40);

            //p1

            if (pressedKeys[87]) {
                p1.jump();
            }

            if (pressedKeys[65]) {
                p1.moveLeft();
                System.out.println("a");
            }

            if (pressedKeys[83]) {
                p1.block();
            }

            if (pressedKeys[68]) {
                p1.moveRight();
                System.out.println("d");
            }

            /*if (pressedKeys[81]) {//q
                p1.//basic attack
            }

            if (pressedKeys[69]) {//e
                p1.//heavy
            }*/



            // W=87; A=65; S=83; D=68
            //p2 q-light e- heavy zxc-flexq

            if (pressedKeys[38]) {
                p2.jump();
                System.out.println("w");
            }

            if (pressedKeys[37]) {
                p2.moveLeft();
            }

            if (pressedKeys[40]) {
                p2.block();
            }

            if (pressedKeys[39]) {
                p2.moveRight();
            }
            if (p1.yCoord<=0) {
                p1.setGrounded(true);
            }
           /* if (p2.yCoord<=900) {
                p2.setGrounded(true);
            }*/
            // up arrow=38; left arrow=37; down arrow=40; right arrow=39;

        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object source =  e.getSource();

        if (source==roundTimer) {
            countdown--;
        }
        if (source==startButton) {
            startWindow=false;
            keybindsWindow=false;
            timer.start();
            roundTimer.start();
            remove(startButton);
            remove(keybindsButton);
            remove(p1Controls);
            remove(p2Controls);
            selectionScreen=true;
            requestFocusInWindow();
        }
        if (source==keybindsButton) {
            startWindow=false;
            keybindsWindow=true;
        }
        if (source==backButton) {
            startWindow=true;
            keybindsWindow=false;
            startButton.setVisible(true);
            keybindsButton.setVisible(true);
            backButton.setVisible(false);
            p1Controls.setVisible(false);
            p2Controls.setVisible(false);
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int x = e.getKeyCode();
        System.out.println(x);
        if (!startWindow&&!keybindsWindow) {
            System.out.println(x);
            pressedKeys[x] = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!startWindow&&!keybindsWindow) {
            int x = e.getKeyCode();
            pressedKeys[x] = false;
        }
    }

}
