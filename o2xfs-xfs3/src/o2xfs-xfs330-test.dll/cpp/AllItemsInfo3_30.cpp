#include "at_o2xfs_xfs_cdm_v3_30_AllItemsInfo3_30Test.h"

#include <Windows.h>
#include <XFSCDM.H>
#include "at.o2xfs.win32.h"

static WFSCDMALLITEMSINFO AllItemsInfo;
static WFSCDMITEMINFOALL ItemsList[3];
static LPWFSCDMITEMINFOALL lppItemsList[3];
static LPWSTR SerialNumber[] = { L"123", L"456", L"789" };
static LPSTR ImageFileName[] = { "file1.jpg", "file2.jpg", NULL };

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cdm_v3_130_AllItemsInfo3_130Test_buildAllItemsInfo3_130(JNIEnv *env, jobject obj) {
	int index = 0;
	ItemsList[index].usLevel = WFS_CDM_LEVEL_2;
	strcpy(ItemsList[index].cCurrencyID, "EUR");
	ItemsList[index].ulValue = 10;
	ItemsList[index].usRelease = 1;
	ItemsList[index].lpszSerialNumber = SerialNumber[index];
	ItemsList[index].lpszImageFileName = ImageFileName[index];
	ItemsList[index].wOnBlacklist = WFS_CDM_ONBLACKLIST;
	ItemsList[index].wItemLocation = WFS_CDM_LOCATION_CASHUNIT;
	ItemsList[index].usNumber = 1;
	lppItemsList[index] = &ItemsList[index];

	index++;
	ItemsList[index].usLevel = WFS_CDM_LEVEL_3;
	strcpy(ItemsList[index].cCurrencyID, "EUR");
	ItemsList[index].ulValue = 20;
	ItemsList[index].usRelease = 2;
	ItemsList[index].lpszSerialNumber = SerialNumber[index];
	ItemsList[index].lpszImageFileName = ImageFileName[index];
	ItemsList[index].wOnBlacklist = WFS_CDM_NOTONBLACKLIST;
	ItemsList[index].wItemLocation = WFS_CDM_LOCATION_CUSTOMER;
	ItemsList[index].usNumber = 2;
	lppItemsList[index] = &ItemsList[index];

	index++;
	ItemsList[index].usLevel = WFS_CDM_LEVEL_4;
	strcpy(ItemsList[index].cCurrencyID, "EUR");
	ItemsList[index].ulValue = 50;
	ItemsList[index].usRelease = 3;
	ItemsList[index].lpszSerialNumber = SerialNumber[index];
	ItemsList[index].lpszImageFileName = ImageFileName[index];
	ItemsList[index].wOnBlacklist = WFS_CDM_BLACKLISTUNKNOWN;
	ItemsList[index].wItemLocation = WFS_CDM_LOCATION_DEVICE;
	ItemsList[index].usNumber = 3;
	lppItemsList[index] = &ItemsList[index];

	AllItemsInfo.usCount = 3;
	AllItemsInfo.lppItemsList = lppItemsList;

	return NewBuffer(env, &AllItemsInfo, sizeof(WFSCDMALLITEMSINFO));
}