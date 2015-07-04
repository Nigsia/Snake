#include <iostream>
#include "window.h"
#include "graphics/board.h"
#include "graphics/shader.h"

void triangle();

int main()
{
	Window window("Snake", WIDTH, HEIGHT);
	glClearColor(0.6f, 0.6f, 0.6f, 1.0f);

	double lastTime = glfwGetTime();
	int fps = 0;

	GLfloat vert[] = 
	{
		 0.0f,  0.5f, 0.0f, 
		-0.5f, -0.5f, 0.0f, 
		 0.5f, -0.5f, 1.0f
	};

	GLuint vbo;
	
	glGenBuffers(1, &vbo);
	glBindBuffer(GL_ARRAY_BUFFER, vbo);
	glBufferData(GL_ARRAY_BUFFER, sizeof(vert), vert, GL_STATIC_DRAW);
	glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 0, 0);
	glEnableVertexAttribArray(0);


	Shader shader("graphics\\shader.vert", "graphics\\shader.frag");
	shader.enable();

	while (!window.closed())
	{
		//	FPS stuff
		double currentTime = glfwGetTime();
		fps++;

		/* Rendering */
		glClear(GL_COLOR_BUFFER_BIT);
		glDrawArrays(GL_TRIANGLES, 0, 3);

		/* Updating */
		window.update();

		//	FPS stuff
		if (currentTime - lastTime >= 1.0)
		{
			std::cout << "FPS: " << fps << std::endl;
			fps = 0;
			lastTime += 1.0;
		}

	}
	return 0;
}

void triangle()
{
	GLuint vao;
	GLuint vbo;

	GLfloat vert[] = 
	{
		1.0f, 2.0f
	};
	glGenVertexArrays(0, &vao);
	glBindVertexArray(vao);
	glGenBuffers(0, &vbo);
	glBindBuffer(GL_ARRAY_BUFFER, vbo);
	glBindVertexArray(0);

	glDrawArrays(GL_ARRAY_BUFFER, 0, 6);
}