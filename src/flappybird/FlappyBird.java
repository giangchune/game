package flappybird;

import javax.swing.JFrame;

public class FlappyBird {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Flappy Bird");
        GamePanel gamePanel = new GamePanel();

        frame.add(gamePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}