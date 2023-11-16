import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;

/**
 * 
 */

/**
 * @author s019343
 *
 */
public class EngRect extends SimulatedObject{

	Image icon = null;
	Game p = null;
	
	/**
	 * @param sideLength
	 * @param color
	 */
	
	public EngRect(Transform t, Color c, boolean collide) {
		super(collide, c);
		this.transform = t;
		this.color = c;
	}
	
	
	public EngRect(Transform t, Color c) {
		this(t, c, false);
	}
	
	public EngRect(Transform t, Image i, boolean collide, Game p) {
		this(t, Color.black);
		icon = i;
		this.p = p;
	}
	
	public EngRect(int x, int y, int width, int height, Color c) {
		this(new Transform(new Point(x, y), new Dimension(width, height)), c);
	}
	
	public EngRect(int x, int y, int width, int height, Color c, boolean collide) {
		this(new Transform(new Point(x, y), new Dimension(width, height)), c, collide);
	}
	
	public EngRect(int x, int y, int width, int height, Image i, boolean collide, Game p) {
		this(new Transform(new Point(x, y), new Dimension(width, height)), i, collide, p);
	}
	

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public void tick(Scene scene) {
		
	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(color);
		if(icon != null) {
			g.drawImage(icon, transform.position.x, transform.position.y, transform.scale.width, transform.scale.height, null);
		}
		else g.fillRect(transform.position.x, transform.position.y, transform.scale.width, transform.scale.height);
	}


	/**
	 * 
	 */
	public void clearInteractable() {
		this.interaction = null;
	}
	
}
