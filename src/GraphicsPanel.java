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
    private JButton startButton, keybindsButton, backButton, kaliButton, saberButton, gonButton, luffyButton, glorpButton, bingusButton, confirmButton;
    private JTextArea p1Controls;
    private JTextArea p2Controls;
    private BufferedImage background, selectionBackground, startBackground, p1CharacterImage, p2CharacterImage, healthBar;
    private Timer timer;
    private Timer roundTimer;
    private Character p1;
    private Character p2;
    int countdown;
    boolean startWindow, keybindsWindow, selectionScreen=false, p1Picked=false, p2Picked=false;
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
        //this.setCursor(); - make a custom cursor(if time)
        try {
            healthBar = ImageIO.read(new File("src/Health_Bar000.jpg"));
            background = ImageIO.read(new File("src/Backgrounds/background2.jpg"));
            startBackground= ImageIO.read(new File("src/StartScreen.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        selectionButtons();

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
            gonButton.setVisible(true);
            gonButton.setLocation(600, 100);
            if (p1!=null) {
                try {
                    g.drawImage(ImageIO.read(new File("src\\CharacterSelectionAssets\\PlayerText\\kaliName.jpg")), 150, 500, null);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                g.drawImage(p1CharacterImage, 100, 650, null);
            }
            if (p2CharacterImage!=null) {

            }
        } else {
            g.drawImage(background, 0, 0, null);
            g.drawImage(p1.getPlayerImage(), p1.xCoord, p1.yCoord, p1.width, p1.height, null);
            g.drawImage(healthBar, 0, 0, null);
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

            p1.checkGrounded();
            //p2.checkGrounded();
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
        if (source==kaliButton) {
            if (!p1Picked) {
                try {
                    p1CharacterImage = ImageIO.read(new File("src\\CharacterSelectionAssets\\PlayerImage\\kaliSelectionPlayer.jpg"));
                    p1 = new Character("Kali", 500, 3, 50, 30, 2, 3, 3, 0, 300, 700, false, false, true);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                try {
                    p2CharacterImage = ImageIO.read(new File("src\\CharacterSelectionAssets\\kaliSelection.jpg"));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            confirmButton.setVisible(true);
            repaint();
        }
        if (source==confirmButton) {
            if (!p1Picked) {
                p1Picked=true;
                confirmButton.setVisible(false);
            } else {
                p2Picked=true;
            }
            if (p2Picked) {
                selectionScreen=false;
                kaliButton.setVisible(false);
                gonButton.setVisible(false);
                confirmButton.setVisible(false);
            }
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

    private void selectionButtons() {
        kaliButton=new JButton();
        try {
            Image img = ImageIO.read(new File("src\\CharacterSelectionAssets\\kaliSelection.jpg"));
            kaliButton.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            System.out.println(e);
        }
        add(kaliButton);
        kaliButton.setVisible(false);

        gonButton=new JButton();
        try {
            Image img = ImageIO.read(new File("src\\CharacterSelectionAssets\\gonSelection.jpg"));
            gonButton.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            System.out.println(e);
        }
        add(gonButton);
        gonButton.setVisible(false);

        confirmButton=new JButton("Confirm");
        add(confirmButton);
        confirmButton.setBounds(800, 800, 200, 100);
        confirmButton.setVisible(false);

        kaliButton.addActionListener(this);
        gonButton.addActionListener(this);
        confirmButton.addActionListener(this);
    }

}
