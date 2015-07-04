#pragma once

#include <GLFW\glfw3.h>
#include "tileType.h"
#include "../math.h"

#define WIDTH 800
#define HEIGHT 600
#define TILE_SIZE 20
#define COLUMNS WIDTH/TILE_SIZE
#define ROWS HEIGHT/TILE_SIZE

class Board
{
private:
	TileType m_tiles[COLUMNS][ROWS];
public:
	Board();
	~Board();
public:
	void clearBoard();
	void setTile(Point, TileType);
	TileType getTile(Point);
	void draw();
private:
	void drawTile(Point, TileType);
	bool checkBoundaries(Point);
};