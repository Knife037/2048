package control;

import java.awt.Point;

import dto.DtoGame;

public class ComputerControl {
	
	/**
	 * 数据访问对象
	 */
	private DtoGame dto = null;

	/**
	 * 核心控制模块
	 */
	public CoreControl coreControl = null;
	
	/**
	 * 构造器
	 * @param coreControl 核心控制模块
	 */
	public ComputerControl(CoreControl coreControl, DtoGame dto) {
		this.coreControl = coreControl;		
		this.dto = dto;
	}

	/**
	 * AI 程序
	 */
	public void AI() {
		
		while(!dto.isLost) {
			int max = 0;
			int maxp = 0;
			int assess = search(getGameMap(getUpCombine(getGameMapCopy())), 1, getAssessValue(dto.getGameMap()));
			if(assess > max) {
				max = assess;
				maxp = 0;
			}
			
			assess = search(getGameMap(getDownCombine(getGameMapCopy())), 1, getAssessValue(dto.getGameMap()));
			if(assess > max) {
				max = assess;
				maxp = 1;
			}
	
			assess = search(getGameMap(getLeftCombine(getGameMapCopy())), 1, getAssessValue(dto.getGameMap()));
			if(assess > max) {
				max = assess;
				maxp = 2;
			}

			assess = search(getGameMap(getRightCombine(getGameMapCopy())), 1, getAssessValue(dto.getGameMap()));
			if(assess > max) {
				max = assess;
				maxp = 3;
			}

			if(!dto.isLost) {
				switch(maxp) {
					case 0 : coreControl.keyUp();break;
					case 1 : coreControl.keyDown();break;
					case 2 : coreControl.keyLeft();break;
					case 3 : coreControl.keyRight();break;
					default : break;
				}
			}
		}
		
	}
	
	// 搜索算法
	public int search(int[][] gameMap, int deep, int score) {			
		if(deep > 1) {
			return getAssessValue(gameMap);
		}
		
		if(score == getAssessValue(gameMap)) {
			return 0;
		}
		
		int max = 0;
		int assess = search(getGameMap(getUpCombine(getGameMapCopy(gameMap))), deep + 1, score);
		if(assess > max) {
			max = assess;
		}
		assess = search(getGameMap(getDownCombine(getGameMapCopy(gameMap))), deep + 1, score);
		if(assess > max) {
			max = assess;
		}
				
		assess = search(getGameMap(getLeftCombine(getGameMapCopy(gameMap))), deep + 1, score);
		if(assess > max) {
			max = assess;
		}
		
		assess = search(getGameMap(getRightCombine(getGameMapCopy(gameMap))), deep + 1, score);
		if(assess > max) {
			max = assess;
		}
		
		return max;	
		
	}
	/**
	 * 获得评估值
	 * @param gameMap 游戏地图
	 * @return 评估值
	 */
	private int getAssessValue(int[][] gameMap) {
		int assess = 0;
		for (int col = 0; col < 4; col++) {
			for (int row = 0; row < 4; row++) {
				if(gameMap[col][row] != 0) {
					assess ++;
				}
			}
		}
		return 16 - assess;
	}
	
	/**
	 * 将目标位置映射转换为坐标
	 * @param offest 映射数组
	 * @return 游戏地图
	 */
	private int[][] getGameMap(Point[][] offest) {
		int[][] gameMap = dto.getGameMap();
		int[][] gameMap0 = new int[4][4];
        for (int col = 0; col < 4; col++) {
            for (int row = 0; row < 4; row++) {
            	if(gameMap[col][row] == 0) {
            		continue;
            	}
            	if(gameMap0[offest[col][row].x][offest[col][row].y] != 0) {
            		gameMap0[offest[col][row].x][offest[col][row].y] ++;            		
            	} else {
            		gameMap0[offest[col][row].x][offest[col][row].y] = gameMap[col][row];
            	}
            }
        }
        return gameMap0;
	}
	
	/**
     * 上合并
     * @return 移动对应位置
     */
    public Point[][] getUpCombine(int[][] gameMap) {
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
    public Point[][] getDownCombine(int[][] gameMap) {
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
    public Point[][] getLeftCombine(int[][] gameMap) {    	
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
    public Point[][] getRightCombine(int[][] gameMap) {
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
    
    /**
     * 获得游戏地图的一个副本
     * @param gameMap2 
     * @return 游戏地图副本
     */
    private int[][] getGameMapCopy() {
        int[][] gameMap = new int[dto.COL][dto.ROW];
        int[][] gameMapTmp = dto.getGameMap();
        for (int col = 0; col < dto.COL; col++) {
            for (int row = 0; row < dto.ROW; row++) {
                gameMap[col][row] = gameMapTmp[col][row];
            }
        }
        return gameMap;
    }
    
    /**
     * 获得游戏地图的一个副本
     * @param gameMap2 
     * @return 游戏地图副本
     */
    private int[][] getGameMapCopy(int[][] gameMapTmp) {
    	int[][] gameMap = new int[4][4];
        for (int col = 0; col < dto.COL; col++) {
            for (int row = 0; row < dto.ROW; row++) {
                gameMap[col][row] = gameMapTmp[col][row];
            }
        }
        return gameMap;
    }
    
    /**
     * 移动块
     */
    private void moveBlock(int[][] gameMap, int col, int row, int colTest, int rowTest) {
        int t = gameMap[col][row];
        gameMap[col][row] = 0;
        gameMap[colTest][rowTest] = t;
    }
	
}
