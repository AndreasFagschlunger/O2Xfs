#include "ptr/at_o2xfs_xfs_v3_00_ptr_PrintForm3Test.h"

#include <Windows.h>
#include <XFSPTR.H>
#include "at.o2xfs.win32.h"

static WFSPTRPRINTFORM PrintForm;
static LPSTR FormName = "XFSFORM";
static LPSTR MediaName = "XFSMEDIA";
static LPSTR Fields = "<FieldName1>=<FieldValue1>\0<FieldName1>=<FieldValue1>\0";
static LPWSTR UNICODEFields = L"<FieldName1>[0]=<FieldValue1>\0<FieldName2>[1]=<FieldValue2>\0";

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_100_ptr_PrintForm3Test_buildPrintForm3(JNIEnv *env, jobject obj) {
	PrintForm.lpszFormName = FormName;
	PrintForm.lpszMediaName = MediaName;
	PrintForm.wAlignment = WFS_PTR_ALNTOPLEFT;
	PrintForm.wOffsetX = 0;
	PrintForm.wOffsetY = WFS_PTR_OFFSETUSEFORMDEFN;
	PrintForm.wResolution = WFS_PTR_RESMED;
	PrintForm.dwMediaControl = WFS_PTR_CTRLEJECT | WFS_PTR_CTRLCUT;
	PrintForm.lpszFields = Fields;
	PrintForm.lpszUNICODEFields = UNICODEFields;
	PrintForm.wPaperSource = WFS_PTR_PAPERANY;
	return NewBuffer(env, &PrintForm, sizeof(WFSPTRPRINTFORM));
}
