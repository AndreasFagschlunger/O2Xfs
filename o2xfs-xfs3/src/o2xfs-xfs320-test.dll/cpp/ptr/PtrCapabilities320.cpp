#include "ptr/at_o2xfs_xfs_v3_20_ptr_PtrCapabilities320Test.h"

#include <Windows.h>
#include <XFSPTR.H>
#include "at.o2xfs.win32.h"

static WFSPTRCAPS Caps;
static USHORT MaxRetract[] = { 10 };
static LPSTR Extra = "Key1=Value1\0Key2=Value2\0";

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_120_ptr_PtrCapabilities320Test_buildPtrCapabilities320(JNIEnv *env, jobject obj) {
	Caps.wClass = WFS_SERVICE_CLASS_PTR;
	Caps.fwType = WFS_PTR_TYPEPASSBOOK | WFS_PTR_TYPESCANNER;
	Caps.bCompound = TRUE;
	Caps.wResolution = WFS_PTR_RESLOW | WFS_PTR_RESMED | WFS_PTR_RESHIGH | WFS_PTR_RESVERYHIGH;
	Caps.fwReadForm = WFS_PTR_READOCR | WFS_PTR_READIMAGE;
	Caps.fwWriteForm = WFS_PTR_WRITETEXT | WFS_PTR_WRITEGRAPHICS | WFS_PTR_WRITEBARCODE;
	Caps.fwExtents = WFS_PTR_EXTHORIZONTAL | WFS_PTR_EXTVERTICAL;
	Caps.fwControl = WFS_PTR_CTRLEJECT | WFS_PTR_CTRLCUT;
	Caps.usMaxMediaOnStacker = 10;
	Caps.bAcceptMedia = TRUE;
	Caps.bMultiPage = FALSE;
	Caps.fwPaperSources = WFS_PTR_PAPERUPPER | WFS_PTR_PAPERLOWER;
	Caps.bMediaTaken = TRUE;
	Caps.usRetractBins = 1;
	Caps.lpusMaxRetract = MaxRetract;
	Caps.fwImageType = WFS_PTR_IMAGETIF | WFS_PTR_IMAGEWMF | WFS_PTR_IMAGEBMP;
	Caps.fwFrontImageColorFormat = WFS_PTR_IMAGECOLORBINARY | WFS_PTR_IMAGECOLORGRAYSCALE | WFS_PTR_IMAGECOLORFULL;
	Caps.fwBackImageColorFormat = WFS_PTR_IMAGECOLORFULL;
	Caps.fwCodelineFormat = WFS_PTR_CODELINECMC7 | WFS_PTR_CODELINEE13B | WFS_PTR_CODELINEOCR;
	Caps.fwImageSource = WFS_PTR_IMAGEFRONT | WFS_PTR_IMAGEBACK | WFS_PTR_CODELINE;
	Caps.fwCharSupport = WFS_PTR_ASCII | WFS_PTR_UNICODE;
	Caps.bDispensePaper = TRUE;
	Caps.lpszExtra = Extra;
	Caps.dwGuidLights[WFS_PTR_GUIDANCE_PRINTER] = WFS_PTR_GUIDANCE_MEDIUM_FLASH | WFS_PTR_GUIDANCE_GREEN;
	Caps.lpszWindowsPrinter = NULL;
	Caps.bMediaPresented = TRUE;
	Caps.usAutoRetractPeriod = 0;
	Caps.bRetractToTransport = FALSE;
	Caps.bPowerSaveControl = TRUE;
	Caps.fwCoercivityType = WFS_PTR_COERCIVITYLOW | WFS_PTR_COERCIVITYHIGH | WFS_PTR_COERCIVITYAUTO;
	Caps.fwControlPassbook = WFS_PTR_PBKCTRLTURNFORWARD | WFS_PTR_PBKCTRLTURNBACKWARD;
	Caps.wPrintSides = WFS_PTR_PRINTSIDESDUAL;
	Caps.bAntiFraudModule = TRUE;
	return NewBuffer(env, &Caps, sizeof(WFSPTRCAPS));
}
