#pragma once

#include <windows.h>

class ClientPipeline {
protected:
	const wchar_t* path;
	HANDLE handle;
public:
	ClientPipeline(const wchar_t* path);
	bool connectPipe();
	bool sendData(const void* data, DWORD size);

};