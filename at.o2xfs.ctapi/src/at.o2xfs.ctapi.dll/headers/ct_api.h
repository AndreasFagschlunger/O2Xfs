/*
 * Copyright (c) 2017, Andreas Fagschlunger. All rights reserved.
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

#ifndef ct_api_h
#define ct_api_h
#include <Windows.h>
#include <jni.h>

#define OK 0
#define ERR_INVALID -1
#define ERR_CT -8
#define ERR_TRANS -10
#define ERR_MEMORY -11
#define ERR_HOST -127
#define ERR_HTSI -128

typedef CHAR (WINAPI *CT_INIT) (USHORT ctn, USHORT pn);
typedef CHAR (WINAPI *CT_DATA) (USHORT ctn, UCHAR* dad, UCHAR* sad, USHORT lenc, UCHAR* command, USHORT* lenr, UCHAR* response);
typedef CHAR (WINAPI *CT_CLOSE) (USHORT ctn);

typedef LPVOID(__cdecl *GET_TYPE_ADDRESS) (JNIEnv *env, jobject type);
typedef LPVOID(__cdecl *GET_TYPE_SIZE) (JNIEnv *env, jobject type);
typedef jobject(__cdecl *NEW_BUFFER) (JNIEnv *env, LPVOID address, jint size);

#endif