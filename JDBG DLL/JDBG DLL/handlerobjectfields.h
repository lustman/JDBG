#pragma once

#include "objecthandler.h"
#include "jdbgpipeline.h"
#include <map>
#include <string>
#include "json.hpp"

using json = nlohmann::basic_json<>;

class HandlerObjectFields : public ObjectHandler {

public:
	HandlerObjectFields(jvmtiEnv* jvmti, JNIEnv* jni, JdbgPipeline* pipeline);
	int handle(char* data, DWORD length, char* responseBuffer, std::map<std::string, jclass>& klassMap) override;
	void buildFields(json& response, jclass& klass, jobject& obj);

};