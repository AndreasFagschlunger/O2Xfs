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

#include "at.o2xfs.xfs.h"
#include "at_o2xfs_xfs_XfsAPI.h"

HINSTANCE hInstance;
HMODULE hinstMsxfsLib;

WFS_CANCEL_ASYNC_REQUEST lpWFSCancelAsyncRequest;
WFS_CANCEL_BLOCKING_CALL lpWFSCancelBlockingCall;
WFS_CLEAN_UP             lpWFSCleanUp;
WFS_CLOSE                lpWFSClose;
WFS_ASYNC_CLOSE          lpWFSAsyncClose;
WFS_CREATE_APP_HANDLE    lpWFSCreateAppHandle;
WFS_DEREGISTER           lpWFSDeregister;
WFS_ASYNC_DEREGISTER     lpWFSAsyncDeregister;
WFS_DESTROY_APP_HANDLE   lpWFSDestroyAppHandle;
WFS_EXECUTE              lpWFSExecute;
WFS_ASYNC_EXECUTE        lpWFSAsyncExecute;
WFS_FREE_RESULT          lpWFSFreeResult;
WFS_GET_INFO             lpWFSGetInfo;
WFS_ASYNC_GET_INFO       lpWFSAsyncGetInfo;
WFS_IS_BLOCKING          lpWFSIsBlocking;
WFS_LOCK                 lpWFSLock;
WFS_ASYNC_LOCK           lpWFSAsyncLock;
WFS_OPEN                 lpWFSOpen;
WFS_ASYNC_OPEN           lpWFSAsyncOpen;
WFS_REGISTER             lpWFSRegister;
WFS_ASYNC_REGISTER       lpWFSAsyncRegister;
WFS_SET_BLOCKING_HOOK    lpWFSSetBlockingHook;
WFS_START_UP             lpWFSStartUp;
WFS_UNHOOK_BLOCKING_HOOK lpWFSUnhookBlockingHook;
WFS_UNLOCK               lpWFSUnlock;
WFS_ASYNC_UNLOCK         lpWFSAsyncUnlock;
WFM_SET_TRACE_LEVEL      lpWFMSetTraceLevel;

HMODULE hinstO2win32Lib;

HAPP hApp;

GET_TYPE_ADDRESS lpGetTypeAddress;
NEW_BUFFER lpNewBuffer;

BOOL WINAPI DllMain(HINSTANCE hinstDLL, DWORD fdwReason, LPVOID lpvReserved) {
	switch(fdwReason) {
		case DLL_PROCESS_ATTACH:
			printf("DLL_PROCESS_ATTACH\n");
			hInstance = hinstDLL;
			break;
		case DLL_PROCESS_DETACH:
			printf("DLL_PROCESS_DETACH\n");
			if (hinstMsxfsLib != NULL) {
				FreeLibrary(hinstMsxfsLib);
			}
			if (hinstO2win32Lib != NULL) {
				FreeLibrary(hinstO2win32Lib);
			}
			break;
	}
	return TRUE;
}

/*
* Class:     at_o2xfs_xfs_XfsAPI
* Method:    loadLibrary
* Signature: ()V
*/
JNIEXPORT void JNICALL Java_at_o2xfs_xfs_XfsAPI_loadLibrary(JNIEnv *env, jobject obj) {
	hinstMsxfsLib = LoadLibrary("msxfs.dll");
	if (hinstMsxfsLib == NULL) {
		printf("Error loading msxfs.dll: %d\r\n", GetLastError()); // FIXME: Error handling
	} else {
		lpWFSCancelAsyncRequest = (WFS_CANCEL_ASYNC_REQUEST) GetProcAddress(hinstMsxfsLib, "WFSCancelAsyncRequest");
		lpWFSCancelBlockingCall = (WFS_CANCEL_BLOCKING_CALL) GetProcAddress(hinstMsxfsLib, "WFSCancelBlockingCall");
		lpWFSCleanUp            = (WFS_CLEAN_UP)             GetProcAddress(hinstMsxfsLib, "WFSCleanUp");
		lpWFSClose              = (WFS_CLOSE)                GetProcAddress(hinstMsxfsLib, "WFSClose");
		lpWFSAsyncClose         = (WFS_ASYNC_CLOSE)          GetProcAddress(hinstMsxfsLib, "WFSAsyncClose");
		lpWFSCreateAppHandle    = (WFS_CREATE_APP_HANDLE)    GetProcAddress(hinstMsxfsLib, "WFSCreateAppHandle");
		lpWFSDeregister         = (WFS_DEREGISTER)           GetProcAddress(hinstMsxfsLib, "WFSDeregister");
		lpWFSAsyncDeregister    = (WFS_ASYNC_DEREGISTER)     GetProcAddress(hinstMsxfsLib, "WFSAsyncDeregister");
		lpWFSDestroyAppHandle   = (WFS_DESTROY_APP_HANDLE)   GetProcAddress(hinstMsxfsLib, "WFSDestroyAppHandle");
		lpWFSExecute            = (WFS_EXECUTE)              GetProcAddress(hinstMsxfsLib, "WFSExecute");
		lpWFSAsyncExecute       = (WFS_ASYNC_EXECUTE)        GetProcAddress(hinstMsxfsLib, "WFSAsyncExecute");
		lpWFSFreeResult         = (WFS_FREE_RESULT)          GetProcAddress(hinstMsxfsLib, "WFSFreeResult");
		lpWFSGetInfo            = (WFS_GET_INFO)             GetProcAddress(hinstMsxfsLib, "WFSGetInfo");
		lpWFSAsyncGetInfo       = (WFS_ASYNC_GET_INFO)       GetProcAddress(hinstMsxfsLib, "WFSAsyncGetInfo");
		lpWFSIsBlocking         = (WFS_IS_BLOCKING)          GetProcAddress(hinstMsxfsLib, "WFSIsBlocking");
		lpWFSLock               = (WFS_LOCK)                 GetProcAddress(hinstMsxfsLib, "WFSLock");
		lpWFSAsyncLock          = (WFS_ASYNC_LOCK)           GetProcAddress(hinstMsxfsLib, "WFSAsyncLock");
		lpWFSOpen               = (WFS_OPEN)                 GetProcAddress(hinstMsxfsLib, "WFSOpen");
		lpWFSAsyncOpen          = (WFS_ASYNC_OPEN)           GetProcAddress(hinstMsxfsLib, "WFSAsyncOpen");
		lpWFSRegister           = (WFS_REGISTER)             GetProcAddress(hinstMsxfsLib, "WFSRegister");
		lpWFSAsyncRegister      = (WFS_ASYNC_REGISTER)       GetProcAddress(hinstMsxfsLib, "WFSAsyncRegister");
		lpWFSSetBlockingHook    = (WFS_SET_BLOCKING_HOOK)    GetProcAddress(hinstMsxfsLib, "WFSSetBlockingHook");
		lpWFSStartUp            = (WFS_START_UP)             GetProcAddress(hinstMsxfsLib, "WFSStartUp");
		lpWFSUnhookBlockingHook = (WFS_UNHOOK_BLOCKING_HOOK) GetProcAddress(hinstMsxfsLib, "WFSUnhookBlockingHook");
		lpWFSUnlock             = (WFS_UNLOCK)               GetProcAddress(hinstMsxfsLib, "WFSUnlock");
		lpWFSAsyncUnlock        = (WFS_ASYNC_UNLOCK)         GetProcAddress(hinstMsxfsLib, "WFSAsyncUnlock");
		lpWFMSetTraceLevel      = (WFM_SET_TRACE_LEVEL)      GetProcAddress(hinstMsxfsLib, "WFMSetTraceLevel");
	}
	hinstO2win32Lib = LoadLibrary("at.o2xfs.win32.dll");
	if (hinstO2win32Lib == NULL) {
		printf("Error loading at.o2xfs.win32.dll: %d\r\n", GetLastError());
	} else {
		lpGetTypeAddress = (GET_TYPE_ADDRESS) GetProcAddress(hinstO2win32Lib, "GetTypeAddress");
		lpNewBuffer = (NEW_BUFFER) GetProcAddress(hinstO2win32Lib, "NewBuffer");
	}
}

/*
 * Class:     at_o2xfs_xfs_XfsAPI
 * Method:    wfsCancelAsyncRequest0
 * Signature: (Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;)J
 */
JNIEXPORT jlong JNICALL Java_at_o2xfs_xfs_XfsAPI_wfsCancelAsyncRequest0(JNIEnv *env, jobject obj, jobject hServiceObj, jobject requestIDObj) {
	HSERVICE hService = (*(LPHSERVICE) lpGetTypeAddress(env, hServiceObj));
	REQUESTID requestID = (*(LPREQUESTID) lpGetTypeAddress(env, requestIDObj));	
	return lpWFSCancelAsyncRequest(hService, requestID);
}

/*
 * Class:     at_o2xfs_xfs_XfsAPI
 * Method:    wfsCleanUp0
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_at_o2xfs_xfs_XfsAPI_wfsCleanUp0(JNIEnv *env, jobject obj) {
	return lpWFSCleanUp();
}

/*
 * Class:     at_o2xfs_xfs_XfsAPI
 * Method:    wfsAsyncClose0
 * Signature: (Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;)I
 */
JNIEXPORT jint JNICALL Java_at_o2xfs_xfs_XfsAPI_wfsAsyncClose0(JNIEnv *env, jobject obj, jobject hServiceObj, jobject hWndObj, jobject requestIDObj) {
	HSERVICE hService = (*(LPHSERVICE) lpGetTypeAddress(env, hServiceObj));
	HWND hWnd = (HWND) (*(LPHANDLE) lpGetTypeAddress(env, hWndObj));
	LPREQUESTID lpRequestID = (LPREQUESTID) lpGetTypeAddress(env, requestIDObj);
	return lpWFSAsyncClose(hService, hWnd, lpRequestID);
}

/*
 * Class:     at_o2xfs_xfs_XfsAPI
 * Method:    wfsCreateAppHandle0
 * Signature: (Lat/o2xfs/win32/Type;)I
 */
JNIEXPORT jint JNICALL Java_at_o2xfs_xfs_XfsAPI_wfsCreateAppHandle0(JNIEnv *env, jobject obj, jobject hAppObj) {
	return lpWFSCreateAppHandle((LPHAPP) lpGetTypeAddress(env, hAppObj));
}

/*
 * Class:     at_o2xfs_xfs_XfsAPI
 * Method:    wfsAsyncDeregister0
 * Signature: (Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;)I
 */
JNIEXPORT jint JNICALL Java_at_o2xfs_xfs_XfsAPI_wfsAsyncDeregister0(JNIEnv *env, jobject obj, jobject hServiceObj, jobject dwEventClassObj, jobject hWndRegObj, jobject hWndObj, jobject requestIDObj) {
	HSERVICE hService = NULL;
	if(hServiceObj != NULL) {
		hService = (*(LPHSERVICE) lpGetTypeAddress(env, hServiceObj));
	}
	DWORD dwEventClass = NULL;
	if(dwEventClassObj != NULL) {
		dwEventClass = (*(LPDWORD) lpGetTypeAddress(env, dwEventClassObj));
	}
	HWND hWndReg = NULL;
	if(hWndRegObj != NULL) {
		hWndReg = (HWND) (*(LPHANDLE) lpGetTypeAddress(env, hWndRegObj));
	}
	HWND hWnd = (HWND) (*(LPHANDLE) lpGetTypeAddress(env, hWndObj));
	LPREQUESTID lpRequestID = (LPREQUESTID) lpGetTypeAddress(env, requestIDObj);
	return lpWFSAsyncDeregister(hService, dwEventClass, hWndReg, hWnd, lpRequestID);
}

/*
 * Class:     at_o2xfs_xfs_XfsAPI
 * Method:    wfsDestroyAppHandle0
 * Signature: (Lat/o2xfs/win32/Type;)I
 */
JNIEXPORT jint JNICALL Java_at_o2xfs_xfs_XfsAPI_wfsDestroyAppHandle0(JNIEnv *env, jobject obj, jobject hAppObj) {
	HAPP hApp = (*(LPHAPP) lpGetTypeAddress(env, hAppObj));
	return lpWFSDestroyAppHandle(hApp);
}

/*
 * Class:     at_o2xfs_xfs_XfsAPI
 * Method:    wfsAsyncExecute0
 * Signature: (Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;)I
 */
JNIEXPORT jint JNICALL Java_at_o2xfs_xfs_XfsAPI_wfsAsyncExecute0(JNIEnv *env, jobject obj, jobject hServiceObj, jobject dwCommandObj, jobject cmdDataObj, jobject dwTimeOutObj, jobject hWndObj, jobject requestIDObj) {
	HSERVICE hService = (*(LPHSERVICE) lpGetTypeAddress(env, hServiceObj));
	DWORD dwCommand = (*(LPDWORD) lpGetTypeAddress(env, dwCommandObj));
	LPVOID lpCmdData = NULL;
	if(cmdDataObj != NULL) {
		lpCmdData = lpGetTypeAddress(env, cmdDataObj);
	}
	DWORD dwTimeOut = (*(LPDWORD) lpGetTypeAddress(env, dwTimeOutObj));
	HWND hWnd = (HWND) (*(LPHANDLE) lpGetTypeAddress(env, hWndObj));
	LPREQUESTID lpRequestID = (LPREQUESTID) lpGetTypeAddress(env, requestIDObj);
	return lpWFSAsyncExecute(hService, dwCommand, lpCmdData, dwTimeOut, hWnd, lpRequestID);
}

/*
 * Class:     at_o2xfs_xfs_XfsAPI
 * Method:    wfsFreeResult0
 * Signature: (Lat/o2xfs/win32/Type;)I
 */
JNIEXPORT jint JNICALL Java_at_o2xfs_xfs_XfsAPI_wfsFreeResult0(JNIEnv *env, jobject obj, jobject resultObj) {
	LPWFSRESULT lpResult = (LPWFSRESULT) lpGetTypeAddress(env, resultObj);
	return lpWFSFreeResult(lpResult);
}

/*
 * Class:     at_o2xfs_xfs_XfsAPI
 * Method:    wfsGetInfo0
 * Signature: (Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;)J
 */
JNIEXPORT jlong JNICALL Java_at_o2xfs_xfs_XfsAPI_wfsGetInfo0(JNIEnv *env, jobject obj, jobject hServiceObj, jobject dwCategoryObj, jobject queryDetailsObj, jobject dwTimeOutObj, jobject resultObj) {
	HSERVICE hService = (*(LPHSERVICE) lpGetTypeAddress(env, hServiceObj));
	DWORD dwCategory = (*(LPDWORD) lpGetTypeAddress(env, dwCategoryObj));
	LPVOID lpQueryDetails = NULL;
	if(queryDetailsObj != NULL) {
		lpQueryDetails = lpGetTypeAddress(env, queryDetailsObj);
	}
	DWORD dwTimeOut = (*(LPDWORD) lpGetTypeAddress(env, dwTimeOutObj));
	LPWFSRESULT* lppResult = (LPWFSRESULT*) lpGetTypeAddress(env, resultObj);
	return lpWFSGetInfo(hService, dwCategory, lpQueryDetails, dwTimeOut, lppResult);
}

/*
 * Class:     at_o2xfs_xfs_XfsAPI
 * Method:    wfsAsyncGetInfo0
 * Signature: (Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;)I
 */
JNIEXPORT jint JNICALL Java_at_o2xfs_xfs_XfsAPI_wfsAsyncGetInfo0(JNIEnv *env, jobject obj, jobject hServiceObj, jobject dwCategoryObj, jobject queryDetailsObj, jobject dwTimeOutObj, jobject hWndObj, jobject requestIDObj) {
	HSERVICE hService = (*(LPHSERVICE) lpGetTypeAddress(env, hServiceObj));
	DWORD dwCategory = (*(LPDWORD) lpGetTypeAddress(env, dwCategoryObj));
	LPVOID lpQueryDetails = NULL;
	if(queryDetailsObj != NULL) {
		lpQueryDetails = lpGetTypeAddress(env, queryDetailsObj);
	}
	DWORD dwTimeOut = (*(LPDWORD) lpGetTypeAddress(env, dwTimeOutObj));
	HWND hWnd = (HWND) (*(LPHANDLE) lpGetTypeAddress(env, hWndObj));
	LPREQUESTID lpRequestID = (LPREQUESTID) lpGetTypeAddress(env, requestIDObj);
	return lpWFSAsyncGetInfo(hService, dwCategory, lpQueryDetails, dwTimeOut, hWnd, lpRequestID);
}

/*
 * Class:     at_o2xfs_xfs_XfsAPI
 * Method:    wfsAsyncOpen0
 * Signature: (Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;)I
 */
JNIEXPORT jint JNICALL Java_at_o2xfs_xfs_XfsAPI_wfsAsyncOpen0(JNIEnv *env, jobject obj, jobject logicalNameObj, jobject hAppObj, jobject appIDObj, jobject dwTraceLevelObj, jobject dwTimeOutObj, jobject hServiceObj, jobject hWndObj, jobject dwSrvcVersionsRequiredObj, jobject srvcVersionObj, jobject spiVersionObj, jobject requestIDObj) {
	LPSTR lpszLogicalName = (LPSTR) lpGetTypeAddress(env, logicalNameObj);
	HAPP hApp = (*(LPHAPP) lpGetTypeAddress(env, hAppObj));
	LPSTR lpszAppID = NULL;
	if(appIDObj != NULL) {
		lpszAppID = (LPSTR) lpGetTypeAddress(env, appIDObj);
	}
	DWORD dwTraceLevel = NULL;
	if(dwTraceLevelObj != NULL) {
		dwTraceLevel = (*(LPDWORD) lpGetTypeAddress(env, dwTraceLevelObj));
	}
	DWORD dwTimeOut = (*(LPDWORD) lpGetTypeAddress(env, dwTimeOutObj));
	LPHSERVICE lphService = (LPHSERVICE) lpGetTypeAddress(env, hServiceObj);
	HWND hWnd = (HWND) (*(LPHANDLE) lpGetTypeAddress(env, hWndObj));
	DWORD dwSrvcVersionsRequired = (*(LPDWORD) lpGetTypeAddress(env, dwSrvcVersionsRequiredObj));
	LPWFSVERSION lpSrvcVersion = (LPWFSVERSION) lpGetTypeAddress(env, srvcVersionObj);
	LPWFSVERSION lpSPIVersion = (LPWFSVERSION) lpGetTypeAddress(env, spiVersionObj);
	LPREQUESTID lpRequestID = (LPREQUESTID) lpGetTypeAddress(env, requestIDObj);
	printf("WFSAsyncOpen: lpszLogicalName=%s,hApp=%p,lpszAppID=%s,dwTraceLevel=%i,dwTimeOut=%i,lphService=%p,hWnd=%p,dwSrvcVersionsRequired=%p,lpSPIVersion=%p,lpRequestID=%p\n", lpszLogicalName, hApp, lpszAppID, dwTraceLevel, dwTimeOut, lphService, hWnd, dwSrvcVersionsRequired, lpSrvcVersion, lpSPIVersion, lpRequestID);
	return lpWFSAsyncOpen(lpszLogicalName, hApp, lpszAppID, dwTraceLevel, dwTimeOut, lphService, hWnd, dwSrvcVersionsRequired, lpSrvcVersion, lpSPIVersion, lpRequestID);
}

/*
 * Class:     at_o2xfs_xfs_XfsAPI
 * Method:    wfsAsyncRegister0
 * Signature: (Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;)I
 */
JNIEXPORT jint JNICALL Java_at_o2xfs_xfs_XfsAPI_wfsAsyncRegister0(JNIEnv *env, jobject obj, jobject hServiceObj, jobject dwEventClassObj, jobject hWndRegObj, jobject hWndObj, jobject requestIDObj) {
	HSERVICE hService = NULL;
	if(hServiceObj != NULL) {
		hService = (*(LPHSERVICE) lpGetTypeAddress(env, hServiceObj));
	}
	DWORD dwEventClass = NULL;
	if(dwEventClassObj != NULL) {
		dwEventClass = (*(LPDWORD) lpGetTypeAddress(env, dwEventClassObj));
	}
	HWND hWndReg =(HWND) (*(LPHANDLE) lpGetTypeAddress(env, hWndRegObj));
	HWND hWnd = (HWND) (*(LPHANDLE) lpGetTypeAddress(env, hWndObj));
	LPREQUESTID lpRequestID = (LPREQUESTID) lpGetTypeAddress(env, requestIDObj);
	return lpWFSAsyncRegister(hService, dwEventClass, hWndReg, hWnd, lpRequestID);
}

/*
 * Class:     at_o2xfs_xfs_XfsAPI
 * Method:    wfsStartUp0
 * Signature: (Lat/o2xfs/win32/Type;Lat/o2xfs/win32/Type;)I
 */
JNIEXPORT jint JNICALL Java_at_o2xfs_xfs_XfsAPI_wfsStartUp0(JNIEnv *env, jobject obj, jobject versionsRequired, jobject wfsVersion) {
	DWORD dwVersionsRequired = (*(LPDWORD) lpGetTypeAddress(env, versionsRequired));
	printf("WFSStartUp: %d.%d - %d.%d\n", LOBYTE(HIWORD(dwVersionsRequired)), HIBYTE(HIWORD(dwVersionsRequired)), LOBYTE(LOWORD(dwVersionsRequired)), HIBYTE(LOWORD(dwVersionsRequired)));
	LPWFSVERSION lpWFSVersion = (LPWFSVERSION) lpGetTypeAddress(env, wfsVersion);
	return lpWFSStartUp(dwVersionsRequired, lpWFSVersion);
}