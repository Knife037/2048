package ui;

import dto.DtoGame;

import java.awt.Graphics;

/**
 * Created by Administrator on 2016/11/26.
 */
public class LayerGame extends Layer {

	/**
	 * 方块大小
	 */
    private static final int BLOCK_SIZE = 64;

    private DtoGame dto = null;
    /**
     * 构造方法
     * @param x 相对 x 坐标
     * @param y 相对 y 坐标
     * @param width 宽度
     * @param height 高度
     */
    public LayerGame(int x, int y, int width, int height, DtoGame dto) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.dto = dto;
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(Img.BACKGROUND1, this.x, this.y, this.x + this.width, this.y + this.height,
                0, 0, 1, 1, null);
        drawGameMap(g);
    }

    private void drawGameMap(Graphics g) {
        int[][] gameMap = dto.getGameMap();
        for (int col = 0; col < DtoGame.COL; col++) {
            for(int row = 0; row < DtoGame.ROW; row++) {
                int numIndex = gameMap[col][row];
                g.drawImage(Img.BLOCK,
                    this.x + col * (BLOCK_SIZE + INNER_PADDING) + INNER_PADDING,
                    this.y + row * (BLOCK_SIZE + INNER_PADDING) + INNER_PADDING,
                    this.x + (col + 1) * (BLOCK_SIZE + INNER_PADDING),
                    this.y + (row + 1) * (BLOCK_SIZE + INNER_PADDING),
                    numIndex * BLOCK_SIZE,
                    0,
                    (numIndex + 1) * BLOCK_SIZE,
                    BLOCK_SIZE,
                    null
                );
            }
        }
    }

}
