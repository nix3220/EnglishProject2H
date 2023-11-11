import java.awt.Dimension;

import javax.swing.JFrame;

/**
 * 
 */

/**
 * @author s019343
 *
 */
public class Main {
	public static void main(String[] args) {
		new Frame(new Dimension(1280, 720));
	}
}

class Frame extends JFrame{

	private static final long serialVersionUID = 1L;
	
	public Frame(Dimension size) {
		this.setFocusable(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setPreferredSize(size);
		Game p = new Game(size, 10);
		this.add(p);
		this.pack();
		p.addKeyListener(p.currentScene.input);
		p.requestFocus();
	}
}