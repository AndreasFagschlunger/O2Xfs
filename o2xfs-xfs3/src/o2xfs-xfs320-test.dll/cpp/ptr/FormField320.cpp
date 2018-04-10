#include "ptr/at_o2xfs_xfs_v3_20_ptr_FormField320Test.h"

#include <Windows.h>
#include <XFSPTR.H>
#include "at.o2xfs.win32.h"

static WFSFRMFIELD Field;
static LPSTR FieldName = "XFSFIELD";

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_120_ptr_FormField320Test_buildFormField320(JNIEnv *env, jobject obj) {
	Field.lpszFieldName = FieldName;
	Field.wIndexCount = 3;
	Field.fwType = WFS_FRM_FIELDTEXT;
	Field.fwClass = WFS_FRM_CLASSOPTIONAL;
	Field.fwAccess = WFS_FRM_ACCESSREAD | WFS_FRM_ACCESSWRITE;
	Field.fwOverflow = WFS_FRM_OVFOVERWRITE;
	Field.lpszInitialValue = NULL;
	Field.lpszUNICODEInitialValue = NULL;
	Field.lpszFormat = NULL;
	Field.lpszUNICODEFormat = NULL;
	Field.wLanguageID = MAKELANGID(LANG_GERMAN, SUBLANG_GERMAN_AUSTRIAN);
	Field.wCoercivity = WFS_FRM_COERCIVITYLOW;
	return NewBuffer(env, &Field, sizeof(WFSFRMFIELD));
}
