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

#include "O2Xfs.h"
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

JNIEXPORT jlong JNICALL Java_at_o2xfs_xfs_XfsAPI_wfsCancelAsyncRequest(JNIEnv *env, jobject obj, jobject lphService, jobject lpRequestID) {
	REQUESTID RequestID = NULL;
	if(lpRequestID != NULL) {
		RequestID = (*(LPREQUESTID) env->GetDirectBufferAddress(lpRequestID));
	}
	return WFSCancelAsyncRequest((*(LPHSERVICE) env->GetDirectBufferAddress(lphService)), RequestID); 
}

JNIEXPORT jint JNICALL Java_at_o2xfs_xfs_XfsAPI_wfsStartUp0(JNIEnv *env, jobject obj, jobject dwVersionsRequired, jobject lpWFSVersion) {
	return WFSStartUp((*(LPDWORD) env->GetDirectBufferAddress(dwVersionsRequired)), (LPWFSVERSION) env->GetDirectBufferAddress(lpWFSVersion));	
}

JNIEXPORT jint JNICALL Java_at_o2xfs_xfs_XfsAPI_wfsCleanUp0(JNIEnv *env, jobject obj) {
	return WFSCleanUp();
}

/*
 * Class:     at_o2xfs_xfs_XfsAPI
 * Method:    wfsCreateAppHandle
 * Signature: (Ljava/nio/ByteBuffer;)I
 */
JNIEXPORT jint JNICALL Java_at_o2xfs_xfs_XfsAPI_wfsCreateAppHandle(JNIEnv *env, jobject obj, jobject lphApp) {
	return WFSCreateAppHandle((LPHAPP) env->GetDirectBufferAddress(lphApp));
}

/*
 * Class:     at_o2xfs_xfs_XfsAPI
 * Method:    wfsAsyncDeregister
 * Signature: (Ljava/nio/ByteBuffer;Ljava/nio/ByteBuffer;Ljava/nio/ByteBuffer;Ljava/nio/ByteBuffer;Ljava/nio/ByteBuffer;)I
 */
JNIEXPORT jint JNICALL Java_at_o2xfs_xfs_XfsAPI_wfsAsyncDeregister(JNIEnv *env, jobject obj, jobject lphService, jobject lpdwEventClass, jobject lphWndReg, jobject lphWnd, jobject lpRequestID) {
	HSERVICE hService = NULL;
	if(lphService != NULL) {
		hService = (*(LPHSERVICE) env->GetDirectBufferAddress(lphService));
	}
	DWORD dwEventClass = NULL;
	if(lpdwEventClass != NULL) {
		dwEventClass = *((LPDWORD) env->GetDirectBufferAddress(lpdwEventClass));
	}
	HWND hWndReg = NULL;
	if(lphWndReg != NULL) {
		hWndReg = (HWND) (*(LPDWORD) env->GetDirectBufferAddress(lphWndReg));
	}
	return WFSAsyncDeregister(hService, dwEventClass, hWndReg, (HWND) (*(LPDWORD) env->GetDirectBufferAddress(lphWnd)), (LPREQUESTID) env->GetDirectBufferAddress(lpRequestID));
}

/*
 * Class:     at_o2xfs_xfs_XfsAPI
 * Method:    wfsDestroyAppHandle
 * Signature: (Ljava/nio/ByteBuffer;)I
 */
JNIEXPORT jint JNICALL Java_at_o2xfs_xfs_XfsAPI_wfsDestroyAppHandle(JNIEnv *env, jobject obj, jobject lphApp) {
	return WFSDestroyAppHandle((*(LPHAPP) env->GetDirectBufferAddress(lphApp)));
}

/*
 * Class:     at_o2xfs_xfs_XfsAPI
 * Method:    wfsAsyncExecute
 * Signature: (Ljava/nio/ByteBuffer;Ljava/nio/ByteBuffer;Ljava/nio/ByteBuffer;Ljava/nio/ByteBuffer;Ljava/nio/ByteBuffer;Ljava/nio/ByteBuffer;)I
 */
JNIEXPORT jint JNICALL Java_at_o2xfs_xfs_XfsAPI_wfsAsyncExecute(JNIEnv *env, jobject obj, jobject hService, jobject lpdwCommand, jobject lpCmdData, jobject dwTimeOut, jobject hWnd, jobject lpRequestID) {
	return WFSAsyncExecute((*(LPHSERVICE) env->GetDirectBufferAddress(hService)), (*(LPDWORD) env->GetDirectBufferAddress(lpdwCommand)), (lpCmdData == NULL ? NULL : (LPVOID) env->GetDirectBufferAddress(lpCmdData)), (*(LPDWORD) env->GetDirectBufferAddress(dwTimeOut)), (HWND) (*(LPDWORD) env->GetDirectBufferAddress(hWnd)), (LPREQUESTID) env->GetDirectBufferAddress(lpRequestID));
}

JNIEXPORT jint JNICALL Java_at_o2xfs_xfs_XfsAPI_wfsFreeResult(JNIEnv *env, jobject obj, jobject lpResult) {
	return WFSFreeResult((LPWFSRESULT) env->GetDirectBufferAddress(lpResult));
}

JNIEXPORT jlong JNICALL Java_at_o2xfs_xfs_XfsAPI_wfsGetInfo(JNIEnv *env, jobject obj, jobject lphService, jobject lpdwCategory, jobject lpQueryDetails, jobject lpdwTimeOut, jobject lppResult) {
	return WFSGetInfo((*(LPHSERVICE) env->GetDirectBufferAddress(lphService)), (*(LPDWORD) env->GetDirectBufferAddress(lpdwCategory)), (lpQueryDetails == NULL ? NULL : (LPVOID) env->GetDirectBufferAddress(lpQueryDetails)), (*(LPDWORD) env->GetDirectBufferAddress(lpdwTimeOut)), (LPWFSRESULT*) env->GetDirectBufferAddress(lppResult));
}

JNIEXPORT jint JNICALL Java_at_o2xfs_xfs_XfsAPI_wfsAsyncGetInfo(JNIEnv *env, jobject obj, jobject lphService, jobject lpdwCategory, jobject lpQueryDetails, jobject lpdwTimeOut, jobject lphWnd, jobject lpRequestID) {
	return WFSAsyncGetInfo((*(LPHSERVICE) env->GetDirectBufferAddress(lphService)), (*(LPDWORD) env->GetDirectBufferAddress(lpdwCategory)), (lpQueryDetails == NULL ? NULL : (LPVOID) env->GetDirectBufferAddress(lpQueryDetails)), (*(LPDWORD) env->GetDirectBufferAddress(lpdwTimeOut)), (HWND) (*(LPDWORD) env->GetDirectBufferAddress(lphWnd)), (LPREQUESTID) env->GetDirectBufferAddress(lpRequestID));
}

/*
 * Class:     at_o2xfs_xfs_XfsAPI
 * Method:    wfsAsyncOpen
 * Signature: (Ljava/nio/ByteBuffer;Ljava/nio/ByteBuffer;Ljava/nio/ByteBuffer;Ljava/nio/ByteBuffer;Ljava/nio/ByteBuffer;Ljava/nio/ByteBuffer;Ljava/nio/ByteBuffer;Ljava/nio/ByteBuffer;Ljava/nio/ByteBuffer;Ljava/nio/ByteBuffer;Ljava/nio/ByteBuffer;)I
 */
JNIEXPORT jint JNICALL Java_at_o2xfs_xfs_XfsAPI_wfsAsyncOpen(JNIEnv *env, jobject obj, jobject lpszLogicalName, jobject lphApp, jobject lpszAppID, jobject dwTraceLevel, jobject dwTimeOut, jobject lphService, jobject hWndBuf, jobject dwSrvcVersionsRequired, jobject lpSrvcVersion, jobject lpSPIVersion, jobject lpRequestID) {	
	LPSTR appID = NULL;
	if(lpszAppID != NULL) {
		appID = (LPSTR) env->GetDirectBufferAddress(lpszAppID);
	}
	DWORD traceLevel = NULL;
	if(dwTraceLevel != NULL) {
		traceLevel = (*(LPDWORD) env->GetDirectBufferAddress(dwTraceLevel));
	}
	return WFSAsyncOpen((LPSTR) env->GetDirectBufferAddress(lpszLogicalName), (*(LPHAPP) env->GetDirectBufferAddress(lphApp)), appID, traceLevel, (*(LPDWORD) env->GetDirectBufferAddress(dwTimeOut)), (LPHSERVICE) env->GetDirectBufferAddress(lphService), (HWND) (*(LPDWORD) env->GetDirectBufferAddress(hWndBuf)), (*(LPDWORD) env->GetDirectBufferAddress(dwSrvcVersionsRequired)), (LPWFSVERSION) env->GetDirectBufferAddress(lpSPIVersion), (LPWFSVERSION) env->GetDirectBufferAddress(lpSrvcVersion), (LPREQUESTID) env->GetDirectBufferAddress(lpRequestID));
}

/*
 * Class:     at_o2xfs_xfs_XfsAPI
 * Method:    WFSAsyncRegister
 * Signature: (Ljava/nio/ByteBuffer;Ljava/nio/ByteBuffer;Ljava/nio/ByteBuffer;Ljava/nio/ByteBuffer;Ljava/nio/ByteBuffer;)V
 */
JNIEXPORT jint JNICALL Java_at_o2xfs_xfs_XfsAPI_WFSAsyncRegister(JNIEnv *env, jobject obj, jobject lphService, jobject lpdwEventClass, jobject lphWndReg, jobject lphWnd, jobject lpRequestID) {
	HSERVICE hService = NULL;
	if(lphService != NULL) {
		hService = (*(LPHSERVICE) env->GetDirectBufferAddress(lphService));
	}
	return WFSAsyncRegister(hService, (*(LPDWORD) env->GetDirectBufferAddress(lpdwEventClass)), (HWND) (*(LPDWORD) env->GetDirectBufferAddress(lphWndReg)), (HWND) (*(LPDWORD) env->GetDirectBufferAddress(lphWnd)), (LPREQUESTID) env->GetDirectBufferAddress(lpRequestID));
}

/*
 * Class:     at_o2xfs_xfs_XfsAPI
 * Method:    wfsAsyncClose
 * Signature: (Ljava/nio/ByteBuffer;Ljava/nio/ByteBuffer;Ljava/nio/ByteBuffer;)I
 */
JNIEXPORT jint JNICALL Java_at_o2xfs_xfs_XfsAPI_wfsAsyncClose(JNIEnv *env, jobject obj, jobject hService, jobject hWnd, jobject lpRequestID) {
	return WFSAsyncClose((*(LPHSERVICE) env->GetDirectBufferAddress(hService)), (HWND) (*(LPDWORD) env->GetDirectBufferAddress(hWnd)), (LPREQUESTID) env->GetDirectBufferAddress(lpRequestID));
}

