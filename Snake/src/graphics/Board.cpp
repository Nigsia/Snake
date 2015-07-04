#include <iostream>
#include "board.h"

Board::Board()
{
	clearBoard();
}

Board::~Board()
{
	/* Free the memory used by the array maybe (?) */
}

void Board::clearBoard()
{
	for (int i = 0; i < COLUMNS; i++)
		for (int j = 0; j < ROWS; j++)
			m_tiles[i][j] = null;
}

void Board::setTile(Point p, TileType t)
{
	if (checkBoundaries(p))
	{
		m_tiles[p.x][p.y] = t;
	}
	return;
}

TileType Board::getTile(Point p)
{
	if (checkBoundaries(p))
		return m_tiles[p.x][p.y];
	else
		return null;
}

void Board::draw()
{
	/* Draw lines */
	/* Draw TileType */
}


void Board::drawTile(Point p, TileType t)
{
	switch (t)
	{
	case Fruit :
		/* Render a red dot */
		break;
	case SnakeBody :
		/* Render a green dot */
		break;
	case SnakeHead :
		/* Render a greener dot */
		break;
	}
}

bool Board::checkBoundaries(Point p)
{
	if (p.x > COLUMNS || p.y > ROWS || p.x < 0 || p.y < 0)
	{
		std::cout << "You're stupid" << std::endl;
		return 0;
	}
}