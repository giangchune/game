package flappybird;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private Bird bird;
    private ArrayList<Pipe> pipes;
    private int pipeSpawnTimer = 0;
    private final int pipeSpawnInterval = 90;
    private boolean gameOver = false;
    private int score = 0;
    private boolean scoreSaved = false;

    private List<String> topScores = new ArrayList<>();

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.cyan);
        setFocusable(true);
        addKeyListener(this);

        bird = new Bird(200, HEIGHT / 2);
        pipes = new ArrayList<>();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Background
        ImageIcon backgroundImage = new ImageIcon("src/img/flappy-bird-background.jpg");
        g.drawImage(backgroundImage.getImage(), 0, 0, WIDTH, HEIGHT, null);

        // Ground
        g.setColor(Color.green);
        g.fillRect(0, HEIGHT - 100, WIDTH, 100);

        bird.draw(g);
        for (Pipe p : pipes) {
            p.draw(g);
        }

        // Score display
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 36));
        g.drawString("Score: " + score, 20, 50);

        if (gameOver) {
            g.setColor(Color.red);
            g.setFont(new Font("Arial", Font.BOLD, 48));
            g.drawString("GAME OVER", WIDTH / 2 - 160, HEIGHT / 2 - 30);
            g.setFont(new Font("Arial", Font.PLAIN, 24));
            g.drawString("Nhấn R để chơi lại", WIDTH / 2 - 110, HEIGHT / 2 + 10);

            // Top 5 scores
            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("TOP 5", WIDTH / 2 - 30, HEIGHT / 2 + 60);
            for (int i = 0; i < topScores.size(); i++) {
                g.drawString((i + 1) + ". " + topScores.get(i), WIDTH / 2 - 80, HEIGHT / 2 + 90 + i * 25);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
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
                }

                if (p.isOffScreen()) {
                    it.remove();
                }
            }

            if (bird.getY() + bird.getHeight() >= HEIGHT - 100) {
                gameOver = true;
            }
        }

        // Khi game over, lưu điểm và đọc top nếu chưa lưu
        if (gameOver && !scoreSaved) {
            saveScoreToFile("Người chơi 1", score);
            topScores = readTopScoresFromFile();
            scoreSaved = true;
        }

        repaint();
    }

    private void saveScoreToFile(String playerName, int score) {
        String fileName = "scoreboard.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(playerName + "," + score + "\n");
        } catch (IOException e) {
            System.err.println("Không thể ghi file scoreboard.txt: " + e.getMessage());
        }
    }

    private List<String> readTopScoresFromFile() {
        String fileName = "scoreboard.txt";
        List<String> allScores = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(",")) {
                    allScores.add(line);
                }
            }

            allScores.sort((a, b) -> {
                try {
                    int scoreA = Integer.parseInt(a.split(",")[1].trim());
                    int scoreB = Integer.parseInt(b.split(",")[1].trim());
                    return Integer.compare(scoreB, scoreA);
                } catch (Exception e) {
                    return 0;
                }
            });

        } catch (IOException e) {
            System.err.println("Không thể đọc file scoreboard.txt: " + e.getMessage());
        }

        return allScores.subList(0, Math.min(5, allScores.size()));
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            bird.jump();
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
        scoreSaved = false;
        topScores.clear();
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}
