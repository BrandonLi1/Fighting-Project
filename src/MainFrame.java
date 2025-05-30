import javax.swing.*;

public class MainFrame {
    public MainFrame() {
        JFrame frame = new JFrame("Dueling of thy Dunces");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 960);
        frame.setLocationRelativeTo(null);
        GraphicsPanel panel = new GraphicsPanel();
        frame.add(panel);
        frame.setVisible(true);
    }
}
