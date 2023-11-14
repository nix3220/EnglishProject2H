import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 
 */

/**
 * @author s019343
 *
 */

class Pair<X, Y> { 
  public X first; 
  public Y second; 
  public Pair(X first, Y second) { 
    this.first = first; 
    this.second = second; 
  } 
  
  @Override
  public String toString() {
	  return "(1: " + first.toString() + ", 2: " + second.toString() + ")";
  }
} 

public class Scene extends SimulatedObject{
	
	Game panel = null;
	public Point currentCameraPosition = new Point(0, 0);
	
	public Pair<Boolean, String> interactableText = new Pair<Boolean, String>(false, "object");
	Input input = null;
	
	public Scene(Game p) {
		super(false, Color.black);
		panel = p;
		input = p.input;
		//p.addKeyListener(input);
	}
	
	boolean stillCamera = false;
	
	Dialogue currentDialogue = null;
	
	
	
	public ArrayList<SimulatedObject> sceneObjects = new ArrayList<>();
	
	public void addObject(SimulatedObject object) {
		sceneObjects.add(object);
	}
	
	public void addObject(int index, SimulatedObject object) {
		sceneObjects.add(index, object);
	}

	@Override
	public void tick(Scene scene) {
		// TODO Auto-generated method stub
		if(input.keyUp(KeyEvent.VK_SPACE)) {
			if(currentDialogue != null) {
				currentDialogue.next();
			}
		}
		if(currentDialogue != null) {
			player().canMove = false;
		}
		for (int i = 0; i < sceneObjects.size(); i++) {
			sceneObjects.get(i).tick(this);
		}
		checkInteractables();
		utilityTick();
	}
	
	public void checkInteractables() {
		interactableText.first = false;
		for(int i = 0; i < sceneObjects.size(); i++) {
			SimulatedObject obj = sceneObjects.get(i);
			if(obj.isInteractable()) {
				if(obj.distance(player()) < obj.interactableDistance) {
					interactableText.first = true;
					interactableText.second = obj.name;
					if(input.keyUp(KeyEvent.VK_E)) {
						obj.interact();
					}
				}
			}
		}
	}
	
	public void showDialogue(Dialogue dialogue) {
		this.currentDialogue = dialogue;
	}
	
	public void drawDialogueWindow(Graphics2D g) {
		int width = 1000;
		int height = 300;
		int x = (panel.getWidth()/2) - (width/2);
		int y = panel.getHeight()/2 - height;
		Rectangle bg = new Rectangle(x, y, width, height);
		Rectangle fg = new Rectangle(x + 20, y + 20, width - 40, height - 40);
		g.setColor(Color.green);
		g.fill(bg);
		g.setColor(Color.black);
		g.fill(fg);
		g.setColor(Color.white);
		g.setFont(new Font("arial", 40, 40));
		String currentStr = currentDialogue.peek();
		
		if(currentStr == null) {
			player().canMove = true;
			Dialogue d = currentDialogue;
			currentDialogue = null;
			if(d.interaction != null) {
				d.interaction.interact();
			}
			return;
		}
		
		String adding = "";
		List<String> lines = new ArrayList<String>();
		int numFittingChars = (width - 80) / 20;
		int j = 0;
		
		while(j < currentStr.length()) {
			for (int i = 0; i < numFittingChars; i++) {
				if(j < currentStr.length()) {
					adding += currentStr.charAt(j);
					j++;
				}
			}
			lines.add(adding);
			adding = "";
		}
		
		for (int i = 0; i < lines.size(); i++) {
			g.drawString(lines.get(i), x+40, y+60+(40*i));
		}
		
		g.setColor(Color.green);
		g.setFont(new Font("arial", 20, 20));
		int strSize = g.getFontMetrics().stringWidth("Press Space to continue");
		g.drawString("Press Space to continue", x + width-strSize - 30, y + height - 30);
		
		int fs = 60;
		g.setFont(new Font("arial", fs, fs));
		g.setColor(Color.white);
		int size = g.getFontMetrics().stringWidth(currentDialogue.author);
		g.fillRect(x, y + height, size + 20, fs+10);
		g.setColor(Color.black);
		g.fillRect(x+5, y + height+5, size + 10, fs);
		g.setColor(Color.white);
		g.drawString(currentDialogue.author, x+10, y+height+fs);
	}
	
	public Pair<Boolean, SimulatedObject> isColliding(SimulatedObject so){
		Pair<Boolean, SimulatedObject> pair = new Pair<Boolean, SimulatedObject>(false, null);
		for(SimulatedObject obj : sceneObjects) {
			if(obj != so) {
				if(obj.transform.rect().intersects(so.transform.rect()) && obj.collide && so.collide) {
					pair.first = true;
					pair.second = obj;
					return pair;
				}
			}
		}
		return pair;
	}
	
	public void utilityTick() {
		input.tick();
	}
	
	public void updateCamera(int x, int y) {
			this.currentCameraPosition.x -= x;
			this.currentCameraPosition.y -= y;
	}
	
	@Override
	public void render(Graphics2D g) {
		//x++;
		//g.translate(0, 0);
		g.drawImage(createImage(), 0, 0, null);
	}
	
	public BufferedImage createImage() {
		BufferedImage img = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D)img.getGraphics();
		g.translate(this.currentCameraPosition.x, this.currentCameraPosition.y);
		for(SimulatedObject obj : sceneObjects) {
			obj.render(g);
		}
		drawUi(img.createGraphics());
		return img;
	}
	
	public void removeObject(SimulatedObject obj) {
		sceneObjects.remove(obj);
	}
	
	void drawUi(Graphics2D g) {
		if(interactableText.first) {
			g.setFont(new Font("arial", 20, 20));
			String s = interactableText.second;
			int width = g.getFontMetrics().stringWidth(s);
			
			int x = 540;
			int y = 450;
			g.setColor(Colors.white);
			g.fillRect(x-5, y-5, width+30, 60);
			g.setColor(Color.black);
			g.fillRect(x, y, width+20, 50);
			g.setColor(Color.white);
			g.drawString(s, x+10, y+30);
		}
		if(currentDialogue != null) {
			drawDialogueWindow(g);
		}
	}
	
	public Player player() {
		for(SimulatedObject obj : sceneObjects) {
			if(obj instanceof Player) {
				return (Player)obj;
			}
		}
		return null;
	}
	
	public String toString() {
		return name;
	}
}
