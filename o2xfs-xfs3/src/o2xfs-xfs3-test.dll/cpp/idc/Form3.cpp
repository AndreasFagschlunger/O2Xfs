#include "idc/at_o2xfs_xfs_v3_00_idc_Form3Test.h"

#include <Windows.h>
#include <XFSIDC.H>
#include "at.o2xfs.win32.h"

static WFSIDCFORM Form;
static LPSTR FormName = "READTRACK3GERMAN";
static LPSTR lpszTrack1Fields = "\0";
static LPSTR lpszTrack2Fields = "MII\0ISSUERID\0ACCOUNT\0LUHNT3\0";
static LPSTR lpszTrack3Fields = "RETRYCOUNT\0DATE\0";

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_100_idc_Form3Test_buildForm3(JNIEnv *env, jobject obj) {
	Form.lpszFormName = FormName;
	Form.cFieldSeparatorTrack1 = '=';
	Form.cFieldSeparatorTrack2 = '=';
	Form.cFieldSeparatorTrack3 = '=';
	Form.fwAction = WFS_IDC_ACTIONREAD;
	Form.lpszTrack1Fields = lpszTrack1Fields;
	Form.lpszTrack2Fields = lpszTrack2Fields;
	Form.lpszTrack3Fields = lpszTrack3Fields;
	return NewBuffer(env, &Form, sizeof(WFSIDCFORM));
}
