#include "idc/at_o2xfs_xfs_v3_20_idc_Form320Test.h"

#include <Windows.h>
#include <XFSIDC.H>
#include "at.o2xfs.win32.h"

static WFSIDCFORM Form;
static LPSTR FormName = "READTRACK3GERMAN";
static LPSTR lpszTrack1Fields = "\0";
static LPSTR lpszTrack2Fields = "MII\0ISSUERID\0ACCOUNT\0LUHNT3\0";
static LPSTR lpszTrack3Fields = "RETRYCOUNT\0DATE\0";
static LPSTR lpszFrontTrack1Fields = "F1\0F2\0";
static LPSTR lpszJIS1Track1Fields = "F3\0F4\0";
static LPSTR lpszJIS1Track3Fields = "F5\0F6\0";

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_120_idc_Form320Test_buildForm320(JNIEnv *env, jobject obj) {
	Form.lpszFormName = FormName;
	Form.cFieldSeparatorTrack1 = '=';
	Form.cFieldSeparatorTrack2 = '=';
	Form.cFieldSeparatorTrack3 = '=';
	Form.fwAction = WFS_IDC_ACTIONREAD;
	Form.lpszTrack1Fields = lpszTrack1Fields;
	Form.lpszTrack2Fields = lpszTrack2Fields;
	Form.lpszTrack3Fields = lpszTrack3Fields;
	Form.lpszFrontTrack1Fields = lpszFrontTrack1Fields;
	Form.cFieldSeparatorFrontTrack1 = '=';
	Form.lpszJIS1Track1Fields = lpszJIS1Track1Fields;
	Form.lpszJIS1Track3Fields = lpszJIS1Track3Fields;
	Form.cFieldSeparatorJIS1Track1 = '=';
	Form.cFieldSeparatorJIS1Track3 = '=';
	return NewBuffer(env, &Form, sizeof(WFSIDCFORM));
}
