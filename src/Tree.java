import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 * 
 */

/**
 * @author s019343
 *
 */
public class Tree extends SimulatedObject{
	int speed = 6;
	Scene s;
	/**
	 * @param collide
	 * @param color
	 */
	public Tree(Scene s, int speed) {
		super(false, Colors.green);
		transform.position = new Point(2500, 570);
		transform.scale = new Dimension(150, 300);
		this.s = s;
		this.speed = speed;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void tick(Scene scene) {
		// TODO Auto-generated method stub
		transform.position.x -= speed;
		if(transform.position.x < 1150) {
			scene.addObject(0, new Tree(scene, speed));
			scene.removeObject(this);
		}
	}

	@Override
	public void render(Graphics2D g) {
		// TODO Auto-generated method stub
		g.drawImage(s.panel.getImage("tree"), transform.position.x - (transform.scale.width/2), transform.position.y - (transform.scale.height/2), transform.scale.width, transform.scale.height, null);
	}
}
