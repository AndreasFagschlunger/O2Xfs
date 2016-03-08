#include "at_o2xfs_xfs_cdm_v3_00_Calibrate3Test.h"

#include <Windows.h>
#include <XFSCDM.H>
#include "at.o2xfs.win32.h"

static WFSCDMCALIBRATE calibrate;
static WFSCDMITEMPOSITION position;
static LPWFSCDMITEMPOSITION lpPosition = &position;
static WFSCDMRETRACT retractArea;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cdm_v3_100_Calibrate3Test_buildCalibrate3(JNIEnv *env, jobject obj) {
	calibrate.usNumber = 1;
	calibrate.usNumOfBills = 8;
	calibrate.lpPosition = &lpPosition;

	position.usNumber = 1;
	position.lpRetractArea = &retractArea;
	position.fwOutputPosition = WFS_CDM_POSREAR;

	retractArea.usRetractArea = WFS_CDM_RA_REJECT;
	retractArea.usIndex = 1;	

	return NewBuffer(env, &calibrate, sizeof(WFSCDMCALIBRATE));
}