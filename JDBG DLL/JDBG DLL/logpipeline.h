#pragma once
#include "pipeline.h"
#include <string>

class LogPipeline : public ClientPipeline {
protected:
	long processId = 0;
	std::string buffer;
public:
	LogPipeline(const wchar_t* path);
	void addMessage(const std::string& msg);
	void addMessage(const std::string& msg, bool flush);
	void flush();
};