package control;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by Administrator on 2016/11/26.
 */
public class KeyControl extends KeyAdapter {	

	private ComputerControl computerControl = null;;
	
	private CoreControl coreControl = null;

	public void setComputerControl(ComputerControl computerControl) {
		this.computerControl = computerControl;
		
	}
	
	public void setCoreControl(CoreControl coreControl) {
		this.coreControl = coreControl;
	}
	
    @Override
    public void keyPressed(KeyEvent e) {
        // TODO 反射机制
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP: coreControl.keyUp(); break;
            case KeyEvent.VK_DOWN: coreControl.keyDown(); break;
            case KeyEvent.VK_LEFT: coreControl.keyLeft(); break;
            case KeyEvent.VK_RIGHT: coreControl.keyRight(); break;
            case KeyEvent.VK_A: computerControl.AI();break;
            default: break;
        }
    }

}
