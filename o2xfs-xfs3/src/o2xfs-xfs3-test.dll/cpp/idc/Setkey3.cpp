#include "idc/at_o2xfs_xfs_idc_v3_00_Setkey3Test.h"

#include <Windows.h>
#include <XFSIDC.H>
#include "at.o2xfs.win32.h"

static WFSIDCSETKEY Setkey;
static LPSTR KeyValue = "The quick brown fox";

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_idc_v3_100_Setkey3Test_buildSetkey3(JNIEnv *env, jobject obj) {
	Setkey.usKeyLen = strlen(KeyValue);
	Setkey.lpbKeyValue = (LPBYTE) KeyValue;
	return NewBuffer(env, &Setkey, sizeof(WFSIDCSETKEY));
}
