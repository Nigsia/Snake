package game.GameState;

import game.Main.Game;
import game.Managers.ResourceManager;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public abstract class GameState {
	
	protected GameStateManager gsm;
	protected ResourceManager rm;
	
	protected static final String C = "c";
	protected static final String O = "o";
	protected static final String FONT = "font";
	
	protected static final Color TITLE_COLOR = new Color(499730);
	protected static final Color COLOR = new Color(255,255,255);
	
	protected Font f;
	
	protected static final int height = Game.HEIGHT;
	protected static final int width = Game.WIDTH;
	
	protected static final int SIZE_12 = width / 95;
	protected static final int SIZE_14 = width / 81;
	protected static final int SIZE_16 = width / 71;
	protected static final int SIZE_20 = width / 57;
	protected static final int SIZE_32 = width / 35;
	protected static final int SIZE_48 = width / 24;
	
	protected int type;
	protected int size;
	
	public GameState(GameStateManager gsm){
		this.gsm = gsm;
		initRM();
	}
	
	public abstract void init();
	public abstract void update();
	public abstract void draw(Graphics g);
	public abstract void handleInput();
	public abstract boolean isPaused();
	
	private void initRM(){
		rm = new ResourceManager();
		rm.loadTexture("SquareBraketC.png", C);
		rm.loadTexture("SquareBraketO.png", O);
		rm.loadFont("Minecraftia.ttf", FONT);
		f = rm.getFont(FONT);
	}

}
