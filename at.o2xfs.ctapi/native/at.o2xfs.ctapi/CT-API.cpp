/*
 * Copyright (c) 2012, Andreas Fagschlunger. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

#include <Windows.h>
#include <jni.h>
#include <tchar.h>
#include "at_o2xfs_ctapi_CTAPI.h"
#include "ct_api.h"

void ThrowLastError(JNIEnv *);

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
	return JNI_VERSION_1_6;
}

BOOL WINAPI DllMain(HINSTANCE hinstDLL, DWORD fdwReason, LPVOID lpvReserved) {
	return TRUE;
}

/*
 * Class:     at_o2xfs_ctapi_CTAPI
 * Method:    loadLibrary0
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT jlong JNICALL Java_at_o2xfs_ctapi_CTAPI_loadLibrary0(JNIEnv *env, jobject obj, jstring fileName) {
	const jchar *str = env->GetStringChars(fileName, NULL);
	if(str == NULL) {
		return 0;
	}
	HMODULE hLib = LoadLibrary((LPWSTR) str);
	env->ReleaseStringChars(fileName, str);
	if(hLib == NULL) {		
		ThrowLastError(env);
	}
	return (jlong) hLib;
}

/*
 * Class:     at_o2xfs_ctapi_CTAPI
 * Method:    getFunctionAddress0
 * Signature: (JLjava/lang/String;)J
 */
JNIEXPORT jlong JNICALL Java_at_o2xfs_ctapi_CTAPI_getFunctionAddress0(JNIEnv *env, jobject obj, jlong hLib, jstring name) {
	const char *procName = env->GetStringUTFChars(name, NULL);
	FARPROC procAddress = GetProcAddress((HMODULE) hLib, (LPCSTR) procName);
	env->ReleaseStringUTFChars(name, procName);
	if(procAddress == NULL) {
		ThrowLastError(env);
	}
	 
	return (jlong) procAddress;
}

/*
 * Class:     at_o2xfs_ctapi_CTAPI
 * Method:    freeLibrary0
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_at_o2xfs_ctapi_CTAPI_freeLibrary0(JNIEnv *env, jobject obj, jlong hLib) {
	if(FreeLibrary((HMODULE) hLib) == 0) {
		ThrowLastError(env);
	}
}

void ThrowLastError(JNIEnv *env) {
	DWORD dwErrorCode = GetLastError();
	LPTSTR lpBuffer = NULL;
	DWORD nSize = FormatMessage(FORMAT_MESSAGE_ALLOCATE_BUFFER | FORMAT_MESSAGE_FROM_SYSTEM | FORMAT_MESSAGE_MAX_WIDTH_MASK, NULL, dwErrorCode, 0, (LPTSTR) &lpBuffer, 0, NULL);
	if(nSize != 0) {
		jclass excCls = env->FindClass("at/o2xfs/ctapi/NativeException");
		if(excCls != NULL) {
			jmethodID methodID = env->GetMethodID(excCls, "<init>", "(ILjava/lang/String;)V");
			if(methodID != NULL) {
				jstring msgStr = env->NewString((jchar *) lpBuffer, nSize);
				jobject exc = env->NewObject(excCls, methodID, dwErrorCode, msgStr);
				if(exc != NULL) {
					env->Throw((jthrowable) exc);
				}
			}
		}
		LocalFree(lpBuffer);
	}
}

/*
 * Class:     at_o2xfs_ctapi_CTAPI
 * Method:    init0
 * Signature: (JLjava/nio/ByteBuffer;Ljava/nio/ByteBuffer;)I
 */
JNIEXPORT jint JNICALL Java_at_o2xfs_ctapi_CTAPI_init0(JNIEnv *env, jobject obj, jlong addr, jobject ctn, jobject pn) {
	CT_INIT CT_init = (CT_INIT) addr;
	return CT_init((*(PUSHORT) env->GetDirectBufferAddress(ctn)), (*(PUSHORT) env->GetDirectBufferAddress(pn)));
}

/*
 * Class:     at_o2xfs_ctapi_CTAPI
 * Method:    data0
 * Signature: (JLjava/nio/ByteBuffer;Ljava/nio/ByteBuffer;Ljava/nio/ByteBuffer;Ljava/nio/ByteBuffer;Ljava/nio/ByteBuffer;Ljava/nio/ByteBuffer;)I
 */
JNIEXPORT jint JNICALL Java_at_o2xfs_ctapi_CTAPI_data0(JNIEnv *env, jobject obj, jlong addr, jobject ctn, jobject dad, jobject sad, jobject command, jobject lenr, jobject response) {
	CT_DATA CT_data = (CT_DATA) addr;
	CHAR rc = CT_data((*(PUSHORT) env->GetDirectBufferAddress(ctn)), (UCHAR*) env->GetDirectBufferAddress(dad), (UCHAR*) env->GetDirectBufferAddress(sad), (USHORT) env->GetDirectBufferCapacity(command), (UCHAR*) env->GetDirectBufferAddress(command), (USHORT*) env->GetDirectBufferAddress(lenr), (UCHAR*) env->GetDirectBufferAddress(response));
	return rc;
}

/*
 * Class:     at_o2xfs_ctapi_CTAPI
 * Method:    close0
 * Signature: (JLjava/nio/ByteBuffer;)I
 */
JNIEXPORT jint JNICALL Java_at_o2xfs_ctapi_CTAPI_close0(JNIEnv *env, jobject obj, jlong addr, jobject ctn) {
	CT_CLOSE CT_close = (CT_CLOSE) addr;
	return CT_close((*(PUSHORT) env->GetDirectBufferAddress(ctn)));
}