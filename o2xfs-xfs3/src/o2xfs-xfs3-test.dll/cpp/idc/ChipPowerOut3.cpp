#include "idc/at_o2xfs_xfs_v3_00_idc_ChipPowerOut3Test.h"

#include <Windows.h>
#include <XFSIDC.H>
#include "at.o2xfs.win32.h"

static WFSIDCCHIPPOWEROUT ChipPowerOut;
static BYTE ChipData[] = { 0xca, 0xfe, 0xba, 0xbe };

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_100_idc_ChipPowerOut3Test_buildChipPowerOut3(JNIEnv *env, jobject obj) {
	ChipPowerOut.ulChipDataLength = sizeof(ChipData) / sizeof(*ChipData);
	ChipPowerOut.lpbChipData = ChipData;
	return NewBuffer(env, &ChipPowerOut, sizeof(WFSIDCCHIPPOWEROUT));
}
