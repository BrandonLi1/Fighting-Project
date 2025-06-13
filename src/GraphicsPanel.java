import org.w3c.dom.css.Rect;

import javax.imageio.ImageIO;
import javax.print.DocFlavor;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;



//https://craftpix.net/freebies/free-animated-explosion-sprite-pack/

//https://craftpix.net/freebies/11-free-pixel-art-explosion-sprites/

public class GraphicsPanel extends JPanel implements ActionListener, KeyListener {
    private JButton playAgain, startButton, keybindsButton, backButton, saberButton, luffyButton, archerButton, glorpButton, confirmButton;
    private JTextArea p1Controls;
    private JTextArea p2Controls;
    private BufferedImage p1WinText, p2WinText, SaberWin, GlorpWin, ArcherWin, LuffyWin, origin, background, selectionBackground, startBackground, p1CharacterImage, p2CharacterImage, healthBar1,healthBar2, p1NameImage, p2NameImage,Hitimage,title;
    private Timer timer;
    private Timer roundTimer;
    private Character p1;
    private Character p2;
    private boolean p1GlorpState = false;
    private boolean p2GlorpState = false;
    private boolean p1InEndLag = false;
    private boolean p2InEndLag = false;
    private boolean checks = true;
    private LinkedList<Long> pressTimestamps = new LinkedList<>();
    private LinkedList<Long> pressTimestampsP2 = new LinkedList<>();
    private boolean hasPressedThreeTimes = false;
    int countdown, counter, x;
    String p1Temp;
    String p2Temp;
    boolean startWindow, keybindsWindow, selectionScreen=false, p1Picked=false, p2Picked=false, endWindow = false, directionP1 = true, directionP2 = false, p1Win, p2Win, p1JustUlted, p2JustUlted;
    boolean[] pressedKeys = new boolean[128];
    //false is left, true is right -what is this bruh
    private boolean p1Attcking = false;
    private boolean p2Attacking = false;
    private List<Arrow> arrows = new ArrayList<>();
    private List<ArcherHeavyEffect> heavyEffects = new ArrayList<>();

    public GraphicsPanel() {
        startButton=new JButton("Start");
        keybindsButton=new JButton("Keybinds");
        backButton=new JButton("back");
        playAgain = new JButton("Play Again");
        startButton.setFocusPainted(false);
        keybindsButton.setFocusPainted(false);
        timer = new Timer(10, this);
        roundTimer = new Timer(1000, this);
        startWindow=true;
        countdown=180;
        p1Controls=new JTextArea();
        p2Controls=new JTextArea();
        p1Controls.setEditable(false);
        p2Controls.setEditable(false);
        try {
            File audioFile = new File("src\\Music\\MHA.wav"); // update path if needed
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();

            // Wait until the clip finishes playing
            Thread.sleep(1);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) {
            e.printStackTrace();
        }


        //this.setCursor(); - make a custom cursor(if time)
        try {
            healthBar1 = ImageIO.read(new File("src\\healthBar\\health bar 1.png"));
            healthBar2 = ImageIO.read(new File("src\\healthBar\\health bar 2.png"));
            background = ImageIO.read(new File("src/Backgrounds/background3.jpg"));
            startBackground= ImageIO.read(new File("src/StartScreen.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        selectionButtons();

        backButton.setVisible(false);
        playAgain.setVisible(false);
        startButton.setSize(500, 500);
        add(startButton);
        add(keybindsButton);
        add(backButton);
        add(p1Controls);
        add(p2Controls);
        add(playAgain);
        startButton.addActionListener(this);
        keybindsButton.addActionListener(this);
        backButton.addActionListener(this);
        playAgain.addActionListener(this);

        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (startWindow) {
            try {
                origin = ImageIO.read((new File("src\\Backgrounds\\origin.png")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            g.drawImage(origin, 0, 0, null);
            try {
                title = ImageIO.read((new File("src\\Winanimations\\title.png")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            g.drawImage(title,450,300,null);

            startButton.setBackground(Color.BLACK);
            startButton.setForeground(Color.WHITE);
            keybindsButton.setBackground(Color.BLACK);
            keybindsButton.setForeground(Color.WHITE);
            startButton.setLocation(870, 575);
            startButton.setSize(100, 60);
            keybindsButton.setLocation(870, 640);
            keybindsButton.setSize(100, 50);
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
            p1Controls.setText("player 1 controls:\nW-jump\nA-left\nS-block\nD-right\nQ-light\nE-heavy\nZ-special 1\nX-special 2");
            p1Controls.setFont(new Font("Pacifico", Font.BOLD, 30));
            p2Controls.setText("player 2 controls:\n↑-jump\n←-left\n↓-block\n→-right\nnumpad4-light\nnumpad5-heavy\nnumpad1-special 1\nnumpad2-special 2");
            p2Controls.setFont(new Font("Pacifico", Font.BOLD, 30));
            p1Controls.setBounds(0, 50, 550, 450);
            p2Controls.setBounds(1000, 50, 550, 450);
        } else if (selectionScreen) {
            playAgain.setVisible(false);
            try {
                selectionBackground = ImageIO.read((new File("src\\Backgrounds\\selectionBackground.jpg")));
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
            glorpButton.setVisible(true);
            glorpButton.setLocation(1200, 100);
            if (p1CharacterImage != null) {
                g.drawImage(p1NameImage, 150, 500, null);
                g.drawImage(p1CharacterImage, 100, 600, null);
            }
            if (p2CharacterImage != null) {
                g.drawImage(p2NameImage, 1000, 500, null);
                g.drawImage(p2CharacterImage, 950, 600, null);
            }
        } else if (endWindow) {
            try {
                p1WinText = ImageIO.read((new File("src\\Winanimations\\P1win.png")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                p2WinText = ImageIO.read((new File("src\\Winanimations\\P2win.png")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                GlorpWin= ImageIO.read((new File("src\\Winanimations\\GlorpWin.png")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                SaberWin= ImageIO.read((new File("src\\Winanimations\\SaberWin.png")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                ArcherWin= ImageIO.read((new File("src\\Winanimations\\ArcherWIn.jpg")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                LuffyWin= ImageIO.read((new File("src\\Winanimations\\luffy.png")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (p2Win) {
                if (p2.getClass() == Glorp.class) {
                    g.drawImage(GlorpWin, 0, 0, null);
                    g.drawImage(p2WinText, 100, 100, null);
                }
                else if (p2.getClass() ==  Archer.class) {
                    g.drawImage(ArcherWin, 0, 0, null);
                    g.drawImage(p2WinText, 100, 100, null);

                }
                else if (p2.getClass() ==  Luffy.class) {
                    p2.heavyAttack();
                    g.drawImage(LuffyWin, 0, 0, null);
                    g.drawImage(p2WinText, 100, 100, null);

                }
                else if (p2.getClass() == Saber.class) {
                    g.drawImage(SaberWin, 0, 0, null);
                    g.drawImage(p2WinText, 100, 100, null);

                }
            }
            else {
                if (p1.getClass() == Glorp.class) {

                    g.drawImage(GlorpWin, 0, 0, null);
                    g.drawImage(p1WinText, 100, 100, null);

                }
                else if (p1.getClass() ==  Archer.class) {
                    g.drawImage(ArcherWin, 0, 0, null);
                    g.drawImage(p1WinText, 100, 100, null);

                }
                else if (p1.getClass() ==  Luffy.class) {
                    g.drawImage(LuffyWin, 0, 0, null);
                    g.drawImage(p1WinText, 100, 100, null);

                }
                else if (p1.getClass() == Saber.class) {
                    g.drawImage(SaberWin, 0, 0, null);
                    g.drawImage(p1WinText, 100, 100, null);

                }
            }

            playAgain.setVisible(true);
            playAgain.grabFocus();
            playAgain.setBackground(Color.BLACK);
            playAgain.setForeground(Color.WHITE);
            playAgain.setLocation(400, 260);
            playAgain.setSize(180, 60);

        } else {
            if (!timer.isRunning()) {
                timer.start();
            }
            if (!roundTimer.isRunning()) {
                roundTimer.start();
            }
            if(checks){
                p1.heavyAttack();
                p2.heavyAttack();
                p1.jump();
                p2.jump();
                checks = false;
            }
            g.drawImage(background, 0, 0, null);
            g.drawImage(p1.getPlayerImage(), (int) p1.getxCoord(), (int) p1.yCoord, p1.getWidth(), p1.height, null);
            if (p1 instanceof Archer) {
                Archer archer = (Archer) p1;
                if (archer.attackAnimationEnded()) {
                    int arrowY = (int) (archer.yCoord + archer.height / 2);
                    int arrowX;
                    if (archer.facingRight) {
                        arrowX = (int) (archer.xCoord + archer.width);
                    } else {
                        arrowX = (int) (archer.xCoord - 50); //remember to adjust this
                    }
                    arrows.add(new Arrow(arrowX, arrowY, archer.facingRight, 1));
                    archer.setIsAttacking(false);
                }
            }

            g.setColor(Color.RED);
            g.fillRect(327, 74, (int) (480 * ((double) p1.getHealth() / p1.getMaxHealth())), 52);
            g.fillRect(1057 + (int) (480 * (1.0 - (double) p2.getHealth() / p2.getMaxHealth())), 74, (int) (480 * ((double) p2.getHealth() / p2.getMaxHealth())), 52);
            g.setColor(Color.BLUE);
            g.fillRect(327, 124, (int) (480 * (p1.meter / 7)), 26);
            g.fillRect(1057 + (int) (480 * (1.0 - p2.meter / 7)), 126, (int) (480 * (p2.meter / 7)), 26);
            g.setColor(Color.BLACK);
            g.drawImage(healthBar1, 200, 50, null);
            g.drawImage(healthBar2, 1050, 50, null);
            g.drawImage(p2.getPlayerImage(), (int) p2.getxCoord(), (int) p2.yCoord, p2.getWidth(), p2.height, null);
            if (p2 instanceof Archer) {
                Archer archer = (Archer) p2;
                if (archer.attackAnimationEnded()) {
                    int arrowY = (int) (archer.yCoord + archer.height / 2);
                    int arrowX;
                    if (archer.facingRight) {
                        arrowX = (int) (archer.xCoord + archer.width);
                    } else {
                        arrowX = (int) (archer.xCoord - 50); //remember to adjust this
                    }
                    arrows.add(new Arrow(arrowX, arrowY, archer.facingRight, 2));
                    archer.setIsAttacking(false);
                }
            }
            for (int i = 0; i < arrows.size(); i++) {
                Arrow arrow = arrows.get(i);
                arrow.update();

                if (arrow.getOwner() == 1 && p2.hitbox().intersects(arrow.hitbox())) {
                    p2.setHealth(p2.getHealth() - 10);
                    arrow.setExpired(true);
                }
                if (arrow.getOwner() == 2 && p1.hitbox().intersects(arrow.hitbox())) {
                    p1.setHealth(p1.getHealth() - 10);
                    arrow.setExpired(true);
                }

                if (arrow.isExpired()) {
                    arrows.remove(i);
                    i--;
                }
            }
            for (Arrow arrow : arrows) {
                arrow.draw(g);
            }
            for (int i = 0; i < heavyEffects.size(); i++) {
                ArcherHeavyEffect effect = heavyEffects.get(i);
                effect.update();
                effect.draw(g);
                if (effect.owner == 1 && effect.hitbox().intersects(p2.hitbox()) && !p2.blocking && !effect.hasHit) {
                    p2.setHealth(p2.getHealth() - 30);
                    p1.addMeter(0.5);
                    effect.hasHit = true;
                }
                if (effect.owner == 2 && effect.hitbox().intersects(p1.hitbox()) && !p1.blocking && !effect.hasHit) {
                    p1.setHealth(p1.getHealth() - 30);
                    p2.addMeter(0.5);
                    effect.hasHit = true;
                }
                if (effect.isExpired()) {
                    heavyEffects.remove(i);
                    i--;
                }
            }
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString(String.valueOf(countdown), 904, 100);
            g.drawRect((int) p1.xCoord, (int) p1.yCoord, p1.width, p1.height);
            g.drawRect((int) p2.xCoord, (int) p2.yCoord, p2.width, p2.height);

            //p1

            //W
            if (!p1.stunned) {
                if (pressedKeys[87]) {
                    if (p1.isGrounded && !p1.blocking) {
                        p1.setAnimationNum(3);
                        p1.jump();
                    }
                }

                //A
                if (pressedKeys[65] && !p1.blocking) {
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
                if (pressedKeys[68] && !p1.blocking) {
                    p1.moveRight();
                    p1.faceRight();
                    p1.setAnimationNum(1);
                    directionP1 = true;
                }

                // basic attack
                if (pressedKeys[81] && !p1.blocking) {
                    ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
                    if (!p1Attcking && !p1InEndLag) {
                        p1Attcking = true;
                        if (p1 instanceof Archer) {
                            try {
                                p1.attack();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            Rectangle damageBox = null;
                            try {
                                damageBox = p1.attack();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            Rectangle hitbox = p2.hitbox();
                            g.drawRect(damageBox.x, damageBox.y, damageBox.width, damageBox.height);

                            if (damageBox.intersects(hitbox) && p2GlorpState) {
                                p1.setHealth(p1.getHealth() - 120);
                            } else if (damageBox.intersects(hitbox) && !p2.blocking) {
                                System.out.println("hit");
                                p2.setHealth(p2.getHealth() - p1.attackDamage);
                                stunP2(p1.normalD - 10);
                                p1.addMeter(.2);
                            } else if (damageBox.intersects(hitbox) && p2.blocking) {
                                p2.setHealth(p2.getHealth() - 1);
                                p1.addMeter(.05);
                            }
                        }


                        // **Track attack timestamps**
                        long now = System.currentTimeMillis();
                        pressTimestamps.add(now);

                        // **Remove old timestamps beyond 3 seconds**
                        while (!pressTimestamps.isEmpty() && now - pressTimestamps.peek() > (long) p1.normalD * p1.comboCounter + 600) {
                            pressTimestamps.poll();
                        }

                        // **Check if 3 attacks happened within 3 seconds**
                        if (pressTimestamps.size() >= 3) {
                            System.out.println("Endlag triggered!"); // Debugging message
                            p1InEndLag = true; // **Start endlag period**

                            executorService.schedule(() -> {
                                p1InEndLag = false; // **Endlag finished, attacks are allowed again**
                            }, p1.normalD + 1500, TimeUnit.MILLISECONDS); // Adjust this duration for endlag
                        }

                        // **Standard attack cooldown**
                        executorService.schedule(() -> {
                            p1Attcking = false;
                        }, p1.normalD, TimeUnit.MILLISECONDS);

                        executorService.shutdown();
                    }
                }
                if (pressedKeys[69]) {
                    ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

                    if (!p1Attcking && !p1JustUlted) {
                        p1Attcking = true;
                        if (p1.getClass() == Glorp.class) {
                            p1.isAttacking = true;
                        }
                            if (p1 instanceof Archer) {
                                Archer archer = (Archer) p1;
                                p1.heavyAttack();
                                if (archer.pendingHeavyEffect != null) {
                                    archer.pendingHeavyEffect.owner = 1;
                                    heavyEffects.add(archer.pendingHeavyEffect);
                                    archer.pendingHeavyEffect = null;
                                }
                            } else {
                                Rectangle damageBox = p1.heavyAttack();
                                Rectangle hitbox = p2.hitbox();
                                g.drawRect(damageBox.x, damageBox.y, damageBox.width, damageBox.height);
                                if (damageBox.intersects(hitbox) && p1GlorpState) {
                                    p2.setHealth(p2.getHealth() - 120);
                                } else if (damageBox.intersects(hitbox)) {
                                    System.out.println("hit");
                                    p2.setHealth(p2.getHealth() - p1.attackDamage * 3);
                                    System.out.println(p2.getHealth());
                                    System.out.println(p1.attackDamage);
                                    if (p1 instanceof Glorp) {
                                        stunP2(1000);
                                    } else {
                                        stunP2(500);
                                    }
                                    p1.addMeter(.5);
                                    if (p1.getClass() == Saber.class) {
                                        ((Saber) p1).energy++;
                                    }
                                }
                            }


                            executorService.schedule(() -> {
                                p1Attcking = false;
                                if (p1.getClass() == Glorp.class) {
                                    p1.isAttacking = false;
                                }
                            }, p1.heavyD, TimeUnit.MILLISECONDS);

                            executorService.shutdown();
                    }
                }

                //Xp1.meter >= 3 &&
                if (pressedKeys[88]) {
                    int y=0;
                    if (p1.getClass() == Glorp.class) {
                        p1GlorpState = true;
                        p1.setGlorpState(true);
                        p1.setStunned(true);
                        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
                        executorService.schedule(() -> {
                            p1GlorpState = false;
                            p1.setGlorpState(false);
                            p1.setStunned(false);
                        }, 1500, TimeUnit.MILLISECONDS);
                        p1.meter -= (int) p1.meter;
                    } else {
                        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
                        p1Attcking = true;
                        Rectangle damageBox = p1.special1();
                        Rectangle hitbox = p2.hitbox();
                        g.drawRect(damageBox.x, damageBox.y, damageBox.width, damageBox.height);
                        if (damageBox.intersects(hitbox) && p1GlorpState) {
                            p2.setHealth(p2.getHealth() - 120);
                        } else if (p1.getClass() == Saber.class) {
                            y=800;
                            if (p1.meter>=3) {
                                x = ((Saber) p1).energy + (int) p1.meter;
                                p1.meter -= (int) p1.meter;

                                System.out.println(x);
                                x *= 30;
                            }
                        } else if(p1.getClass() == Luffy.class){
                            y=600;
                            if (p1.meter>=3) {
                                x = 5;
                                p1.meter -= (int) p1.meter;
                                System.out.println(x);
                                x *= 30;
                            }
                        }
                        System.out.println(x);
                        //just change where it says p1.heavyD right below to whatever you its suppsied to be
                        executorService.schedule(() -> {
                            if (damageBox.intersects(hitbox)) {
                                System.out.println("?dakshgdsbja");
                                p2.setHealth(p2.getHealth() - x);
                            }
                            p1Attcking = false;
                        }, y, TimeUnit.MILLISECONDS);

                        executorService.shutdown();
                    }
                    p1JustUlted=true;
                }
            }
            else {
                ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
                executorService.schedule(() -> {
                    p1Attcking = false;
                }, 2000, TimeUnit.MILLISECONDS);

            }


            //basic attack

            /*
            if (pressedKeys[69]) {//e
                p1.//heavy
            }*/


            // W=87; A=65; S=83; D=68
            //p2 q-light e- heavy zxc-flexq

            //Up Key
            if (!p2.stunned) {
                if (pressedKeys[38] && !p2.blocking) {
                    if (p2.isGrounded) {
                        p2.setAnimationNum(3);
                        p2.jump();
                    }
                }

                //Left Key
                if (pressedKeys[37] && !p2.blocking) {
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
                if (pressedKeys[39] && !p2.blocking) {
                    p2.moveRight();
                    p2.faceRight();
                    p2.setAnimationNum(1);
                    directionP2 = true;
                }

                if (pressedKeys[100] && !p2.blocking) {
                    ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

                    if (!p2Attacking && !p2InEndLag) {
                        p2Attacking = true;
                        if (p2 instanceof Archer) {
                            try {
                                p2.attack();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            Rectangle damageBox = null; // FIXED: removed try/catch
                            try {
                                damageBox = p2.attack();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            Rectangle hitbox = p1.hitbox();
                            g.drawRect(damageBox.x, damageBox.y, damageBox.width, damageBox.height);

                            if (damageBox.intersects(hitbox) && p1GlorpState) {
                                p2.setHealth(p2.getHealth() - 120);
                            } else if (damageBox.intersects(hitbox) && !p1.blocking) {
                                System.out.println("hit");
                                p1.setHealth(p1.getHealth() - p2.attackDamage);
                                p2.addMeter(.2);
                                stunP1(p2.normalD - 5);
                            } else if (damageBox.intersects(hitbox) && p1.blocking) {
                                p1.setHealth(p1.getHealth() - 1);
                                p2.addMeter(.05);
                            }
                        }

                        // **Track attack timestamps**
                        long now = System.currentTimeMillis();
                        pressTimestampsP2.add(now);

                        // **Remove old timestamps beyond 3 seconds**
                        while (!pressTimestampsP2.isEmpty() && now - pressTimestampsP2.peek() > p2.normalD * p2.comboCounter + 600) {
                            pressTimestampsP2.poll();
                        }

                        // **Check if P2 attacked 3 times within 3 seconds**
                        if (pressTimestampsP2.size() >= 3) {
                            System.out.println("Endlag triggered for P2!"); // Debugging message
                            p2InEndLag = true; // **Start endlag period**

                            executorService.schedule(() -> {
                                p2InEndLag = false; // **Endlag finished, P2 can attack again**
                            }, p2.normalD + 1500, TimeUnit.MILLISECONDS); // Adjust this duration for endlag
                        }

                        // **Standard attack cooldown**
                        executorService.schedule(() -> {
                            p2Attacking = false;
                        }, p2.normalD, TimeUnit.MILLISECONDS);
                        executorService.shutdown();
                    }
                }
                if (pressedKeys[101]) {
                    ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

                    if (!p2Attacking) {
                        p2Attacking = true;
                        if (p2.getClass() == Glorp.class) {
                            p2.isAttacking = true;
                        }
                        if (p2 instanceof Archer) {
                            Archer archer = (Archer) p2;
                            p2.heavyAttack();
                            if (archer.pendingHeavyEffect != null) {
                                archer.pendingHeavyEffect.owner = 2;
                                heavyEffects.add(archer.pendingHeavyEffect);
                                archer.pendingHeavyEffect = null;
                            }
                        } else {
                            Rectangle damageBox = p2.heavyAttack();
                            Rectangle hitbox = p1.hitbox();
                            g.drawRect(damageBox.x, damageBox.y, damageBox.width, damageBox.height);
                            if (damageBox.intersects(hitbox) && p1GlorpState) {
                                p2.setHealth(p2.getHealth() - 120);
                            } else if (damageBox.intersects(hitbox)) {
                                System.out.println("hit");
                                p1.setHealth(p1.getHealth() - p2.attackDamage * 3);
                                System.out.println(p1.getHealth());
                                System.out.println(p2.attackDamage);
                                if (p2 instanceof Glorp) {
                                    stunP1(1000);
                                } else {
                                    stunP1(500);
                                }
                                p2.addMeter(.5);
                                if (p2.getClass()==Saber.class) {
                                    ((Saber) p2).energy++;
                                }
                            }
                        }



                        executorService.schedule(() -> {
                            p2Attacking = false;
                            if (p2.getClass() == Glorp.class) {
                                p2.isAttacking = false;
                            }
                        }, p2.heavyD, TimeUnit.MILLISECONDS);

                        executorService.shutdown();
                    }
                }

                if (pressedKeys[98]) {
                    int y=0;
                    if (p2.getClass() == Glorp.class) {
                        p2GlorpState = true;
                        p2.setGlorpState(true);
                        p2.setStunned(true);
                        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
                        executorService.schedule(() -> {
                            p2GlorpState = false;
                            p2.setGlorpState(false);
                            p2.setStunned(false);
                        }, 1500, TimeUnit.MILLISECONDS);
                        p2.meter -= (int) p2.meter;
                    } else {
                        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
                        p1Attcking = true;
                        Rectangle damageBox = p2.special1();
                        Rectangle hitbox = p1.hitbox();
                        g.drawRect(damageBox.x, damageBox.y, damageBox.width, damageBox.height);
                        if (damageBox.intersects(hitbox) && p2GlorpState) {
                            p1.setHealth(p1.getHealth() - 120);
                        } else if (p2.getClass() == Saber.class) {
                            y=800;
                            if (p1.meter>=3) {
                                x = ((Saber) p2).energy + (int) p2.meter;
                                p2.meter -= (int) p2.meter;

                                System.out.println(x);
                                x *= 30;
                            }
                        } else if(p2.getClass() == Luffy.class){
                            y=600;
                            if (p2.meter>=3) {
                                x = 5;
                                p2.meter -= (int) p2.meter;
                                System.out.println(x);
                                x *= 30;
                            }
                        }
                        System.out.println(x);
                        //just change where it says p1.heavyD right below to whatever you its suppsied to be
                        executorService.schedule(() -> {
                            if (damageBox.intersects(hitbox)) {
                                System.out.println("?dakshgdsbja");
                                p1.setHealth(p1.getHealth() - x);
                            }
                            p2Attacking = false;
                        }, y, TimeUnit.MILLISECONDS);

                        executorService.shutdown();
                    }
                    p2JustUlted=true;
                }
            else {
                ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
                executorService.schedule(() -> {
                    p2Attacking = false;
                }, 2000, TimeUnit.MILLISECONDS);

            }

                if (p1.getHealth()<=0) {
                    p2Win=true;
                    endWindow = true;
                }
                if(p2.getHealth()<=0){
                    p1Win = true;
                    endWindow = true;
                }


                p1.checkGrounded();
                p2.checkGrounded();
                // up arrow=38; left arrow=37; down arrow=40; right arrow=39;


            }
            else {

            }
        }
        checkAttacksP1();
        checkAttacksP2();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        requestFocusInWindow();
        Object source =  e.getSource();



        if (source==roundTimer) {
            if (p1JustUlted || p2JustUlted) {
                counter++;
            }
            countdown--;
            if (counter%3==0) {
                p1JustUlted = false;
                p2JustUlted = false;
            }
            if (countdown<=0) {
                if (p1.health > p2.health) {
                    endWindow = true;
                }
                else {
                    endWindow = true;
                }
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
        if (source==glorpButton) {
            if (!p1Picked) {
                try {
                    p1CharacterImage = ImageIO.read(new File("src\\CharacterSelectionAssets\\PlayerImage\\GlorpSelectionPlayer.png"));
                    p1Temp="Glorp";
                    p1NameImage=ImageIO.read(new File("src\\CharacterSelectionAssets\\PlayerText\\saberName.jpg"));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                try {
                    p2CharacterImage = ImageIO.read(new File("src\\CharacterSelectionAssets\\PlayerImage\\GlorpSelectionPlayer.png"));
                    p2Temp="Glorp";
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
                if (p1Temp.equals("Glorp")) {
                    p1=new Glorp();
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
                if (p2Temp.equals("Glorp")) {
                    p2=new Glorp();
                }
                p2.setxCoord(1300);
            }
            if (p2Picked) {
                selectionScreen=false;
                saberButton.setVisible(false);
                luffyButton.setVisible(false);
                glorpButton.setVisible(false);
                archerButton.setVisible(false);
                confirmButton.setVisible(false);
            }
        }
        if (source == playAgain) {
            selectionScreen = true;
            p1Picked=false;
            p2Picked=false;
            p1=null;
            p2=null;
            endWindow = false;
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
        }
        if (e.getKeyCode()==100) {
            System.out.println("p2 attack");
        }
        if (e.getKeyCode()==83) {
            p1.setBlocking(false);
        }
        if (e.getKeyCode()==40) {
            p2.setBlocking(false);
        }
    }

    private void selectionButtons() {
        glorpButton=new JButton();
        glorpButton.setSize(92, 85);
        try {
            Image img = ImageIO.read(new File("src\\CharacterSelectionAssets\\GlorpSelection.png"));
            glorpButton.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            System.out.println(e);
        }
        add(glorpButton);
        glorpButton.setVisible(false);

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
        glorpButton.addActionListener(this);
        System.out.println("action listener added");
        confirmButton.addActionListener(this);
    }

    //merge force
    public void stunP1 (int duration) {
        p1.stunned = true;

        ScheduledExecutorService stun = Executors.newSingleThreadScheduledExecutor();
        stun.schedule(() -> {
            p1.stunned = false;
        }, duration, TimeUnit.MILLISECONDS);
    }

    public void stunP2 (int duration) {
        p2.stunned = true;

        ScheduledExecutorService stun = Executors.newSingleThreadScheduledExecutor();
        stun.schedule(() -> {
            p2.stunned = false;
        }, duration, TimeUnit.MILLISECONDS);
    }


    private void checkAttacksP1() {
        long now = System.currentTimeMillis();

        // Remove old presses beyond the calculated attack window
        while (!pressTimestamps.isEmpty() && now - pressTimestamps.peek() > p1.normalD * p1.comboCounter + 100) {
            pressTimestamps.poll();
        }

        // Check if the player has pressed 3 times in the window
        if (pressTimestamps.size() >= 3) {
            if (!p1Attcking && !p1InEndLag) { // NEW: Prevent attacking during endlag
                p1Attcking = true;
                p1InEndLag = true; // Start endlag timer

                ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

                executorService.schedule(() -> {
                    p1Attcking = false; // Allow attacking again after normal attack duration
                }, p1.normalD, TimeUnit.MILLISECONDS);

                // NEW: Add **extra** cooldown before allowing attacks again
                executorService.schedule(() -> {
                    p1InEndLag = false; // Endlag finished, player can attack again
                }, p1.normalD + 300, TimeUnit.MILLISECONDS); // Adjust this delay for endlag duration

                executorService.shutdown();
            }
        }
    }


    private void checkAttacksP2() {
        long now = System.currentTimeMillis();

        // Remove old presses beyond 3 seconds
        while (!pressTimestamps.isEmpty() && now - pressTimestamps.peek() > 3000) {
            pressTimestamps.poll();
        }

        // Check if the player has pressed 3 times in the window
        if (pressTimestamps.size() >= 3) {

        }    }

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