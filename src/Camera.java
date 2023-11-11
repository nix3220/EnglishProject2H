import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 * 
 */

/**
 * @author s019343
 *
 */
public class Camera {
	public Point currentPosition = new Point(0,0);
	public Point distance;
	Dimension size;
	
	public Camera(Dimension size) {
		this.size = size;
	}
	
	public void centerOn(Point p) {
		int distanceX = p.x - currentPosition.x;
		int distanceY = p.y - currentPosition.y;
		//currentPosition.x;
		distance = new Point(distanceX, distanceY);
	}
	
	public Point centeredPosition() {
		return new Point(currentPosition.x - size.width/2, currentPosition.y - size.height/2);
	}
	
	public void centerOn(SimulatedObject obj) {
		centerOn(obj.transform.position);
	}
	
	public void tick() {
		distance = new Point(0, 0);
	}
}
