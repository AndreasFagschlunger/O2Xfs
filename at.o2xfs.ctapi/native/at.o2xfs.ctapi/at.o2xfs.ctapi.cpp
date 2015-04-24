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
#include <at.o2xfs.win32.h>
#include "at_o2xfs_ctapi_CTAPI.h"
#include "ct_api.h"

void ThrowLastError(JNIEnv *);

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
	return JNI_VERSION_1_6;
}

BOOL WINAPI DllMain(HINSTANCE hinstDLL, DWORD fdwReason, LPVOID lpvReserved) {
	return TRUE;
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
 * Method:    loadLibrary0
 * Signature: (Lat/o2xfs/win32/ZSTR;)Lat/o2xfs/win32/Buffer;
 */
JNIEXPORT jobject JNICALL Java_at_o2xfs_ctapi_CTAPI_loadLibrary0(JNIEnv *env, jobject obj, jobject fileName) {
	HMODULE hLib = LoadLibrary((LPCTSTR) GetTypeAddress(env, fileName));
	if(hLib == NULL) {		
		ThrowLastError(env);
	}
	return NewBuffer(env, hLib, sizeof(hLib));
}

/*
 * Class:     at_o2xfs_ctapi_CTAPI
 * Method:    getFunctionAddress0
 * Signature: (Lat/o2xfs/win32/Pointer;Lat/o2xfs/win32/ZSTR;)Lat/o2xfs/win32/Buffer;
 */
JNIEXPORT jobject JNICALL Java_at_o2xfs_ctapi_CTAPI_getFunctionAddress0(JNIEnv *env, jobject obj, jobject hLib, jobject procName) {
	FARPROC procAddress = GetProcAddress((HMODULE) GetTypeAddress(env, hLib), (LPCSTR) GetTypeAddress(env, procName));
	if(procAddress == NULL) {
		ThrowLastError(env);
	}
	return NewBuffer(env, (LPVOID) procAddress, sizeof(procAddress));
}

/*
 * Class:     at_o2xfs_ctapi_CTAPI
 * Method:    init0
 * Signature: (Lat/o2xfs/win32/Pointer;Lat/o2xfs/win32/USHORT;Lat/o2xfs/win32/USHORT;)I
 */
JNIEXPORT jint JNICALL Java_at_o2xfs_ctapi_CTAPI_init0(JNIEnv *env, jobject obj, jobject addrObj, jobject ctnObj, jobject pnObj) {
	CT_INIT CT_init = (CT_INIT) GetTypeAddress(env, addrObj);
	USHORT ctn = (*(PUSHORT) GetTypeAddress(env, ctnObj));
	USHORT pn (*(PUSHORT) GetTypeAddress(env, pnObj));
	return CT_init(ctn, pn);
}

/*
 * Class:     at_o2xfs_ctapi_CTAPI
 * Method:    data0
 * Signature: (Lat/o2xfs/win32/Pointer;Lat/o2xfs/win32/USHORT;Lat/o2xfs/win32/UINT8;Lat/o2xfs/win32/UINT8;Lat/o2xfs/win32/ByteArray;Lat/o2xfs/win32/USHORT;Lat/o2xfs/win32/ByteArray;)I
 */
JNIEXPORT jint JNICALL Java_at_o2xfs_ctapi_CTAPI_data0(JNIEnv *env, jobject obj, jobject addrObj, jobject ctnObj, jobject dadObj, jobject sadObj, jobject commandObj, jobject lenrObj, jobject responseObj) {
	CT_DATA CT_data = (CT_DATA) GetTypeAddress(env, addrObj);
	USHORT ctn = (*(PUSHORT) GetTypeAddress(env, ctnObj));
	UCHAR *dad = (UCHAR*) GetTypeAddress(env, dadObj);
	UCHAR *sad = (UCHAR*) GetTypeAddress(env, sadObj);
	USHORT lenc = (USHORT) GetTypeSize(env, commandObj);
	UCHAR *command = (UCHAR*) GetTypeAddress(env, commandObj);
	USHORT *lenr = (USHORT*) GetTypeAddress(env, lenrObj);
	UCHAR *response = (UCHAR*) GetTypeAddress(env, responseObj);
	CHAR rc = CT_data(ctn, dad, sad, lenc, command, lenr, response);
	return rc;
}

/*
 * Class:     at_o2xfs_ctapi_CTAPI
 * Method:    close0
 * Signature: (Lat/o2xfs/win32/Pointer;Lat/o2xfs/win32/USHORT;)I
 */
JNIEXPORT jint JNICALL Java_at_o2xfs_ctapi_CTAPI_close0(JNIEnv *env, jobject obj, jobject addrObj, jobject ctnObj) {
	CT_CLOSE CT_close = (CT_CLOSE) GetTypeAddress(env, addrObj);
	USHORT ctn = (*(PUSHORT) GetTypeAddress(env, ctnObj));
	return CT_close(ctn);
}

/*
 * Class:     at_o2xfs_ctapi_CTAPI
 * Method:    freeLibrary0
 * Signature: (Lat/o2xfs/win32/Pointer;)V
 */
JNIEXPORT void JNICALL Java_at_o2xfs_ctapi_CTAPI_freeLibrary0(JNIEnv *env, jobject obj, jobject hLib) {
	if(FreeLibrary((HMODULE) GetTypeAddress(env, hLib)) == 0) {
		ThrowLastError(env);
	}
}