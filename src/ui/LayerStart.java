package ui;

import java.awt.Graphics;

import dto.DtoGame;

public class LayerStart extends Layer{

	/**
     * 构造方法
     * @param x 相对 x 坐标
     * @param y 相对 y 坐标
     * @param width 宽度
     * @param height 高度
     */
    public LayerStart(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void paint(final Graphics g) {
    	g.drawImage(Img.START, this.x, this.y, this.width, this.height, null);

    }
}
