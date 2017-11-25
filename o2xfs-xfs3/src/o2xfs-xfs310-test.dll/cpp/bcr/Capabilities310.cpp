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

#include "bcr/at_o2xfs_xfs_v3_10_bcr_Capabilities310Test.h"

#include <Windows.h>
#include <XFSBCR.H>
#include "at.o2xfs.win32.h"

static WFSBCRCAPS Caps;
static WORD wSymbologies[] = { WFS_BCR_SYM_EAN128, WFS_BCR_SYM_JAN13, 0 };
static LPSTR lpszExtra = "Key1=Value1\0Key2=Value2\0";

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_110_bcr_Capabilities310Test_buildCapabilities310(JNIEnv *env, jobject obj) {
	Caps.wClass = WFS_SERVICE_CLASS_BCR;
	Caps.bCompound = FALSE;
	Caps.bCanFilterSymbologies = TRUE;
	Caps.lpwSymbologies = wSymbologies;
	Caps.dwGuidLights[WFS_BCR_GUIDANCE_BCR] = WFS_BCR_GUIDANCE_MEDIUM_FLASH | WFS_BCR_GUIDANCE_GREEN;
	Caps.lpszExtra = lpszExtra;
	Caps.bPowerSaveControl = TRUE;
	return NewBuffer(env, &Caps, sizeof(WFSBCRCAPS));
}
