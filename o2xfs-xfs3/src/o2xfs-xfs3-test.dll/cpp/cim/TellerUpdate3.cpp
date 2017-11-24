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

#include "cim/at_o2xfs_xfs_v3_00_cim_TellerUpdate3Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

static WFSCIMTELLERUPDATE TellerUpdate;
static WFSCIMTELLERDETAILS TellerDetails[1];
static LPWFSCIMTELLERDETAILS lpTellerDetails[2];
static WFSCIMTELLERTOTALS TellerTotals[1];
static LPWFSCIMTELLERTOTALS lpTellerTotals[2];

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_100_cim_TellerUpdate3Test_buildTellerUpdate3(JNIEnv *env, jobject obj) {
	TellerUpdate.usAction = WFS_CIM_CREATE_TELLER;
	TellerUpdate.lpTellerDetails = TellerDetails;

	lpTellerDetails[0] = &TellerDetails[0];
	TellerDetails[0].usTellerID = 1;
	TellerDetails[0].fwInputPosition = WFS_CIM_POSINFRONT;
	TellerDetails[0].fwOutputPosition = WFS_CIM_POSOUTFRONT;
	TellerDetails[0].lppTellerTotals = lpTellerTotals;
	lpTellerTotals[0] = &TellerTotals[0];
	strcpy(TellerTotals[0].cCurrencyID, "EUR");
	TellerTotals[0].ulItemsReceived = 1234;
	TellerTotals[0].ulItemsDispensed = 4567;
	TellerTotals[0].ulCoinsReceived = 4321;
	TellerTotals[0].ulCoinsDispensed = 7654;
	TellerTotals[0].ulCashBoxReceived = 1;
	TellerTotals[0].ulCashBoxDispensed = 2;
	lpTellerTotals[1] = NULL;

	lpTellerDetails[1] = NULL;
	return NewBuffer(env, &TellerUpdate, sizeof(WFSCIMTELLERUPDATE));
}
