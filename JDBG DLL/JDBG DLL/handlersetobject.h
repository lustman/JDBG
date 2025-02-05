#pragma once

#include "objecthandler.h"
#include "jdbgpipeline.h"
#include <map>
#include <string>

class HandlerSetObject : public ObjectHandler {

public:
	HandlerSetObject(jvmtiEnv* jvmti, JNIEnv* jni, JdbgPipeline* pipeline);
	int handle(char* data, DWORD length, char* responseBuffer, std::map<std::string, jclass>& klassMap) override;

};