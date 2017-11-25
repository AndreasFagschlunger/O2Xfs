#include "idc/at_o2xfs_xfs_v3_30_idc_AppData330Test.h"

#include <Windows.h>
#include <XFSIDC.H>
#include "at.o2xfs.win32.h"

static WFSIDCAPPDATA AppData;
static WFSIDCHEXDATA AID;
static WFSIDCHEXDATA KernelIdentifier;
static BYTE AIDData[] = { 0xA0, 0x00, 0x00, 0x00, 0x0F, 0x12, 0x34 };
static BYTE KernelIdentifierData[] = { 0xCA, 0xFE, 0xBA, 0xBE };

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_130_idc_AppData330Test_buildAppData330(JNIEnv *env, jobject obj) {
	AID.ulLength = 7;
	AID.lpbData = AIDData;
	KernelIdentifier.ulLength = 4;
	KernelIdentifier.lpbData = KernelIdentifierData;
	AppData.lpAID = &AID;
	AppData.lpKernelIdentifier = &KernelIdentifier;
	return NewBuffer(env, &AppData, sizeof(WFSIDCAPPDATA));
}
