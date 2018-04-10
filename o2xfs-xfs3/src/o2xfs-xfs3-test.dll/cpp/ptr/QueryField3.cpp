#include "ptr/at_o2xfs_xfs_v3_00_ptr_QueryField3Test.h"

#include <Windows.h>
#include <XFSPTR.H>
#include "at.o2xfs.win32.h"

static WFSPTRQUERYFIELD QueryField;
static LPSTR FormName = "XFSFORM";
static LPSTR FieldName = "XFSFIELD";

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_100_ptr_QueryField3Test_buildQueryField3(JNIEnv *env, jobject obj) {
	QueryField.lpszFormName = FormName;
	QueryField.lpszFieldName = FieldName;
	return NewBuffer(env, &QueryField, sizeof(WFSPTRQUERYFIELD));
}
