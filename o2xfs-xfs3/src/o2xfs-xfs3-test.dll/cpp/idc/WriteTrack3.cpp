#include "idc/at_o2xfs_xfs_v3_00_idc_WriteTrack3Test.h"

#include <Windows.h>
#include <XFSIDC.H>
#include "at.o2xfs.win32.h"

static WFSIDCWRITETRACK WriteTrack;
static LPSTR FormName = "TRACK1";
static LPSTR TrackData = "11111=11111111=111111";

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_100_idc_WriteTrack3Test_buildWriteTrack3(JNIEnv *env, jobject obj) {
	WriteTrack.lpstrFormName = FormName;
	WriteTrack.lpstrTrackData = TrackData;
	WriteTrack.fwWriteMethod = WFS_IDC_LOCO;
	return NewBuffer(env, &WriteTrack, sizeof(WFSIDCWRITETRACK));
}
