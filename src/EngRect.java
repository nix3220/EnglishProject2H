import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

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
	public EngRect(Transform t, Color c) {
		super(true, c);
		this.transform = t;
		this.color = c;
	}
	
	public EngRect(Transform t, Image i, boolean collide, Game p) {
		super(collide, Color.BLACK);
		this.transform = t;
		icon = i;
		this.p = p;
	}
	
	public EngRect(Transform t, Color c, boolean collide) {
		super(collide, c);
		this.transform = t;
		this.color = c;
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
	
}
