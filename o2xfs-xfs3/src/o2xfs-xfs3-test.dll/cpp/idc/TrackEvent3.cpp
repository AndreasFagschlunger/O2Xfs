#include "idc/at_o2xfs_xfs_v3_00_idc_TrackEvent3Test.h"

#include <Windows.h>
#include <XFSIDC.H>
#include "at.o2xfs.win32.h"

static WFSIDCTRACKEVENT TrackEvent;
static LPSTR lpstrTrack  = "TRACK3";
static LPSTR lpstrData = "1234567890";

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_100_idc_TrackEvent3Test_buildTrackEvent3(JNIEnv *env, jobject obj) {
	TrackEvent.fwStatus = WFS_IDC_DATAOK;
	TrackEvent.lpstrTrack = lpstrTrack;
	TrackEvent.lpstrData = lpstrData;
	return NewBuffer(env, &TrackEvent, sizeof(WFSIDCTRACKEVENT));
}
