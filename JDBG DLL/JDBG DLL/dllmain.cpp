// dllmain.cpp : Defines the entry point for the DLL application.
#include <windows.h>
#include "pipeline.h"
#include "jdbgpipeline.h"
#include <jni.h>|
#include <jvmti.h>
#include <string>
#include <Zydis.h>

struct EnvResult {
    char code;
    jvmtiEnv* jvmti;
    JNIEnv* jni;
    JavaVM* jvm;
};

typedef jint(JNICALL* JNI_GetCreatedJavaVMs_t)(JavaVM** vm, jsize max_count, jsize* count);

EnvResult getEnv() {
    JavaVM* JVM;
    jvmtiEnv* jvmTI;
    JNIEnv* env;
    jsize nVMs;
    jint res;


    HMODULE hJvm = GetModuleHandle(L"jvm.dll");
    
    MessageBoxA(nullptr, std::to_string((uintptr_t)hJvm).c_str(), "Insider", MB_ICONERROR);

    if (hJvm == NULL) {
        MessageBoxA(nullptr, "Failed to get jvm.dll handle", "Insider", MB_ICONERROR);
        return EnvResult{ -1, nullptr,nullptr,nullptr };

    }

    // Get the address of the JNI_GetCreatedJavaVMs function
    JNI_GetCreatedJavaVMs_t JNI_GetCreatedJavaVMs = (JNI_GetCreatedJavaVMs_t)GetProcAddress(hJvm, "JNI_GetCreatedJavaVMs");
    if (JNI_GetCreatedJavaVMs == NULL) {
        MessageBoxA(nullptr, "Failed to find JNI_GetCreatedJavaVMs function in jvm.dll", "Insider", MB_ICONERROR);
        return EnvResult{ -1, nullptr,nullptr,nullptr };

    }

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

     if (env.code != 0) {
         return 0;
     }

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
     capabilities.can_suspend = 1;
     capabilities.can_generate_breakpoint_events = 1;


     jvmtiError error = jvmti->AddCapabilities(&capabilities);

     void* addy = (void*)jvmti->functions->AddCapabilities;

     MessageBoxA(nullptr,
         (std::string{ "ThingOO: " } + std::to_string(reinterpret_cast<uintptr_t>(addy))).c_str(),
         "Insider", MB_ICONERROR);

     if (error != JVMTI_ERROR_NONE) {
         MessageBoxA(nullptr, (std::string{ "Failed to add capabilities. Error code: " } + std::to_string(error)).c_str(), "Insider", MB_ICONERROR);
         serverPipe.sendStatus(2);

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
        CreateThread(NULL, 0, start, NULL, 0, NULL);
    }



    return TRUE;
}

