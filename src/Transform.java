import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * 
 */

/**
 * @author s019343
 *
 */
public class Transform {
	public Point position = new Point(0, 0);
	public Dimension scale = new Dimension(100, 100);
	/**
	 * @param position
	 * @param scale
	 */
	public Transform(Point position, Dimension scale) {
		super();
		this.position = position;
		this.scale = scale;
	}
	/**
	 * 
	 */
	public Transform() {
		// TODO Auto-generated constructor stub
	}
	
	public Rectangle rect() {
		return new Rectangle(position.x, position.y, scale.width, scale.height);
	}
}
