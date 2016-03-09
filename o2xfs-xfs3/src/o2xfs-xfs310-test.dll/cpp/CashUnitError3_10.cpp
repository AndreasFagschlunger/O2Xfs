#include "at_o2xfs_xfs_cdm_v3_10_CashUnitError3_10Test.h"

#include <Windows.h>
#include <XFSCDM.H>
#include "at.o2xfs.win32.h"

static WFSCDMCUERROR CashUnitError;
static WFSCDMCASHUNIT CashUnit;
static WFSCDMPHCU PhysicalCUs[2];
static LPWFSCDMPHCU lppPhysical[2];
static LPSTR PhysicalPositionName[] = { "SLOT1", "SLOT2" };

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cdm_v3_110_CashUnitError3_110Test_buildCashUnitError3_110(JNIEnv *env, jobject obj) {
	CashUnitError.wFailure = WFS_CDM_CASHUNITERROR;
	CashUnitError.lpCashUnit = &CashUnit;

	CashUnit.usNumber = 1;
	CashUnit.usType = WFS_CDM_TYPEBILLCASSETTE;
	CashUnit.lpszCashUnitName = NULL;
	strcpy(CashUnit.cUnitID, "00001");
	strcpy(CashUnit.cCurrencyID, "EUR");
	CashUnit.ulValues = 10000;
	CashUnit.ulInitialCount = 100;
	CashUnit.ulCount = 98;
	CashUnit.ulRejectCount = 1;
	CashUnit.ulMinimum = 0;
	CashUnit.ulMaximum = 0;
	CashUnit.bAppLock = false;
	CashUnit.usStatus = WFS_CDM_STATCUINOP;
	CashUnit.usNumPhysicalCUs = 2;
	CashUnit.lppPhysical = lppPhysical;
	CashUnit.ulDispensedCount = 2;
	CashUnit.ulPresentedCount = 1;
	CashUnit.ulRetractedCount = 3;

	int index = 0;
	PhysicalCUs[index].lpPhysicalPositionName = PhysicalPositionName[index];
	strcpy(PhysicalCUs[index].cUnitID, CashUnit.cUnitID);
	PhysicalCUs[index].ulInitialCount = 0;
	PhysicalCUs[index].ulCount = 0;
	PhysicalCUs[index].ulRejectCount = 0;
	PhysicalCUs[index].ulMaximum = 50;
	PhysicalCUs[index].usPStatus = WFS_CDM_STATCUOK;
	PhysicalCUs[index].bHardwareSensor = false;
	PhysicalCUs[index].ulDispensedCount = 1;
	PhysicalCUs[index].ulPresentedCount = 1;
	PhysicalCUs[index].ulRetractedCount = 1;
	lppPhysical[index] = &PhysicalCUs[index];

	index++;
	PhysicalCUs[index].lpPhysicalPositionName = PhysicalPositionName[index];
	strcpy(PhysicalCUs[index].cUnitID, CashUnit.cUnitID);
	PhysicalCUs[index].ulInitialCount = 0;
	PhysicalCUs[index].ulCount = 0;
	PhysicalCUs[index].ulRejectCount = 0;
	PhysicalCUs[index].ulMaximum = 50;
	PhysicalCUs[index].usPStatus = WFS_CDM_STATCUOK;
	PhysicalCUs[index].bHardwareSensor = false;
	PhysicalCUs[index].ulDispensedCount = 1;
	PhysicalCUs[index].ulPresentedCount = 0;
	PhysicalCUs[index].ulRetractedCount = 2;
	lppPhysical[index] = &PhysicalCUs[index];

	return NewBuffer(env, &CashUnitError, sizeof(WFSCDMCUERROR));
}