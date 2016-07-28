#include "cim/at_o2xfs_xfs_cim_v3_30_Blacklist3_30Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

static WFSCIMBLACKLIST Blacklist;
static LPWSTR lpszVersion = L"1.0.0";
static WFSCIMBLACKLISTELEMENT BlacklistElements[1];
static LPWFSCIMBLACKLISTELEMENT lpBlacklistElements[1];
static LPWSTR lpszSerialNumber = L"1234-5678-9123";

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cim_v3_130_Blacklist3_130Test_buildBlacklist3_130(JNIEnv *env, jobject obj) {
	Blacklist.lpszVersion = lpszVersion;
	Blacklist.usCount = 1;
	Blacklist.lppBlacklistElements = lpBlacklistElements;
	lpBlacklistElements[0] = &BlacklistElements[0];
	BlacklistElements[0].lpszSerialNumber = lpszSerialNumber;
	strcpy(BlacklistElements[0].cCurrencyID, "EUR");
	BlacklistElements[0].ulValue = 1000;
	return NewBuffer(env, &Blacklist, sizeof(WFSCIMBLACKLIST));
}
