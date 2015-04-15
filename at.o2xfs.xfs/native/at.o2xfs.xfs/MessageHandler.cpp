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
#include "at_o2xfs_xfs_util_MessageHandler.h"

LRESULT CALLBACK WndProc (HWND, UINT, WPARAM, LPARAM);
JavaVM *jvm = NULL;
JNIEnv *g_pEnv = NULL;
jobject handlerObj = NULL;

static jmethodID callbackMID = NULL;

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
	jvm = vm;
	return JNI_VERSION_1_6;
}

/*
 * Class:     at_o2xfs_xfs_util_MessageHandler
 * Method:    init
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_at_o2xfs_xfs_util_MessageHandler_init(JNIEnv *env, jclass clazz) {
	callbackMID = (jmethodID) env->GetMethodID(clazz, "callback", "(ILat/o2xfs/win32/Buffer;)V");
}

JNIEXPORT void JNICALL Java_at_o2xfs_xfs_util_MessageHandler_run0(JNIEnv *env, jobject obj, jobject hWndBuf) {
	static TCHAR szAppName[] = TEXT("XFSWrapper");
    WNDCLASS wndclass;
	MSG msg;
	wndclass.style = 0;
	wndclass.lpfnWndProc = WndProc;
	wndclass.cbClsExtra = 0;
	wndclass.cbWndExtra = 0;
	wndclass.hInstance = hInstance;
	wndclass.hIcon = LoadIcon(NULL, IDI_APPLICATION);
	wndclass.hCursor = NULL;
	wndclass.hbrBackground = (HBRUSH) GetStockObject (WHITE_BRUSH);
	wndclass.lpszMenuName = NULL;
	wndclass.lpszClassName = szAppName;

	if (!RegisterClass(&wndclass)) {
		MessageBox(NULL, TEXT("Program requires Windows NT!"), szAppName, MB_ICONERROR);
		return;
	}

	HWND hWnd = CreateWindow(szAppName, TEXT("O2Xfs"), WS_DISABLED, -1, -1, 0, 0, NULL, NULL, hInstance, NULL);
	jmethodID methodID = GetMethodID(env, env->GetObjectClass(obj), "hWnd", "()V");	
	memcpy(GetTypeAddress(env, hWndBuf), &hWnd, sizeof(HWND));
	env->CallVoidMethod(obj, methodID);
	handlerObj = env->NewGlobalRef(obj);

	g_pEnv = env;

	UpdateWindow(hWnd);
	printf("MSG LOOP - START\n");
	while (GetMessage(&msg, NULL, 0, 0) > 0) {
		TranslateMessage(&msg);
		DispatchMessage(&msg);
	}
	printf("MSG LOOP - END\n");
	return;
}

LRESULT CALLBACK WndProc(HWND hWnd, UINT msg, WPARAM wParam, LPARAM lParam) {
	if(msg > WM_USER) {
		g_pEnv->CallVoidMethod(handlerObj, callbackMID, msg, NewBuffer(g_pEnv, &lParam, sizeof(lParam)));
		return 0;
	} else {
		switch(msg) {
			case WM_DESTROY:
				PostQuitMessage(0);
				return 0;
		}
	}
	return DefWindowProc(hWnd, msg, wParam, lParam);
}

/*
 * Class:     at_o2xfs_xfs_util_MessageHandler
 * Method:    close0
 * Signature: (Lat/o2xfs/win32/Type;)V
 */
JNIEXPORT void JNICALL Java_at_o2xfs_xfs_util_MessageHandler_close0(JNIEnv *env, jobject obj, jobject hWndObj) {
	SendMessage((HWND) (*(LPHANDLE) GetTypeAddress(env, hWndObj)), WM_CLOSE, 0, 0);
}
