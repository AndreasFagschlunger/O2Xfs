#include "cim/at_o2xfs_xfs_cim_v3_20_CashInLimit3_20Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

static WFSCIMCASHINLIMIT CashInLimit;
static WFSCIMAMOUNTLIMIT AmountLimit;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cim_v3_120_CashInLimit3_120Test_buildCashInLimit3_120(JNIEnv *env, jobject obj) {
	CashInLimit.ulTotalItemsLimit = 200;
	CashInLimit.lpAmountLimit = &AmountLimit;
	strcpy(AmountLimit.cCurrencyID, "EUR");
	AmountLimit.ulAmount = 1000;
	return NewBuffer(env, &CashInLimit, sizeof(WFSCIMCASHINLIMIT));
}
