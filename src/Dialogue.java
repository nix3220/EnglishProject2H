/**
 * 
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
/**
 * @author s019343
 *
 */
public class Dialogue{
	public String author;
	private Queue<String> dialogue = new LinkedList<String>();
	public Interaction interaction = null;
	
	public Dialogue(String author, List<String> items) {
		this.author = author;
		for(int i = items.size()-1; i >= 0; i--) {
			String s = items.get(i);
			dialogue.offer(s);
		}
	}
	
	public Dialogue(String author, String... strings) {
		this(author, Arrays.asList(strings));
	}
	
	public Dialogue(String filePath) {
		File f = new File(filePath);
		try {
			Scanner s = new Scanner(f);
			int lines = 0;
			while(s.hasNext()) {
				String line = s.nextLine();
				if(lines == 0) {
					this.author = line;
				}
				else {
					dialogue.offer(line);
				}
				lines++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String next() {
		return dialogue.poll();
	}
	
	public String peek() {
		return dialogue.peek();
	}
}
