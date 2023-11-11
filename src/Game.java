import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.*;

/**
 * 
 */

/**
 * @author s019343
 *
 */
public class Game extends JPanel implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	
	public List<SimulatedObject> allObjects = new ArrayList<SimulatedObject>();
	
	Timer timer;
	Player player = new Player(new Transform(new Point(580, 510), new Dimension(100, 100)));
	Scene currentScene = null;
	boolean needRepaint = false;
	Input input = new Input();
	
	private boolean collectedShoes = false;
	
	public Game(Dimension size, int delay) {
		this.setPreferredSize(size);
		timer = new Timer(delay, this);
		timer.start();
		loadScene(houseScene());
		this.setFocusable(true);
		this.setBackground(Color.black);
	}
	
	public Image getImage(String imageName) {
		return getImage(imageName, "png");
	}
	
	public Image getImage(String imageName, String extension) {
		ClassLoader cldr = this.getClass().getClassLoader();
		String imagePath = "resources/"+imageName+"."+extension;
		URL imageURL = cldr.getResource(imagePath);
		ImageIcon icon = new ImageIcon(imageURL);
		Image img = icon.getImage();
		return img;
	}
	
	public void loadScene(Scene scene) {
		if(currentScene != null) scene.currentCameraPosition = currentScene.currentCameraPosition;
		currentScene = scene;
		resetPlayer();
	}
	
	public Scene houseScene() {
		Scene defaultScene = new Scene(this);
		
		//bg
		defaultScene.addObject(new EngRect(new Transform(new Point(20, 350), new Dimension(1980, 400)), Colors.DARK_GRAY, false));
		
		defaultScene.addObject(new EngRect(new Transform(new Point(1000, 400), new Dimension(150, 150)), getImage("mirror"), false, this));
		defaultScene.addObject(new EngRect(new Transform(new Point(926, 520), new Dimension(300, 200)), getImage("dresser"), false, this));
		
		EngRect shoes = new EngRect(new Transform(new Point(1200, 660), new Dimension(60, 60)), getImage("shoe"), false, this);
		shoes.addInteractable(() -> {
			currentScene.removeObject(shoes);
			collectedShoes = true;
		}, 200, "Press E to pickup the shoes");
		defaultScene.addObject(shoes);
		
		//Player
		defaultScene.addObject(player);
		//floor
		defaultScene.addObject(new EngRect(new Transform(new Point(0, 720), new Dimension(2000, 100)), Colors.light_brown));
		
		//ceiling
		defaultScene.addObject(new EngRect(new Transform(new Point(0, 320), new Dimension(2000, 30)), Colors.light_brown));
		
		//wall
		defaultScene.addObject(new EngRect(new Transform(new Point(0, 350), new Dimension(30, 170)), Colors.light_brown));
		defaultScene.addObject(new EngRect(new Transform(new Point(1970, 350), new Dimension(30, 170)), Colors.light_brown));
		
		//locked door
		EngRect locked = new EngRect(new Transform(new Point(0, 520), new Dimension(20, 200)), Colors.brown);
		locked.addInteractable(() -> {defaultScene.showDialogue(new Dialogue("Me", "It's locked"));}, 110, "The door is locked");
		defaultScene.addObject(locked);
		
		//Roofish
		defaultScene.addObject(new EngTri(false, new Point(0, 320), 2000, 300, Color.orange));
		
		
		
		//Open door
		EngRect door = new EngRect(new Transform(new Point(1980, 520), new Dimension(20, 200)), Colors.brown);
		door.addInteractable(() -> openDoor(), 200, "Press E to open door");
		defaultScene.addObject(door);
		
		
		defaultScene.showDialogue(new Dialogue("src/dialogues/mainSceneHallway"));
		return defaultScene;
	}
	
	public Scene carScene() {
		Scene scene = new Scene(this);
		scene.addObject(new Tree(scene, 3));
		scene.addObject(new Tree(scene, 5));
		scene.addObject(new Tree(scene, 7));
		scene.addObject(player);
		scene.addObject(new EngRect(new Transform(new Point(0, 720), new Dimension(200, 100)), Colors.LIGHT_GRAY));
		scene.addObject(new EngRect(new Transform(new Point(200, 720), new Dimension(3000, 100)), Colors.DARK_GRAY));
		scene.addObject(new EngRect(new Transform(new Point(-1000, 300), new Dimension(1000, 520)), Colors.light_brown));
		scene.addObject(new EngTri(false, new Point(-1000, 300), 1000, 300, Color.orange));
		
		EngRect door = new EngRect(new Transform(new Point(-20, 520), new Dimension(20, 200)), Colors.brown);
		door.addInteractable(() -> {}, 120, "I should get in the car");
		scene.addObject(door);
		
		EngRect car = new EngRect(new Transform(new Point(400, 520), new Dimension(400, 200)), getImage("car"), true, this);
		car.addInteractable(() -> {
			player.move(1500, -50, scene);
			player.canMove = false;
			Dialogue d = new Dialogue("src/dialogues/radio");
			scene.showDialogue(d);
			d.interaction = () -> {
				Dialogue meTalk = new Dialogue("src/dialogues/radioResponse");
				meTalk.interaction = () -> loadScene(houseScene());
				currentScene.showDialogue(meTalk);
			};
		}, 150, "Press E to get in the car");
		scene.addObject(car);
		
		EngRect carOnRoad = new EngRect(new Transform(new Point(1600, 520), new Dimension(400, 200)), getImage("car"), false, this);
		scene.addObject(carOnRoad);
		return scene;
	}
	
	public void resetPlayer() {
		player.move(-player.transform.position.x+100, 620-player.transform.position.y, currentScene);
	}
	
	public void openDoor() {
		if(collectedShoes) {
			System.out.println("Okay for store");
			loadScene(carScene());
		}
		else {
			currentScene.showDialogue(new Dialogue("Me", "I need to put on my shoes first"));
		}
	}
	
	public void paintComponent(Graphics g) {
		//super.paintComponent(g);
		Graphics2D g2D = (Graphics2D)g;
		g2D.drawImage(getImage("sky", "jpeg"), 0, 0, null);
		if(currentScene != null) currentScene.render(g2D);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(currentScene != null) {
			currentScene.tick(currentScene);
		}
		this.repaint();
	}
	
}
