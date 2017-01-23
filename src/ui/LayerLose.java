package ui;

import dto.DtoGame;

import java.awt.Graphics;

/**
 * Created by Administrator on 2016/11/27.
 */
public class LayerLose extends Layer{

	/**
	 * 数据访问对象
	 */
	private DtoGame dto = null;

	/**
	 * 构造器
	 * @param x x坐标
	 * @param y y坐标
	 * @param width 宽度
	 * @param height 高度
	 * @param dto 数据传输对象
	 */
    public LayerLose(int x, int y, int width, int height, DtoGame dto) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.dto = dto;
    }

    @Override
    public void paint(Graphics g) {
        if(dto.isLost) {
            g.drawImage(Img.GAME_OVER, this.x, this.y, this.x + this.width, this.y + this.height,
                    0, 0, Img.GAME_OVER_W, Img.GAME_OVER_H, null);
        }
    }
}
