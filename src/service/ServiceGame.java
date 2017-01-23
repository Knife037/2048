package service;

import dto.DtoGame;

import java.awt.Point;
import java.util.Random;


/**
 * Created by Administrator on 2016/11/26.
 */
public class ServiceGame {

    /**
     * 方块初始值
     */
    private static final int initValue = 1;

    /**
     * 数据传输对象
     */
    private DtoGame dto = new DtoGame();

    /**
     * 构造方法
     * @param dto 数据访问对象
     */
    public ServiceGame(DtoGame dto) {
        this.dto = dto;
        start();
        
    }
    
    public void start() {
    	nextBlock();
    }

    /**
     * 下一个方块
     */
    public boolean nextBlock() {
        if(isFull()) {
            if(isLost()) {
                return false;
            }
            return true;
        }
        Point point = getEmptyPos();
        int[][] gameMap = dto.getGameMap();
        Random r = new Random();
        int rand = r.nextInt(10);
        gameMap[point.x][point.y] = rand < 10 ? initValue : initValue << 1;
        dto.setGameMap(gameMap);
        return true;
    }
    
    
    /**
     * 检查是否失败
     * @param offest 偏移量
     * @return 是否失败
     */
    private boolean isLost() {
        int[][] gameMap = dto.getGameMap();
        
        for (int col = 0; col < DtoGame.COL; col++) {
        	for (int row = 0; row < DtoGame.ROW; row++) {
        		if(col + 1< DtoGame.COL && gameMap[col][row] == gameMap[col + 1][row]) {
        			return false;
        		}
        		if(col - 1> -1 && gameMap[col][row] == gameMap[col - 1][row]) {
        			return false;
        		}
        		if(row + 1< DtoGame.ROW && gameMap[col][row] == gameMap[col][row + 1]) {
        			return false;
        		}
        		if(row - 1> -1 && gameMap[col][row] == gameMap[col][row - 1]) {
        			return false;
        		}
        	}
        }
        return true;
    }
    
    /**
     * 判断是否满盘
     * @return 是否满盘
     */
    private boolean isFull() {
        int[][] gameMap = dto.getGameMap();
        for (int col = 0; col < DtoGame.COL; col++) {
            for (int row = 0; row < DtoGame.ROW; row++) {
                if(gameMap[col][row] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 获取一个空位置，用来存放下一个方块
     * @return 获得的空位置
     */
    private Point getEmptyPos() {
        Random r = new Random();
        int col = r.nextInt(DtoGame.COL);
        int row = r.nextInt(DtoGame.ROW);
        int[][] gameMap = dto.getGameMap();
        while (gameMap[col][row] != 0) {
            col = r.nextInt(DtoGame.COL);
            row = r.nextInt(DtoGame.ROW);
        }
        return new Point(col, row);
    }
    
    /**
     * 移动块
     */
    private void moveBlock(int[][] gameMap, int col, int row, int colTest, int rowTest) {
        int t = gameMap[col][row];
        gameMap[col][row] = 0;
        gameMap[colTest][rowTest] = t;
    }

    /**
     * 获得游戏地图的一个副本
     * @return 游戏地图副本
     */
    private int[][] getGameMapCopy() {
        int[][] gameMap = new int[DtoGame.COL][DtoGame.ROW];
        int[][] gameMapTmp = dto.getGameMap();
        for (int col = 0; col < DtoGame.COL; col++) {
            for (int row = 0; row < DtoGame.ROW; row++) {
                gameMap[col][row] = gameMapTmp[col][row];
            }
        }
        return gameMap;
    }

    /**
     * 上合并
     * @return 移动对应位置
     */
    public Point[][] getUpCombine() {
    	int[][] gameMap = getGameMapCopy();
        Point[][] offest = new Point[DtoGame.COL][DtoGame.ROW];
        
        for (int col = 0; col < DtoGame.COL; col++) {
        	for (int row = 0; row < DtoGame.ROW; row++) {
        		boolean[] isDealed = new boolean[DtoGame.ROW];
        		int rowTest = row - 1;
        		   
        		if(gameMap[col][row] == 0) {
        			offest[col][row] = new Point(col, row);        			
        			continue;
        		}
        		
        		while(rowTest > -1 && gameMap[col][rowTest] == 0) {
        			rowTest --;
        		}
        		
        		if(rowTest < 0) {
        			offest[col][row] = new Point(col, rowTest + 1);
        			moveBlock(gameMap, col, row, col, rowTest + 1);        			
        			continue;
        		}
        		
        		if(gameMap[col][row] == gameMap[col][rowTest] && (!isDealed[rowTest])) {        			
        			offest[col][row] = new Point(col, rowTest);
        			moveBlock(gameMap, col, row, col, rowTest);
        			isDealed[rowTest] = true; 
        		} else {
        			offest[col][row] = new Point(col, rowTest + 1);
        			moveBlock(gameMap, col, row, col, rowTest + 1);        			        	
        		}
        	}
        }    
        return offest;
    }

    /**
     * 下合并
     * @return 移动对应位置
     */
    public Point[][] getDownCombine() {
    	int[][] gameMap = getGameMapCopy();
        Point[][] offest = new Point[DtoGame.COL][DtoGame.ROW];
        
        for (int col = 0; col < DtoGame.COL; col++) {
        	for (int row = DtoGame.ROW - 1; row > -1; row--) {
        		boolean[] isDealed = new boolean[DtoGame.ROW];
        		int rowTest = row + 1;
        		   
        		if(gameMap[col][row] == 0) {
        			offest[col][row] = new Point(col, row);        			
        			continue;
        		}
        		
        		while(rowTest < DtoGame.ROW && gameMap[col][rowTest] == 0) {
        			rowTest ++;
        		}
        		
        		if(rowTest > DtoGame.ROW - 1) {
        			offest[col][row] = new Point(col, rowTest - 1);        	
        			moveBlock(gameMap, col, row, col, rowTest - 1);        			
        			continue;
        		}
        		
        		if(gameMap[col][row] == gameMap[col][rowTest] && (!isDealed[rowTest])) {        		
        			offest[col][row] = new Point(col, rowTest);
        			moveBlock(gameMap, col, row, col, rowTest);
        			isDealed[rowTest] = true; 
        		} else {
        			offest[col][row] = new Point(col, rowTest - 1);
        			moveBlock(gameMap, col, row, col, rowTest - 1);        			        		
        		}
        	}
        }    
        return offest;
    }

    /**
     * 左合并
     * @return 移动对应位置
     */
    public Point[][] getLeftCombine() {
    	int[][] gameMap = getGameMapCopy();
        Point[][] offest = new Point[DtoGame.COL][DtoGame.ROW];
        
        for (int row = 0; row < DtoGame.ROW; row++) {
        	for (int col = 0; col < DtoGame.COL; col++) {
        		boolean[] isDealed = new boolean[DtoGame.COL];
        		int colTest = col - 1;
        		   
        		if(gameMap[col][row] == 0) {
        			offest[col][row] = new Point(col, row);        			
        			continue;
        		}
        		
        		while(colTest > -1 && gameMap[colTest][row] == 0) {
        			colTest --;
        		}
        		
        		if(colTest < 0) {
        			offest[col][row] = new Point(colTest + 1, row);
        			moveBlock(gameMap, col, row, colTest + 1, row);        			
        			continue;
        		}
        		
        		if(gameMap[col][row] == gameMap[colTest][row] && (!isDealed[row])) {        			
        			offest[col][row] = new Point(colTest, row);
        			moveBlock(gameMap, col, row, colTest, row);
        			isDealed[colTest] = true; 
        		} else {
        			offest[col][row] = new Point(colTest + 1, row);
        			moveBlock(gameMap, col, row, colTest + 1, row);        			        	
        		}
        	}
        }    
        return offest;
    }

    /**
     * 右合并
     * @return 移动对应位置
     */
    public Point[][] getRightCombine() {
    	int[][] gameMap = getGameMapCopy();
        Point[][] offest = new Point[DtoGame.COL][DtoGame.ROW];
        
        for (int row = 0; row < DtoGame.ROW; row++) {
        	for (int col = DtoGame.ROW - 1; col > -1; col--) {
        		boolean[] isDealed = new boolean[DtoGame.COL];
        		int colTest = col + 1;
        		   
        		if(gameMap[col][row] == 0) {
        			offest[col][row] = new Point(col, row);        			
        			continue;
        		}
        		
        		while(colTest < DtoGame.COL && gameMap[colTest][row] == 0) {
        			colTest ++;
        		}
        		
        		if(colTest > DtoGame.COL - 1) {
        			offest[col][row] = new Point(colTest - 1, row);        	
        			moveBlock(gameMap, col, row, colTest - 1, row);        			
        			continue;
        		}
        		
        		if(gameMap[col][row] == gameMap[colTest][row] && (!isDealed[colTest])) {        		
        			offest[col][row] = new Point(colTest, row);
        			moveBlock(gameMap, col, row, colTest, row);
        			isDealed[colTest] = true; 
        		} else {
        			offest[col][row] = new Point(colTest - 1, row);
        			moveBlock(gameMap, col, row, colTest - 1, row);        			        		
        		}
        	}
        }    
        return offest;
    }
}
