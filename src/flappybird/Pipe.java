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
        ImageIcon pipeTopImage = new ImageIcon("src/img/pipe_up.png"); // Đảm bảo đường dẫn đúng
        ImageIcon pipeBottomImage = new ImageIcon("src/img/pipe_down.png"); // Đảm bảo đường dẫn đúng

        g.drawImage(pipeTopImage.getImage(), x, 0, width, heightTop, null); // Vẽ ống trên
        g.drawImage(pipeBottomImage.getImage(), x, heightTop + gap, width, heightBottom, null); // Vẽ ống dưới
    }

    public Rectangle getTopBounds() {
        return new Rectangle(x, 0, width, heightTop);
    }

    public Rectangle getBottomBounds() {
        return new Rectangle(x, heightTop + gap, width, heightBottom);
    }

    // Thêm getter cho width
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
