#include "ptr/at_o2xfs_xfs_v3_00_ptr_FormField3Test.h"

#include <Windows.h>
#include <XFSPTR.H>
#include "at.o2xfs.win32.h"

static WFSFRMFIELD Field;
static LPSTR FieldName = "XFSFIELD";
static LPSTR InitialValue = "InitialValue";
static LPWSTR UNICODEInitialValue = L"UNICODEInitialValue";
static LPSTR Format = "Format";
static LPWSTR UNICODEFormat = L"UNICODEFormat";

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_100_ptr_FormField3Test_buildFormField3(JNIEnv *env, jobject obj) {
	Field.lpszFieldName = FieldName;
	Field.wIndexCount = 3;
	Field.fwType = WFS_FRM_FIELDTEXT;
	Field.fwClass = WFS_FRM_CLASSOPTIONAL;
	Field.fwAccess = WFS_FRM_ACCESSREAD | WFS_FRM_ACCESSWRITE;
	Field.fwOverflow = WFS_FRM_OVFOVERWRITE;
	Field.lpszInitialValue = InitialValue;
	Field.lpszUNICODEInitialValue = UNICODEInitialValue;
	Field.lpszFormat = Format;
	Field.lpszUNICODEFormat = UNICODEFormat;
	return NewBuffer(env, &Field, sizeof(WFSFRMFIELD));
}
