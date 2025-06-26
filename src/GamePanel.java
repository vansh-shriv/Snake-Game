import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    static final int DELAY = 100; // milliseconds (speed)

    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 4;
    int applesEaten;
    int appleX, appleY;
    char direction = 'R'; // U D L R
    boolean running = false;
    Timer timer;
    Random random;
    GameFrame frame;
    GameState gameState = GameState.PLAYING;

    public GamePanel(GameFrame frame) {
        this.frame = frame;
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.pink);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        //openmenu() which will open the menu and  form there we can start the game .
        startGame();
    }

    public void startGame() {
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (running) {
            if (gameState == GameState.PAUSED) {
                g.setColor(new Color(0, 0, 0, 150));
                g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", Font.BOLD, 40));
                g.drawString("PAUSED", SCREEN_WIDTH / 2 - 80, SCREEN_HEIGHT / 2);
                g.setFont(new Font("Arial", Font.PLAIN, 20));
                g.drawString("Press P to Resume", SCREEN_WIDTH / 2 - 90, SCREEN_HEIGHT / 2 + 30);
            }
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < bodyParts; i++) {
                g.setColor(i == 0 ? Color.green : new Color(45, 180, 0));
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }

            g.setColor(Color.white);
            g.setFont(new Font("Ink Free", Font.BOLD, 30));
            g.drawString("Score: " + applesEaten, 10, 30);
        } else {
            gameOver(g);
        }
    }

    public void newApple() {
        appleX = random.nextInt((SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        appleY = random.nextInt((SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }

    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direction) {
            case 'U' -> y[0] -= UNIT_SIZE;
            case 'D' -> y[0] += UNIT_SIZE;
            case 'L' -> x[0] -= UNIT_SIZE;
            case 'R' -> x[0] += UNIT_SIZE;
        }
    }

    public void checkApple() {
        if (x[0] == appleX && y[0] == appleY) {
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }

    public void checkCollisions() {
        // Hit body
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
                break;
            }
        }
        // Hit wall
        if (x[0] < 0 || x[0] >= SCREEN_WIDTH || y[0] < 0 || y[0] >= SCREEN_HEIGHT)
            running = false;

        if (!running)
            timer.stop();
    }

    public void gameOver(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if(gameState==GameState.PLAYING){
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT -> {
                        if (direction != 'R') direction = 'L';
                    }
                    case KeyEvent.VK_RIGHT -> {
                        if (direction != 'L') direction = 'R';
                    }
                    case KeyEvent.VK_UP -> {
                        if (direction != 'D') direction = 'U';
                    }
                    case KeyEvent.VK_DOWN -> {
                        if (direction != 'U') direction = 'D';
                    }
                    case KeyEvent.VK_P ->{
                        gameState = GameState.PAUSED;
                        timer.stop();
                        repaint();
                    }
                    case KeyEvent.VK_SPACE->{
                        gameState = GameState.PAUSED;
                        timer.stop();
                        repaint();
                    }
                }
            }
            else if(gameState==GameState.PAUSED){
                if(e.getKeyCode()==KeyEvent.VK_P||e.getKeyCode()==KeyEvent.VK_SPACE){
                    gameState=GameState.PLAYING;
                    timer.start();
                }
            }
        }
    }
}
