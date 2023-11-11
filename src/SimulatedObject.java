import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 * 
 */

/**
 * @author s019343
 *
 */
public abstract class SimulatedObject {
	
	public String name;
	public int interactableDistance = 10;
	public Interaction interaction = null;
	public Color color;
	public boolean collide = false;
	public Transform transform = new Transform();
	
	public SimulatedObject(boolean collide, Color color) {
		this.collide = collide;
		this.color = color;
	}
	
	public boolean isInteractable() {
		return interaction != null;
	}
	
	public void addInteractable(Interaction interaction, int distance, String name) {
		this.interaction = interaction;
		this.interactableDistance = distance;
		this.name = name;
	}
	
	public void interact() {
		if(isInteractable()) {
			interaction.interact();
		}
	}
	
	public double distance(SimulatedObject other) {
		int x = other.transform.position.x - transform.position.x;
		int y = other.transform.position.y - transform.position.y;
		Point dir = new Point(x, y);
		return Math.sqrt(dir.x*dir.x + dir.y*dir.y);
	}
	
	public abstract void tick(Scene scene);
	public abstract void render(Graphics2D g);
}

@FunctionalInterface
interface Interaction{
	void interact();
}