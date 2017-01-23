package ui;

import control.KeyControl;
import control.MouseControl;
import dto.DtoGame;

import javax.swing.JPanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/26.
 */
@SuppressWarnings("serial")
public class JPanelGame extends JPanel {

	/**
	 * 数据访问对象
	 */
    private DtoGame dto = null;

    /**
     * 要绘制的层
     */
    private List<Layer> layers = new ArrayList<Layer>();

    /**
     * 构造器
     * @param keyControl 键盘控制器
     * @param mouseControl 鼠标控制器
     * @param dto 数据访问对象
     */
    public JPanelGame(KeyControl keyControl, MouseControl mouseControl, DtoGame dto) {    	     
    	this.dto = dto;
    	// 初始化图层对象
        initializedLayers();
        // 添加键盘控制
        this.addKeyListener(keyControl);     
        // 添加鼠标控制
        this.addMouseListener(mouseControl);
        // 添加鼠标控制层
        mouseControl.setLayer(layers.get(1));
    }

    private void initializedLayers() {
    	// 创建各个显示层
        layers.add(new LayerBackground(0,0, 368, 448));
        layers.add(new LayerStart(16, 16, 160, 64));
        layers.add(new LayerScore(192, 16, 160, 64, this.dto));
        layers.add(new LayerGame(16, 96, 336, 336, this.dto));
        layers.add(new LayerLose(16, 96, 336, 336, this.dto));
        
    }


	@Override
    public void paintComponent(Graphics g) {
		
        for (Layer layer : layers) {
        	layer.paint(g);
        }
        this.requestFocus();
    }

	/**
	 * 绘制移动动画 为了保持高校绘图 整个move几乎未被拆开
	 * @param g0 
	 * @param offest 目的坐标映射
	 */
    public void move(Graphics g0, Point[][] offest) {
        // 创建缓冲画板
        Image img = this.createImage(336, 336);
        // 获得缓冲画板的设备上下文接口
        Graphics g = img.getGraphics();
        // 设置FPS     
        int fps = 10;
        // 设置延时
        int interval = 5;
        // 移动间距
        int[][] spanX = new int[DtoGame.COL][DtoGame.ROW];
        int[][] spanY = new int[DtoGame.COL][DtoGame.ROW];
        // 获得游戏地图
        int[][] gameMap = dto.getGameMap();
        // 获得移动间距
        getSpan(spanX, spanY, offest, fps);

        // 循环绘制图像，以达到动画效果
        for (int i = 0; i < fps; i++) {
            // 设置延迟
            try {
                Thread.sleep(interval);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 绘制大背景
            g.drawImage(Img.BACKGROUND1, 0, 0, 336, 336, 0, 0, 1, 1, null);

            // 绘制背景方块
            for (int col = 0; col < DtoGame.COL; col++) {
                for (int row = 0; row < DtoGame.ROW; row++) {
                	
                	// 在被移动方块的原位置绘制空方块
                    if((offest[col][row].x != col) || (offest[col][row].y != row)) {                    	
                    	g.drawImage(Img.BLOCK,
                        		col * 80 + 16,
                        		row * 80 + 16,
                        		col * 80 + 80,
                        		row * 80 + 80,
                                0, 0, 64, 64, null
                        );
                        continue;
                    }
                    
                    // 绘制未移动方块
                    int numIndex = gameMap[col][row];             
                    g.drawImage(Img.BLOCK,
                    		col * 80 + 16,
                            row * 80 + 16,
                            col * 80 + 80,
                            row * 80 + 80,
                            numIndex * 64,
                            0,
                            (numIndex + 1) * 64,
                            64,
                            null
                    );
                }
            }

            // 绘制被移动方块
            for (int col = 0; col < DtoGame.COL; col++) {
                for (int row = 0; row < DtoGame.ROW; row++) {
                    if((offest[col][row].x == col) && (offest[col][row].y == row)) {
                        continue;
                    }
                    
                    int numIndex = gameMap[col][row];
                    int xOffest = col * 80 + spanX[col][row] * (i + 1);
                    int yOffest = row * 80 + spanY[col][row] * (i + 1);                                    
                    
                    g.drawImage(Img.BLOCK,
                            xOffest + 16,
                            yOffest + 16,
                            xOffest + 80,
                            yOffest + 80,
                            numIndex * 64,
                            0,
                            (numIndex + 1) * 64,
                            64,
                            null
                    );

                }
            }
            
            // 将缓冲区的图像绘制到屏幕
            g0.drawImage(img, 16, 96, null);
        }                      
    }
    
    /**
     * 获得每次移动间距
     * @param spanX 横向间距 
     * @param spanY 纵向间距
     * @param offest 目的坐标映射
     */
    private void getSpan(int[][] spanX, int[][] spanY, final Point[][] offest, int fps) {
    
        for (int col = 0; col < DtoGame.COL; col++) {
            for (int row = 0; row < DtoGame.ROW; row++) {
                Point point = offest[col][row];
                spanX[col][row] = (point.x - col) * 80 / fps;
                spanY[col][row] = (point.y - row) * 80 / fps;
            }
        }
    }
}
