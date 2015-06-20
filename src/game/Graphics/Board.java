package game.Graphics;

import game.Entities.TileType;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Board{
	
	private TileType[] tiles;
	
	private static final int height = game.Main.Game.HEIGHT;
	private static final int width = game.Main.Game.WIDTH - game.Main.Game.SIDEBAR_WIDTH;
	private static final int TILE_SIZE = 20;
	public static final int COLUMNS = width / TILE_SIZE;
	public static final int ROWS = height / TILE_SIZE;
	
	public Board() {
		this.tiles = new TileType[COLUMNS * ROWS];
	}
	
	public void clearBoard(){
		for(int i = 0; i < tiles.length; i++){
			tiles[i] = null;
		}
	}
	
	public void setTile (Point point, TileType type){
		setTile(point.x, point.y, type);
	}
	
	public void setTile(int x, int y, TileType type){
		
		tiles[y*ROWS + x] = type;
	}
	
	public TileType getTile(int x, int y){
		
		return tiles[y*ROWS + x];
	}

	public void draw(Graphics g) {
		
		g.setColor(Color.BLACK);
		g.fillRect(0,0,width, height);
		
			
			g.setColor(Color.DARK_GRAY);
			for(int x = 0; x < COLUMNS; x++){
				for(int y = 0; y < ROWS; y++){
					g.drawLine(x * TILE_SIZE, 0, x * TILE_SIZE, height);
					g.drawLine(0, y*TILE_SIZE, width, y*TILE_SIZE);
				}
			}
				
			for(int x = 0; x < COLUMNS; x++){
				for(int y = 0; y < ROWS; y++){
					TileType tile = getTile(x, y);
					if(tile != null){
						drawTile(x*TILE_SIZE, y*TILE_SIZE, tile, g);
					}
				}
			}
	}

	private void drawTile(int x, int y, TileType tile, Graphics g) {
		
		switch(tile){
		
			case Fruit:
				g.setColor(Color.RED);
				g.fillOval(x, y, TILE_SIZE, TILE_SIZE);
				break;
			case SnakeHead:
				g.setColor(Color.BLUE);
				g.fillRect(x, y, TILE_SIZE, TILE_SIZE);
				break;
			case SnakeBody:
				g.setColor(Color.GREEN);
				g.fillRect(x, y, TILE_SIZE, TILE_SIZE);
				break;	
		}	
	}
}
