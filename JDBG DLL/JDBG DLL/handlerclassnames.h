#pragma once

#include "handler.h"
#include "jdbgpipeline.h"
#include <map>
#include <string>

class HandlerClassNames : public Handler {

public:
	HandlerClassNames(jvmtiEnv* jvmti, JNIEnv* jni, JdbgPipeline* pipeline);
	int handle(char* data, DWORD length, char* responseBuffer, int& status, std::map<std::string, jclass>& klassMap) override;

};