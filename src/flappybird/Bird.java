package flappybird;

import java.awt.*;
import javax.swing.*;


public class Bird {
    private int x, y, width, height;
    private int velocity;
    private final int gravity = 1;
    private final int lift = -15;

    public Bird(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 40;
        this.height = 40;
        this.velocity = 0;
    }

    public void update() {
        velocity += gravity;
        y += velocity;

        if (y + height >= GamePanel.HEIGHT - 100) {
            y = GamePanel.HEIGHT - 100 - height;
            velocity = 0;
        }

        if (y < 0) {
            y = 0;
            velocity = 0;
        }
    }

    public void jump() {
        velocity = lift;
    }

    public void draw(Graphics g) {
        ImageIcon birdImage = new ImageIcon("src/img/bird.png"); // Đảm bảo đường dẫn đúng
        g.drawImage(birdImage.getImage(), x, y, width, height, null); // Vẽ chim
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    // Thêm getter cho height
    public int getHeight() {
        return height;
    }

    public int getY() {
        return y;
    }

    // Thêm getter cho x
    public int getX() {
        return x;
    }
}
