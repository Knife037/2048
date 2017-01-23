package ui;

import javax.swing.ImageIcon;
import java.awt.Image;

/**
 * Created by Administrator on 2016/11/26.
 */
public class Img {

    /**
     * 底层背景
     */
    public static final Image BACKGROUND0 = new ImageIcon("graphics/background_0.png").getImage();

    /**
     * 背景
     */
    public static final Image BACKGROUND1 = new ImageIcon("graphics/background_1.png").getImage();

    /**
     * 方块
     */
    public static final Image BLOCK = new ImageIcon("graphics/BLOCK.png").getImage();

    /**
     * 游戏结束
     */
    public static final Image GAME_OVER= new ImageIcon("graphics/gameover.png").getImage();
    public static final int GAME_OVER_W = GAME_OVER.getWidth(null);
    public static final int GAME_OVER_H = GAME_OVER.getHeight(null);
    
    /**
     * 分数
     */
    public static final Image SCORE = new ImageIcon("graphics/score.png").getImage();
    
    /**
     * 开始
     */
    public static final Image START = new ImageIcon("graphics/start.png").getImage();
    
}
