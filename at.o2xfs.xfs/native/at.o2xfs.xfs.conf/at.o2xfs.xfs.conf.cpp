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
#include <xfsconf.h>
#include <at.o2xfs.win32.h>
#include "at_o2xfs_xfs_conf_O2XfsConf.h"

/*
 * Class:     at_o2xfs_xfs_conf_O2XfsConf
 * Method:    wfmCloseKey0
 * Signature: (Lat/o2xfs/win32/Type;)I
 */
JNIEXPORT jint JNICALL Java_at_o2xfs_xfs_conf_O2XfsConf_wfmCloseKey0(JNIEnv *env, jobject obj, jobject objhKey) {
	HKEY hKey = (*(PHKEY) GetTypeAddress(env, objhKey));
	return WFMCloseKey(hKey);
}

/*
 * Class:     at_o2xfs_xfs_conf_O2XfsConf
 * Method:    wfmOpenKey0
 * Signature: (Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;)I
 */
JNIEXPORT jint JNICALL Java_at_o2xfs_xfs_conf_O2XfsConf_wfmOpenKey0(JNIEnv *env, jobject obj, jobject objhKey, jobject objlpszSubKey, jobject objphkResult) {
	HKEY hKey = (*(PHKEY) GetTypeAddress(env, objhKey));
	LPSTR lpszSubKey = (LPSTR) GetTypeAddress(env, objlpszSubKey);
	PHKEY phkResult = (PHKEY) GetTypeAddress(env, objphkResult);
	return WFMOpenKey(hKey, lpszSubKey, phkResult);
}

/*
 * Class:     at_o2xfs_xfs_conf_O2XfsConf
 * Method:    wfmQueryValue0
 * Signature: (Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;)I
 */
JNIEXPORT jint JNICALL Java_at_o2xfs_xfs_conf_O2XfsConf_wfmQueryValue0(JNIEnv *env, jobject obj, jobject objhKey, jobject objlpszValueName, jobject objlpszData, jobject objlpcchData) {
	HKEY hKey = (*(PHKEY) GetTypeAddress(env, objhKey));
	LPSTR lpszValueName = (LPSTR) GetTypeAddress(env, objlpszValueName);
	LPSTR lpszData = (LPSTR) GetTypeAddress(env, objlpszData);
	LPDWORD lpcchData = (LPDWORD) GetTypeAddress(env, objlpcchData);
	return WFMQueryValue(hKey, lpszValueName, lpszData, lpcchData);
}

/*
 * Class:     at_o2xfs_xfs_conf_O2XfsConf
 * Method:    wfmEnumKey0
 * Signature: (Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;)I
 */
JNIEXPORT jint JNICALL Java_at_o2xfs_xfs_conf_O2XfsConf_wfmEnumKey0(JNIEnv *env, jobject obj, jobject objhKey, jobject objiSubKey, jobject objlpszName, jobject objlpcchName, jobject objlpftLastWrite) {
	HKEY hKey = (*(PHKEY) GetTypeAddress(env, objhKey));
	DWORD iSubKey = (*(LPDWORD) GetTypeAddress(env, objiSubKey));
	LPSTR lpszName = (LPSTR) GetTypeAddress(env, objlpszName);
	LPDWORD lpcchName = (LPDWORD) GetTypeAddress(env, objlpcchName);
	PFILETIME lpftLastWrite = (PFILETIME) GetTypeAddress(env, objlpftLastWrite);
	return WFMEnumKey(hKey, iSubKey, lpszName, lpcchName, lpftLastWrite);
}

/*
 * Class:     at_o2xfs_xfs_conf_O2XfsConf
 * Method:    wfmEnumValue0
 * Signature: (Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;)I
 */
JNIEXPORT jint JNICALL Java_at_o2xfs_xfs_conf_O2XfsConf_wfmEnumValue0(JNIEnv *env, jobject obj, jobject objhKey, jobject objiValue, jobject objlpszValue, jobject objlpcchValue, jobject objlpszData, jobject objlpcchData) {
	HKEY hKey = (*(PHKEY) GetTypeAddress(env, objhKey));
	DWORD iValue = (*(LPDWORD) GetTypeAddress(env, objiValue));
	LPSTR lpszValue = (LPSTR) GetTypeAddress(env, objlpszValue);
	LPDWORD lpcchValue = (LPDWORD) GetTypeAddress(env, objlpcchValue);
	LPSTR lpszData =  (LPSTR) GetTypeAddress(env, objlpszData);
	LPDWORD lpcchData =  (LPDWORD) GetTypeAddress(env, objlpcchData);
	return WFMEnumValue(hKey, iValue, lpszValue, lpcchValue, lpszData, lpcchData);
}