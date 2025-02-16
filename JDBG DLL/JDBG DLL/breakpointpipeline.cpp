#include "breakpointpipeline.h"
#include <jni.h>
#include <jvmti.h>
#include <json.hpp>

using json = nlohmann::basic_json<>;





void populateStackTrace(jvmtiEnv* jvmti, jthread& thread, BreakpointResponse& response) {
	constexpr int FRAME_COUNT = 16;
	jvmtiFrameInfo* frames = new jvmtiFrameInfo[FRAME_COUNT];
	jint count;
	jvmti->GetStackTrace(thread, 0, FRAME_COUNT, frames, &count);

	for (int i = 0; i < count; i++) {

		jvmtiFrameInfo& info = frames[i];

		char* frameMethodName;
		char* frameMethodSig;
		jvmti->GetMethodName(info.method, &frameMethodName, &frameMethodSig, NULL);


		jclass frameKlass;
		jvmti->GetMethodDeclaringClass(info.method, &frameKlass);
		char* frameKlassSignature;
		jvmti->GetClassSignature(frameKlass, &frameKlassSignature, NULL);

		StackTraceElement elm;
		elm.klassSignature = frameKlassSignature;
		elm.methodName = frameMethodName;
		elm.methodSignature = frameMethodSig;
		elm.location = info.location;

		response.stackTrace.push_back(elm);
	}
}


void JNICALL breakpoint(jvmtiEnv* jvmti,
	JNIEnv* jni,
	jthread thread,
	jmethodID method,
	jlocation location) {


	BreakpointResponse response;

	char* methodName;
	char* methodSig;
	jvmti->GetMethodName(method, &methodName, &methodSig, NULL);


	jclass klass;
	jvmti->GetMethodDeclaringClass(method, &klass);
	char* klassSignature;
	jvmti->GetClassSignature(klass, &klassSignature, NULL);


	response.klassSignature = klassSignature;
	response.methodName = methodName;
	response.methodSignature = methodSig;

	populateStackTrace(jvmti, thread, response);




	BreakpointPipeline::getInstance().sendAndAwait(response);
}

BreakpointPipeline::BreakpointPipeline(const wchar_t* p) : ClientPipeline{ p } {
};

void BreakpointPipeline::sendAndAwait(BreakpointResponse& response) {
	json j = response;
	std::string dump{ j.dump() };

	sendData(dump.c_str(), strlen(dump.c_str()));


	// block until it sends something.
	char buffer[1];
	DWORD bytesRead;
	if (!ReadFile(handle, buffer, 1, &bytesRead, NULL)) {
		MessageBoxA(nullptr, std::to_string(GetLastError()).c_str(), "BreakpointPipeline", MB_ICONERROR);
	}

	MessageBoxA(nullptr, "Read", "Insider", MB_ICONERROR);

}