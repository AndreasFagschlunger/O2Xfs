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

#ifndef _O2XFS_
#define _O2XFS_

#include <Windows.h>
#include <XFSAPI.H>
#include "JNIUtil.h"
#include "at.o2xfs.win32.h"

extern HINSTANCE hInstance;

typedef HRESULT(WINAPI *WFS_CANCEL_ASYNC_REQUEST)  (HSERVICE hService, REQUESTID RequestID);
typedef HRESULT(WINAPI *WFS_CANCEL_BLOCKING_CALL)  (DWORD dwThreadID);
typedef HRESULT(WINAPI *WFS_CLEAN_UP)  ();
typedef HRESULT(WINAPI *WFS_CLOSE)  (HSERVICE hService);
typedef HRESULT(WINAPI *WFS_ASYNC_CLOSE)  (HSERVICE hService, HWND hWnd, LPREQUESTID lpRequestID);
typedef HRESULT(WINAPI *WFS_CREATE_APP_HANDLE)  (LPHAPP lphApp);
typedef HRESULT(WINAPI *WFS_DEREGISTER)  (HSERVICE hService, DWORD dwEventClass, HWND hWndReg);
typedef HRESULT(WINAPI *WFS_ASYNC_DEREGISTER)  (HSERVICE hService, DWORD dwEventClass, HWND hWndReg, HWND hWnd, LPREQUESTID lpRequestID);
typedef HRESULT(WINAPI *WFS_DESTROY_APP_HANDLE)  (HAPP hApp);
typedef HRESULT(WINAPI *WFS_EXECUTE)  (HSERVICE hService, DWORD dwCommand, LPVOID lpCmdData, DWORD dwTimeOut, LPWFSRESULT * lppResult);
typedef HRESULT(WINAPI *WFS_ASYNC_EXECUTE)  (HSERVICE hService, DWORD dwCommand, LPVOID lpCmdData, DWORD dwTimeOut, HWND hWnd, LPREQUESTID lpRequestID);
typedef HRESULT(WINAPI *WFS_FREE_RESULT)  (LPWFSRESULT lpResult);
typedef HRESULT(WINAPI *WFS_GET_INFO)  (HSERVICE hService, DWORD dwCategory, LPVOID lpQueryDetails, DWORD dwTimeOut, LPWFSRESULT * lppResult);
typedef HRESULT(WINAPI *WFS_ASYNC_GET_INFO)  (HSERVICE hService, DWORD dwCategory, LPVOID lpQueryDetails, DWORD dwTimeOut, HWND hWnd, LPREQUESTID lpRequestID);
typedef BOOL(WINAPI *WFS_IS_BLOCKING)  ();
typedef HRESULT(WINAPI *WFS_LOCK)  (HSERVICE hService, DWORD dwTimeOut, LPWFSRESULT * lppResult);
typedef HRESULT(WINAPI *WFS_ASYNC_LOCK)  (HSERVICE hService, DWORD dwTimeOut, HWND hWnd, LPREQUESTID lpRequestID);
typedef HRESULT(WINAPI *WFS_OPEN)  (LPSTR lpszLogicalName, HAPP hApp, LPSTR lpszAppID, DWORD dwTraceLevel, DWORD dwTimeOut, DWORD dwSrvcVersionsRequired, LPWFSVERSION lpSrvcVersion, LPWFSVERSION lpSPIVersion, LPHSERVICE lphService);
typedef HRESULT(WINAPI *WFS_ASYNC_OPEN)  (LPSTR lpszLogicalName, HAPP hApp, LPSTR lpszAppID, DWORD dwTraceLevel, DWORD dwTimeOut, LPHSERVICE lphService, HWND hWnd, DWORD dwSrvcVersionsRequired, LPWFSVERSION lpSrvcVersion, LPWFSVERSION lpSPIVersion, LPREQUESTID lpRequestID);
typedef HRESULT(WINAPI *WFS_REGISTER)  (HSERVICE hService, DWORD dwEventClass, HWND hWndReg);
typedef HRESULT(WINAPI *WFS_ASYNC_REGISTER)  (HSERVICE hService, DWORD dwEventClass, HWND hWndReg, HWND hWnd, LPREQUESTID lpRequestID);
typedef HRESULT(WINAPI *WFS_SET_BLOCKING_HOOK)  (XFSBLOCKINGHOOK lpBlockFunc, LPXFSBLOCKINGHOOK lppPrevFunc);
typedef HRESULT(WINAPI *WFS_START_UP)  (DWORD dwVersionsRequired, LPWFSVERSION lpWFSVersion);
typedef HRESULT(WINAPI *WFS_UNHOOK_BLOCKING_HOOK)  ();
typedef HRESULT(WINAPI *WFS_UNLOCK)  (HSERVICE hService);
typedef HRESULT(WINAPI *WFS_ASYNC_UNLOCK)  (HSERVICE hService, HWND hWnd, LPREQUESTID lpRequestID);
typedef HRESULT(WINAPI *WFM_SET_TRACE_LEVEL)  (HSERVICE hService, DWORD dwTraceLevel);

typedef LPVOID(__cdecl *GET_TYPE_ADDRESS) (JNIEnv *env, jobject type);
typedef jobject(__cdecl *NEW_BUFFER) (JNIEnv *env, LPVOID address, jint size);

extern GET_TYPE_ADDRESS lpGetTypeAddress;
extern NEW_BUFFER lpNewBuffer;

#endif // _O2XFS_