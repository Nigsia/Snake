var canvas;
var context;
var keystate;
var frames;
var SPEED = 7.0;

var TILE_SIZE = 20;
var COLS;
var ROWS;
var MIN_SNAKE_LENGTH = 5;

var EMPTY = 0;
var SNAKE = 1;
var FRUIT = 2;

var LEFT  = 37;
var UP 	  = 38;
var RIGHT = 39;
var DOWN  = 40;

var score = 0;
var nextScore = 10;
var score_increment = 10;

var gameOver = false;

var grid =
{
	width: null,
	height: null,
	_grid: null,


	init: function(d, c, r)
	{

		this.width = c;
		this.height = r;
		this._grid = [];

		for(var x = 0; x < c; x++)
		{
			this._grid.push([]);
			for(var y = 0; y < r; y++)
			{
				this._grid[x].push(d);
			}
		}
	},

	set: function(val, x, y)
	{
		this._grid[x][y] = val;
	},

	get: function(x, y)
	{
		return this._grid[x][y];
	},
}

var snake =
{
	direction: null,
	last: null,
	_queue: null,

	init: function(d, x, y)
	{
		this.direction = d;
		this._queue = [];
		this.insert(x, y);
	},

	insert: function(x, y)
	{
		this._queue.unshift({x:x, y:y});
		this.last = this._queue[0];
	},

	remove: function()
	{
		return this._queue.pop();
	},
}

	function setFood()
	{
		var a_empty = [];
		for(var x = 0; x < grid.width; x++)
		{
			for(var y = 0; y < grid.height; y++)
			{
				if(grid.get(x, y) === EMPTY)
				{
					a_empty.push({x:x, y:y});
				}
			}
		}

		var randpos = a_empty[Math.round(Math.random() * a_empty.length - 1)];
		grid.set(FRUIT, randpos.x, randpos.y);
	}

	function main()
	{
		canvas = document.getElementById('canvas');
		context = canvas.getContext('2d');

		COLS = canvas.width  / TILE_SIZE;
		ROWS = canvas.height / TILE_SIZE;

		init();

		document.addEventListener('keydown', function(e)
		{
			keystate[e.keyCode] = true;
		});
		document.addEventListener('keyup', function(e)
		{
			delete keystate[e.keyCode];
		});
		document.addEventListener('keydown', function()
		{
			if(gameOver) init();
		});

		loop();
	}

	function init()
	{

		document.getElementById("score").innerHTML = "Score: 0";

		this.gameOver = false;

		grid.init(EMPTY, COLS, ROWS);

		keystate = {};
		frames = 0;

		this.score = 0;
		this.nextScore = 10;

		var sp = {x:Math.floor(COLS / 2), y:Math.floor(ROWS / 2)};
		snake.init(UP, sp.x, sp.y);
		for(var i = 1; i < MIN_SNAKE_LENGTH; i++){
			snake.insert(sp.x, sp.y+i);
		}
		grid.set(SNAKE, sp.x, sp.y);

		setFood();
	}

	function loop()
	{
		update();
		draw();
		window.requestAnimationFrame(loop, canvas);
	}

	function update()
	{
		if(!gameOver)
		{
			frames++;

			if(keystate[LEFT]  && snake.direction !== RIGHT) snake.direction = LEFT;
			if(keystate[UP]    && snake.direction !== DOWN)  snake.direction = UP;
			if(keystate[RIGHT] && snake.direction !== LEFT) snake.direction = RIGHT;
			if(keystate[DOWN]  && snake.direction !== UP) snake.direction = DOWN;

			if(frames % SPEED === 0)
			{
				var nx = snake.last.x;
				var ny = snake.last.y;

				switch(snake.direction)
				{
					case LEFT:
						nx--;
						break;

					case UP:
						ny--;
						break;

					case RIGHT:
						nx++;
						break;

					case DOWN:
						ny++;
						break;
				}

				console.log("nx: " + nx + " ny: " + ny);

				if(nx < 0) nx = grid.width - 1;
				if(nx > grid.width - 1) nx = 0;
				if(ny < 0) ny = grid.height - 1;
				if(ny > grid.height - 1) ny = 0;

				if(grid.get(nx, ny) === SNAKE){
					gO();
				}

				if(grid.get(nx, ny) === FRUIT)
				{
					addScorePoint();
					var tail = {x:nx, y:ny};
					setFood();
				}
				else
				{
					var tail = snake.remove();
					grid.set(EMPTY, tail.x, tail.y);
					tail.x = nx;
					tail.y = ny;
				}

				grid.set(SNAKE, tail.x, tail.y);
				snake.insert(tail.x, tail.y);
			}
		}
	}

	function draw()
	{

		if(!gameOver){
			for(var x = 0; x < grid.width; x++)
			{
				for(var y = 0; y < grid.height; y++)
				{

					switch(grid.get(x, y))
					{
						case EMPTY:
							context.fillStyle = "#ddd";
							break;
						case SNAKE:
							context.fillStyle = "#00aa00";
							break;
						case FRUIT:
							context.fillStyle = "red";
							break;
					}
					context.fillRect(x * TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE);

					context.fillStyle = "#555";
					context.fillRect(x * TILE_SIZE, y * TILE_SIZE, 0.5, COLS * TILE_SIZE);
					context.fillRect(x * TILE_SIZE, y * TILE_SIZE, ROWS * TILE_SIZE, 0.5);
				}
			}
		}else
		{
			context.fillStyle = "#000";
			context.fillRect(0,0, canvas.width, canvas.height);
			context.fillStyle = "#fff";
			context.font = "bold 3em Arial";
			context.textAlign = "center";
			context.fillText("GAME OVER", canvas.width / 2, canvas.height / 4);
			context.font = "bold 2em Arial";
			context.fillText("You achieved " + score + " points. Congratulations!", canvas.width / 2, canvas.height / 2);
			context.font = "bold 1em Arial";
			context.fillText("Press any key to continue", canvas.width / 2, canvas.height / 4 * 3);
		}
	}

	function addScorePoint()
	{
		score += nextScore;
		nextScore += score_increment;
		document.getElementById('score').innerHTML = "Score: " + score;
	}

	function gO()
	{
		gameOver = true;
	}

window.addEventListener('load', main);