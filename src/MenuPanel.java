import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.*;

public class MenuPanel extends JPanel {
    public MenuPanel(GameFrame frame){
        this.setPreferredSize(new Dimension(600,600));
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.darkGray);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);

        JButton startButton = new JButton("Start");
        JButton scoreButton = new JButton("High Score");
        JButton settingsButton = new JButton("Settings");
        JButton exitButton = new JButton("Exit");

        startButton.addActionListener(e -> frame.startGame());
        exitButton.addActionListener(e -> System.exit(0));

        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(startButton, gbc);
        gbc.gridy++;
        this.add(scoreButton, gbc);
        gbc.gridy++;
        this.add(settingsButton, gbc);
        gbc.gridy++;
        this.add(exitButton, gbc);

    }
}
