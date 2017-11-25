#include "idc/at_o2xfs_xfs_v3_30_idc_EmvClessConfigData330Test.h"

#include <Windows.h>
#include <XFSIDC.H>
#include "at.o2xfs.win32.h"

static WFSIDCEMVCLESSCONFIGDATA ClessConfigData;
static WFSIDCHEXDATA TerminalData;
static BYTE TerminalDataData[] = { 0xca, 0xfe, 0xba, 0xbe };
static LPWFSIDCAIDDATA lppAIDData[3];
static WFSIDCAIDDATA AIDData[2];

static LPWFSIDCKEYDATA lppKeyData[2];
static WFSIDCKEYDATA KeyData[1];

static WFSIDCHEXDATA AID[2];
static LPSTR Data[] = { "A00000000F1234", "CAFE", "9F33039F1A029F35019F4005", "A0000000031010", "BABE", "FC40BCA000" };

static WFSIDCHEXDATA KernelIdentifier[2];
static WFSIDCHEXDATA ConfigData[2];

static WFSIDCHEXDATA RID;
static BYTE RIDData[] = { 0xa0, 0x00, 0x00, 0x00 };
static BYTE Exponent[] = { 0x01, 0x00, 0x01 };
static WFSIDCHEXDATA CAPublicKeyExponent;
static BYTE Modulus[] = { 0xca, 0xfe, 0xba, 0xbe };
static WFSIDCHEXDATA CAPublicKeyModulus;
static BYTE CAPublicKeyChecksum[] = { 0xc0, 0x7b, 0x64, 0xd4, 0xa9, 0xed, 0x77, 0x91, 0xf6, 0xc4, 0xec, 0x69, 0x33, 0xff, 0xcc, 0x42, 0x3f, 0x33, 0x90, 0x18 };

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_130_idc_EmvClessConfigData330Test_buildEmvClessConfigData330(JNIEnv *env, jobject obj) {
	TerminalData.ulLength = 4;
	TerminalData.lpbData = TerminalDataData;
	ClessConfigData.lpTerminalData = &TerminalData;
	
	AID[0].ulLength = strlen(Data[0]);
	AID[0].lpbData = (LPBYTE) Data[0];
	AIDData[0].lpAID = &AID[0];
	AIDData[0].bPartialSelection = FALSE;
	AIDData[0].ulTransactionType = 4;
	
	KernelIdentifier[0].ulLength = strlen(Data[1]);
	KernelIdentifier[0].lpbData = (LPBYTE) Data[1];
	AIDData[0].lpKernelIdentifier = &KernelIdentifier[0];
	
	ConfigData[0].ulLength = strlen(Data[2]);
	ConfigData[0].lpbData = (LPBYTE) Data[2];
	AIDData[0].lpConfigData = &ConfigData[0];	
	lppAIDData[0] = &AIDData[0];
	
	AID[1].ulLength = strlen(Data[3]);
	AID[1].lpbData = (LPBYTE) Data[3];
	AIDData[1].lpAID = &AID[1];
	AIDData[1].bPartialSelection = FALSE;
	AIDData[1].ulTransactionType = 0;

	KernelIdentifier[1].ulLength = strlen(Data[4]);
	KernelIdentifier[1].lpbData = (LPBYTE) Data[4];
	AIDData[1].lpKernelIdentifier = &KernelIdentifier[1];

	ConfigData[1].ulLength = strlen(Data[5]);
	ConfigData[1].lpbData = (LPBYTE) Data[5];
	AIDData[1].lpConfigData = &ConfigData[1];
	lppAIDData[1] = &AIDData[1];
	
	lppAIDData[2] = NULL;
	
	ClessConfigData.lppAIDData = lppAIDData;
	
	RID.ulLength = 4;
	RID.lpbData = RIDData;
	KeyData[0].lpRID = &RID;
	
	KeyData[0].wCAPublicKeyIndex = 0xFF;
	KeyData[0].wAPublicKeyAlgorithmIndicator = 1;
	CAPublicKeyExponent.ulLength = 3;
	CAPublicKeyExponent.lpbData = Exponent;
	KeyData[0].lpCAPublicKeyExponent = &CAPublicKeyExponent;
	CAPublicKeyModulus.ulLength = 4;
	CAPublicKeyModulus.lpbData = Modulus;
	KeyData[0].lpCAPublicKeyModulus = &CAPublicKeyModulus;
	KeyData[0].lpbCAPublicKeyCheckSum = CAPublicKeyChecksum;
	lppKeyData[0] = &KeyData[0];
	lppKeyData[1] = NULL;

	ClessConfigData.lppKeyData = lppKeyData;
	
	return NewBuffer(env, &ClessConfigData, sizeof(WFSIDCEMVCLESSCONFIGDATA));
}
