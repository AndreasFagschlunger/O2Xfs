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

#include "cim/at_o2xfs_xfs_v3_00_cim_Capabilities3Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

static WFSCIMCAPS caps;
static LPSTR lpszExtra = "P6=2\0";

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_100_cim_Capabilities3Test_buildCapabilities3(JNIEnv *env, jobject obj) {
	caps.wClass = WFS_SERVICE_CLASS_CIM;
	caps.fwType = WFS_CIM_SELFSERVICEBILL;
	caps.wMaxCashInItems = 200;
	caps.bCompound = TRUE;
	caps.bShutter = TRUE;
	caps.bShutterControl = TRUE;
	caps.bSafeDoor = TRUE;
	caps.bCashBox = FALSE;
	caps.fwIntermediateStacker = 200;
	caps.bItemsTakenSensor = TRUE;
	caps.bItemsInsertedSensor = TRUE;
	caps.fwPositions = WFS_CIM_POSINTOP | WFS_CIM_POSOUTTOP;
	caps.fwExchangeType = WFS_CIM_EXBYHAND;
	caps.fwRetractAreas = WFS_CIM_RA_RETRACT | WFS_CIM_RA_TRANSPORT | WFS_CIM_RA_STACKER;
	caps.fwRetractTransportActions = WFS_CIM_RETRACT;
	caps.fwRetractStackerActions = WFS_CIM_RETRACT;
	caps.lpszExtra = lpszExtra;
	return NewBuffer(env, &caps, sizeof(WFSCIMCAPS));
}
