import java.awt.*;

public class EngText extends SimulatedObject{

	public String text;
	public int fontSize;
	
	public EngText(int x, int y, Color color, String text, int fontSize) {
		super(false, color);
		this.text = text;
		this.fontSize = fontSize;
		this.transform = new Transform(new Point(x, y), new Dimension(1280, 720));
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
		g.setFont(new Font("arial", 10, fontSize));
		g.drawString(text, transform.position.x, transform.position.y);
	}

}
