#include "at_o2xfs_xfs_cdm_v3_00_Dispense3Test.h"

#include <Windows.h>
#include <XFSCDM.H>
#include "at.o2xfs.win32.h"

static WFSCDMDISPENSE dispense;
static WFSCDMDENOMINATION denomination;
static ULONG values[] = { 1, 2, 1 };

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cdm_v3_100_Dispense3Test_buildDispense3(JNIEnv *env, jobject obj) {
	dispense.usTellerID = 0;
	dispense.usMixNumber = WFS_CDM_INDIVIDUAL;
	dispense.fwPosition = WFS_CDM_POSFRONT;
	dispense.bPresent = FALSE;
	dispense.lpDenomination = &denomination;

	strcpy(denomination.cCurrencyID, "EUR");
	denomination.ulAmount = 150;
	denomination.usCount = 3;
	denomination.lpulValues = values;
	denomination.ulCashBox = 10;

	return NewBuffer(env, &dispense, sizeof(WFSCDMDISPENSE));
}