#include "idc/at_o2xfs_xfs_idc_v3_10_InterfaceModuleIdentifier3_10Test.h"

#include <Windows.h>
#include <XFSIDC.H>
#include "at.o2xfs.win32.h"

static WFSIDCIFMIDENTIFIER IFMIdentifier;
static LPSTR Identifier = "IFM";

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_idc_v3_110_InterfaceModuleIdentifier3_110Test_buildInterfaceModuleIdentifier3_110(JNIEnv *env, jobject obj) {
	IFMIdentifier.wIFMAuthority = WFS_IDC_IFMEMV;
	IFMIdentifier.lpszIFMIdentifier = Identifier;
	return NewBuffer(env, &IFMIdentifier, sizeof(WFSIDCIFMIDENTIFIER));
}
