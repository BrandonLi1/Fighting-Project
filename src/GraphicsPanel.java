import org.w3c.dom.css.Rect;

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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GraphicsPanel extends JPanel implements ActionListener, KeyListener {
    private JButton startButton, keybindsButton, backButton, saberButton, luffyButton, archerButton, glorpButton, bingusButton, confirmButton;
    private JTextArea p1Controls;
    private JTextArea p2Controls;
    private BufferedImage background, selectionBackground, startBackground, p1CharacterImage, p2CharacterImage, healthBar1,healthBar2, p1NameImage, p2NameImage;
    private Timer timer;
    private Timer roundTimer;
    private Timer holdTimer;
    private Character p1;
    private Character p2;
    int countdown, holdCount=0, p1AttackCount;
    String p1Temp;
    String p2Temp;
    boolean startWindow, keybindsWindow, selectionScreen=false, p1Picked=false, p2Picked=false;
    boolean[] pressedKeys = new boolean[128];
    private boolean directionP1 = true;
    private boolean directionP2 = false;
    //false is left, true is right -what is this bruh


    public GraphicsPanel() {
        startButton=new JButton("Start");
        keybindsButton=new JButton("Keybinds");
        backButton=new JButton("back");
        startButton.setFocusPainted(false);
        keybindsButton.setFocusPainted(false);
        timer = new Timer(10, this);
        roundTimer = new Timer(1000, this);
        holdTimer = new Timer(500, this);
        startWindow=true;
        countdown=180;
        p1Controls=new JTextArea();
        p2Controls=new JTextArea();
        p1Controls.setEditable(false);
        p2Controls.setEditable(false);
        //this.setCursor(); - make a custom cursor(if time)
        try {
            healthBar1 = ImageIO.read(new File("src\\healthBar\\health bar 1.png"));
            healthBar2 = ImageIO.read(new File("src\\healthBar\\health bar 2.png"));
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
            keybindsButton.setVisible(false);
            startButton.setVisible(false);
            backButton.setVisible(true);
            backButton.setLocation(0, 0);
            p1Controls.setLineWrap(true);
            p2Controls.setLineWrap(true);
            p1Controls.setVisible(true);
            p2Controls.setVisible(true);
            p1Controls.setText("player 1 controls:\nW-jump\nA-left\nS-block\nD-right\nQ-light\nE-heavy\nZ-special 1\nX-special 2\nC-special 3(mode if applicable)");
            p1Controls.setFont(new Font("Pacifico", Font.BOLD, 30));
            p2Controls.setText("player 2 controls:\n↑-jump\n←-left\n↓-block\n→-right\nnumpad4-light\nnumpad5-heavy\nnumpad1-special 1\nnumpad2-special 2\nnumpad3-special 3(mode if applicable)");
            p2Controls.setFont(new Font("Pacifico", Font.BOLD, 30));
            p1Controls.setBounds(0, 50, 550, 450);
            p2Controls.setBounds(1000, 50, 550, 450);
        } else if (selectionScreen) {
            try {
                selectionBackground= ImageIO.read((new File("src\\Backgrounds\\selectionBackground.jpg")));
            } catch (Exception e) {
                System.out.println(e);
            }
            g.drawImage(selectionBackground, 0, 0, null);
            saberButton.setVisible(true);
            saberButton.setLocation(300, 100);
            luffyButton.setVisible(true);
            luffyButton.setLocation(600, 100);
            archerButton.setVisible(true);
            archerButton.setLocation(900, 100);
            if (p1CharacterImage!=null) {
                g.drawImage(p1NameImage, 150, 500, null);
                g.drawImage(p1CharacterImage, 100, 600, null);
            }
            if (p2CharacterImage!= null) {
                g.drawImage(p2NameImage, 1000, 500, null);
                g.drawImage(p2CharacterImage, 950, 600, null);
            }
        } else {
            if (!timer.isRunning()) {
                timer.start();
            }
            if (!roundTimer.isRunning()) {
                roundTimer.start();
            }
            g.drawImage(background, 0, 0, null);
            g.drawImage(p1.getPlayerImage(), (int) p1.getxCoord(), (int) p1.yCoord, p1.getWidth(), p1.height, null);
            g.setColor(Color.RED);
            g.fillRect(327,74,(int)(480*((double)p1.getHealth()/p1.getMaxHealth())),52);
            g.fillRect(1057+(int)(480*(1.0-(double)p2.getHealth()/p2.getMaxHealth())),74,(int)(480*((double)p2.getHealth()/p2.getMaxHealth())),52);
            g.setColor(Color.BLUE);
            g.fillRect(327,124,480,26);
            g.fillRect(1057,126,480,26);
            g.setColor(Color.BLACK);
            g.drawImage(healthBar1, 200, 50, null);
            g.drawImage(healthBar2, 1050, 50, null);
            g.drawImage(p2.getPlayerImage(), (int) p2.getxCoord(),(int) p2.yCoord, p2.getWidth(), p2.height, null);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString(String.valueOf(countdown), 904, 100);

            //p1

            //W
            if (pressedKeys[87]) {
                if(p1.isGrounded){
                    p1.setAnimationNum(3);
                    p1.jump();

                }
            }

            //A
            if (pressedKeys[65]) {
                p1.moveLeft();
                p1.faceLeft();
                p1.setAnimationNum(1);
                directionP1 = false;

            }

            //S
            if (pressedKeys[83]) {
                p1.block();
            }

            //D
            if (pressedKeys[68]) {
                p1.moveRight();
                p1.faceRight();
                p1.setAnimationNum(1);
                directionP1 = true;
            }

            // basic attack
            if (pressedKeys[81]) {
                ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
                executor.schedule(() -> {
                    Rectangle damageBox = p1.attack();
                    Rectangle hitbox = p2.hitbox();
                    if (damageBox.intersects(hitbox)) {

                        System.out.println("hit");
                    }
                    executor.shutdown();
                }, p1.normalD, TimeUnit.MILLISECONDS);
            }

            if(pressedKeys[120]) {
                System.out.println("Before delay");
                ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
                executor.schedule(() -> {
                    Rectangle damageBox = p1.attack();
                    Rectangle hitbox = p2.hitbox();
                    if (damageBox.intersects(hitbox)) {
                        System.out.println("hit");
                    }
                    executor.shutdown();
                }, p1.heavyD, TimeUnit.MILLISECONDS);
            }

            //C
            if (pressedKeys[67]) { //check for mode(transform) holding
                holdTimer.start();
            }


            //basic attack

            /*
            if (pressedKeys[69]) {//e
                p1.//heavy
            }*/



            // W=87; A=65; S=83; D=68
            //p2 q-light e- heavy zxc-flexq

            //Up Key
            if (pressedKeys[38]) {
                if (p2.isGrounded){
                    p2.setAnimationNum(3);
                    p2.jump();
                }
            }

            //Left Key
            if (pressedKeys[37]) {
                p2.moveLeft();
                p2.faceLeft();
                p2.setAnimationNum(1);
                directionP2 = false;
            }

            //Down Key
            if (pressedKeys[40]) {
                p2.block();
            }

            //Right Key
            if (pressedKeys[39]) {
                p2.moveRight();
                p2.faceRight();
                p2.setAnimationNum(1);
                directionP2 = true;
            }

            if (pressedKeys[100]) {
                Rectangle damageBox = p2.attack();
                Rectangle hitbox = p1.hitbox();
                if (damageBox.intersects(hitbox) && p2AttackCount==0) {
                    System.out.println("hit");
                    p1.setHealth(p1.getHealth()-p2.attackDamage);
                    System.out.println(p1.getHealth());
                    System.out.println(p2.attackDamage);
                    p2AttackCount++;
                }
            }

            p1.checkGrounded();
            p2.checkGrounded();
            // up arrow=38; left arrow=37; down arrow=40; right arrow=39;


        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        requestFocusInWindow();
        Object source =  e.getSource();

        if (source == holdTimer) {
            holdCount+=1;
        }

        if (source==roundTimer) {
            countdown--;
            if (countdown<=0) {
                //placeholder for round end


                System.exit(0);
            }
        }
        if (source==startButton) {
            startWindow=false;
            keybindsWindow=false;
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
        if (source==saberButton) {
           if (!p1Picked) {
                try {
                    p1CharacterImage = ImageIO.read(new File("src\\CharacterSelectionAssets\\PlayerImage\\saberSelectionPlayer.jpg"));
                    p1Temp="Saber";
                    p1NameImage=ImageIO.read(new File("src\\CharacterSelectionAssets\\PlayerText\\saberName.jpg"));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
               try {
                    p2CharacterImage = ImageIO.read(new File("src\\CharacterSelectionAssets\\PlayerImage\\saberSelectionPlayer.jpg"));
                    p2Temp="Saber";
                    p2NameImage=ImageIO.read(new File("src\\CharacterSelectionAssets\\PlayerText\\saberName.jpg"));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

            confirmButton.setVisible(true);
            repaint();
        }
        if (source==luffyButton) {
            if (!p1Picked) {
                try {
                    p1CharacterImage = ImageIO.read(new File("src\\CharacterSelectionAssets\\PlayerImage\\luffySelectionPlayer.jpg"));
                    p1Temp="Luffy";
                    p1NameImage=ImageIO.read(new File("src\\CharacterSelectionAssets\\PlayerText\\luffyName.png"));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                try {
                    p2CharacterImage = ImageIO.read(new File("src\\CharacterSelectionAssets\\PlayerImage\\luffySelectionPlayer.jpg"));
                    p2NameImage=ImageIO.read(new File("src\\CharacterSelectionAssets\\PlayerText\\luffyName.png"));
                    p2Temp="Luffy";
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            confirmButton.setVisible(true);
            repaint();
        }
        if (source == archerButton) {
            if (!p1Picked) {
                try {
                    p1CharacterImage = ImageIO.read(new File("src\\CharacterSelectionAssets\\PlayerImage\\archerSelectionPlayer_p1.jpg"));
                    p1Temp="Archer";
                    p1NameImage=ImageIO.read(new File("src\\CharacterSelectionAssets\\PlayerText\\archerName.png"));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                try {
                    p2CharacterImage = ImageIO.read(new File("src\\CharacterSelectionAssets\\PlayerImage\\archerSelectionPlayer_p2.jpg"));
                    p2NameImage=ImageIO.read(new File("src\\CharacterSelectionAssets\\PlayerText\\archerName.png"));
                    p2Temp="Archer";
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
                if (p1Temp.equals("Saber")) {
                   p1 = new Saber();
                }
                if (p1Temp.equals("Luffy")) {
                    p1=new Luffy();
                }
                if (p1Temp.equals("Archer")) {
                    p1 = new Archer();
                }
            } else {
                p2Picked=true;
                if (p2Temp.equals("Saber")) {
                  p2=new Saber();
                }
                if (p2Temp.equals("Luffy")) {
                    p2=new Luffy();
                }
                if (p2Temp.equals("Archer")) {
                    p2 = new Archer();
                }
                p2.setxCoord(1300);
            }
            if (p2Picked) {
                selectionScreen=false;
                saberButton.setVisible(false);
                luffyButton.setVisible(false);
                archerButton.setVisible(false);
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
        if (!startWindow&&!keybindsWindow) {
            pressedKeys[x] = true;
        }
    }



    @Override
    public void keyReleased(KeyEvent e) {
        if (!startWindow&&!keybindsWindow && !selectionScreen) {
            int x = e.getKeyCode();
            pressedKeys[x] = false;
            p1.setAnimationNum(2);
            p2.setAnimationNum(2);
        }
        if (e.getKeyCode()==81) {
            System.out.println("p1 attack");
            p1AttackCount=0;
        }
        if (e.getKeyCode()==100) {
            System.out.println("p2 attack");
            p2AttackCount=0;
        }
        if (e.getKeyCode()==67) {
            System.out.println(holdCount);
            if (holdCount>=3) {
                //p1.transform();
            }
            holdCount=0;
            holdTimer.restart();
            holdTimer.stop();
        }
        if (e.getKeyCode()==83) {
            p1.setBlocking(false);
        }
        if (e.getKeyCode()==40) {
            p2.setBlocking(false);
        }
    }

    private void selectionButtons() {
        saberButton=new JButton();
        saberButton.setSize(101, 101);
        try {
            Image img = ImageIO.read(new File("src\\CharacterSelectionAssets\\saberSelection.jpg"));
            saberButton.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            System.out.println(e);
        }
        add(saberButton);
        saberButton.setVisible(false);

        luffyButton=new JButton();
        luffyButton.setSize(92, 85);
        try {
            Image img = ImageIO.read(new File("src\\CharacterSelectionAssets\\luffySelection.jpg"));
            luffyButton.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            System.out.println(e);
        }
        add(luffyButton);
        luffyButton.setVisible(false);

        archerButton=new JButton();
        archerButton.setSize(92, 85);
        try {
            Image img = ImageIO.read(new File("src\\CharacterSelectionAssets\\archerSelection.png"));
            archerButton.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            System.out.println(e);
        }
        add(archerButton);
        archerButton.setVisible(false);

        confirmButton=new JButton("Confirm");
        confirmButton.setFocusPainted(false);
        add(confirmButton);
        confirmButton.setBounds(800, 800, 200, 100);
        confirmButton.setVisible(false);

        saberButton.addActionListener(this);
        luffyButton.addActionListener(this);
        archerButton.addActionListener(this);
        System.out.println("action listener added");
        confirmButton.addActionListener(this);
    }

    //merge force
}

/*3 -- Cancel
8 -- Backspace
9 -- Tab
10 -- Enter
12 -- Clear
16 -- Shift
17 -- Ctrl
18 -- Alt
19 -- Pause
20 -- Caps Lock
21 -- Kana
24 -- Final
25 -- Kanji
27 -- Escape
28 -- Convert
29 -- No Convert
30 -- Accept
31 -- Mode Change
32 -- Space
33 -- Page Up
34 -- Page Down
35 -- End
36 -- Home
37 -- Left
38 -- Up
39 -- Right
40 -- Down
44 -- Comma
45 -- Minus
46 -- Period
47 -- Slash
48 -- 0
49 -- 1
50 -- 2
51 -- 3
52 -- 4
53 -- 5
54 -- 6
55 -- 7
56 -- 8
57 -- 9
59 -- Semicolon
61 -- Equals
65 -- A
66 -- B
67 -- C
68 -- D
69 -- E
70 -- F
71 -- G
72 -- H
73 -- I
74 -- J
75 -- K
76 -- L
77 -- M
78 -- N
79 -- O
80 -- P
81 -- Q
82 -- R
83 -- S
84 -- T
85 -- U
86 -- V
87 -- W
88 -- X
89 -- Y
90 -- Z
91 -- Open Bracket
92 -- Back Slash
93 -- Close Bracket
96 -- NumPad-0
97 -- NumPad-1
98 -- NumPad-2
99 -- NumPad-3
100 -- NumPad-4
101 -- NumPad-5
102 -- NumPad-6
103 -- NumPad-7
104 -- NumPad-8
105 -- NumPad-9
106 -- NumPad *
107 -- NumPad +
108 -- NumPad ,
109 -- NumPad -
110 -- NumPad .
111 -- NumPad /
112 -- F1
113 -- F2
114 -- F3
115 -- F4
116 -- F5
117 -- F6
118 -- F7
119 -- F8
120 -- F9
121 -- F10
122 -- F11
123 -- F12
127 -- Delete
128 -- Dead Grave
129 -- Dead Acute
130 -- Dead Circumflex
131 -- Dead Tilde
132 -- Dead Macron
133 -- Dead Breve
134 -- Dead Above Dot
135 -- Dead Diaeresis
136 -- Dead Above Ring
137 -- Dead Double Acute
138 -- Dead Caron
139 -- Dead Cedilla
140 -- Dead Ogonek
141 -- Dead Iota
142 -- Dead Voiced Sound
143 -- Dead Semivoiced Sound
144 -- Num Lock
145 -- Scroll Lock
150 -- Ampersand
151 -- Asterisk
152 -- Double Quote
153 -- Less
154 -- Print Screen
155 -- Insert
156 -- Help
157 -- Meta
160 -- Greater
161 -- Left Brace
162 -- Right Brace
192 -- Back Quote
222 -- Quote
224 -- Up
225 -- Down
226 -- Left
227 -- Right
240 -- Alphanumeric
241 -- Katakana
242 -- Hiragana
243 -- Full-Width
244 -- Half-Width
245 -- Roman Characters
256 -- All Candidates
257 -- Previous Candidate
258 -- Code Input
259 -- Japanese Katakana
260 -- Japanese Hiragana
261 -- Japanese Roman
262 -- Kana Lock
263 -- Input Method On/Off
512 -- At
513 -- Colon
514 -- Circumflex
515 -- Dollar
516 -- Euro
517 -- Exclamation Mark
518 -- Inverted Exclamation Mark
519 -- Left Parenthesis
520 -- Number Sign
521 -- Plus
522 -- Right Parenthesis
523 -- Underscore
524 -- Windows
525 -- Context Menu
61440 -- F13
61441 -- F14
61442 -- F15
61443 -- F16
61444 -- F17
61445 -- F18
61446 -- F19
61447 -- F20
61448 -- F21
61449 -- F22
61450 -- F23
61451 -- F24
65312 -- Compose
65368 -- Begin
65406 -- Alt Graph
65480 -- Stop
65481 -- Again
65482 -- Props
65483 -- Undo
65485 -- Copy
65487 -- Paste
65488 -- Find
65489 -- Cut

 */
