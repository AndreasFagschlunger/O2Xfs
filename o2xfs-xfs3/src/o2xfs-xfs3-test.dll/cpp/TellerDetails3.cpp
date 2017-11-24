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

#include "at_o2xfs_xfs_v3_00_cdm_TellerDetails3Test.h"

#include <Windows.h>
#include <XFSCDM.H>
#include "at.o2xfs.win32.h"

static WFSCDMTELLERDETAILS tellerDetails;
static WFSCDMTELLERTOTALS tellerTotals[2];
static LPWFSCDMTELLERTOTALS lppTellerTotals[2];

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_100_cdm_TellerDetails3Test_buildTellerDetails3(JNIEnv *env, jobject object) {
	tellerDetails.usTellerID = 1;
	tellerDetails.ulInputPosition = WFS_CDM_POSINFRONT;
	tellerDetails.fwOutputPosition = WFS_CDM_POSTOP;
	tellerDetails.lppTellerTotals = lppTellerTotals;

	strcpy(tellerTotals[0].cCurrencyID, "EUR");
	tellerTotals[0].ulItemsReceived = 0;
	tellerTotals[0].ulItemsDispensed = 0;
	tellerTotals[0].ulCoinsReceived = 0;
	tellerTotals[0].ulCoinsDispensed = 0;
	tellerTotals[0].ulCashBoxReceived = 0;
	tellerTotals[0].ulCashBoxDispensed = 0;
	lppTellerTotals[0] = &tellerTotals[0];

	return NewBuffer(env, &tellerDetails, sizeof(WFSCDMTELLERDETAILS));
}
