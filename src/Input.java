import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

/**
 * 
 */

/**
 * @author s019343
 *
 */
public class Input implements KeyListener{

	private List<Integer> keysDown = new ArrayList<Integer>();
	private List<Integer> keysUp = new ArrayList<Integer>();
	
	public boolean keyDown(int key) {
		return keysDown.contains(key);
	}
	
	public boolean keyUp(int key) {
		return keysUp.contains(key);
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(!keysDown.contains(e.getKeyCode())) keysDown.add(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		for(int i = 0; i < keysDown.size(); i++) {
			if(keysDown.get(i) == e.getKeyCode()) {
				keysDown.remove(i);
			}
		}
		if(!keyUp(e.getKeyCode())) {
			keysUp.add(e.getKeyCode());
		}
	}
	
	public void tick() {
		keysUp.clear();
	}
}
