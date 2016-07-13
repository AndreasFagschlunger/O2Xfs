#include "cim/at_o2xfs_xfs_cim_v3_00_TellerUpdate3Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

static WFSCIMTELLERUPDATE TellerUpdate;
static WFSCIMTELLERDETAILS TellerDetails[1];
static LPWFSCIMTELLERDETAILS lpTellerDetails[2];
static WFSCIMTELLERTOTALS TellerTotals[1];
static LPWFSCIMTELLERTOTALS lpTellerTotals[2];

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cim_v3_100_TellerUpdate3Test_buildTellerUpdate3(JNIEnv *env, jobject obj) {
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