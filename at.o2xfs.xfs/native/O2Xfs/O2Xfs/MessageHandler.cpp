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
#include "at_o2xfs_xfs_util_MessageHandler.h"

LRESULT CALLBACK WndProc (HWND, UINT, WPARAM, LPARAM);
JavaVM *jvm = NULL;
JNIEnv *g_pEnv = NULL;
jobject handlerObj = NULL;
jmethodID callbackMethod = NULL;

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
	jvm = vm;
	return JNI_VERSION_1_6;
}

JNIEXPORT void JNICALL Java_at_o2xfs_xfs_util_MessageHandler_run0(JNIEnv *env, jobject obj, jobject hWndBuf) {
	static TCHAR szAppName[] = TEXT("XFSWrapper");
    WNDCLASS wndclass;
	MSG msg;
	wndclass.style = CS_HREDRAW | CS_VREDRAW ;
	wndclass.lpfnWndProc = WndProc ;
	wndclass.cbClsExtra = 0;
	wndclass.cbWndExtra = 0;
	wndclass.hInstance = hInstance;
	wndclass.hIcon = LoadIcon (NULL, IDI_APPLICATION);
	wndclass.hCursor = LoadCursor (NULL, IDC_ARROW);
	wndclass.hbrBackground = (HBRUSH) GetStockObject (WHITE_BRUSH);
	wndclass.lpszMenuName = NULL;
	wndclass.lpszClassName = szAppName;

	if (!RegisterClass(&wndclass)) {
		MessageBox(NULL, TEXT ("Program requires Windows NT!"), szAppName, MB_ICONERROR);
		return;
	}

	HWND hWnd = CreateWindow(szAppName, TEXT("TITLE"),WS_OVERLAPPEDWINDOW, CW_USEDEFAULT, CW_USEDEFAULT, CW_USEDEFAULT, CW_USEDEFAULT, NULL, NULL, hInstance, NULL);
	jmethodID methodID = GetMethodID(env, env->GetObjectClass(obj), "hWnd", "()V");	
	memcpy(env->GetDirectBufferAddress(hWndBuf), &hWnd, sizeof(HWND));
	env->CallVoidMethod(obj, methodID);

	handlerObj = env->NewGlobalRef(obj);
	callbackMethod = GetMethodID(env, env->GetObjectClass(handlerObj), "callback", "(ILjava/nio/ByteBuffer;)V");

	ShowWindow(hWnd, SW_SHOWMINIMIZED);
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
		if(g_pEnv == NULL) {
			if(jvm->AttachCurrentThread((void **) &g_pEnv, NULL) < 0) {
				printf("Attaching current thread failed\n");
				return 0;
			} else {
				printf("Successfully attached current thread\n");
			}
		}
		g_pEnv->CallVoidMethod(handlerObj, callbackMethod, msg, g_pEnv->NewDirectByteBuffer((void *) lParam, sizeof(WFSRESULT)));
		jint r = jvm->DetachCurrentThread();
		if(r == 0) {
			printf("Successfully detached current thread\n");
			g_pEnv = NULL;
		} else {
			printf("Detaching current thread failed (%i)\n", r);
		}
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

JNIEXPORT void JNICALL Java_at_o2xfs_xfs_util_MessageHandler_close(JNIEnv *env, jobject obj, jobject hWnd) {
	SendMessage((HWND) (*(LPDWORD) env->GetDirectBufferAddress(hWnd)), WM_CLOSE, 0, 0);
}
