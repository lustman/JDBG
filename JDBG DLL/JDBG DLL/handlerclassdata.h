#pragma once

#include "handler.h"
#include "jdbgpipeline.h"
#include <map>
#include <string>

class HandlerClassData : public Handler {

public:
	HandlerClassData(jvmtiEnv* jvmti, JNIEnv* jni, JdbgPipeline* pipeline);
	int handle(char* data, DWORD length, char* responseBuffer, std::map<std::string, jclass>& klassMap) override;

};