#pragma once
#include "pipeline.h"
#include <string>
#include <jni.h>
#include <jvmti.h>
#include <json.hpp>


struct StackTraceElement {
	char* klassSignature;
	char* methodName;
	char* methodSignature;
	int location;

	NLOHMANN_DEFINE_TYPE_INTRUSIVE(StackTraceElement, klassSignature, methodName, methodSignature, location);
};

struct LocalVariableElement {
	char* signature;
	char* name;
	std::string value;
};

struct BreakpointResponse {
	char* klassSignature;
	char* methodName;
	char* methodSignature;
	std::vector<StackTraceElement> stackTrace;

	NLOHMANN_DEFINE_TYPE_INTRUSIVE(BreakpointResponse, klassSignature, methodName, methodSignature, stackTrace);
};

void JNICALL breakpoint(jvmtiEnv* jvmti_env,
	JNIEnv* jni_env,
	jthread thread,
	jmethodID method,
	jlocation location);


class BreakpointPipeline : public ClientPipeline {
public:

public:
	BreakpointPipeline(const wchar_t* path);
	void sendAndAwait(BreakpointResponse& response);

	static BreakpointPipeline& getInstance() {
		static BreakpointPipeline instance{ L"\\\\.\\pipe\\jdbg_breakpoint" };
		return instance;
	}
};