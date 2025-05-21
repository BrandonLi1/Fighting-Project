import javax.swing.*;

public class MainFrame {
    public MainFrame() {
        JFrame frame = new JFrame("Mashup Fighting");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(960, 580);
        frame.setLocationRelativeTo(null);
        GraphicsPanel panel = new GraphicsPanel();
        frame.add(panel);
        frame.setVisible(true);
    }
}
