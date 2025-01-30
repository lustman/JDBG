// dllmain.cpp : Defines the entry point for the DLL application.
#include <windows.h>
#include "pipeline.h"
#include "jdbgpipeline.h"
#include <jni.h>|
#include <jvmti.h>

struct EnvResult {
    char code;
    jvmtiEnv* jvmti;
    JNIEnv* jni;
    JavaVM* jvm;
};

EnvResult getEnv() {
    JavaVM* JVM;
    jvmtiEnv* jvmTI;
    JNIEnv* env;
    jsize nVMs;
    jint res;

    try {
        res = JNI_GetCreatedJavaVMs(&JVM, 1, &nVMs);
        if (res != JNI_OK || nVMs < 1) {
            return EnvResult{ 1, nullptr, nullptr, nullptr };
        }
    }
    catch (...) {
        return EnvResult{ 1, nullptr, nullptr, nullptr };
    }
    try {
        res = JVM->AttachCurrentThread((void**)&env, nullptr);
        if (res != JNI_OK) {
            return EnvResult{ 2, nullptr, nullptr, nullptr };

        }

        res = JVM->GetEnv((void**)&jvmTI, JVMTI_VERSION_1_1);
        if (res != JNI_OK || jvmTI == nullptr) {
            return EnvResult{ 2, nullptr, nullptr, nullptr };

        }
    }
    catch (...) {
        return EnvResult{ 2, nullptr, nullptr, nullptr };
    }

    return EnvResult{ 0, jvmTI, env, JVM };
}

DWORD WINAPI start(LPVOID lpParam) {
    JdbgPipeline serverPipe{ L"\\\\.\\pipe\\jdbg" };

     serverPipe.connectPipe();
     EnvResult env{ getEnv() };

     jvmtiEnv* jvmti = env.jvmti;

     jvmtiCapabilities capabilities{};
     capabilities.can_redefine_classes = 1;
     capabilities.can_redefine_any_class = 1;
     capabilities.can_tag_objects = 1;
     capabilities.can_retransform_classes = 1;
     capabilities.can_retransform_any_class = 1;
     capabilities.can_generate_all_class_hook_events = 1;
     capabilities.can_get_bytecodes = 1;
     capabilities.can_get_synthetic_attribute = 1;


     jvmtiError error = jvmti->AddCapabilities(&capabilities);
     if (error != JVMTI_ERROR_NONE) {
         MessageBoxA(nullptr, "Failed to add capabilities", "Insider", MB_ICONERROR);
         return 0;
     }


     serverPipe.sendStatus(env.code);
     serverPipe.startListen(env.jvmti, env.jni);

     return 0;
}





BOOL APIENTRY DllMain( HMODULE hModule,
                       DWORD  ul_reason_for_call,
                       LPVOID lpReserved
                     )
{

    if (ul_reason_for_call == DLL_PROCESS_ATTACH) {
        QueueUserWorkItem(start, NULL, NULL);
    }



    return TRUE;
}

