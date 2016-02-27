#include "at_o2xfs_xfs_cdm_v3_10_CashUnit3_10Test.h"

#include <Windows.h>
#include <XFSCDM.H>
#include "at.o2xfs.win32.h"

static WFSCDMCASHUNIT cashUnit;
static LPSTR cashUnitName = "BIN_1\0";
static WFSCDMPHCU physicalCashUnit;
static LPWFSCDMPHCU lppPhysical = &physicalCashUnit;

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cdm_v3_110_CashUnit3_110Test_buildCashUnit3_110(JNIEnv *env, jobject obj) {
	cashUnit.usNumber = 1;
	cashUnit.usType = WFS_CDM_TYPEBILLCASSETTE;
	cashUnit.lpszCashUnitName = cashUnitName;
	strcpy(cashUnit.cUnitID, "00001");
	strcpy(cashUnit.cCurrencyID, "EUR");
	cashUnit.ulValues = 10000;
	cashUnit.ulInitialCount = 100;
	cashUnit.ulCount = 98;
	cashUnit.ulRejectCount = 1;
	cashUnit.ulMinimum = 0;
	cashUnit.ulMaximum = 0;
	cashUnit.bAppLock = false;
	cashUnit.usStatus = WFS_CDM_STATCUOK;
	cashUnit.usNumPhysicalCUs = 1;
	cashUnit.lppPhysical = &lppPhysical;
	cashUnit.ulDispensedCount = 2;
	cashUnit.ulPresentedCount = 1;
	cashUnit.ulRetractedCount = 0;

	physicalCashUnit.lpPhysicalPositionName = cashUnitName;
	strcpy(physicalCashUnit.cUnitID, cashUnit.cUnitID);
	physicalCashUnit.ulInitialCount = cashUnit.ulInitialCount;
	physicalCashUnit.ulCount = cashUnit.ulCount;
	physicalCashUnit.ulRejectCount = cashUnit.ulRejectCount;
	physicalCashUnit.ulMaximum = 2200;
	physicalCashUnit.usPStatus = WFS_CDM_STATCUOK;
	physicalCashUnit.bHardwareSensor = false;
	physicalCashUnit.ulDispensedCount = cashUnit.ulDispensedCount;
	physicalCashUnit.ulPresentedCount = cashUnit.ulPresentedCount;
	physicalCashUnit.ulRetractedCount = cashUnit.ulRetractedCount;

	return NewBuffer(env, &cashUnit, sizeof(WFSCDMCASHUNIT));
}