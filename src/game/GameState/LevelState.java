package game.GameState;

import game.Entities.Directions;
import game.Entities.TileType;
import game.Graphics.Board;
import game.Graphics.Sidebar;
import game.InputHandler.InputHandler;
import game.Managers.SaveHandler;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.util.LinkedList;
import java.util.Random;

public class LevelState extends GameState{
	
	private static final int MIN_SNAKE_LENGTH = 5;
	private static final int MAX_DIRECTIONS = 3;
	
	public final Font font = rm.deriveFont(f, Font.PLAIN, SIZE_14);
	
	private static final int COLUMNS = game.Graphics.Board.COLUMNS;
	private static final int ROWS = game.Graphics.Board.ROWS;
	
	private Board board;
	private Random random;
	private Sidebar sidebar;
	
	private boolean isGameOver = false;
	private boolean isPaused = false;
	
	private LinkedList<Point> snake;
	private LinkedList<Directions> directions;
	
	private long score = 0L;
	private int fruitsEaten = 0;
	private int nextFruitScore = 0;

	public LevelState(GameStateManager gsm) {
		super(gsm);
		random = new Random();
		directions = new LinkedList<>();
		snake = new LinkedList<>();
		board = new Board();
		sidebar = new Sidebar(this, font);
		resetGame();
	}

	public void init() {}

	public void update() {
		handleInput();
		sidebar.update();
		TileType collision = updateSnake();
		
		if(collision == TileType.Fruit){
			fruitsEaten++;
			score += (long) nextFruitScore;
			spawnFruit();		
		}
		if(collision == TileType.SnakeBody){
			
			try{
				SaveHandler.load();
				SaveHandler.gd.setTempScore(score);
					if(SaveHandler.gd.isHighScore(score)){
						gsm.setState(GameStateManager.DEADSTATE);
					}else gsm.setState(GameStateManager.MENUSTATE);
			}catch(Exception e){e.printStackTrace();}
			
			isGameOver = true;
			
		}
		if(collision == TileType.SnakeHead){
			nextFruitScore--;
		}
	}
	
	private TileType updateSnake(){
	
		Directions direction = directions.peekFirst();
		Point head = new Point(snake.peekFirst());
		
		switch(direction){

		case North: head.y--; break;
		case South: head.y++; break;
		case East:  head.x++; break;
		case West:  head.x--; break;
		
		}
		//Border collision
		if(head.x < 0 || head.x >= COLUMNS  || head.y < 0 || head.y >= ROWS){
			return TileType.SnakeBody;
		}
		//if the next tile is not a fruit ->
		TileType old = board.getTile(head.x, head.y);
		if(old != TileType.Fruit && snake.size() > MIN_SNAKE_LENGTH){
			Point tail = snake.removeLast();
			board.setTile(tail, null);
			old = board.getTile(head.x, head.y);
		}
		//if the next tile is not our body...
		if(old != TileType.SnakeBody){
			board.setTile(snake.peekFirst(), TileType.SnakeBody);
			snake.addFirst(head);
			board.setTile(head, TileType.SnakeHead);
			if(directions.size() > 1){
				directions.poll();
			}
		}
		
		return old;
		
	}
	
	private void resetGame(){
		
		this.isGameOver = false;
		
		Point head = new Point(COLUMNS /2, ROWS /2);
		
		snake.clear();
		snake.add(head);
		
		board.clearBoard();
		board.setTile(head, TileType.SnakeHead);
		
		directions.clear();
		directions.add(Directions.North);
		
		nextFruitScore = 0;
		score = 0;
		fruitsEaten = 0;
		spawnFruit();
	}
	
	private void spawnFruit() {
		this.nextFruitScore += 10;
		int index = random.nextInt(COLUMNS * ROWS - snake.size());
		int freeFound = -1;
		for(int x = 0; x < COLUMNS; x++){
			for(int y  = 0; y < ROWS; y++){
				TileType type = board.getTile(x, y);
				if(type == null || type == TileType.Fruit){
					if(++freeFound == index){
						board.setTile(x, y, TileType.Fruit);
						break;
					}
				}
			}
		}
	}
	
	public void draw(Graphics g) {
		board.draw(g);
		sidebar.draw(g);
	}

	public void handleInput() {
		
		if(InputHandler.isPressed(InputHandler.GAME_KEY_UP)){
			if(!isPaused && !isGameOver){
				if(directions.size() < MAX_DIRECTIONS){
					Directions last = directions.peekLast();
					if(last != Directions.South && last != Directions.North){
						directions.addLast(Directions.North);
					}
				}
			}
		}
		if(InputHandler.isPressed(InputHandler.GAME_KEY_DOWN)){
			if(!isPaused && !isGameOver){
				if(directions.size() < MAX_DIRECTIONS){
					Directions last = directions.peekLast();
					if(last != Directions.South && last != Directions.North){
						directions.addLast(Directions.South);
					}
				}
			}
		}
		if(InputHandler.isPressed(InputHandler.GAME_KEY_LEFT)){
			if(!isPaused && !isGameOver){
				if(directions.size() < MAX_DIRECTIONS){
					Directions last = directions.peekLast();
					if(last != Directions.East && last != Directions.West){
						directions.addLast(Directions.West);
					}
				}
			}
		}
		if(InputHandler.isPressed(InputHandler.GAME_KEY_RIGHT)){
			if(!isPaused && !isGameOver){
				if(directions.size() < MAX_DIRECTIONS){
					Directions last = directions.peekLast();
					if(last != Directions.East && last != Directions.West){
						directions.addLast(Directions.East);
					}
				}
			}
		}
		
		if(InputHandler.isPressed(InputHandler.GAME_KEY_ESCAPE)){
			if(!isGameOver){
				setPaused(true);
			}
		}
	}
	
	public boolean isGameOver() { return isGameOver; }
	public boolean isPaused() { return isPaused; }
	
	private void setPaused(boolean b) { gsm.setPaused(b); }
	
	public long getScore() { return score; }
	public int getFruitsEaten() { return fruitsEaten; }
	public int getNextFruitScore() { return nextFruitScore; }
	public Directions getDirection() { return directions.peekLast(); }
	public Font getFont() { return font; }
	public Sidebar getSidebar() { return sidebar; }

}
