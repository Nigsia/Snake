package game.GameState;

import game.InputHandler.InputHandler;
import game.Managers.SaveHandler;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class DeadState extends GameState {
	
	private boolean newHighScore;
	private char[][] newName;
	private int X = 0, Y = 0;
	private char[] name;
	
	BufferedImage open, close;
	
	private long newScore;	
	
	private Font TITLE_FONT = rm.deriveFont(f, Font.BOLD, 32);
	private Font font = rm.deriveFont(f, Font.BOLD, 20);

	public DeadState(GameStateManager gsm) {
		super(gsm);
		init();
	}

	public void init() {	
		open = rm.getTexture("o");
		close = rm.getTexture("c");
		try{
			newScore = SaveHandler.gd.getTempScore();
			newHighScore = SaveHandler.gd.isHighScore(newScore);
		}catch(Exception e){e.printStackTrace();}
		
		if(newHighScore){
			
			newName = new char[][] { 
					{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'I', 'J'},
					{'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S'},
					{'T', 'U', 'V', 'W', 'Y', 'X', 'Z', ' ', '*'},
					{'.', ',', '-', '!', '?', '(', ')', '/', '&'},
					{'1', '2', '3', '4', '5', '6', '7', '8', '9'}
			};
			
			name = new char[]  {'_', '_', '_', '_', '_', '_', '_', '_', '_', '_' };
			
		}else{
			gsm.setState(GameStateManager.MENUSTATE);
		}
		
	}

	public void update() {	
		
		handleInput();
		
	}

	public void draw(Graphics g) {	
		
		g.setColor(Color.BLACK);
		g.fillRect(0,0,width, height);
		g.setFont(TITLE_FONT);
		g.setColor(COLOR);
		g.drawString("GAME OVER", (width - (g.getFontMetrics().stringWidth("GAME OVER"))) /2, height / 6);
		String s = "New High Score: " + newScore;
		g.setFont(font);
		g.drawString(s, (width - g.getFontMetrics().stringWidth(s)) /2, height /6 * 2);
		g.drawString("Please, introduce your name: ", (width - g.getFontMetrics().stringWidth("Please, introduce your name: ")) / 2, height / 12 * 5);
		for(int i = 0; i < 5; i++){
			for(int j = 0; j < 9; j++){
				g.drawString(Character.toString(newName[i][j]), (width /4) + ((width / 16) * j) , (height / 6 * 4) + ((height / 18) * i));
			}		
		}
		//IF WE ARE IN THE ENTER OPTION
		if(X < 9){
		g.drawImage(open, (((width / 2) - (width / 4)) + ((width / 16) * X)) - /*CHANGE HERE*/ (width / 63), ((height / 6 * 4) + ((height / 18) * Y)) - /*CHANGE HERE*/  (height / 36), null);
		g.drawImage(close, (((width / 2) - (width / 4)) + ((width / 16) * X)) + /*CHANGE HERE*/ (width / 52), ((height / 6 * 4) + ((height / 18) * Y)) - /*CHANGE HERE*/ (height / 36), null);
		}
		//if we get into the ENTER
		else if(X == 9 && Y == 4){
			g.drawImage(open, ((width /4) + ((width / 16) * X)) - /*CHANGE HERE*/ (width / 60), ((height / 6 * 4) + ((height / 18) * Y)) - /*CHANGE HERE*/ (height / 36), null);
			g.drawImage(close, (((width / 2) - (width / 4)) + ((width / 16) * X)) + /*CHANGE HERE*/ (width / 11), ((height / 6 * 4) + ((height / 18) * Y)) - /*CHANGE HERE*/ (height / 36), null);
		}
		//We draw the name (At the start is _ _ _ ... and it changes when we introduce a value
		for(int i = 0; i < name.length; i++){
			g.drawString(Character.toString(name[i]), (((width / 2) - (width / 4)) + ((width / 16) * i)), ((height / 6 * 3) + ((height / 18))));
		}
		
		//We draw the enter thing
		g.drawString("ENTER", ((width / 2) - (width / 4)) + ((width / 16) * 9), (height / 6 * 4) + ((height / 18) * 4));
		
	}

	public void handleInput() {	
		if(Y != 4){
			if(InputHandler.isPressed(InputHandler.GAME_KEY_RIGHT)){
				if(X >= 8) X = 0;
				else X++;
			}
			if(InputHandler.isPressed(InputHandler.GAME_KEY_LEFT)){
				if(X <= 0) X = 8;
				else X--;
			}
			if(InputHandler.isPressed(InputHandler.GAME_KEY_UP)){
				if(Y <= 0) Y = 4;
				else Y--;
			}
			if(InputHandler.isPressed(InputHandler.GAME_KEY_DOWN)){
				if(Y >= 4) Y = 0;
				else Y++;
			}
		}else{
			if(InputHandler.isPressed(InputHandler.GAME_KEY_RIGHT)){
				if(X >= 9) X = 0;
				else X++;
			}
			if(InputHandler.isPressed(InputHandler.GAME_KEY_LEFT)){
				if(X <= 0) X = 9;
				else X--;
			}
			if(InputHandler.isPressed(InputHandler.GAME_KEY_UP)){
				Y = 3;
				if(X == 9) X = 8;
			}
			if(InputHandler.isPressed(InputHandler.GAME_KEY_DOWN)){
				Y = 0;
				if(X == 9) X = 8;
			}
		}
		if(InputHandler.isPressed(InputHandler.GAME_KEY_ENTER)){
			if(X == 9 && Y == 4){
				
				for(int i = 0; i < name.length; i++){
					if(Character.toString(name[i]).contains("_")){
						name[i] = ' ';
					}
				}
				
				SaveHandler.gd.addHighScore(newScore, new String(name));
				SaveHandler.save();
				gsm.setState(GameStateManager.MENUSTATE);
				
			}
			for(int i = 0; i < name.length; i++){
				if(Character.toString(name[i]).contains("_")){
					name[i] = newName[Y][X];
					break;
				}else continue;
			}
		}
		
	}

	public boolean isPaused() {
		return false;
	} 

}
