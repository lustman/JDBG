#pragma once

#include <jvmti.h>
#include <jni.h>
#include <string>

jfieldID getField(jclass& klass, jobject& obj, std::string& fieldName, jvmtiEnv* jvmti, JNIEnv* jni);

jfieldID getField(jclass& klass, int idx, jvmtiEnv* jvmti, JNIEnv* jni);

jmethodID getMethod(jclass& klass, int idx, jvmtiEnv* jvmti, JNIEnv* jni);

jobject getObjectField(jclass& klass, jobject& obj, std::string& fieldName, jvmtiEnv* jvmti, JNIEnv* jni);

jbyte getByteField(jclass& klass, jobject& obj, std::string& fieldName, jvmtiEnv* jvmti, JNIEnv* jni);

jint getIntField(jclass& klass, jobject& obj, std::string& fieldName, jvmtiEnv* jvmti, JNIEnv* jni);

jchar getCharField(jclass& klass, jobject& obj, std::string& fieldName, jvmtiEnv* jvmti, JNIEnv* jni);

jdouble getDoubleField(jclass& klass, jobject& obj, std::string& fieldName, jvmtiEnv* jvmti, JNIEnv* jni);


jlong getLongField(jclass& klass, jobject& obj, std::string& fieldName, jvmtiEnv* jvmti, JNIEnv* jni);


jshort getShortField(jclass& klass, jobject& obj, std::string& fieldName, jvmtiEnv* jvmti, JNIEnv* jni);


jboolean getBoolField(jclass& klass, jobject& obj, std::string& fieldName, jvmtiEnv* jvmti, JNIEnv* jni);
