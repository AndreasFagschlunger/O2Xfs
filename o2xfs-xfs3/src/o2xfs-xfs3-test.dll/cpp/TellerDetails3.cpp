#include "at_o2xfs_xfs_cdm_v3_00_TellerDetails3Test.h"

#include <Windows.h>
#include <XFSCDM.H>
#include "at.o2xfs.win32.h"

static WFSCDMTELLERDETAILS tellerDetails;
static WFSCDMTELLERTOTALS tellerTotals[2];
static LPWFSCDMTELLERTOTALS lppTellerTotals[2];

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cdm_v3_100_TellerDetails3Test_buildTellerDetails3(JNIEnv *env, jobject object) {
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