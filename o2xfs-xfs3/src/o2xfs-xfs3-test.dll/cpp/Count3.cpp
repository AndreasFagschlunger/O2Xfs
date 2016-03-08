#include "at_o2xfs_xfs_cdm_v3_00_Count3Test.h"

#include <Windows.h>
#include <XFSCDM.H>
#include "at.o2xfs.win32.h"

static WFSCDMCOUNT count;
static WFSCDMCOUNTEDPHYSCU countedPhysCUs[2];
static LPWFSCDMCOUNTEDPHYSCU lppCountedPhysCUs[2];
static LPSTR physicalPositionNames[2] = {"SLOT1", "SLOT2"};

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cdm_v3_100_Count3Test_buildCount3(JNIEnv *env, jobject obj) {
	count.usNumPhysicalCUs = 2;
	count.lppCountedPhysCUs = lppCountedPhysCUs;

	countedPhysCUs[0].lpPhysicalPositionName = physicalPositionNames[0];
	strcpy(countedPhysCUs[0].cUnitId, "00001");
	countedPhysCUs[0].ulDispensed = 10;
	countedPhysCUs[0].ulCounted = 12;
	countedPhysCUs[0].usPStatus = WFS_CDM_STATCUOK;
	lppCountedPhysCUs[0] = &countedPhysCUs[0];

	countedPhysCUs[1].lpPhysicalPositionName = physicalPositionNames[1];
	strcpy(countedPhysCUs[1].cUnitId, "00002");
	countedPhysCUs[1].ulDispensed = 10;
	countedPhysCUs[1].ulCounted = 12;
	countedPhysCUs[1].usPStatus = WFS_CDM_STATCUFULL;
	lppCountedPhysCUs[1] = &countedPhysCUs[1];

	return NewBuffer(env, &count, sizeof(WFSCDMCOUNT));
}