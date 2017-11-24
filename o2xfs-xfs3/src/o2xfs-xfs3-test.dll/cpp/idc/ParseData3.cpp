#include "idc/at_o2xfs_xfs_v3_00_idc_ParseData3Test.h"

#include <Windows.h>
#include <XFSIDC.H>
#include "at.o2xfs.win32.h"

static WFSIDCPARSEDATA ParseData;
static LPSTR FormName = "WRITETRACK3";
static WFSIDCCARDDATA CardData[3];
static LPWFSIDCCARDDATA lppCardData[4];
static LPSTR Data[] = { "11111=11111111=111111", "1111111111111111111=11111111111111111", "111111111=111111111111=1111111111111111111111111111111111111111===1=111111111111111111111111111111111111" };

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_100_idc_ParseData3Test_buildParseData3(JNIEnv *env, jobject obj) {
	ParseData.lpstrFormName = FormName;
	ParseData.lppCardData = lppCardData;
	int length = sizeof(CardData) / sizeof(WFSIDCCARDDATA);
	for (int i = 0; i <= length; i++) {
		if (i == length) {
			lppCardData[i] = NULL;
		} else {
			lppCardData[i] = &CardData[i];
		}
	}

	CardData[0].wDataSource = WFS_IDC_TRACK1;
	CardData[0].wStatus = WFS_IDC_DATAOK;
	CardData[0].ulDataLength = strlen(Data[0]);
	CardData[0].lpbData = (LPBYTE) Data[0];
	CardData[0].fwWriteMethod = WFS_IDC_LOCO;

	CardData[1].wDataSource = WFS_IDC_TRACK2;
	CardData[1].wStatus = WFS_IDC_DATAOK;
	CardData[1].ulDataLength = strlen(Data[1]);
	CardData[1].lpbData = (LPBYTE) Data[1];
	CardData[1].fwWriteMethod = WFS_IDC_LOCO;

	CardData[2].wDataSource = WFS_IDC_TRACK3;
	CardData[2].wStatus = 0;
	CardData[2].ulDataLength = strlen(Data[2]);
	CardData[2].lpbData = (LPBYTE) Data[2];
	CardData[2].fwWriteMethod = WFS_IDC_HICO;

	return NewBuffer(env, &ParseData, sizeof(WFSIDCPARSEDATA));
}
