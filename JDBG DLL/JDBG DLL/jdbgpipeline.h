#pragma once

#include "pipeline.h"
#include <jni.h>
#include <jvmti.h>

enum ServerCommand {
	GET_LOADED_CLASS_NAMES=0,
	GET_CLASS_DATA=1,
	GET_OBJECT_TAGS=2,
	GET_REFS=3,
	GET_FIELDS=4
};



class JdbgPipeline : public ClientPipeline {
public:
	JdbgPipeline(const wchar_t* path);
	void sendStatus(char status);


	void startListen(jvmtiEnv* jvmti, JNIEnv* jni);
	
};