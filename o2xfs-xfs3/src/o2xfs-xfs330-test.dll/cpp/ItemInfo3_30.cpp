#include "at_o2xfs_xfs_cdm_v3_30_ItemInfo3_30Test.h"

#include <Windows.h>
#include <XFSCDM.H>
#include "at.o2xfs.win32.h"

static WFSCDMITEMINFO ItemInfo;
static LPWSTR lpszSerialNumber = L"1234";
static WFSCDMSIGNATURE Signature;
static LPSTR lpszImageFileName = "C:\\Temp\\cash123456.jpg";
static char Data[] = { 0xAB, 0xCD, 0xEF };

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cdm_v3_130_ItemInfo3_130Test_buildItemInfo3_130(JNIEnv *env, jobject obj) {
	Signature.ulLength = 3;
	Signature.lpData = Data;

	strcpy(ItemInfo.cCurrencyID, "EUR");
	ItemInfo.ulValue = 10;
	ItemInfo.usRelease = 1;
	ItemInfo.lpszSerialNumber = lpszSerialNumber;
	ItemInfo.lpSignature = &Signature;
	ItemInfo.lpszImageFileName = lpszImageFileName; 

	return NewBuffer(env, &ItemInfo, sizeof(WFSCDMITEMINFO));
}