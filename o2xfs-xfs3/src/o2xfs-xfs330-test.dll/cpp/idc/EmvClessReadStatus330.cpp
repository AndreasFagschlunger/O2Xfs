#include "idc/at_o2xfs_xfs_idc_v3_30_EmvClessReadStatus3_30Test.h"

#include <Windows.h>
#include <XFSIDC.H>
#include "at.o2xfs.win32.h"

static WFSIDCMVCLESSREADSTATUS ReadStatus;
static WFSIDCEMVCLESSUI ClessUI;
static LPSTR Value = "100";
static LPSTR CurrencyCode = "EUR";
static LPSTR LanguagePreferenceData = "deen";

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_idc_v3_130_EmvClessReadStatus3_130Test_buildEmvClessReadStatus3_130(JNIEnv *env, jobject obj) {
	ClessUI.wMessageId = 0;
	ClessUI.wStatus = WFS_IDC_CLESS_PROCESSING;
	ClessUI.ulHoldTime = 10;
	ClessUI.wValueQualifier = WFS_IDC_CLESS_AMOUNT;
	ClessUI.lpszValue = Value;
	ClessUI.lpszCurrencyCode = CurrencyCode;
	ClessUI.lpszLanguagePreferenceData = LanguagePreferenceData;
	ReadStatus.lpClessUI = &ClessUI;
	return NewBuffer(env, &ReadStatus, sizeof(WFSIDCMVCLESSREADSTATUS));
}
