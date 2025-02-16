
#include "logpipeline.h"
#include <Windows.h>


LogPipeline::LogPipeline(const wchar_t* p) : ClientPipeline{ p } {
	processId = GetCurrentProcessId();
};


void LogPipeline::addMessage(const std::string& message) {
	addMessage(message, false);
}


void LogPipeline::addMessage(const std::string& message, bool f) {
	std::string realMessage{ std::to_string(processId) + std::string{ " - " } + message };
	buffer += '\n';
	buffer += realMessage;


	if (f) {
		flush();
	}
}

void LogPipeline::flush() {
	const char* c{ buffer.c_str() };
	if (!sendData(c, strlen(c))) {
		CloseHandle(handle);
	}
	buffer.clear();
}
