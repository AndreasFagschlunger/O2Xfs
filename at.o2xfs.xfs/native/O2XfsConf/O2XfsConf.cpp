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

#include <windows.h>
#include <jni.h>
#include "xfsconf.h"
#include "at_o2xfs_xfs_conf_O2XfsConf.h"

BOOL WINAPI DllMain(HINSTANCE hinstDLL, DWORD fdwReason, LPVOID lpvReserved) {
	switch(fdwReason) {
		case DLL_PROCESS_ATTACH:
			break;
		case DLL_PROCESS_DETACH:
			break;
	}
	return TRUE;
}

/*
 * Class:     at_o2xfs_xfs_conf_O2XfsConf
 * Method:    wfmCloseKey
 * Signature: (Ljava/nio/ByteBuffer;)I
 */
JNIEXPORT jint JNICALL Java_at_o2xfs_xfs_conf_O2XfsConf_wfmCloseKey(JNIEnv *env, jobject obj, jobject hKey) {
	return WFMCloseKey((*(PHKEY) env->GetDirectBufferAddress(hKey)));
}

/*
 * Class:     at_o2xfs_xfs_conf_O2XfsConf
 * Method:    wfmOpenKey
 * Signature: (Ljava/nio/ByteBuffer;Ljava/nio/ByteBuffer;Ljava/nio/ByteBuffer;)I
 */
JNIEXPORT jint JNICALL Java_at_o2xfs_xfs_conf_O2XfsConf_wfmOpenKey(JNIEnv *env, jobject obj, jobject hKey, jobject lpszSubKey, jobject phkResult) {
	return WFMOpenKey((*(PHKEY) env->GetDirectBufferAddress(hKey)), (lpszSubKey == NULL ? NULL : (LPSTR) env->GetDirectBufferAddress(lpszSubKey)), (PHKEY) env->GetDirectBufferAddress(phkResult));
}

/*
 * Class:     at_o2xfs_xfs_conf_O2XfsConf
 * Method:    wfmQueryValue
 * Signature: (Ljava/nio/ByteBuffer;Ljava/nio/ByteBuffer;Ljava/nio/ByteBuffer;)I
 */
JNIEXPORT jint JNICALL Java_at_o2xfs_xfs_conf_O2XfsConf_wfmQueryValue(JNIEnv *env, jobject obj, jobject phkKey, jobject lpszValueName, jobject lpszData) {
	DWORD cchData = env->GetDirectBufferCapacity(lpszData);
	return WFMQueryValue((*(PHKEY) env->GetDirectBufferAddress(phkKey)), (LPSTR) env->GetDirectBufferAddress(lpszValueName), (LPSTR) env->GetDirectBufferAddress(lpszData), &cchData);
}

/*
 * Class:     at_o2xfs_xfs_conf_O2XfsConf
 * Method:    wfmEnumKey
 * Signature: (Ljava/nio/ByteBuffer;Ljava/nio/ByteBuffer;Ljava/nio/ByteBuffer;)I
 */
JNIEXPORT jint JNICALL Java_at_o2xfs_xfs_conf_O2XfsConf_wfmEnumKey(JNIEnv *env, jobject obj, jobject hKey, jobject iSubKey, jobject lpszName) {
	DWORD cchName = env->GetDirectBufferCapacity(lpszName);
	FILETIME lastWrite;	
	return WFMEnumKey((*(PHKEY) env->GetDirectBufferAddress(hKey)), (*(LPDWORD) env->GetDirectBufferAddress(iSubKey)), (LPSTR) env->GetDirectBufferAddress(lpszName),  (LPDWORD) &cchName, &lastWrite);
}

JNIEXPORT jint JNICALL Java_at_o2xfs_xfs_conf_O2XfsConf_wfmEnumValue(JNIEnv *env, jobject obj, jobject hKey, jobject iValue, jobject lpszValue, jobject lpszData) {
	DWORD cchValue = env->GetDirectBufferCapacity(lpszValue);
	DWORD cchData = env->GetDirectBufferCapacity(lpszData);
	return WFMEnumValue((*(PHKEY) env->GetDirectBufferAddress(hKey)), (*(LPDWORD) env->GetDirectBufferAddress(iValue)), (LPSTR) env->GetDirectBufferAddress(lpszValue), &cchValue, (LPSTR) env->GetDirectBufferAddress(lpszData), &cchData);
}