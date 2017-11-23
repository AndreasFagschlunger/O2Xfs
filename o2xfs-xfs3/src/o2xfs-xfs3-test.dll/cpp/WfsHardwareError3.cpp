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

#include "at_o2xfs_xfs_v3_00_WfsHardwareError3Test.h"

#include <Windows.h>
#include <XFSCDM.H>
#include "at.o2xfs.win32.h"

static WFSHWERROR hwError;
static WFSHWERROR hwError2;

static LPSTR lpszLogicalName = "LogicalName";
static LPSTR lpszPhysicalName = "PhysicalName";
static LPSTR lpszWorkstationName = "WorkstationName";
static LPSTR lpszAppID = "AppID";
static LPSTR lpszDescription = "Description";

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_100_WfsHardwareError3Test_buildWfsHardwareError3(JNIEnv *env, jobject obj) {
	hwError.lpszLogicalName = lpszLogicalName;
	hwError.lpszPhysicalName = lpszPhysicalName;
	hwError.lpszWorkstationName = lpszWorkstationName;
	hwError.lpszAppID = lpszAppID;
	hwError.dwAction = WFS_ERR_ACT_HWCLEAR;
	hwError.dwSize = strlen(lpszDescription);
	hwError.lpbDescription = (LPBYTE) lpszDescription;
	return NewBuffer(env, &hwError, sizeof(WFSHWERROR));
}

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_100_WfsHardwareError3Test_buildDescriptionNull(JNIEnv *env, jobject obj) {
	hwError2.lpszLogicalName = lpszLogicalName;
	hwError2.lpszPhysicalName = lpszPhysicalName;
	hwError2.lpszWorkstationName = lpszWorkstationName;
	hwError2.lpszAppID = lpszAppID;
	hwError2.dwAction = WFS_ERR_ACT_HWCLEAR;
	hwError2.dwSize = 0;
	hwError2.lpbDescription = NULL;
	return NewBuffer(env, &hwError2, sizeof(WFSHWERROR));
}