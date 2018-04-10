#include "ptr/at_o2xfs_xfs_v3_00_ptr_ReadForm3Test.h"

#include <Windows.h>
#include <XFSPTR.H>
#include "at.o2xfs.win32.h"

static WFSPTRREADFORM ReadForm;
static LPSTR FormName = "XFSFORM";
static LPSTR FieldNames = "Name 1\0Name 2\0";

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_100_ptr_ReadForm3Test_buildReadForm3(JNIEnv *env, jobject obj) {
	ReadForm.lpszFormName = FormName;
	ReadForm.lpszFieldNames = FieldNames;
	ReadForm.lpszMediaName = 0;
	ReadForm.dwMediaControl = WFS_PTR_CTRLCUT | WFS_PTR_CTRLEJECT;
	return NewBuffer(env, &ReadForm, sizeof(WFSPTRREADFORM));
}
