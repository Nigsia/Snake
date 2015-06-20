package game.Graphics;

import game.GameState.LevelState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Sidebar{
	
	private LevelState level;
	private Font FONT;
	
	private static final int width = game.Main.Game.SIDEBAR_WIDTH;
	private static final int height = game.Main.Game.HEIGHT;
	
	private static final int centerX = game.Main.Game.WIDTH - width;
	
	private long score = -1;
	private int fruitsEaten = -1, nextFruitScore = -1;
	private long sTimer, mTimer, timer;
	
	public Sidebar(LevelState ls, Font f){
		this.level = ls;
		FONT = f;
		resetSidebar();
		
	}
	
	public void resetSidebar(){
		timer = 0;
		sTimer = 0;
		mTimer = 0;
		score = level.getScore();
		fruitsEaten = level.getFruitsEaten();
		nextFruitScore = level.getNextFruitScore();
		
	}
	
	public void init() {}
	
	public void draw(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(game.Main.Game.WIDTH - width, 0, width, height);
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(game.Main.Game.WIDTH - width, 0, 10, height);
		
		g.setColor(Color.WHITE);
		g.setFont(FONT);
		if(score > -1 && fruitsEaten >-1 && nextFruitScore > -1){
			String Score = "Score: " + Long.toString(score);
			String FruitsEaten = "Fruits eaten: " + Integer.toString(fruitsEaten);
			String NextFruitsEaten = "Next fruit is worth " + Integer.toString(nextFruitScore);
			
			g.drawString(Score, centerX + (width - g.getFontMetrics().stringWidth(Score)) /2, height / 3);
			g.drawString(FruitsEaten, centerX + (width - g.getFontMetrics().stringWidth(FruitsEaten)) /2, height /2);
			g.drawString(NextFruitsEaten, centerX + (width - g.getFontMetrics().stringWidth(NextFruitsEaten)) /2, height /3 *2);
		}
		if(sTimer >= 0){
			
			String template = "00 : 00";
			
			if(sTimer < 10){
				if(mTimer < 10){
					g.drawString("0" + mTimer + " : 0" + sTimer, centerX + (width - g.getFontMetrics().stringWidth(template)) /2, 20);
				}else 
				if(mTimer >= 10){
					g.drawString(mTimer + " : 0" + sTimer, centerX + (width - g.getFontMetrics().stringWidth(template)) /2, 20); 
				}
			}else
			if(sTimer >= 10){
				if(mTimer < 10){
					g.drawString("0" + mTimer + " : " + sTimer, centerX + (width - g.getFontMetrics().stringWidth(template)) /2, 20);
				}else 
				if(mTimer >= 10){
					g.drawString(mTimer + " : " + sTimer, centerX + (width - g.getFontMetrics().stringWidth(template)) /2, 20); 
				}
			}
			
		}
	}

	
	public void update(){		
		score = level.getScore();
		fruitsEaten = level.getFruitsEaten();
		nextFruitScore = level.getNextFruitScore();
	}
	
	public void updateTimer(){
		timer++;
		sTimer = timer;
		
		if(sTimer >= 60){
			sTimer = 0;
			mTimer++;
		}
	}
	
	public void handleInput(){	}

}
