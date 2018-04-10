#include "ptr/at_o2xfs_xfs_v3_00_ptr_PtrStatus3Test.h"

#include <Windows.h>
#include <XFSPTR.H>
#include "at.o2xfs.win32.h"

static WFSPTRSTATUS Status;
static LPWFSPTRRETRACTBINS RetractBins[2];
static WFSPTRRETRACTBINS RetractBin;
static LPSTR Extra = "Key=Value\0";

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_100_ptr_PtrStatus3Test_buildPtrStatus3(JNIEnv *env, jobject obj) {
	Status.fwDevice = WFS_PTR_DEVONLINE;
	Status.fwMedia = WFS_PTR_MEDIANOTPRESENT;
	Status.fwPaper[WFS_PTR_SUPPLYUPPER] = WFS_PTR_PAPERFULL;
	Status.fwPaper[WFS_PTR_SUPPLYLOWER] = WFS_PTR_PAPERLOW;
	Status.fwToner = WFS_PTR_TONERLOW;
	Status.fwInk = WFS_PTR_INKNOTSUPP;
	Status.fwLamp = WFS_PTR_LAMPOK;
	Status.lppRetractBins = RetractBins;
	RetractBin.wRetractBin = WFS_PTR_RETRACTBINHIGH;
	RetractBin.usRetractCount = 10;
	RetractBins[0] = &RetractBin;
	RetractBins[1] = NULL;
	Status.usMediaOnStacker = 3;
	Status.lpszExtra = Extra;
	return NewBuffer(env, &Status, sizeof(WFSPTRSTATUS));
}
