package flappybird;

import java.awt.*;
import javax.swing.*;

public class Bird {
    private int x, y, width, height;
    private int velocity;
    private final int gravity = 1;
    private final int lift = -15;

    private ImageIcon birdImage;

    public Bird(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 40;
        this.height = 40;
        this.velocity = 0;
        birdImage = new ImageIcon(Bird.class.getResource("/img/bird.png")); // Load sẵn ảnh 1 lần
    }

    public void update() {
        velocity += gravity;
        y += velocity;

        if (y < 0) {
            y = 0;
            velocity = 0;
        }
    }

    public void jump() {
        velocity = lift;
    }

    // ⭐ Thêm floatOffset để chim nhấp nhô theo offset
    public void draw(Graphics g, int floatOffset) {
        g.drawImage(birdImage.getImage(), x, y + floatOffset, width, height, null);
    }

    // ⭐ Nếu đã vào chơi thì chỉ cần gọi hàm cũ này để vẽ
    public void draw(Graphics g) {
        draw(g, 0);
    }

    public Rectangle getBounds() {
        int padding = 12;
        return new Rectangle(x + padding, y + padding, width - 2 * padding, height - 2 * padding);
    }

    public int getHeight() {
        return height;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
}
