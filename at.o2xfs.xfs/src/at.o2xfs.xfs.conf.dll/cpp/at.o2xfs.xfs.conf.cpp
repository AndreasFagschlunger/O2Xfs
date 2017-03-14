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

#include <windows.h>
#include <jni.h>
#include <xfsconf.h>
#include <at.o2xfs.win32.h>
#include "at_o2xfs_xfs_conf_O2XfsConf.h"

typedef HRESULT(WINAPI *WFM_CLOSE_KEY) (HKEY hKey);
typedef HRESULT(WINAPI *WFM_CREATE_KEY) (HKEY hKey, LPSTR lpszSubKey, PHKEY phkResult, LPDWORD lpdwDisposition);
typedef HRESULT(WINAPI *WFM_DELETE_KEY) (HKEY hKey, LPSTR lpszSubKey);
typedef HRESULT(WINAPI *WFM_DELETE_VALUE) (HKEY hKey, LPSTR lpszValue);
typedef HRESULT(WINAPI *WFM_ENUM_KEY) (HKEY hKey, DWORD iSubKey, LPSTR lpszName, LPDWORD lpcchName, PFILETIME lpftLastWrite);
typedef HRESULT(WINAPI *WFM_ENUM_VALUE) (HKEY hKey, DWORD iValue, LPSTR lpszValue, LPDWORD lpcchValue, LPSTR lpszData, LPDWORD lpcchData);
typedef HRESULT(WINAPI *WFM_OPEN_KEY) (HKEY hKey, LPSTR lpszSubKey, PHKEY phkResult);
typedef HRESULT(WINAPI *WFM_QUERY_VALUE) (HKEY hKey, LPSTR lpszValueName, LPSTR lpszData, LPDWORD lpcchData);
typedef HRESULT(WINAPI *WFM_SET_VALUE) (HKEY hKey, LPSTR lpszValueName, LPSTR lpszData, DWORD cchData);

typedef LPVOID(__cdecl *GET_TYPE_ADDRESS) (JNIEnv *env, jobject type);
typedef jobject(__cdecl *NEW_BUFFER) (JNIEnv *env, LPVOID address, jint size);

HMODULE hinstXfsconfLib = NULL;
WFM_CLOSE_KEY     lpWFMCloseKey;
WFM_CREATE_KEY    lpWFMCreateKey;
WFM_DELETE_KEY    lpWFMDeleteKey;
WFM_DELETE_VALUE  lpWFMDeleteValue;
WFM_ENUM_KEY      lpWFMEnumKey;
WFM_ENUM_VALUE    lpWFMEnumValue;
WFM_OPEN_KEY      lpWFMOpenKey;
WFM_QUERY_VALUE   lpWFMQueryValue;
WFM_SET_VALUE     lpWFMSetValue;

HMODULE hinstO2win32Lib;
GET_TYPE_ADDRESS lpGetTypeAddress;

BOOL WINAPI DllMain(HINSTANCE hinstDLL, DWORD fdwReason, LPVOID lpvReserved) {
	switch (fdwReason) {
	case DLL_PROCESS_DETACH:
		if (hinstXfsconfLib != NULL) {
			FreeLibrary(hinstXfsconfLib);
		}
		if (hinstO2win32Lib != NULL) {
			FreeLibrary(hinstO2win32Lib);
		}
		break;
	}
	return TRUE;
}

/*
* Class:     at_o2xfs_xfs_conf_O2XfsConf
* Method:    loadLibraries
* Signature: ()V
*/
JNIEXPORT void JNICALL Java_at_o2xfs_xfs_conf_O2XfsConf_loadLibraries(JNIEnv *env, jobject obj) {
	hinstXfsconfLib = LoadLibrary("xfs_conf.dll");
	if (hinstXfsconfLib != NULL) {
		lpWFMCloseKey = (WFM_CLOSE_KEY) GetProcAddress(hinstXfsconfLib, "WFMCloseKey");
		lpWFMCreateKey = (WFM_CREATE_KEY) GetProcAddress(hinstXfsconfLib, "WFMCreateKey");
		lpWFMDeleteKey = (WFM_DELETE_KEY) GetProcAddress(hinstXfsconfLib, "WFMDeleteKey");
		lpWFMDeleteValue = (WFM_DELETE_VALUE) GetProcAddress(hinstXfsconfLib, "WFMDeleteValue");
		lpWFMEnumKey = (WFM_ENUM_KEY) GetProcAddress(hinstXfsconfLib, "WFMEnumKey");
		lpWFMEnumValue = (WFM_ENUM_VALUE) GetProcAddress(hinstXfsconfLib, "WFMEnumValue");
		lpWFMOpenKey = (WFM_OPEN_KEY) GetProcAddress(hinstXfsconfLib, "WFMOpenKey");
		lpWFMQueryValue = (WFM_QUERY_VALUE) GetProcAddress(hinstXfsconfLib, "WFMQueryValue");
		lpWFMSetValue = (WFM_SET_VALUE) GetProcAddress(hinstXfsconfLib, "WFMSetValue");
	} else {
		printf("Error loading xfs_conf.dll: %d\r\n", GetLastError());
	}
	hinstO2win32Lib = LoadLibrary("at.o2xfs.win32.dll");
	if (hinstO2win32Lib == NULL) {
		printf("Error loading at.o2xfs.win32.dll: %d\r\n", GetLastError());
	}
	else {
		lpGetTypeAddress = (GET_TYPE_ADDRESS) GetProcAddress(hinstO2win32Lib, "GetTypeAddress");
	}
}

/*
 * Class:     at_o2xfs_xfs_conf_O2XfsConf
 * Method:    wfmCloseKey0
 * Signature: (Lat/o2xfs/win32/Type;)I
 */
JNIEXPORT jint JNICALL Java_at_o2xfs_xfs_conf_O2XfsConf_wfmCloseKey0(JNIEnv *env, jobject obj, jobject objhKey) {
	HKEY hKey = (*(PHKEY) lpGetTypeAddress(env, objhKey));
	return lpWFMCloseKey(hKey);
}

/*
 * Class:     at_o2xfs_xfs_conf_O2XfsConf
 * Method:    wfmOpenKey0
 * Signature: (Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;)I
 */
JNIEXPORT jint JNICALL Java_at_o2xfs_xfs_conf_O2XfsConf_wfmOpenKey0(JNIEnv *env, jobject obj, jobject objhKey, jobject objlpszSubKey, jobject objphkResult) {
	HKEY hKey = (*(PHKEY) lpGetTypeAddress(env, objhKey));
	LPSTR lpszSubKey = (LPSTR) lpGetTypeAddress(env, objlpszSubKey);
	PHKEY phkResult = (PHKEY) lpGetTypeAddress(env, objphkResult);
	return lpWFMOpenKey(hKey, lpszSubKey, phkResult);
}

/*
 * Class:     at_o2xfs_xfs_conf_O2XfsConf
 * Method:    wfmQueryValue0
 * Signature: (Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;)I
 */
JNIEXPORT jint JNICALL Java_at_o2xfs_xfs_conf_O2XfsConf_wfmQueryValue0(JNIEnv *env, jobject obj, jobject objhKey, jobject objlpszValueName, jobject objlpszData, jobject objlpcchData) {
	HKEY hKey = (*(PHKEY) lpGetTypeAddress(env, objhKey));
	LPSTR lpszValueName = (LPSTR) lpGetTypeAddress(env, objlpszValueName);
	LPSTR lpszData = (LPSTR) lpGetTypeAddress(env, objlpszData);
	LPDWORD lpcchData = (LPDWORD) lpGetTypeAddress(env, objlpcchData);
	return lpWFMQueryValue(hKey, lpszValueName, lpszData, lpcchData);
}

/*
 * Class:     at_o2xfs_xfs_conf_O2XfsConf
 * Method:    wfmEnumKey0
 * Signature: (Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;)I
 */
JNIEXPORT jint JNICALL Java_at_o2xfs_xfs_conf_O2XfsConf_wfmEnumKey0(JNIEnv *env, jobject obj, jobject objhKey, jobject objiSubKey, jobject objlpszName, jobject objlpcchName, jobject objlpftLastWrite) {
	HKEY hKey = (*(PHKEY) lpGetTypeAddress(env, objhKey));
	DWORD iSubKey = (*(LPDWORD) lpGetTypeAddress(env, objiSubKey));
	LPSTR lpszName = (LPSTR) lpGetTypeAddress(env, objlpszName);
	LPDWORD lpcchName = (LPDWORD) lpGetTypeAddress(env, objlpcchName);
	PFILETIME lpftLastWrite = (PFILETIME) lpGetTypeAddress(env, objlpftLastWrite);
	return lpWFMEnumKey(hKey, iSubKey, lpszName, lpcchName, lpftLastWrite);
}

/*
* Class:     at_o2xfs_xfs_conf_O2XfsConf
* Method:    wfmEnumValue0
* Signature: (Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;)I
*/
JNIEXPORT jint JNICALL Java_at_o2xfs_xfs_conf_O2XfsConf_wfmEnumValue0(JNIEnv *env, jobject obj, jobject objhKey, jobject objiValue, jobject szValue, jobject cchValue, jobject szData, jobject cchData) {
	HKEY hKey = (*(PHKEY) lpGetTypeAddress(env, objhKey));
	DWORD iValue = (*(LPDWORD) lpGetTypeAddress(env, objiValue));
	LPSTR lpszValue = (LPSTR) lpGetTypeAddress(env, szValue);
	LPDWORD lpcchValue = (LPDWORD) lpGetTypeAddress(env, cchValue);
	LPSTR lpszData =  (LPSTR) lpGetTypeAddress(env, szData);
	LPDWORD lpcchData =  (LPDWORD) lpGetTypeAddress(env, cchData);
	return lpWFMEnumValue(hKey, iValue, lpszValue, lpcchValue, lpszData, lpcchData);
}