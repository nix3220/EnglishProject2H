import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

/**
 * 
 */

/**
 * @author s019343
 *
 */
public class Player extends SimulatedObject{
	
	int vX = 0;
	int vY = 0;
	public boolean canMove = true;
	
	public Player(Transform t) {
		super(true, Color.green);
		this.transform = t;
	}
	
	public Point getPosition() {
		return new Point(transform.position.x, transform.position.y);
	}
	
	public Point getCenteredPosition() {
		return new Point(transform.position.x + (transform.scale.width/2), transform.position.y + (transform.scale.height/2));
	}
	
	public Transform getTransform() {
		return transform;
	}
	
	public void move(int x, int y, Scene scene) {
		if(canMove) {
			transform.position.x += x;
			transform.position.y += y;
			scene.updateCamera(x, y);
		}
	}

	@Override
	public void tick(Scene scene) {
		// TODO Auto-generated method stub
		Pair<Boolean, SimulatedObject> pair = scene.isColliding(this);
		if(!pair.first) {
			if(scene.input.keyDown(KeyEvent.VK_A)) {
				move(-3, 0, scene);
			}
			if(scene.input.keyDown(KeyEvent.VK_D)) {
				move(3, 0, scene);
			}
		}
		else {
			Point other = pair.second.transform.position;
			if(other.x < transform.position.x) {
				move(3, 0, scene);
			}
			else {
				move(-3, 0, scene);
			}
		}
		
		if(transform.position.y + transform.scale.height-25 <= scene.panel.getHeight()) {
			//move(0, 2, scene);
		}
	}
	

	@Override
	public void render(Graphics2D g) {
		// TODO Auto-generated method stub
		g.setColor(color);
		g.fillRect(transform.position.x, transform.position.y, transform.scale.width, transform.scale.height);
	}
}
