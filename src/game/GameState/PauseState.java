package game.GameState;

import game.InputHandler.InputHandler;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class PauseState extends GameState{
	
	private static final int width = game.Main.Game.WIDTH - game.Main.Game.SIDEBAR_WIDTH;
	
	private final Font FONT = rm.deriveFont(f, Font.BOLD, SIZE_32);
	private static final Color BG_COLOR = new Color(0,0,0,5);	
	
	public PauseState(GameStateManager gsm){
		super(gsm);
	}
	
	public void init(){}
	public void update(){ handleInput();}
	public void draw(Graphics g){
		g.setFont(FONT);
		g.setColor(BG_COLOR);
		g.fillRect(0,0, width, height);
		g.setColor(COLOR);
		g.drawString("Paused.",  width/2 , height /2);	
		}

	public boolean isPaused(){
		if(gsm.getState(GameStateManager.LEVELSTATE) != null) return gsm.getState(GameStateManager.LEVELSTATE).isPaused();
		else return false;
	}

	public void handleInput() {	
		if(InputHandler.isPressed(InputHandler.GAME_KEY_ESCAPE)) gsm.setPaused(false);	
	}

	}
