package ui;

import java.awt.Graphics;

/**
 * Created by Administrator on 2016/11/26.
 */
public class LayerBackground extends Layer{

    /**
     * 构造方法
     * @param x 相对 x 坐标
     * @param y 相对 y 坐标
     * @param width 宽度
     * @param height 高度
     */
    public LayerBackground(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    @Override
    public void paint(Graphics g) {
        g.drawImage(Img.BACKGROUND0, this.x, this.y, this.x + this.width, this.y + this.height,
                0, 0, 1, 1, null);
    }
}
