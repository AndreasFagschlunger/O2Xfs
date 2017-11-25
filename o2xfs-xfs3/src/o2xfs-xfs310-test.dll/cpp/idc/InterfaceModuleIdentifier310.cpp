#include "idc/at_o2xfs_xfs_v3_10_idc_InterfaceModuleIdentifier310Test.h"

#include <Windows.h>
#include <XFSIDC.H>
#include "at.o2xfs.win32.h"

static WFSIDCIFMIDENTIFIER IFMIdentifier;
static LPSTR Identifier = "IFM";

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_110_idc_InterfaceModuleIdentifier310Test_buildInterfaceModuleIdentifier310(JNIEnv *env, jobject obj) {
	IFMIdentifier.wIFMAuthority = WFS_IDC_IFMEMV;
	IFMIdentifier.lpszIFMIdentifier = Identifier;
	return NewBuffer(env, &IFMIdentifier, sizeof(WFSIDCIFMIDENTIFIER));
}
