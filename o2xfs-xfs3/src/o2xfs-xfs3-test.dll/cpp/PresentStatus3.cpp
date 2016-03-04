#include "at_o2xfs_xfs_cdm_v3_00_PresentStatus3Test.h"

#include <Windows.h>
#include <XFSCDM.H>
#include "at.o2xfs.win32.h"

static WFSCDMPRESENTSTATUS presentStatus;
static WFSCDMDENOMINATION denomination;
static ULONG values[] = {1, 2, 2};
static LPSTR lpszExtra = "Key1=Value1\0Key2=Value2\0";

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cdm_v3_100_PresentStatus3Test_buildPresentStatus3(JNIEnv *env, jobject obj) {
	strcpy(denomination.cCurrencyID, "EUR");
	denomination.ulAmount = 150;
	denomination.usCount = 3;
	denomination.lpulValues = values;

	presentStatus.lpDenomination = &denomination;
	presentStatus.wPresentState = WFS_CDM_PRESENTED;
	presentStatus.lpszExtra = lpszExtra;

	return NewBuffer(env, &presentStatus, sizeof(LPWFSCDMPRESENTSTATUS));
}