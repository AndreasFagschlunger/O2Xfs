#include "cim/at_o2xfs_xfs_cim_v3_10_P6CompareSignature3_10Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

WFSCIMP6COMPARESIGNATURE P6CompareSignature;
WFSCIMP6SIGNATURE P6Signatures[2];
LPWFSCIMP6SIGNATURE lpP6ReferenceSignatures[2];
LPWFSCIMP6SIGNATURE lpP6Signatures[2];
char *lpSignature[] = { "ABC", "DEF" };

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cim_v3_110_P6CompareSignature3_110Test_buildP6CompareSignature3_110(JNIEnv *env, jobject obj) {
	P6Signatures[0].usNoteId = 1;
	P6Signatures[0].ulLength = strlen(lpSignature[0]);
	P6Signatures[0].dwOrientation = WFS_CIM_ORFRONTTOP;
	P6Signatures[0].lpSignature = lpSignature[0];
	lpP6ReferenceSignatures[0] = &P6Signatures[0]; 
	lpP6ReferenceSignatures[1] = NULL;
	P6CompareSignature.lppP6ReferenceSignatures = lpP6ReferenceSignatures;

	P6Signatures[1].usNoteId = 1;
	P6Signatures[1].ulLength = strlen(lpSignature[1]);
	P6Signatures[1].dwOrientation = WFS_CIM_ORFRONTTOP;
	P6Signatures[1].lpSignature = lpSignature[1];
	lpP6Signatures[0] = &P6Signatures[1]; 
	lpP6Signatures[1] = NULL; 
	P6CompareSignature.lppP6Signatures = lpP6Signatures;
	return NewBuffer(env, &P6CompareSignature, sizeof(WFSCIMP6COMPARESIGNATURE));
}
