package game.Main;

import game.GameState.GameStateManager;
import game.GameState.LevelState;
import game.InputHandler.InputHandler;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener{
	
	public static final int WIDTH = Game.WIDTH;
	public static final int HEIGHT = Game.HEIGHT;
	public static final float FPS = 9F;
	private static final long FRAME_TIME = 30L;
	
	private Thread thread;
	private boolean running;
	
	private BufferedImage image;
	private Graphics g;
	private int counter;
	
	private GameStateManager gsm;
	private Clock clock;
	
	public GamePanel(){
		super();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		requestFocus();
	}
	
	public void addNotify(){
		super.addNotify();
		if(thread == null){
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
		}	
	}
	
	private void init(){
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = image.getGraphics();
		clock = new Clock(FPS);
		running = true;
			
		gsm = new GameStateManager(this);
	}

	public void run() {		
		
		init();
		clock.setPaused(true);
		
		while(running){
			long start = System.nanoTime();
			
			clock.setPaused(false);
			clock.update();
			if(clock.hasElapsedCycle()){
				update();
				draw(g);
				drawToScreen();
				if(gsm.getState(GameStateManager.LEVELSTATE) != null && gsm.isPaused() == false){
					if(counter == 9){
						LevelState ls = (LevelState) gsm.getState(GameStateManager.LEVELSTATE);
						ls.getSidebar().updateTimer();
						counter = 0;
					}else{counter++;}
				}	
			}
			long delta = (System.nanoTime() - start) / 1000000L;
				if(delta < FRAME_TIME){
					try{
						Thread.sleep(FRAME_TIME - delta);
					}catch(Exception e){e.printStackTrace();}
				}	
		}
	}
	
	private void update(){
		gsm.update();	
		InputHandler.update();
	}
	private void draw(Graphics g){
		gsm.draw(g);
	}
	
	private void drawToScreen(){
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, WIDTH, HEIGHT, null);
		g2.dispose();
	}
	
	public void keyPressed(KeyEvent e) { 
		InputHandler.KeyPressed(e.getKeyCode(), true);
	}


	public void keyReleased(KeyEvent e) {	
		InputHandler.KeyPressed(e.getKeyCode(), false);
	}

	@Override
	public void keyTyped(KeyEvent e) {}

}
