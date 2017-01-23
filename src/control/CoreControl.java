package control;

import java.awt.Point;

import dto.DtoGame;
import service.ServiceGame;
import ui.JFrameGame;
import ui.JPanelGame;

/**
 * Created by Administrator on 2016/11/26.
 */
public class CoreControl {
	/**
     * JPanelGame对象
     */
    private JPanelGame panelGame = null;
    
    /**
     * 数据访问对象
     */
    private DtoGame dto = null;

    /**
     *  键盘控制对象
     */
    private KeyControl keyControl = null;
    
    /**
     *  鼠标控制对象
     */
    private MouseControl mouseControl = null;

    /**
     * 游戏服务对象
     */
    private ServiceGame serviceGame = null;
    

    /**
     * 构造方法
     */
    public CoreControl() {
    	
    	// 构造数据访问对象
        dto = new DtoGame();   
        
        // 构造电脑控制对象
        ComputerControl computerControl = new ComputerControl(this, dto);
        
        // 构造键盘控制对象
        keyControl = new KeyControl();        
        keyControl.setComputerControl(computerControl);       
        keyControl.setCoreControl(this);
        
        // 构造鼠标控制对象
        mouseControl = new MouseControl(this);
        
        //构造游戏服务对象
        serviceGame = new ServiceGame(dto);      
        
        //构造画面显示对象
        panelGame = new JPanelGame(keyControl, mouseControl, dto);    
        
        // 用JPanelGame对象作为参数构造JFrameGame
        new JFrameGame(panelGame); 
        
    }         
    
    public void newGame() {
    	dto.isLost = false;
    	dto.newDtoGame();
    	serviceGame.start();
    	panelGame.repaint();
    }
   

    /**
     * 方向键按下 上
     */
    public void keyUp() {
    	Point[][] offest = serviceGame.getUpCombine();
        panelGame.move(panelGame.getGraphics(), offest);
        nextAndUpdate(offest);      
    }

    /**
     * 方向键按下 下
     */
    public void keyDown() {
    	Point[][] offest = serviceGame.getDownCombine();
        panelGame.move(panelGame.getGraphics(), offest);    
        nextAndUpdate(offest);        
    }

    /**
     * 方向键按下 左
     */
    public void keyLeft() {
    	Point[][] offest = serviceGame.getLeftCombine();
        panelGame.move(panelGame.getGraphics(), offest); 
        nextAndUpdate(offest);
    }

    /**
     * 方向键按下 右
     */
    public void keyRight() {
    	Point[][] offest = serviceGame.getRightCombine();
        panelGame.move(panelGame.getGraphics(), offest);
        
        nextAndUpdate(offest);
    }

    private void nextAndUpdate(Point[][] offest)
    {
    	updateMap(offest);
        if (!serviceGame.nextBlock()) {  
        	dto.isLost = true;
            panelGame.removeKeyListener(keyControl);
        }      
        panelGame.repaint();        
    }
    
    /**
     * 更新地图
     * @param offest 目标位置映射
     */
    private void updateMap(Point[][] offest) {
    	int[][] gameMap = dto.getGameMap();
    	int[][] gameMap0 = new int[4][4];
        for (int col = 0; col < 4; col++) {
            for (int row = 0; row < 4; row++) {
            	if(gameMap[col][row] == 0) {
            		continue;
            	}
            	if(gameMap0[offest[col][row].x][offest[col][row].y] != 0) {
            		gameMap0[offest[col][row].x][offest[col][row].y] ++;
            		dto.score += 1 << gameMap0[offest[col][row].x][offest[col][row].y];
            	} else {
            		gameMap0[offest[col][row].x][offest[col][row].y] = gameMap[col][row];
            	}
            }
        }
        dto.setGameMap(gameMap0);
    }

}
