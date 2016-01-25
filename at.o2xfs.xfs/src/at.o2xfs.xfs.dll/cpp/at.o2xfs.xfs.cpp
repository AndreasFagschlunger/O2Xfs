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

#include "at.o2xfs.xfs.h"
#include "at_o2xfs_xfs_XfsAPI.h"

HINSTANCE hInstance;

BOOL WINAPI DllMain(HINSTANCE hinstDLL, DWORD fdwReason, LPVOID lpvReserved) {
	switch(fdwReason) {
		case DLL_PROCESS_ATTACH:
			printf("DLL_PROCESS_ATTACH\n");
			hInstance = hinstDLL;
			break;
		case DLL_PROCESS_DETACH:
			printf("DLL_PROCESS_DETACH\n");
			break;
	}
	return TRUE;
}

/*
 * Class:     at_o2xfs_xfs_XfsAPI
 * Method:    wfsCancelAsyncRequest0
 * Signature: (Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;)J
 */
JNIEXPORT jlong JNICALL Java_at_o2xfs_xfs_XfsAPI_wfsCancelAsyncRequest0(JNIEnv *env, jobject obj, jobject hServiceObj, jobject requestIDObj) {
	HSERVICE hService = (*(LPHSERVICE) GetTypeAddress(env, hServiceObj));
	REQUESTID requestID = (*(LPREQUESTID) GetTypeAddress(env, requestIDObj));
	printf("WFSCancelAsyncRequest: hService=%d, RequestID=%d\n", hService, requestID);
	return WFSCancelAsyncRequest(hService, requestID);
}

/*
 * Class:     at_o2xfs_xfs_XfsAPI
 * Method:    wfsCleanUp0
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_at_o2xfs_xfs_XfsAPI_wfsCleanUp0(JNIEnv *env, jobject obj) {
	return WFSCleanUp();
}

/*
 * Class:     at_o2xfs_xfs_XfsAPI
 * Method:    wfsAsyncClose0
 * Signature: (Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;)I
 */
JNIEXPORT jint JNICALL Java_at_o2xfs_xfs_XfsAPI_wfsAsyncClose0(JNIEnv *env, jobject obj, jobject hServiceObj, jobject hWndObj, jobject requestIDObj) {
	HSERVICE hService = (*(LPHSERVICE) GetTypeAddress(env, hServiceObj));
	HWND hWnd = (HWND) (*(LPHANDLE) GetTypeAddress(env, hWndObj));
	LPREQUESTID lpRequestID = (LPREQUESTID) GetTypeAddress(env, requestIDObj);
	printf("WFSAsyncClose: hService=%d, hWnd=%X, requestID=%d\n", hService, hWnd, *lpRequestID);
	return WFSAsyncClose(hService, hWnd, lpRequestID);
}

/*
 * Class:     at_o2xfs_xfs_XfsAPI
 * Method:    wfsCreateAppHandle0
 * Signature: (Lat/o2xfs/win32/Type;)I
 */
JNIEXPORT jint JNICALL Java_at_o2xfs_xfs_XfsAPI_wfsCreateAppHandle0(JNIEnv *env, jobject obj, jobject hApp) {
	return WFSCreateAppHandle((LPHAPP) GetTypeAddress(env, hApp));
}

/*
 * Class:     at_o2xfs_xfs_XfsAPI
 * Method:    wfsAsyncDeregister0
 * Signature: (Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;)I
 */
JNIEXPORT jint JNICALL Java_at_o2xfs_xfs_XfsAPI_wfsAsyncDeregister0(JNIEnv *env, jobject obj, jobject hServiceObj, jobject dwEventClassObj, jobject hWndRegObj, jobject hWndObj, jobject requestIDObj) {
	HSERVICE hService = NULL;
	if(hServiceObj != NULL) {
		hService = (*(LPHSERVICE) GetTypeAddress(env, hServiceObj));
	}
	DWORD dwEventClass = NULL;
	if(dwEventClassObj != NULL) {
		dwEventClass = (*(LPDWORD) GetTypeAddress(env, dwEventClassObj));
	}
	HWND hWndReg = NULL;
	if(hWndRegObj != NULL) {
		hWndReg = (HWND) (*(LPHANDLE) GetTypeAddress(env, hWndRegObj));
	}
	HWND hWnd = (HWND) (*(LPHANDLE) GetTypeAddress(env, hWndObj));
	LPREQUESTID lpRequestID = (LPREQUESTID) GetTypeAddress(env, requestIDObj);
	return WFSAsyncDeregister(hService, dwEventClass, hWndReg, hWnd, lpRequestID);
}

/*
 * Class:     at_o2xfs_xfs_XfsAPI
 * Method:    wfsDestroyAppHandle0
 * Signature: (Lat/o2xfs/win32/Type;)I
 */
JNIEXPORT jint JNICALL Java_at_o2xfs_xfs_XfsAPI_wfsDestroyAppHandle0(JNIEnv *env, jobject obj, jobject hAppObj) {
	HAPP hApp = (*(LPHAPP) GetTypeAddress(env, hAppObj));
	return WFSDestroyAppHandle(hApp);
}

/*
 * Class:     at_o2xfs_xfs_XfsAPI
 * Method:    wfsAsyncExecute0
 * Signature: (Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;)I
 */
JNIEXPORT jint JNICALL Java_at_o2xfs_xfs_XfsAPI_wfsAsyncExecute0(JNIEnv *env, jobject obj, jobject hServiceObj, jobject dwCommandObj, jobject cmdDataObj, jobject dwTimeOutObj, jobject hWndObj, jobject requestIDObj) {
	HSERVICE hService = (*(LPHSERVICE) GetTypeAddress(env, hServiceObj));
	DWORD dwCommand = (*(LPDWORD) GetTypeAddress(env, dwCommandObj));
	LPVOID lpCmdData = NULL;
	if(cmdDataObj != NULL) {
		lpCmdData = GetTypeAddress(env, cmdDataObj);
	}
	DWORD dwTimeOut = (*(LPDWORD) GetTypeAddress(env, dwTimeOutObj));
	HWND hWnd = (HWND) (*(LPHANDLE) GetTypeAddress(env, hWndObj));
	LPREQUESTID lpRequestID = (LPREQUESTID) GetTypeAddress(env, requestIDObj);
	return WFSAsyncExecute(hService, dwCommand, lpCmdData, dwTimeOut, hWnd, lpRequestID);
}

/*
 * Class:     at_o2xfs_xfs_XfsAPI
 * Method:    wfsFreeResult0
 * Signature: (Lat/o2xfs/win32/Type;)I
 */
JNIEXPORT jint JNICALL Java_at_o2xfs_xfs_XfsAPI_wfsFreeResult0(JNIEnv *env, jobject obj, jobject resultObj) {
	LPWFSRESULT lpResult = (LPWFSRESULT) GetTypeAddress(env, resultObj);
	printf("WFSFreeResult: lpResult=%X\n", lpResult);
	return WFSFreeResult(lpResult);
}

/*
 * Class:     at_o2xfs_xfs_XfsAPI
 * Method:    wfsGetInfo0
 * Signature: (Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;)J
 */
JNIEXPORT jlong JNICALL Java_at_o2xfs_xfs_XfsAPI_wfsGetInfo0(JNIEnv *env, jobject obj, jobject hServiceObj, jobject dwCategoryObj, jobject queryDetailsObj, jobject dwTimeOutObj, jobject resultObj) {
	HSERVICE hService = (*(LPHSERVICE) GetTypeAddress(env, hServiceObj));
	DWORD dwCategory = (*(LPDWORD) GetTypeAddress(env, dwCategoryObj));
	LPVOID lpQueryDetails = NULL;
	if(queryDetailsObj != NULL) {
		lpQueryDetails = GetTypeAddress(env, queryDetailsObj);
	}
	DWORD dwTimeOut = (*(LPDWORD) GetTypeAddress(env, dwTimeOutObj));
	LPWFSRESULT* lppResult = (LPWFSRESULT*) GetTypeAddress(env, resultObj);	
	return WFSGetInfo(hService, dwCategory, lpQueryDetails, dwTimeOut, lppResult);
}

/*
 * Class:     at_o2xfs_xfs_XfsAPI
 * Method:    wfsAsyncGetInfo0
 * Signature: (Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;)I
 */
JNIEXPORT jint JNICALL Java_at_o2xfs_xfs_XfsAPI_wfsAsyncGetInfo0(JNIEnv *env, jobject obj, jobject hServiceObj, jobject dwCategoryObj, jobject queryDetailsObj, jobject dwTimeOutObj, jobject hWndObj, jobject requestIDObj) {
	HSERVICE hService = (*(LPHSERVICE) GetTypeAddress(env, hServiceObj));
	DWORD dwCategory = (*(LPDWORD) GetTypeAddress(env, dwCategoryObj));
	LPVOID lpQueryDetails = NULL;
	if(queryDetailsObj != NULL) {
		lpQueryDetails = GetTypeAddress(env, queryDetailsObj);
	}
	DWORD dwTimeOut = (*(LPDWORD) GetTypeAddress(env, dwTimeOutObj));
	HWND hWnd = (HWND) (*(LPHANDLE) GetTypeAddress(env, hWndObj));
	LPREQUESTID lpRequestID = (LPREQUESTID) GetTypeAddress(env, requestIDObj);
	printf("WFSAsyncGetInfo: hService=%d, dwCategory=%d, lpQueryDetails=%p, dwTimeOut=%d, hWnd=%p, lpRequestID=%p\n", hService, dwCategory, lpQueryDetails, dwTimeOut, hWnd, lpRequestID);
	return WFSAsyncGetInfo(hService, dwCategory, lpQueryDetails, dwTimeOut, hWnd, lpRequestID);
}

/*
 * Class:     at_o2xfs_xfs_XfsAPI
 * Method:    wfsAsyncOpen0
 * Signature: (Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;)I
 */
JNIEXPORT jint JNICALL Java_at_o2xfs_xfs_XfsAPI_wfsAsyncOpen0(JNIEnv *env, jobject obj, jobject logicalNameObj, jobject hAppObj, jobject appIDObj, jobject dwTraceLevelObj, jobject dwTimeOutObj, jobject hServiceObj, jobject hWndObj, jobject dwSrvcVersionsRequiredObj, jobject srvcVersionObj, jobject spiVersionObj, jobject requestIDObj) {
	LPSTR lpszLogicalName = (LPSTR) GetTypeAddress(env, logicalNameObj);
	HAPP hApp = (*(LPHAPP) GetTypeAddress(env, hAppObj));
	LPSTR lpszAppID = NULL;
	if(appIDObj != NULL) {
		lpszAppID = (LPSTR) GetTypeAddress(env, appIDObj);
	}
	DWORD dwTraceLevel = NULL;
	if(dwTraceLevelObj != NULL) {
		dwTraceLevel = (*(LPDWORD) GetTypeAddress(env, dwTraceLevelObj));
	}
	DWORD dwTimeOut = (*(LPDWORD) GetTypeAddress(env, dwTimeOutObj));
	LPHSERVICE lphService = (LPHSERVICE) GetTypeAddress(env, hServiceObj);
	HWND hWnd = (HWND) (*(LPHANDLE) GetTypeAddress(env, hWndObj));
	DWORD dwSrvcVersionsRequired = (*(LPDWORD) GetTypeAddress(env, dwSrvcVersionsRequiredObj));
	LPWFSVERSION lpSrvcVersion = (LPWFSVERSION) GetTypeAddress(env, srvcVersionObj);
	LPWFSVERSION lpSPIVersion = (LPWFSVERSION) GetTypeAddress(env, spiVersionObj);
	LPREQUESTID lpRequestID = (LPREQUESTID) GetTypeAddress(env, requestIDObj);
	printf("WFSAsyncOpen: lpszLogicalName=%s,hApp=%p,lpszAppID=%s,dwTraceLevel=%i,dwTimeOut=%i,lphService=%p,hWnd=%p,dwSrvcVersionsRequired=%p,lpSPIVersion=%p,lpRequestID=%p\n", lpszLogicalName, hApp, lpszAppID, dwTraceLevel, dwTimeOut, lphService, hWnd, dwSrvcVersionsRequired, lpSrvcVersion, lpSPIVersion, lpRequestID);
	return WFSAsyncOpen(lpszLogicalName, hApp, lpszAppID, dwTraceLevel, dwTimeOut, lphService, hWnd, dwSrvcVersionsRequired, lpSrvcVersion, lpSPIVersion, lpRequestID);
}

/*
 * Class:     at_o2xfs_xfs_XfsAPI
 * Method:    wfsAsyncRegister0
 * Signature: (Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;)I
 */
JNIEXPORT jint JNICALL Java_at_o2xfs_xfs_XfsAPI_wfsAsyncRegister0(JNIEnv *env, jobject obj, jobject hServiceObj, jobject dwEventClassObj, jobject hWndRegObj, jobject hWndObj, jobject requestIDObj) {
	HSERVICE hService = NULL;
	if(hServiceObj != NULL) {
		hService = (*(LPHSERVICE) GetTypeAddress(env, hServiceObj));
	}
	DWORD dwEventClass = NULL;
	if(dwEventClassObj != NULL) {
		dwEventClass = (*(LPDWORD) GetTypeAddress(env, dwEventClassObj));
	}
	HWND hWndReg =(HWND) (*(LPHANDLE) GetTypeAddress(env, hWndRegObj));
	HWND hWnd = (HWND) (*(LPHANDLE) GetTypeAddress(env, hWndObj));
	LPREQUESTID lpRequestID = (LPREQUESTID) GetTypeAddress(env, requestIDObj);
	return WFSAsyncRegister(hService, dwEventClass, hWndReg, hWnd, lpRequestID);
}

/*
 * Class:     at_o2xfs_xfs_XfsAPI
 * Method:    wfsStartUp0
 * Signature: (Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;)I
 */
JNIEXPORT jint JNICALL Java_at_o2xfs_xfs_XfsAPI_wfsStartUp0(JNIEnv *env, jobject obj, jobject versionsRequired, jobject wfsVersion) {
	DWORD dwVersionsRequired = (*(LPDWORD) GetTypeAddress(env, versionsRequired));
	printf("WFSStartUp: %d.%d - %d.%d\n", LOBYTE(HIWORD(dwVersionsRequired)), HIBYTE(HIWORD(dwVersionsRequired)), LOBYTE(LOWORD(dwVersionsRequired)), HIBYTE(LOWORD(dwVersionsRequired)));
	LPWFSVERSION lpWFSVersion = (LPWFSVERSION) GetTypeAddress(env, wfsVersion);
	return WFSStartUp(dwVersionsRequired, lpWFSVersion);
}