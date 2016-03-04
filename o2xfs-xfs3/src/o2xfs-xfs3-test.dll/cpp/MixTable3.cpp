#include "at_o2xfs_xfs_cdm_v3_00_MixTable3Test.h"

#include <Windows.h>
#include <XFSCDM.H>
#include "at.o2xfs.win32.h"

static WFSCDMMIXTABLE mixTable;
static LPSTR szName = "Test";
static ULONG ulMixHeader[3] = {10, 20, 50};
static WFSCDMMIXROW mixRows[1];
static LPWFSCDMMIXROW lpMixRows[1];
static USHORT usMixture[3] = {1, 2, 1};

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cdm_v3_100_MixTable3Test_buildMixTable3(JNIEnv *env, jobject obj) {
	mixTable.usMixNumber = 1;
	mixTable.lpszName = szName;
	mixTable.usRows = 1;
	mixTable.usCols = 3;
	mixTable.lpulMixHeader = ulMixHeader;
	mixTable.lppMixRows = lpMixRows;

	mixRows[0].ulAmount = 100;
	mixRows[0].lpusMixture = usMixture;
	lpMixRows[0] = mixRows;

	return NewBuffer(env, &mixTable, sizeof(LPWFSCDMMIXTABLE));
}