package flappybird;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private Bird bird;
    private ArrayList<Pipe> pipes;
    private Timer timer;
    private int pipeSpawnTimer = 0;
    private final int pipeSpawnInterval = 90;
    private boolean gameOver = false;
    private boolean start = false;
    private int score = 0;
    private int highScore = 0;
    private final int EXTRA_FALL_DISTANCE = 50;

    private int floatOffset = 0; 
    private int floatDirection = 1;

    private Leaderboard leaderboard = new Leaderboard();

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.cyan);
        setFocusable(true);
        addKeyListener(this);

        bird = new Bird(200, HEIGHT / 2);
        pipes = new ArrayList<>();
        timer = new Timer(20, this);
        timer.start();

        if (!leaderboard.getScores().isEmpty()) {
            highScore = leaderboard.getScores().get(0).getScore();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        ImageIcon backgroundImage = new ImageIcon(GamePanel.class.getResource("/img/flappy-bird-background.png"));
        g.drawImage(backgroundImage.getImage(), 0, 0, WIDTH, HEIGHT, null);

        if (!start) {
            //Hiện tiêu đề
            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.BOLD, 64));
            String title = "FLAPPY BIRD";
            int titleWidth = g.getFontMetrics().stringWidth(title);
            g.drawString(title, (WIDTH - titleWidth) / 2, 180);

            bird.draw(g, floatOffset);

            //"Nhấn Space để bắt đầu!"
            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.BOLD, 32));
            String pressText = "Nhấn Space để bắt đầu!";
            int pressWidth = g.getFontMetrics().stringWidth(pressText);
            g.drawString(pressText, (WIDTH - pressWidth) / 2, bird.getY() + bird.getHeight() + 50);
        } else {
            bird.draw(g);

            for (Pipe p : pipes) {
                p.draw(g);
            }

            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.drawString("Score: " + score, 20, 50);

            g.setFont(new Font("Arial", Font.BOLD, 24));
            g.drawString("High Score: " + highScore, WIDTH - 250, 50);
        }

        if (gameOver) {
            g.setColor(Color.red);
            g.setFont(new Font("Arial", Font.BOLD, 48));
            g.drawString("GAME OVER", WIDTH / 2 - 160, HEIGHT / 2);
            g.setFont(new Font("Arial", Font.PLAIN, 24));
            g.drawString("Nhấn R để chơi lại", WIDTH / 2 - 110, HEIGHT / 2 + 40);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!start) {
            // ⭐ Chim nhấp nhô trước khi bắt đầu
            floatOffset += floatDirection;
            if (floatOffset > 10 || floatOffset < -10) {
                floatDirection *= -1;
            }
            repaint();
            return;
        }

        if (!gameOver) {
            bird.update();

            pipeSpawnTimer++;
            if (pipeSpawnTimer >= pipeSpawnInterval) {
                pipes.add(new Pipe(WIDTH));
                pipeSpawnTimer = 0;
            }

            Iterator<Pipe> it = pipes.iterator();
            while (it.hasNext()) {
                Pipe p = it.next();
                p.update();

                if (p.getTopBounds().intersects(bird.getBounds()) || p.getBottomBounds().intersects(bird.getBounds())) {
                    gameOver = true;
                }

                if (p.getX() + p.getWidth() < bird.getX() && !p.isPassed()) {
                    score++;
                    p.setPassed(true);

                    if (score > highScore) {
                        highScore = score;
                    }
                }

                if (p.isOffScreen()) {
                    it.remove();
                }
            }

            if (bird.getY() + bird.getHeight() >= HEIGHT - 100 + EXTRA_FALL_DISTANCE) {
                gameOver = true;
            }
        }

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (!start) {
                start = true; // ⭐ Bấm Space để bắt đầu luôn
            } else {
                bird.jump();  // ⭐ Nếu đã start, Space là nhảy
            }
        }
        if (gameOver && e.getKeyCode() == KeyEvent.VK_R) {
            restartGame();
        }
    }

    public void restartGame() {
        bird = new Bird(200, HEIGHT / 2);
        pipes.clear();
        score = 0;
        pipeSpawnTimer = 0;
        gameOver = false;
        start = false;
        floatOffset = 0;
        floatDirection = 1;
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}
