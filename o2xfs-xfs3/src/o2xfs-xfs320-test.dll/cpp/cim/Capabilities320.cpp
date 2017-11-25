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

#include "cim/at_o2xfs_xfs_v3_20_cim_Capabilities320Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

static WFSCIMCAPS Caps;
static LPSTR lpszExtra = "Key=Value\0";

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_120_cim_Capabilities320Test_buildCapabilities320(JNIEnv *env, jobject obj) {
	Caps.wClass = WFS_SERVICE_CLASS_CIM;
	Caps.fwType = WFS_CIM_SELFSERVICEBILL;
	Caps.wMaxCashInItems = 200;
	Caps.bCompound = TRUE;
	Caps.bShutter = TRUE;
	Caps.bShutterControl = FALSE;
	Caps.bSafeDoor = FALSE;
	Caps.bCashBox = FALSE;
	Caps.fwIntermediateStacker = 200;
	Caps.bItemsTakenSensor = TRUE;
	Caps.bItemsInsertedSensor = TRUE;
	Caps.fwPositions = WFS_CIM_POSINFRONT | WFS_CIM_POSOUTFRONT;
	Caps.fwExchangeType = WFS_CIM_EXBYHAND;
	Caps.fwRetractAreas = WFS_CIM_RA_TRANSPORT;
	Caps.fwRetractTransportActions = WFS_CIM_PRESENT | WFS_CIM_RETRACT;
	Caps.fwRetractStackerActions = WFS_CIM_NOTSUPP;
	Caps.lpszExtra = lpszExtra;
	Caps.dwGuidLights[WFS_CIM_GUIDANCE_POSINFRONT] = WFS_CIM_GUIDANCE_SLOW_FLASH | WFS_CIM_GUIDANCE_MEDIUM_FLASH | WFS_CIM_GUIDANCE_QUICK_FLASH | WFS_CIM_GUIDANCE_CONTINUOUS | WFS_CIM_GUIDANCE_GREEN | WFS_CIM_GUIDANCE_RED | WFS_CIM_GUIDANCE_YELLOW;
	Caps.dwItemInfoTypes = WFS_CIM_ITEM_SERIALNUMBER | WFS_CIM_ITEM_SIGNATURE;
	Caps.bCompareSignatures = FALSE;
	Caps.bPowerSaveControl = FALSE;
	Caps.bReplenish = TRUE;
	Caps.fwCashInLimit = WFS_CIM_LIMITBYTOTALITEMS | WFS_CIM_LIMITBYAMOUNT;
	Caps.fwCountActions = WFS_CIM_COUNTINDIVIDUAL | WFS_CIM_COUNTALL;
	Caps.bDeviceLockControl = TRUE;
	Caps.wMixedMode = WFS_CIM_IPMMIXEDMEDIA;
	Caps.bMixedDepositAndRollback = TRUE;
	Caps.bAntiFraudModule = TRUE;
	return NewBuffer(env, &Caps, sizeof(WFSCIMCAPS));
}
