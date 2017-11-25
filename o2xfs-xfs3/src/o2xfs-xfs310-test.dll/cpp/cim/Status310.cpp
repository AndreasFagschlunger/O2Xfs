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

#include "cim/at_o2xfs_xfs_v3_10_cim_Status310Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

WFSCIMSTATUS Status;
WFSCIMINPOS Positions[2];
LPWFSCIMINPOS lppPositions[3];
LPSTR lpszExtra = "LASTERROR=StClass=00000000\0StCode=00000000\0LastErrorText=OK:none\0";

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_110_cim_Status310Test_buildStatus310(JNIEnv *env, jobject obj) {

	Status.fwDevice = WFS_CIM_DEVONLINE;
	Status.fwSafeDoor = WFS_CIM_DOORCLOSED;
	Status.fwAcceptor = WFS_CIM_ACCOK;
	Status.fwIntermediateStacker = WFS_CIM_ISEMPTY;
	Status.fwStackerItems = WFS_CIM_NOITEMS;
	Status.fwBanknoteReader = WFS_CIM_BNROK;
	Status.bDropBox = FALSE;
	Status.lppPositions = lppPositions;
	Status.lpszExtra = lpszExtra;

	int positionIndex = 0;
	lppPositions[positionIndex] = &Positions[positionIndex];
	Positions[positionIndex].fwPosition = WFS_CIM_POSOUTFRONT;
	Positions[positionIndex].fwShutter = WFS_CIM_SHTCLOSED;
	Positions[positionIndex].fwPositionStatus = WFS_CIM_PSEMPTY;
	Positions[positionIndex].fwTransport = WFS_CIM_TPOK;
	Positions[positionIndex].fwTransportStatus = WFS_CIM_TPSTATEMPTY;

	positionIndex++;
	lppPositions[positionIndex] = &Positions[positionIndex];
	Positions[positionIndex].fwPosition = WFS_CIM_POSINFRONT;
	Positions[positionIndex].fwShutter = WFS_CIM_SHTCLOSED;
	Positions[positionIndex].fwPositionStatus = WFS_CIM_PSEMPTY;
	Positions[positionIndex].fwTransport = WFS_CIM_TPOK;
	Positions[positionIndex].fwTransportStatus = WFS_CIM_TPSTATNOTSUPPORTED;

	positionIndex++;
	lppPositions[positionIndex] = NULL;
	Status.dwGuidLights[WFS_CIM_GUIDANCE_POSINFRONT] = WFS_CIM_GUIDANCE_MEDIUM_FLASH | WFS_CIM_GUIDANCE_BLUE;
	Status.wDevicePosition = WFS_CIM_DEVICEINPOSITION;
	Status.usPowerSaveRecoveryTime = 0;
	return NewBuffer(env, &Status, sizeof(WFSCIMSTATUS));
}
