#include "pipeline.h"
#include <windows.h>


ClientPipeline::ClientPipeline(const wchar_t* p) : path{ p } { 
    connectPipe();
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
        return false;
    }

    this->handle = pipe;


    return true;
}


bool ClientPipeline::sendData(const void* data, DWORD size) {
    DWORD bytesWritten{ 0 };
    return WriteFile(handle, data, size, &bytesWritten, NULL);
}