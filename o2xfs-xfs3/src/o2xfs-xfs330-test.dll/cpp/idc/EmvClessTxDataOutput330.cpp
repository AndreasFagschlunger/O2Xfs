#include "idc/at_o2xfs_xfs_v3_30_idc_EmvClessTxDataOutput330Test.h"

#include <Windows.h>
#include <XFSIDC.H>
#include "at.o2xfs.win32.h"

static WFSIDCEMVCLESSTXDATAOUTPUT ClessTxDataOutput;
static WFSIDCHEXDATA DataRead;
static BYTE Data[] = { 0xca, 0xfe, 0xba, 0xbe };
static WFSIDCEMVCLESSOUTCOME ClessOutcome;
static WFSIDCEMVCLESSUI ClessUIOutcome;
static LPSTR Value = "100";
static LPSTR CurrencyCode = "EUR";
static LPSTR LanguagePreferenceData = "deen";
static WFSIDCHEXDATA DiscretionaryData;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_130_idc_EmvClessTxDataOutput330Test_buildEmvClessTxDataOutput330(JNIEnv *env, jobject obj) {
	ClessTxDataOutput.wDataSource = WFS_IDC_CHIP;
	ClessTxDataOutput.wTxOutcome = WFS_IDC_CLESS_APPROVE;
	ClessTxDataOutput.wCardholderAction = WFS_IDC_CLESS_NOACTION;
	
	DataRead.ulLength = 4;
	DataRead.lpbData = Data;
	ClessTxDataOutput.lpDataRead = &DataRead;
	
	ClessOutcome.wCVM = WFS_IDC_CLESS_ONLINEPIN;
	ClessOutcome.wAlternateInterface = WFS_IDC_CLESS_CONTACT;
	ClessOutcome.bReceipt = FALSE;
	ClessUIOutcome.wMessageId = 0;
	ClessUIOutcome.wStatus = WFS_IDC_CLESS_PROCESSING;
	ClessUIOutcome.ulHoldTime = 10;
	ClessUIOutcome.wValueQualifier = WFS_IDC_CLESS_AMOUNT;
	ClessUIOutcome.lpszValue = Value;
	ClessUIOutcome.lpszCurrencyCode = CurrencyCode;
	ClessUIOutcome.lpszLanguagePreferenceData = LanguagePreferenceData;
	ClessOutcome.lpClessUIOutcome = &ClessUIOutcome;
	ClessOutcome.lpClessUIRestart = &ClessUIOutcome;
	ClessOutcome.ulClessFieldOffHoldTime = 0;
	ClessOutcome.ulCardRemovalTimeoutValue = 10;
	DiscretionaryData.ulLength = 4;
	DiscretionaryData.lpbData = Data;
	ClessOutcome.lpDiscretionaryData = &DiscretionaryData;
	ClessTxDataOutput.lpClessOutcome = &ClessOutcome;
	return NewBuffer(env, &ClessTxDataOutput, sizeof(WFSIDCEMVCLESSTXDATAOUTPUT));
}
