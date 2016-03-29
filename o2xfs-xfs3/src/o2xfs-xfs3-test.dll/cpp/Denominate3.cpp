#include "at_o2xfs_xfs_cdm_v3_00_Denominate3Test.h"

#include <Windows.h>
#include <XFSCDM.H>
#include "at.o2xfs.win32.h"

static WFSCDMDENOMINATE Denominate;
static WFSCDMDENOMINATION Denomination;
static ULONG Values[] = { 1, 2, 3};

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cdm_v3_100_Denominate3Test_buildDenominate3(JNIEnv *env, jobject object) {
	Denominate.usTellerID = 0;
	Denominate.usMixNumber = WFS_CDM_INDIVIDUAL;
	Denominate.lpDenomination = &Denomination;

	strcpy(Denomination.cCurrencyID, "EUR");
	Denomination.ulAmount = 1234;
	Denomination.usCount = 3;
	Denomination.lpulValues = Values;
	Denomination.ulCashBox = 456;

	return NewBuffer(env, &Denominate, sizeof(WFSCDMDENOMINATE));
}