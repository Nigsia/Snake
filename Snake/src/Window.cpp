#include "window.h"
#include <iostream>

Window::Window(const char *name, int w, int h)
{
	m_Name = name;
	m_Height = h;
	m_Width = w;
	if (!init())
		glfwTerminate();
}

Window::~Window()
{
	glfwTerminate();
}

bool Window::init()
{
	if (!glfwInit())
	{
		std::cout << "Failed to init GLFW" << std::endl;
		return 0;
	}
	m_Window = glfwCreateWindow(m_Width, m_Height, m_Name, NULL, NULL);
	if (!m_Window)
	{
		std::cout << "Failed to create GLFW window" << std::endl;
		return 0;
	}
	glfwMakeContextCurrent(m_Window);

	if(glewInit() != GLEW_OK)
	{ 
		std::cout << "Failed to init GLEW" << std::endl;
		return 0;
	}
	std::cout << glGetString(GL_VERSION) << std::endl;

	return 1;
}

bool Window::closed() const
{
	return glfwWindowShouldClose(m_Window) == 1;
}

void Window::update()  const
{
	//glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	glfwPollEvents();
	glfwSwapBuffers(m_Window);
}
