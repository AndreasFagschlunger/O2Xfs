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

#include "at_o2xfs_xfs_v3_10_cdm_CdmStatus310Test.h"

#include <Windows.h>
#include <XFSCDM.H>
#include "at.o2xfs.win32.h"

static WFSCDMSTATUS status;
static WFSCDMOUTPOS position;
static LPWFSCDMOUTPOS positions[2];
static LPSTR lpszExtra = "Key1=Value1\0Key2=Value2\0";

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_110_cdm_CdmStatus310Test_buildCdmStatus310(JNIEnv *env, jobject object) {
	position.fwPosition = WFS_CDM_POSFRONT;
	position.fwShutter = WFS_CDM_SHTCLOSED;
	position.fwPositionStatus = WFS_CDM_PSEMPTY;
	position.fwTransport = WFS_CDM_TPOK;
	position.fwTransportStatus = WFS_CDM_TPSTATEMPTY;
	positions[0] = &position;

	status.fwDevice = WFS_CDM_DEVONLINE;
	status.fwDispenser = WFS_CDM_DISPOK;
	status.fwIntermediateStacker = WFS_CDM_ISEMPTY;
	status.fwSafeDoor = WFS_CDM_DOORCLOSED;
	status.lppPositions = positions;
	status.lpszExtra = lpszExtra;
	status.dwGuidLights[WFS_CDM_GUIDANCE_POSOUTFRONT] = WFS_CDM_GUIDANCE_MEDIUM_FLASH | WFS_CDM_GUIDANCE_GREEN;
	status.wDevicePosition = WFS_CDM_DEVICEINPOSITION;
	status.usPowerSaveRecoveryTime = 3;

	return NewBuffer(env, &status, sizeof(WFSCDMSTATUS));
}
