package game.GameState;

import game.InputHandler.InputHandler;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class HelpState extends GameState{
	
	private final Font font = rm.deriveFont(f, Font.BOLD, SIZE_14);

	private static final String[] text = new String[]{
		"This is the help screen.",
		"This game is a remake of the 'Snake' game, made famous in old mobile devices.",
		"In order to play this game you can use several Keys of your keyboard: ",
		"To control the snake, you can either use UP, DOWN, LEFT, RIGHT, or maybe, if you feel more comfortable, WASD.",
		"To pause the game you can press ESC at any time you want and the game will stop.",
		"There is a Higscores file in the game folder, which you can't edit. This saves your highscores.",
		"If you want to show your friends your highscores, you just have to pass them that file.",
		"Replace their highscores file and they will be able to see your highscores from their 'Highscores' option!",
		"Play with friends and have fun beating each other!",
		"Press ESC to return to the main menu."
		
	};
	
	
	public HelpState(GameStateManager gsm){
		super(gsm);
	}

	public void init(){ }
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0,0, width, height);
		
		g.setFont(font);
		g.setColor(COLOR);
		for(int i = 0; i < text.length; i++){
			if(i == text.length -1){
				g.drawString(text[i], width / 60, height - (height / 60));
				continue;
			}
			g.drawString(text[i], width / 60 , height /16 + (i*height / 16));
		}
	}	

	public void update() {
		handleInput();
	}
	public void handleInput() {
		if(InputHandler.isPressed(InputHandler.GAME_KEY_ESCAPE)) gsm.setState(GameStateManager.MENUSTATE);
	}

	@Override
	public boolean isPaused() {
		return false;
	}
	
}
