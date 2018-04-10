#include "ptr/at_o2xfs_xfs_v3_00_ptr_FieldFailure3Test.h"

#include <Windows.h>
#include <XFSPTR.H>
#include "at.o2xfs.win32.h"

static WFSPTRFIELDFAIL FieldFail;
static LPTSTR FieldName = "XFSFIELD";
static LPTSTR FormName = "XFSFORM";

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_100_ptr_FieldFailure3Test_buildFieldFailure3(JNIEnv *env, jobject obj) {
	FieldFail.lpszFieldName = FieldName;
	FieldFail.lpszFormName = FormName;
	FieldFail.wFailure = WFS_PTR_FIELDOVERFLOW;
	return NewBuffer(env, &FieldFail, sizeof(WFSPTRFIELDFAIL));
}
