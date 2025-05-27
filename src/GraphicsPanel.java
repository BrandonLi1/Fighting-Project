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
    boolean startWindow, keybindsWindow, selectionScreen=false, p1Picked=false, p2Picked=true;
    boolean[] pressedKeys = new boolean[128];
    private boolean directionP1 = true;
    private boolean directionP2 = false;
    //false is left, true is right


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
        p1 = new Character("joe", 1, 1, 100,100 , 10,10 ,1 ,1 ,300, 720, false, false ,true, 100);
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
        } else {
            g.drawImage(background, 0, 0, null);
            g.drawImage(p1.getPlayerImage(), (int) p1.xCoord, (int) p1.yCoord, p1.width, p1.height, null);
            g.drawImage(healthBar, 0, 0, null);
            //g.drawImage(p2.getPlayerImage(), p2.xCoord, p2.yCoord, p2.width, p2.height, null);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString(String.valueOf(countdown), 450, 40);

            //p1

            if (pressedKeys[87]) {
                if (p1.isGrounded)
                p1.jump();
            }

            if (pressedKeys[65]) {
                p1.moveLeft();
                System.out.println("a");
                directionP1 = false;
            }

            if (pressedKeys[83]) {
                p1.block();
            }

            if (pressedKeys[68]) {
                p1.moveRight();
                System.out.println("d");
                directionP1 = true;
            }

            //basic attack
            if (pressedKeys[81]) {
                Rectangle damageBox = p1.attack((int)p1.xCoord, (int)p1.yCoord, directionP1);
                if (damageBox.intersects(new Rectangle((int)p2.xCoord, (int)p2.yCoord, (int)p2.xCoord - (int)p2.width, (int)p2.yCoord - (int)p2.height))) {
                    p2.health -= p1.getAttackDamage();
                }
            }

            /*
            if (pressedKeys[69]) {//e
                p1.//heavy
            }*/



            // W=87; A=65; S=83; D=68
            //p2 q-light e- heavy zxc-flexq

            if (pressedKeys[38]) {
                if (p2.isGrounded)
                p2.jump();
                System.out.println("w");
            }

            if (pressedKeys[37]) {
                p2.moveLeft();
                directionP2 = false;
            }

            if (pressedKeys[40]) {
                p2.block();
            }

            if (pressedKeys[39]) {
                p2.moveRight();
                directionP2 = true;
            }
            if (p1.yCoord<=0) {
                p1.setGrounded(true);
            }
           /* if (p2.yCoord<=900) {
                p2.setGrounded(true);
            }*/
            // up arrow=38; left arrow=37; down arrow=40; right arrow=39;

            // basic attack
            if (pressedKeys[97]) {
                Rectangle damageBox = p2.attack((int)p2.xCoord,(int) p2.yCoord, directionP2);
                if (damageBox.intersects(new Rectangle((int)p2.xCoord, (int)p2.yCoord,(int) p2.xCoord - (int)p2.width,(int) p2.yCoord - (int) p2.height))) {
                    p1.health -= p2.getAttackDamage();
                }
            }

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
                    p1CharacterImage = ImageIO.read(new File("src\\CharacterSelectionAssets\\kaliSelection.jpg"));
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
        }
        if (source==confirmButton) {
            if (!p1Picked) {
                p1Picked=true;
                p2Picked=false;
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
