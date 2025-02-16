#include "pipeline.h"
#include <windows.h>
#include <string>

ClientPipeline::ClientPipeline(const wchar_t* p) : path{ p } { 
    if (!connectPipe()) {

    }
}



bool ClientPipeline::connectPipe() {

    HANDLE pipe = CreateFile(
        path,
        GENERIC_READ | GENERIC_WRITE,
        FILE_SHARE_READ | FILE_SHARE_WRITE,
        NULL,
        OPEN_EXISTING,
        FILE_ATTRIBUTE_NORMAL,
        NULL
    );


    if (pipe == INVALID_HANDLE_VALUE) {
        MessageBoxA(nullptr, std::to_string(GetLastError()).c_str(), "Pipe Error", MB_ICONERROR);
        MessageBox(nullptr, path, L"Pipe Error", MB_ICONERROR);

        return false;
    }

    this->handle = pipe;


    return true;
}


bool ClientPipeline::sendData(const void* data, DWORD size) {
    DWORD bytesWritten{ 0 };
    return WriteFile(handle, data, size, &bytesWritten, NULL);
}