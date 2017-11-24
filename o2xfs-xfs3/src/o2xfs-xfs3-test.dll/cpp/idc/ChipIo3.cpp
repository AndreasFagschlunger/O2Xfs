#include "idc/at_o2xfs_xfs_v3_00_idc_ChipIo3Test.h"

#include <Windows.h>
#include <XFSIDC.H>
#include "at.o2xfs.win32.h"

static WFSIDCCHIPIO ChipIo;
static UCHAR ChipData[] = { 0x00, 0xa4, 0x04, 0x00, 0x08, 0xd0, 0x40, 0x00, 0x00, 0x01, 0x00, 0x00, 0x02 };

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_100_idc_ChipIo3Test_buildChipIo3(JNIEnv *env, jobject obj) {
	ChipIo.wChipProtocol = WFS_IDC_CHIPT1;
	ChipIo.ulChipDataLength = sizeof(ChipData) / sizeof(*ChipData);
	ChipIo.lpbChipData = ChipData;
	return NewBuffer(env, &ChipIo, sizeof(WFSIDCCHIPIO));
}
