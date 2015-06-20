package game.GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import game.InputHandler.*;

public class MenuState extends GameState {
	
	private final Font FONT = rm.deriveFont(f, Font.BOLD, SIZE_32);
	private final Font TITLE_FONT = rm.deriveFont(f, Font.BOLD, SIZE_48);
	
	private static final int width = game.Main.Game.WIDTH;
	private static final int height =  game.Main.Game.HEIGHT;
	
	private static final String[] names = new String[]{"Start", "Highscores", "Help", "Exit"};
	
	private static final short START = 0;
	private static final short HIGHSCORES = 1;
	private static final short HELP = 2;
	private static final short EXIT = 3;
	
	private int currentSelection;

	public MenuState(GameStateManager gsm) {
		super(gsm);
		currentSelection = 0;
	}
	
	public void init(){ 	}
	public void draw(Graphics g){
		
		g.setColor(Color.BLACK);
		g.fillRect(0,0, width, height);
		
		g.setFont(TITLE_FONT);
		g.setColor(TITLE_COLOR);
		g.drawString("SNAKE", width/2 - g.getFontMetrics().stringWidth("SNAKE") /2, (height / 9));
		
		g.setFont(FONT);
		g.setColor(COLOR);
		g.drawString(names[START], width / 2 - g.getFontMetrics().stringWidth(names[START]) /2, (height/5));
		g.drawString(names[HIGHSCORES], width /2 - g.getFontMetrics().stringWidth(names[HIGHSCORES]) /2, (height/5)*2);
		g.drawString(names[HELP], width / 2 - g.getFontMetrics().stringWidth(names[HELP]) /2, (height/5) * 3);
		g.drawString(names[EXIT], width / 2 - g.getFontMetrics().stringWidth(names[EXIT]) /2, (height/5) *4);
		
			if(currentSelection == 0){				g.setColor(TITLE_COLOR); g.drawString(names[START], width / 2 - g.getFontMetrics().stringWidth(names[START]) /2, (height/5));}
			else if(currentSelection == 1){			g.setColor(TITLE_COLOR); g.drawString(names[HIGHSCORES], width / 2 - g.getFontMetrics().stringWidth(names[HIGHSCORES]) /2, (height/5) * 2);}
			else if(currentSelection == 2){			g.setColor(TITLE_COLOR); g.drawString(names[HELP], width / 2 - g.getFontMetrics().stringWidth(names[HELP]) /2, (height/5) * 3);}
			else if(currentSelection == 3){			g.setColor(TITLE_COLOR); g.drawString(names[EXIT], width / 2 - g.getFontMetrics().stringWidth(names[EXIT]) /2, (height/5) *4);}
			
	}
			
	public void update() { 
		handleInput();	
		
	}
	
	public void handleInput(){
		
		if(InputHandler.isPressed(InputHandler.GAME_KEY_ENTER)) select();
		else if(InputHandler.isPressed(InputHandler.GAME_KEY_DOWN)) {
			currentSelection++;
			if(currentSelection > 3) currentSelection = 0;
		}
		else if(InputHandler.isPressed(InputHandler.GAME_KEY_UP)) {
			currentSelection--;
			if(currentSelection < 0) currentSelection = 3;
		}	
	}
	
	private void select(){
		if(currentSelection == 0) gsm.setState(GameStateManager.LEVELSTATE);
		if(currentSelection == 1) gsm.setState(GameStateManager.HIGHSCORES);
		if(currentSelection == 2) gsm.setState(GameStateManager.HELPSTATE);
		if(currentSelection == 3) System.exit(0);
		
	}

	@Override
	public boolean isPaused() {
		return false;
	}

}
