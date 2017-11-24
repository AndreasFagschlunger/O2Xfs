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

#include "at_o2xfs_xfs_v3_00_cdm_CashUnitError3Test.h"

#include <Windows.h>
#include <XFSCDM.H>
#include "at.o2xfs.win32.h"

static WFSCDMCUERROR CashUnitError;
static WFSCDMCASHUNIT CashUnit;
static WFSCDMPHCU PhysicalCUs[2];
static LPWFSCDMPHCU lppPhysical[2];
static LPSTR PhysicalPositionName[] = { "SLOT1", "SLOT2" };

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_100_cdm_CashUnitError3Test_buildCashUnitError3(JNIEnv *env, jobject obj) {
	CashUnitError.wFailure = WFS_CDM_CASHUNITERROR;
	CashUnitError.lpCashUnit = &CashUnit;

	CashUnit.usNumber = 1;
	CashUnit.usType = WFS_CDM_TYPEBILLCASSETTE;
	CashUnit.lpszCashUnitName = NULL;
	strcpy(CashUnit.cUnitID, "00001");
	strcpy(CashUnit.cCurrencyID, "EUR");
	CashUnit.ulValues = 10000;
	CashUnit.ulInitialCount = 100;
	CashUnit.ulCount = 98;
	CashUnit.ulRejectCount = 1;
	CashUnit.ulMinimum = 0;
	CashUnit.ulMaximum = 0;
	CashUnit.bAppLock = false;
	CashUnit.usStatus = WFS_CDM_STATCUINOP;
	CashUnit.usNumPhysicalCUs = 2;
	CashUnit.lppPhysical = lppPhysical;

	int index = 0;
	PhysicalCUs[index].lpPhysicalPositionName = PhysicalPositionName[index];
	strcpy(PhysicalCUs[index].cUnitID, CashUnit.cUnitID);
	PhysicalCUs[index].ulInitialCount = 0;
	PhysicalCUs[index].ulCount = 0;
	PhysicalCUs[index].ulRejectCount = 0;
	PhysicalCUs[index].ulMaximum = 50;
	PhysicalCUs[index].usPStatus = WFS_CDM_STATCUOK;
	PhysicalCUs[index].bHardwareSensor = false;
	lppPhysical[index] = &PhysicalCUs[index];

	index++;
	PhysicalCUs[index].lpPhysicalPositionName = PhysicalPositionName[index];
	strcpy(PhysicalCUs[index].cUnitID, CashUnit.cUnitID);
	PhysicalCUs[index].ulInitialCount = 0;
	PhysicalCUs[index].ulCount = 0;
	PhysicalCUs[index].ulRejectCount = 0;
	PhysicalCUs[index].ulMaximum = 50;
	PhysicalCUs[index].usPStatus = WFS_CDM_STATCUOK;
	PhysicalCUs[index].bHardwareSensor = false;
	lppPhysical[index] = &PhysicalCUs[index];

	return NewBuffer(env, &CashUnitError, sizeof(WFSCDMCUERROR));
}
