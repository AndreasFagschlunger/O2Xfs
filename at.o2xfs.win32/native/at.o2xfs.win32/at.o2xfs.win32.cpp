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

#define DllExport

#include <Windows.h>
#include <tchar.h>
#include "at.o2xfs.win32.h"
#include "at_o2xfs_win32_Type.h"
#include "at_o2xfs_win32_impl_Win32Buffer.h"

static jclass typeClass    = NULL;
static jclass bufferClass  = NULL;
static jfieldID bufferFID  = NULL;
static jfieldID addressFID = NULL;
static jfieldID sizeFID    = NULL;

static bool initializedBufferSupport = false;


static bool initializeBufferSupport(JNIEnv *env) {
	if(initializedBufferSupport) {
		return true;
	}
	typeClass = (jclass) env->NewGlobalRef(env->FindClass("at/o2xfs/win32/Type"));
	bufferClass = (jclass) env->NewGlobalRef(env->FindClass("at/o2xfs/win32/Buffer"));
	if(typeClass != NULL && bufferClass != NULL) {
		bufferFID = env->GetFieldID(typeClass, "buffer", "Lat/o2xfs/win32/Buffer;");
		addressFID = env->GetFieldID(bufferClass, "address", "[B");
		sizeFID = env->GetFieldID(bufferClass, "size", "I");
		initializedBufferSupport = bufferFID != NULL && addressFID != NULL && sizeFID != NULL;
	}
	return initializedBufferSupport;
}

LPVOID ToPointer(JNIEnv *env, jbyteArray buf) {
	LPVOID result = NULL;
	env->GetByteArrayRegion(buf, 0, env->GetArrayLength(buf), (jbyte*) &result);
	return result;
}

O2XFSWIN32_API jobject NewBuffer(JNIEnv *env, LPVOID address, jint size) {
	jobject result = NULL;
	if(!initializeBufferSupport(env)) {
		return NULL;
	}
	jclass factoryClass = env->FindClass("at/o2xfs/win32/BufferFactory");
	jmethodID getInstanceMethod = env->GetStaticMethodID(factoryClass, "getInstance", "()Lat/o2xfs/win32/BufferFactory;");
	jmethodID createBufferMethod = env->GetMethodID(factoryClass, "createBuffer", "([BI)Lat/o2xfs/win32/Buffer;");
	jobject factoryObj = env->CallStaticObjectMethod(factoryClass, getInstanceMethod);	
	jbyteArray addressArray = env->NewByteArray(sizeof(address));
	env->SetByteArrayRegion(addressArray, 0, env->GetArrayLength(addressArray), (jbyte*) &address);
	result = env->CallObjectMethod(factoryObj, createBufferMethod, addressArray, size);
	env->DeleteLocalRef(addressArray);
	return result;
}

O2XFSWIN32_API jlong GetTypeSize(JNIEnv *env, jobject type) {
	if(initializeBufferSupport(env) && type != NULL && env->IsInstanceOf(type, typeClass)) {
		jobject buf = env->GetObjectField(type, bufferFID);
		return env->GetIntField(buf, sizeFID);
	}
	return (jlong) -1;
}

O2XFSWIN32_API LPVOID GetTypeAddress(JNIEnv *env, jobject obj) {
	LPVOID result = NULL;
	if(initializeBufferSupport(env) && obj != NULL && env->IsInstanceOf(obj, typeClass)) {
		jobject buf = env->GetObjectField(obj, bufferFID);
		result = GetBufferAddress(env, buf);
	}
	return result;
}

O2XFSWIN32_API void* GetBufferAddress(JNIEnv* env, jobject buf) {
	void* result = NULL;
	if(initializeBufferSupport(env) && buf != NULL && env->IsInstanceOf(buf, bufferClass)) {
		result = (void*) ToPointer(env, (jbyteArray) env->GetObjectField(buf, addressFID));
	}
	return result;
}

/*
 * Class:     at_o2xfs_win32_impl_Win32Buffer
 * Method:    allocate0
 * Signature: (I)J
 */
JNIEXPORT jbyteArray JNICALL Java_at_o2xfs_win32_impl_Win32Buffer_allocate0(JNIEnv *env, jobject obj, jint size) {
	void* p = malloc(size);
	memset(p, 0, size);
	jbyteArray result = env->NewByteArray(sizeof(p));
	if(result != NULL) {
		env->SetByteArrayRegion(result, 0, env->GetArrayLength(result), (jbyte*) &p);
	}
	return result;
}

/*
 * Class:     at_o2xfs_win32_impl_Win32Buffer
 * Method:    get0
 * Signature: ([BI)[B
 */
JNIEXPORT jbyteArray JNICALL Java_at_o2xfs_win32_impl_Win32Buffer_get0(JNIEnv *env, jobject obj, jbyteArray address, jint size) {
	jbyteArray result = env->NewByteArray(size);
	if(result != NULL) {
		env->SetByteArrayRegion(result, 0, size, (jbyte*) ToPointer(env, address));
	}
	return result;
}

/*
 * Class:     at_o2xfs_win32_impl_Win32Buffer
 * Method:    put0
 * Signature: ([B[B)V
 */
JNIEXPORT void JNICALL Java_at_o2xfs_win32_impl_Win32Buffer_put0(JNIEnv *env, jobject obj, jbyteArray address, jbyteArray src) {
	env->GetByteArrayRegion(src, 0, env->GetArrayLength(src), (jbyte*) ToPointer(env, address));
}

/*
 * Class:     at_o2xfs_win32_impl_Win32Buffer
 * Method:    subBuffer0
 * Signature: (II)Lat/o2xfs/win32/Buffer;
 */
JNIEXPORT jobject JNICALL Java_at_o2xfs_win32_impl_Win32Buffer_subBuffer0(JNIEnv *env, jobject obj, jbyteArray address, jint index, jint size) {
	return NewBuffer(env, &(((jbyte*) ToPointer(env, address))[index]), size);
}

/*
 * Class:     at_o2xfs_win32_impl_Win32Buffer
 * Method:    free0
 * Signature: ([B)V
 */
JNIEXPORT void JNICALL Java_at_o2xfs_win32_impl_Win32Buffer_free0(JNIEnv *env, jobject obj, jbyteArray buf) {
	LPVOID p = ToPointer(env, buf);
	free(p);
}