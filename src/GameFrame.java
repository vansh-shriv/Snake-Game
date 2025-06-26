import java.awt.CardLayout;
import javax.swing.*;

public class GameFrame extends JFrame {
    MenuPanel menuPanel;
    GamePanel gamePanel;

    public GameFrame() {
        // this.add(new GamePanel());
        this.setTitle("Snake Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(new CardLayout());

        menuPanel = new MenuPanel(this); 
        this.add(menuPanel,"Menu");
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null); // Center on screen
    }

    public void startGame(){
        this.getContentPane().removeAll();
        gamePanel = new GamePanel(this);
        this.add(gamePanel);
        this.revalidate();
        this.repaint();
        gamePanel.requestFocusInWindow();
    }

    public void backToMenu(){
        this.getContentPane().removeAll();
        menuPanel = new MenuPanel(this);
        this.add(menuPanel);
        this.revalidate();
        this.repaint();
        menuPanel.requestFocusInWindow();
    }

    public static void main(String[] args) {
        GameFrame frame = new GameFrame();
    }
}