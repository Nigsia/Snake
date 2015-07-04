#version 330

in vec4 position;

out vec4 col;

void main()
{	
	gl_Position = position;
	col = position + 0.5;
}

