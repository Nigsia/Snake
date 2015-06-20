package game.GameState;

import game.Main.GamePanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class GameStateManager {
	
	private GameState[] gameState;
	public int currentState;
	
	private boolean isPaused;
	
	public static final int NUM_GAMESATES = 6;
	public static final int MENUSTATE = 0;
	public static final int LEVELSTATE = 1;
	public static final int HELPSTATE = 2;
	public static final int DEADSTATE = 3;
	public static final int PAUSESTATE = 4;
	public static final int HIGHSCORES = 5;
	
	public GameStateManager(GamePanel gp){
		
		isPaused = false;
		
		gameState = new GameState[NUM_GAMESATES];
		
		gameState[PAUSESTATE] = new PauseState(this);
		
		currentState = MENUSTATE;
		loadState(currentState);
		
	}
	
	private void loadState(int state){
		if(state == MENUSTATE) gameState[MENUSTATE] = new MenuState(this);
		if(state == LEVELSTATE) gameState[LEVELSTATE] = new LevelState(this);
		if(state == HELPSTATE) gameState[HELPSTATE] = new HelpState(this);
		if(state == DEADSTATE) gameState[DEADSTATE] = new DeadState(this);
		if(state == HIGHSCORES) gameState[HIGHSCORES] = new HighScoresState(this);
	}
	
	public void setState(int state){
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
	}
	
	private void unloadState(int state){
		gameState[state] = null;
	}
	
	public void setPaused(boolean b) { isPaused = b; }
	 
	public void update() {
		
		if(isPaused){
			gameState[PAUSESTATE].update(); return;
		}else
		if(gameState[currentState] != null){
			gameState[currentState].update();
		}
		
	}
	
	public GameState getState(int State) { 
		if(State > NUM_GAMESATES){
			return gameState[MENUSTATE] = new MenuState(this);
		}
		return gameState[State];
	}
	
	public void draw(Graphics g) {
		
		if(isPaused){
			gameState[PAUSESTATE].draw(g);
			return;
		}else
		if(gameState[currentState] != null) {
			gameState[currentState].draw(g);
		}
		else {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.WIDTH);
			g.setFont(new Font("Arial", Font.PLAIN, 20));
			g.setColor(Color.WHITE);
			g.drawString("Something went wrong.", GamePanel.WIDTH /2, GamePanel.HEIGHT /2);
		}
	}
	
	public boolean isPaused() { return isPaused; }
}
