#include "idc/at_o2xfs_xfs_v3_20_idc_TrackDetected320Test.h"

#include <Windows.h>
#include <XFSIDC.H>
#include "at.o2xfs.win32.h"

static WFSIDCTRACKDETECTED TrackDetected;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_120_idc_TrackDetected320Test_buildTrackDetected320(JNIEnv *env, jobject obj) {
	TrackDetected.fwTracks = WFS_IDC_TRACK1 | WFS_IDC_TRACK2 | WFS_IDC_TRACK3 | WFS_IDC_TRACK_WM | WFS_IDC_FRONT_TRACK_1;
	return NewBuffer(env, &TrackDetected, sizeof(WFSIDCTRACKDETECTED));
}
