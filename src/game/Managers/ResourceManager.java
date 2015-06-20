package game.Managers;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class ResourceManager {

	private HashMap<String, BufferedImage> tex;
	BufferedImage img;
	
	private HashMap<String, Font> font;
	Font f;
	GraphicsEnvironment ge;
	
	public ResourceManager(){

		tex = new HashMap<String, BufferedImage>();
		font = new HashMap<String, Font>();
		ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		
	}
	
	public void loadFont(String path, String key){
		
		try{		
			f = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/" + path));	
			ge.registerFont(f);
			//set the default size (just in case)
			f.deriveFont(Font.PLAIN, 20);
		}catch (Exception e) { e.printStackTrace(); }
		
		font.put(key, f);	
	}
	
	public void disposeFont(String key){
		if(font.get(key) != null) font.clear();
	}
	
	public Font getFont(String key){
		return font.get(key);
	}
	//We derive directly from the font on the HashMap
	public Font deriveFont(String key, int style, int size){
		return getFont(key).deriveFont(style, size);
	}
	//We derive the given font (we can instantiate the font, so we have a copy of it and we can make changes without modifying the other ones)
	public Font deriveFont(Font fo, int style, int size){
		return fo.deriveFont(style, size);
	}
	
	public void loadTexture(String path, String key){
		
		try{
			img = ImageIO.read(getClass().getResourceAsStream("/" + path));
		}catch(Exception e) { e.printStackTrace(); }
		
		tex.put(key, img);
		
	}
	
	public BufferedImage getTexture(String key){
		return tex.get(key);
	}
	
	public void disposeTexture(String key){
	
		if(tex.get(key) != null) tex.clear();
		
	}
	
}
