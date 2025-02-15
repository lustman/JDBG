#include "jdbgpipeline.h"
#include "breakpointhandler.h"

void JNICALL
breakpoint(jvmtiEnv* jvmti,
	JNIEnv* jni,
	jthread thread,
	jmethodID method,
	jlocation location) {

	MessageBoxA(nullptr, "Here I am!", "Insider", MB_ICONERROR);

	char* methodName;
	char* methodSig;
	jvmti->GetMethodName(method, &methodName, &methodSig, NULL);

	jclass klass;
	jvmti->GetMethodDeclaringClass(method, &klass);
	char* klassSignature;
	jvmti->GetClassSignature(klass, &klassSignature, NULL);

	constexpr int FRAME_COUNT = 16;
	jvmtiFrameInfo* frames = new jvmtiFrameInfo[FRAME_COUNT];
	jint count;
	jvmti->GetStackTrace(thread, 0, FRAME_COUNT, frames, &count);

	for (int i = 0; i < count; i++) {
		jvmtiFrameInfo& info = frames[i];





	}








}
