#include "cim/at_o2xfs_xfs_cim_v3_30_AllItemsInfo3_30Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

static WFSCIMALLITEMSINFO AllItemsInfo;
static WFSCIMITEMINFOALL ItemsList[1];
static LPWFSCIMITEMINFOALL lpItemsList[1];
static LPWSTR lpszSerialNumber = L"12?4";
static LPSTR lpszP6SignatureFileName = "P6.txt";
static LPSTR lpszImageFileName = "note.jpg";

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cim_v3_130_AllItemsInfo3_130Test_buildAllItemsInfo3_130(JNIEnv *env, jobject obj) {
	AllItemsInfo.usCount = 1;
	AllItemsInfo.lppItemsList = lpItemsList;
	lpItemsList[0] = &ItemsList[0];
	ItemsList[0].usLevel = WFS_CIM_LEVEL_4;
	ItemsList[0].usNoteID = 1;
	ItemsList[0].lpszSerialNumber = lpszSerialNumber;
	ItemsList[0].dwOrientation = WFS_CIM_ORBACKBOTTOM;
	ItemsList[0].lpszP6SignatureFileName = lpszP6SignatureFileName;
	ItemsList[0].lpszImageFileName = lpszImageFileName;
	ItemsList[0].wOnBlacklist = WFS_CIM_ONBLACKLIST;
	ItemsList[0].wItemLocation = WFS_CIM_LOCATION_CASHUNIT;
	ItemsList[0].usNumber = 1;
	return NewBuffer(env, &AllItemsInfo, sizeof(WFSCIMALLITEMSINFO));
}
