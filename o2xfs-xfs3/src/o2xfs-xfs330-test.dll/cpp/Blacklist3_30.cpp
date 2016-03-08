#include "at_o2xfs_xfs_cdm_v3_30_Blacklist3_30Test.h"

#include <Windows.h>
#include <XFSCDM.H>
#include "at.o2xfs.win32.h"

static WFSCDMBLACKLIST Blacklist;
static LPWSTR Version = L"1.0.0";
static WFSCDMBLACKLISTELEMENT BlacklistElements[2];
static LPWFSCDMBLACKLISTELEMENT lppBlacklistElements[2];
static LPWSTR SerialNumber[] = { L"123", L"456" };

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cdm_v3_130_Blacklist3_130Test_buildBlacklist3_130(JNIEnv *env, jobject obj) {
	Blacklist.lpszVersion = Version;
	Blacklist.usCount = 2;
	Blacklist.lppBlacklistElements = lppBlacklistElements;

	BlacklistElements[0].lpszSerialNumber = SerialNumber[0];
	strcpy(BlacklistElements[0].cCurrencyID, "EUR");
	BlacklistElements[0].ulValue = 10;
	lppBlacklistElements[0] = &BlacklistElements[0];

	BlacklistElements[1].lpszSerialNumber = SerialNumber[1];
	strcpy(BlacklistElements[1].cCurrencyID, "EUR");
	BlacklistElements[1].ulValue = 50;
	lppBlacklistElements[1] = &BlacklistElements[1];

	return NewBuffer(env, &Blacklist, sizeof(WFSCDMBLACKLIST));
}