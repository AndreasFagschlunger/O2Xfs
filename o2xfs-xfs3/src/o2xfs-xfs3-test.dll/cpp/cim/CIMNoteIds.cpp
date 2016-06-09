#include "cim/at_o2xfs_xfs_cim_v3_00_CIMNoteIdsTest.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

static USHORT usNoteIDs[] = { 4, 3, 2, 1, 0 };

JNIEXPORT void JNICALL Java_at_o2xfs_xfs_cim_v3_100_CIMNoteIdsTest_foobar(JNIEnv *env, jobject obj, jobject objNoteIDs) {
	LPUSHORT lpusNoteIDs = (LPUSHORT) GetTypeAddress(env, objNoteIDs);
	// LPUSHORT lpusNoteIDs = usNoteIDs;
	for(int i = 0; i < 4; i++) {
		printf("lpusNoteIDs[%d]=%d\n", i, lpusNoteIDs[i]);
	}
}