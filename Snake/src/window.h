#pragma once

#include <GL\glew.h>
#include <GLFW\glfw3.h>


class Window
{
private:
	int			m_Width, m_Height;
	const char *m_Name;
	GLFWwindow *m_Window;
	bool		m_Closed;
public:
	Window(const char *name, int w, int h);
	~Window();
	bool closed() const;
	void update() const;
private:
	bool init();
};