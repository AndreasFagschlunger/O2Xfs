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

#include "at_o2xfs_xfs_v3_10_cdm_CashUnit310Test.h"

#include <Windows.h>
#include <XFSCDM.H>
#include "at.o2xfs.win32.h"

static WFSCDMCASHUNIT cashUnit;
static LPSTR cashUnitName = "BIN_1\0";
static WFSCDMPHCU physicalCashUnit;
static LPWFSCDMPHCU lppPhysical = &physicalCashUnit;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_110_cdm_CashUnit310Test_buildCashUnit310(JNIEnv *env, jobject obj) {
	cashUnit.usNumber = 1;
	cashUnit.usType = WFS_CDM_TYPEBILLCASSETTE;
	cashUnit.lpszCashUnitName = cashUnitName;
	strcpy(cashUnit.cUnitID, "00001");
	strcpy(cashUnit.cCurrencyID, "EUR");
	cashUnit.ulValues = 10000;
	cashUnit.ulInitialCount = 100;
	cashUnit.ulCount = 98;
	cashUnit.ulRejectCount = 1;
	cashUnit.ulMinimum = 0;
	cashUnit.ulMaximum = 0;
	cashUnit.bAppLock = false;
	cashUnit.usStatus = WFS_CDM_STATCUOK;
	cashUnit.usNumPhysicalCUs = 1;
	cashUnit.lppPhysical = &lppPhysical;
	cashUnit.ulDispensedCount = 2;
	cashUnit.ulPresentedCount = 1;
	cashUnit.ulRetractedCount = 0;

	physicalCashUnit.lpPhysicalPositionName = cashUnitName;
	strcpy(physicalCashUnit.cUnitID, cashUnit.cUnitID);
	physicalCashUnit.ulInitialCount = cashUnit.ulInitialCount;
	physicalCashUnit.ulCount = cashUnit.ulCount;
	physicalCashUnit.ulRejectCount = cashUnit.ulRejectCount;
	physicalCashUnit.ulMaximum = 2200;
	physicalCashUnit.usPStatus = WFS_CDM_STATCUOK;
	physicalCashUnit.bHardwareSensor = false;
	physicalCashUnit.ulDispensedCount = cashUnit.ulDispensedCount;
	physicalCashUnit.ulPresentedCount = cashUnit.ulPresentedCount;
	physicalCashUnit.ulRetractedCount = cashUnit.ulRetractedCount;

	return NewBuffer(env, &cashUnit, sizeof(WFSCDMCASHUNIT));
}
