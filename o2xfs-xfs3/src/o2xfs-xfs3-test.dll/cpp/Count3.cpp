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

#include "at_o2xfs_xfs_v3_00_cdm_Count3Test.h"

#include <Windows.h>
#include <XFSCDM.H>
#include "at.o2xfs.win32.h"

static WFSCDMCOUNT count;
static WFSCDMCOUNTEDPHYSCU countedPhysCUs[2];
static LPWFSCDMCOUNTEDPHYSCU lppCountedPhysCUs[2];
static LPSTR physicalPositionNames[2] = {"SLOT1", "SLOT2"};

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_100_cdm_Count3Test_buildCount3(JNIEnv *env, jobject obj) {
	count.usNumPhysicalCUs = 2;
	count.lppCountedPhysCUs = lppCountedPhysCUs;

	countedPhysCUs[0].lpPhysicalPositionName = physicalPositionNames[0];
	strcpy(countedPhysCUs[0].cUnitId, "00001");
	countedPhysCUs[0].ulDispensed = 10;
	countedPhysCUs[0].ulCounted = 12;
	countedPhysCUs[0].usPStatus = WFS_CDM_STATCUOK;
	lppCountedPhysCUs[0] = &countedPhysCUs[0];

	countedPhysCUs[1].lpPhysicalPositionName = physicalPositionNames[1];
	strcpy(countedPhysCUs[1].cUnitId, "00002");
	countedPhysCUs[1].ulDispensed = 10;
	countedPhysCUs[1].ulCounted = 12;
	countedPhysCUs[1].usPStatus = WFS_CDM_STATCUFULL;
	lppCountedPhysCUs[1] = &countedPhysCUs[1];

	return NewBuffer(env, &count, sizeof(WFSCDMCOUNT));
}
