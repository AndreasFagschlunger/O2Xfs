#include "cim/at_o2xfs_xfs_cim_v3_00_TellerInfo3Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

static WFSCIMTELLERINFO TellerInfo;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cim_v3_100_TellerInfo3Test_buildTellerInfo3(JNIEnv *env, jobject obj) {
	TellerInfo.usTellerID = 1;
	strcpy(TellerInfo.cCurrencyID, "EUR");
	return NewBuffer(env, &TellerInfo, sizeof(WFSCIMTELLERINFO));
}