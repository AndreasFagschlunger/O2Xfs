#include "cim/at_o2xfs_xfs_cim_v3_10_P6CompareResult3_10Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

WFSCIMP6COMPARERESULT P6CompareResult;
WFSCIMP6SIGNATURESINDEX P6SignaturesIndex[1];
LPWFSCIMP6SIGNATURESINDEX lpP6SignaturesIndex[2];
char* lpComparisonData = "ABC";

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cim_v3_110_P6CompareResult3_110Test_buildP6CompareResult3_110(JNIEnv *env, jobject obj) {
	P6CompareResult.usCount = 1;
	P6CompareResult.lppP6SignaturesIndex = lpP6SignaturesIndex;
	P6SignaturesIndex[0].usIndex = 1;
	P6SignaturesIndex[0].usConfidenceLevel = 90;
	P6SignaturesIndex[0].ulLength = strlen(lpComparisonData);
	P6SignaturesIndex[0].lpComparisonData = lpComparisonData;
	lpP6SignaturesIndex[0] = &P6SignaturesIndex[0];
	lpP6SignaturesIndex[1] = NULL;
	return NewBuffer(env, &P6CompareResult, sizeof(WFSCIMP6COMPARERESULT));
}
