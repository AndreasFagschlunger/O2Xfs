#ifndef _O2XFSWIN32_H_
#define _O2XFSWIN32_H_

#include <Windows.h>
#include <jni.h>

#ifdef DllExport
#define O2XFSWIN32_API __declspec(dllexport)
#else
#define O2XFSWIN32_API __declspec(dllimport)
#endif

#ifdef __cplusplus
extern "C" {
#endif

O2XFSWIN32_API LPVOID GetTypeAddress(JNIEnv *env, jobject type);

O2XFSWIN32_API jlong GetTypeSize(JNIEnv *env, jobject type);

O2XFSWIN32_API jobject NewBuffer(JNIEnv *env, LPVOID address, jint size);

#ifdef __cplusplus
}
#endif

#endif