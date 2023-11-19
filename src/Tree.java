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
	int position = 2500;
	/**
	 * @param collide
	 * @param color
	 */
	public Tree(Scene s, int speed, int position) {
		super(false, Colors.green);
		int h = speed*25;
		int w = speed*50;
		transform.position = new Point(position, 720-h);
		transform.scale = new Dimension(h, w);
		this.s = s;
		this.speed = speed;
		this.position = position;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void tick(Scene scene) {
		// TODO Auto-generated method stub
		transform.position.x -= speed;
		if(transform.position.x < position-1350) {
			scene.addObject(0, new Tree(scene, speed, position));
			scene.removeObject(this);
		}
	}

	@Override
	public void render(Graphics2D g) {
		// TODO Auto-generated method stub
		g.drawImage(s.panel.getImage("tree"), transform.position.x - (transform.scale.width/2), transform.position.y - (transform.scale.height/2), transform.scale.width, transform.scale.height, null);
	}
}
