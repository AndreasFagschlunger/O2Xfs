#include "cim/at_o2xfs_xfs_cim_v3_00_CurrencyExp3Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

static WFSCIMCURRENCYEXP currencyExp[2];
static LPWFSCIMCURRENCYEXP lppCurrencyExp[3];

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cim_v3_100_CurrencyExp3Test_buildCurrencyExp3(JNIEnv *env, jobject obj) {
	strcpy(currencyExp[0].cCurrencyID, "EUR");
	currencyExp[0].sExponent = -2;
	lppCurrencyExp[0] = &currencyExp[0];

	strcpy(currencyExp[1].cCurrencyID, "JPY");
	currencyExp[1].sExponent = +3;
	lppCurrencyExp[1] = &currencyExp[1];

	return NewBuffer(env, &lppCurrencyExp, sizeof(LPWFSCIMCURRENCYEXP));
}
