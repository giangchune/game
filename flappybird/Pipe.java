package flappybird;

import java.awt.*;
import javax.swing.ImageIcon;

public class Pipe {
    private int x, y, width, heightTop, heightBottom;
    private final int gap = 200;
    private boolean passed = false;

    public Pipe(int x) {
        this.x = x;
        this.width = 80;
        this.heightTop = (int) (Math.random() * 300) + 50;
        this.heightBottom = GamePanel.HEIGHT - heightTop - gap;
    }

    public void update() {
        x -= 5;
    }

    public void draw(Graphics g) {
        ImageIcon pipeTopImage = new ImageIcon(Pipe.class.getResource("/img/pipe_up.png"));
        ImageIcon pipeBottomImage = new ImageIcon(Pipe.class.getResource("/img/pipe_down.png"));

        g.drawImage(pipeTopImage.getImage(), x, 0, width, heightTop, null);
        g.drawImage(pipeBottomImage.getImage(), x, heightTop + gap, width, heightBottom, null);
    }

    public Rectangle getTopBounds() {
        int padding = 10;
        int capHeight = 20;
        return new Rectangle(x + padding, capHeight, width - 2 * padding, heightTop - capHeight);
    }

    public Rectangle getBottomBounds() {
        int padding = 10;
        int capHeight = 20;
        return new Rectangle(x + padding, heightTop + gap, width - 2 * padding, heightBottom - capHeight);
    }

    public int getWidth() {
        return width;
    }

    public int getX() {
        return x;
    }

    public boolean isOffScreen() {
        return x + width < 0;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }
}
