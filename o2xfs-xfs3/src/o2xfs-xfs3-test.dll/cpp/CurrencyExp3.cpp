#include "at_o2xfs_xfs_cdm_v3_00_CurrencyExp3Test.h"

#include <Windows.h>
#include <XFSCDM.H>
#include "at.o2xfs.win32.h"

static WFSCDMCURRENCYEXP currencyExp[2];
static LPWFSCDMCURRENCYEXP lppCurrencyExp[3];

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cdm_v3_100_CurrencyExp3Test_buildCurrencyExp3(JNIEnv *env, jobject object) {
	strcpy(currencyExp[0].cCurrencyID, "EUR");
	currencyExp[0].sExponent = -2;
	lppCurrencyExp[0] = &currencyExp[0];

	strcpy(currencyExp[1].cCurrencyID, "JPY");
	currencyExp[1].sExponent = +3;
	lppCurrencyExp[1] = &currencyExp[1];

	return NewBuffer(env, &lppCurrencyExp, sizeof(LPWFSCDMCURRENCYEXP));
}