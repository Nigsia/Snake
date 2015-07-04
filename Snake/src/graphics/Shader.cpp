#include "shader.h"

Shader::Shader(char* vertexShaderPath, char* fragmentShaderPath) :
	m_VertPath(vertexShaderPath), m_FragPath(fragmentShaderPath)
{
	m_shaderID = load();
}

Shader::~Shader()
{
	glDeleteProgram(m_shaderID);
}

GLuint Shader::load()
{
	GLuint program = glCreateProgram();
	GLuint vertex = glCreateShader(GL_VERTEX_SHADER);
	GLuint fragment = glCreateShader(GL_FRAGMENT_SHADER);
	GLint result;

	std::string vs = readFile(m_VertPath);
	std::string fs = readFile(m_FragPath);

	const char* vertSource = vs.c_str();
	const char* fragSource = fs.c_str();

	// Vertex

	glShaderSource(vertex, 1, &vertSource, NULL);
	glCompileShader(vertex);
	
	glGetShaderiv(vertex, GL_COMPILE_STATUS, &result);
	if (result == GL_FALSE)
	{
		GLint length;
		glGetShaderiv(vertex, GL_INFO_LOG_LENGTH, &length);
		std::vector<char> error(length);
		glGetShaderInfoLog(vertex, length,&length, &error[0]);
		std::cout << "Failed to compile Vertex Shader" << std::endl << &error[0] << std::endl;
		glDeleteShader(vertex);
		return 0;
	}

	// Fragment

	glShaderSource(fragment, 1, &fragSource, NULL);
	glCompileShader(fragment);

	glGetShaderiv(fragment, GL_COMPILE_STATUS, &result);
	if (result == GL_FALSE)
	{
		GLint length;
		glGetShaderiv(fragment, GL_INFO_LOG_LENGTH, &length);
		std::vector<char> error(length);
		glGetShaderInfoLog(fragment, length, &length, &error[0]);
		std::cout << "Failed to compile Fragment Shader" << std::endl << &error[0] << std::endl;
		glDeleteShader(fragment);
		return 0;
	}

	glAttachShader(program, vertex);
	glAttachShader(program, fragment);

	glLinkProgram(program);
	glValidateProgram(program);

	glDeleteShader(vertex);
	glDeleteShader(fragment);

	return program;
}

void Shader::enable() const
{
	glUseProgram(m_shaderID);
}

void Shader::disable() const
{
	glUseProgram(0);
}