#pragma once

#include "jdbgpipeline.h"
#include "handler.h"


struct BreakpointResponse {
	char* klassSignature;
	char* methodName;
	char* methodSignature;
};



void JNICALL
breakpoint(jvmtiEnv* jvmti_env,
	JNIEnv* jni_env,
	jthread thread,
	jmethodID method,
	jlocation location);

class BreakpointHandler : public Handler {
protected:

public:
	BreakpointHandler(ServerCommand c, jvmtiEnv* jvmti, JNIEnv* jni, JdbgPipeline* pipeline) : Handler{ c, jvmti, jni, pipeline } {

	}

};