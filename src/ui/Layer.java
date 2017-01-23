package ui;

import java.awt.Graphics;

/**
 * Created by Administrator on 2016/11/26.
 */
public abstract class Layer {

    /**
     * 边距
     */
    protected static final int INNER_PADDING = 16;

    /**
     * 相对 x 坐标
     */
    protected int x;

    /**
     * 相对 y 坐标
     */
    protected int y;
    
    /**
     * 图层宽度
     */
	protected int width;

    /**
     * 图层高度
     */
	protected int height;

    /**
     * 构造函数
     */
    public Layer() {
    }

    public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

    protected abstract void paint(Graphics g);
}
