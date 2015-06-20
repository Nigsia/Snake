package game.GameState;

import game.InputHandler.InputHandler;
import game.Managers.SaveHandler;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class HighScoresState extends GameState {
	
	private Font TITLE_FONT = rm.deriveFont(f,  Font.BOLD, SIZE_32);
	private Font font = rm.deriveFont(f, Font.BOLD, SIZE_20);
	
	private long[] highScores;
	private String[] names;
	

	public HighScoresState(GameStateManager gsm) {
		super(gsm);
		SaveHandler.load();
		highScores = SaveHandler.gd.getHighScores();
		names = SaveHandler.gd.getNames();
	}

	public void init() {	}

	public void update() {
		handleInput();
	}

	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0,0,width, height);
		g.setFont(TITLE_FONT);
		g.setColor(TITLE_COLOR);
		g.drawString("HIGHSCORES", (width - g.getFontMetrics().stringWidth("HIGHSCORES")) / 2, height / 6);
		String s;
		int w;
		g.setColor(COLOR);
		g.setFont(font);
		for(int i = 0; i < highScores.length; i++){
			s = String.format("%2d. %7s %s", i+1, highScores[i], names[i]);
			w = g.getFontMetrics().stringWidth(s); 
			g.drawString(s, (width - w)/2, (height / 6) + (50) + (40*i));
		}
		
	}

	@Override
	public void handleInput() {
		if(InputHandler.isPressed(InputHandler.GAME_KEY_ENTER) || InputHandler.isPressed(InputHandler.GAME_KEY_ESCAPE)) gsm.setState(GameStateManager.MENUSTATE);	
	}

	@Override
	public boolean isPaused() { return false; }

}
