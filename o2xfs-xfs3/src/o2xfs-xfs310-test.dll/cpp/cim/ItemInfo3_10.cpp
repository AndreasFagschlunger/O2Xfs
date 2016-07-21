#include "cim/at_o2xfs_xfs_cim_v3_10_ItemInfo3_10Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

WFSCIMITEMINFO ItemInfo;
LPWSTR lpszSerialNumber = L"1234-5678";

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cim_v3_110_ItemInfo3_110Test_buildItemInfo3_110(JNIEnv *env, jobject obj) {
	ItemInfo.usNoteID = 1234;
	ItemInfo.lpszSerialNumber = lpszSerialNumber;
	ItemInfo.lpP6Signature = NULL;
	return NewBuffer(env, &ItemInfo, sizeof(WFSCIMITEMINFO));
}
