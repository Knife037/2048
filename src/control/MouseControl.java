package control;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import ui.Layer;

public class MouseControl extends MouseAdapter{

	/**
	 * 核心控制模块
	 */
	private CoreControl coreControl = null;
	
	/**
	 * 开始按钮图层
	 */
	private Layer layer = null;	

	/**
	 * 构造器
	 * @param coreControl 核心控制模块
	 * @param computerControl AI控制模块
	 */
	public MouseControl(CoreControl coreControl) {
		this.coreControl = coreControl;

	}
	
	public void setLayer(Layer layer) {
		this.layer = layer;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int x = e.getPoint().x;
		int y = e.getPoint().y;
		if(x >= layer.getX() && x <= layer.getX() + layer.getWidth() && y > layer.getY() && y < layer.getY() + layer.getHeight()) {
			coreControl.newGame();
		}
	}

}
