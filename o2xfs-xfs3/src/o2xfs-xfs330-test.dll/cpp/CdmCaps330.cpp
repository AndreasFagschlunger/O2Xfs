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

#include "at_o2xfs_xfs_v3_30_cdm_CdmCaps330Test.h"

#include <Windows.h>
#include <XFSCDM.H>
#include "at.o2xfs.win32.h"

static WFSCDMCAPS caps;
static LPSTR lpszExtra = "Key1=Value1\0Key2=Value2\0";
static DWORD synchronizableCommands[3];

BOOL WINAPI DllMain(HINSTANCE hinstDLL, DWORD fdwReason, LPVOID lpvReserved) {
	return TRUE;
}

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
	return JNI_VERSION_1_6;
}

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_130_cdm_CdmCaps330Test_buildCdmCaps330(JNIEnv *env, jobject obj) {
	caps.wClass = WFS_SERVICE_CLASS_CDM;
	caps.fwType = WFS_CDM_SELFSERVICEBILL;
	caps.wMaxDispenseItems = 40;
	caps.bCompound = false;
	caps.bShutter = true;
	caps.bShutterControl = false;
	caps.fwRetractAreas = WFS_CDM_RA_REJECT | WFS_CDM_RA_RETRACT | WFS_CDM_RA_TRANSPORT;
	caps.fwRetractTransportActions = WFS_CDM_PRESENT | WFS_CDM_RETRACT | WFS_CDM_REJECT;
	caps.fwRetractStackerActions = WFS_CDM_PRESENT | WFS_CDM_RETRACT | WFS_CDM_REJECT;
	caps.bSafeDoor = false;
	caps.bCashBox = false;
	caps.bIntermediateStacker = true;
	caps.bItemsTakenSensor = true;
	caps.fwPositions = WFS_CDM_POSFRONT;
	caps.fwMoveItems = WFS_CDM_FROMCU | WFS_CDM_TOTRANSPORT;
	caps.fwExchangeType = WFS_CDM_EXBYHAND;
	caps.lpszExtra = lpszExtra;
	caps.dwGuidLights[WFS_CDM_GUIDANCE_POSOUTFRONT] = WFS_CDM_GUIDANCE_SLOW_FLASH | WFS_CDM_GUIDANCE_MEDIUM_FLASH | WFS_CDM_GUIDANCE_QUICK_FLASH | WFS_CDM_GUIDANCE_GREEN | WFS_CDM_GUIDANCE_RED;
	caps.bPowerSaveControl = true;
	caps.bPrepareDispense = true;
	caps.bAntiFraudModule = true;
	caps.dwItemInfoTypes = WFS_CDM_ITEM_SERIALNUMBER | WFS_CDM_ITEM_SIGNATURE | WFS_CDM_ITEM_IMAGEFILE;
	caps.bBlacklist = true;

	synchronizableCommands[0] = WFS_CMD_CDM_DISPENSE;
	synchronizableCommands[1] = WFS_CMD_CDM_PRESENT;
	caps.lpdwSynchronizableCommands = synchronizableCommands;

	return NewBuffer(env, &caps, sizeof(WFSCDMCAPS));
}
