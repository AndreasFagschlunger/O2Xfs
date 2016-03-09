#include "at_o2xfs_xfs_cdm_v3_30_IncompleteRetract3_30Test.h"

#include <Windows.h>
#include <XFSCDM.H>
#include "at.o2xfs.win32.h"

static WFSCDMINCOMPLETERETRACT IncompleteRetract;
static WFSCDMITEMNUMBERLIST ItemNumberList;
static WFSCDMITEMNUMBER ItemNumber[3];
static LPWFSCDMITEMNUMBER lppItemNumber[3];

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cdm_v3_130_IncompleteRetract3_130Test_buildIncompleteRetract3_130(JNIEnv *env, jobject obj) {
	ItemNumberList.usNumOfItemNumbers = 3;
	ItemNumberList.lppItemNumber = lppItemNumber;

	strcpy(ItemNumber[0].cCurrencyID, "EUR");
	ItemNumber[0].ulValues = 10;
	ItemNumber[0].usRelease = 0;
	ItemNumber[0].ulCount = 1;
	ItemNumber[0].usNumber = 0;
	lppItemNumber[0] = &ItemNumber[0];

	strcpy(ItemNumber[1].cCurrencyID, "EUR");
	ItemNumber[1].ulValues = 20;
	ItemNumber[1].usRelease = 0;
	ItemNumber[1].ulCount = 1;
	ItemNumber[1].usNumber = 0;
	lppItemNumber[1] = &ItemNumber[1];

	strcpy(ItemNumber[2].cCurrencyID, "EUR");
	ItemNumber[2].ulValues = 100;
	ItemNumber[2].usRelease = 0;
	ItemNumber[2].ulCount = 1;
	ItemNumber[2].usNumber = 0;
	lppItemNumber[2] = &ItemNumber[2];

	IncompleteRetract.lpItemNumberList = ItemNumberList;
	IncompleteRetract.usReason = WFS_CDM_IRFOREIGNITEMSDETECTED;

	return NewBuffer(env, &IncompleteRetract, sizeof(WFSCDMINCOMPLETERETRACT));
}