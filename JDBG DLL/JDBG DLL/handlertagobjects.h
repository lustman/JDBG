#pragma once

#include "objecthandler.h"
#include "jdbgpipeline.h"
#include <map>
#include <string>

class HandlerTagObjects : public ObjectHandler {

public:
	HandlerTagObjects(jvmtiEnv* jvmti, JNIEnv* jni, JdbgPipeline* pipeline);
	int handle(char* data, DWORD length, char* responseBuffer, int& status, std::map<std::string, jclass>& klassMap) override;

};