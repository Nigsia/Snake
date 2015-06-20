package game.InputHandler;

import java.awt.event.KeyEvent;

public class InputHandler {
	
	public static final int NUM_KEYS = 6;
	
	public static boolean[] keyState = new boolean[NUM_KEYS];
	public static boolean[] pKeyState = new boolean[NUM_KEYS];
	
	public static final int GAME_KEY_UP = 0;	
	public static final int GAME_KEY_DOWN = 1;
	public static final int GAME_KEY_LEFT = 2;
	public static final int GAME_KEY_RIGHT = 3;
	public static final int GAME_KEY_ENTER = 4;
	public static final int GAME_KEY_ESCAPE = 5;
	
	public static void KeyPressed(int k, boolean b){
		 if(k == KeyEvent.VK_UP ||  k == KeyEvent.VK_W)		keyState[GAME_KEY_UP] = b;
		 if(k == KeyEvent.VK_DOWN || k == KeyEvent.VK_S)	keyState[GAME_KEY_DOWN] = b;
		 if(k == KeyEvent.VK_LEFT || k == KeyEvent.VK_A) 	keyState[GAME_KEY_LEFT] = b;
		 if(k == KeyEvent.VK_RIGHT || k == KeyEvent.VK_D) 	keyState[GAME_KEY_RIGHT] = b;
		 if(k == KeyEvent.VK_ENTER)							keyState[GAME_KEY_ENTER] = b;
		 if(k == KeyEvent.VK_ESCAPE)						keyState[GAME_KEY_ESCAPE] = b;
	}
	
	public static void update(){		
		for(int i = 0; i < NUM_KEYS; i++){
			pKeyState[i] = keyState[i];
		}
	}
	
	public static boolean isPressed(int i){
		return keyState[i] && !pKeyState[i];
	}
	
	public static boolean anyKeyPressed(){
		for(int i = 0; i < NUM_KEYS; i++){
			if(keyState[i]) return true;
		}
		return false;
	}

}
