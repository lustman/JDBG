#pragma once

#include "handler.h"
#include "jdbgpipeline.h"
#include <map>
#include <string>
#include <boost/unordered/unordered_flat_map.hpp>


void JNICALL
loadHook(jvmtiEnv* jvmti_env,
    JNIEnv* jni_env,
    jclass class_being_redefined,
    jobject loader,
    const char* name,
    jobject protection_domain,
    jint class_data_len,
    const unsigned char* class_data,
    jint* new_class_data_len,
    unsigned char** new_class_data);

class HandlerClassData : public Handler {

public:
	HandlerClassData(jvmtiEnv* jvmti, JNIEnv* jni, JdbgPipeline* pipeline);
	int handle(char* data, DWORD length, char* responseBuffer, int& status, std::map<std::string, jclass>& klassMap) override;

};