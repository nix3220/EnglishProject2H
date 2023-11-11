import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.util.Iterator;

/**
 * 
 */

/**
 * @author s019343
 *
 */
public class EngTri extends SimulatedObject{

	public Polygon tri = new Polygon();
	
	/**
	 * @param collide
	 */
	public EngTri(boolean collide, Point position, int base, int height, Color color) {
		super(collide, color);
		transform.position = position;
		tri.addPoint(transform.position.x, transform.position.y);
		tri.addPoint(transform.position.x+base, transform.position.y);
		tri.addPoint(transform.position.x+(base/2), transform.position.y-height);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void tick(Scene scene) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics2D g) {
		// TODO Auto-generated method stub
		g.setColor(color);
		g.fill(tri);
	}

}
