#include "at_o2xfs_xfs_cdm_v3_00_MixType3Test.h"

#include <Windows.h>
#include <XFSCDM.H>
#include "at.o2xfs.win32.h"

static WFSCDMMIXTYPE mixTypes[2];
static LPSTR szNames[2] = { "Foo", "Bar" };
static LPWFSCDMMIXTYPE lpMixTypes[3];

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cdm_v3_100_MixType3Test_buildMixTypes3(JNIEnv *env, jobject obj) {
	mixTypes[0].usMixNumber = 1;
	mixTypes[0].usMixType = WFS_CDM_MIXALGORITHM;
	mixTypes[0].usSubType = WFS_CDM_MIX_EQUAL_EMPTYING_OF_CASH_UNITS;
	mixTypes[0].lpszName = szNames[0];
	lpMixTypes[0] = &mixTypes[0];

	mixTypes[1].usMixNumber = 2;
	mixTypes[1].usMixType = WFS_CDM_MIXTABLE;
	mixTypes[1].usSubType = 8000;
	mixTypes[1].lpszName = szNames[1];
	lpMixTypes[1] = &mixTypes[1];

	return NewBuffer(env, &lpMixTypes, sizeof(LPWFSCDMMIXTYPE));
}