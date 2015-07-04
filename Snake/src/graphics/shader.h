#pragma once

#include <string>
#include <iostream>
#include <vector>
#include <GL\glew.h>

class Shader
{
	static std::string readFile(char* filepath)
	{
		std::string fp = "src\\";
		fp.append(filepath);
		FILE* file = fopen(fp.c_str(), "rt");
		if (file == nullptr)
		{
			std::cout << "Could not open file " << filepath << std::endl;
			std::string nya = "nya";
			return nya;
		}

		fseek(file, 0, SEEK_END);
		unsigned long length = ftell(file);
		char* data = new char[length + 1];
		memset(data, 0, length + 1);
		fseek(file, 0, SEEK_SET);
		fread(data, 1, length, file);
		fclose(file);

		std::string result(data);
		delete[] data;
		return result;
	}

private :
	GLuint m_shaderID;
	char*  m_VertPath;
	char*  m_FragPath;
public:
	Shader(char* vertexShaderPath, char* fragmentShaderPath);
	~Shader();

	void enable() const;
	void disable() const;

private:
	GLuint load();

};
