#include "at_o2xfs_xfs_cdm_v3_00_CashUnitInfo3Test.h"

#include <Windows.h>
#include <XFSCDM.H>
#include "at.o2xfs.win32.h"

static WFSCDMCUINFO cashUnitInfo;
static WFSCDMCASHUNIT cashUnits[6];
static LPWFSCDMCASHUNIT lppList[6];
static WFSCDMPHCU physicalCUs[6];
static LPWFSCDMPHCU lppPhysical[6];
static LPSTR physicalPositionNames[6] = {"COMPARTMENT1", "COMPARTMENT2", "SLOT1", "SLOT2", "SLOT3", "SLOT4"};

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cdm_v3_100_CashUnitInfo3Test_buildCashUnitInfo3(JNIEnv *env, jobject obj) {
	cashUnitInfo.usTellerID = 0;
	cashUnitInfo.usCount = 6;
	cashUnitInfo.lppList = lppList;
	
	for(int i = 0; i < 6; i++) {
		lppList[i] = &cashUnits[i];		
		lppPhysical[i] = &physicalCUs[i];
		cashUnits[i].lppPhysical = &lppPhysical[i];
	}

	int index = 0;
	cashUnits[index].usNumber = index + 1;
	cashUnits[index].usType = WFS_CDM_TYPERETRACTCASSETTE;
	cashUnits[index].lpszCashUnitName = NULL;
	strcpy(cashUnits[index].cUnitID, "99999");
	strcpy(cashUnits[index].cCurrencyID, "   ");
	cashUnits[index].ulValues = 0;
	cashUnits[index].ulInitialCount = 0;
	cashUnits[index].ulCount = 0;
	cashUnits[index].ulRejectCount = 0;
	cashUnits[index].ulMinimum = 0;
	cashUnits[index].ulMaximum = 0;
	cashUnits[index].bAppLock = false;
	cashUnits[index].usStatus = WFS_CDM_STATCUOK;
	cashUnits[index].usNumPhysicalCUs = 1;

	physicalCUs[index].lpPhysicalPositionName = physicalPositionNames[index];
	strcpy(physicalCUs[index].cUnitID, cashUnits[index].cUnitID);
	physicalCUs[index].ulInitialCount = 0;
	physicalCUs[index].ulCount = 0;
	physicalCUs[index].ulRejectCount = 0;
	physicalCUs[index].ulMaximum = 50;
	physicalCUs[index].usPStatus = WFS_CDM_STATCUOK;
	physicalCUs[index].bHardwareSensor = false;
	
	index++;
	cashUnits[index].usNumber = index + 1;
	cashUnits[index].usType = WFS_CDM_TYPEREJECTCASSETTE;
	cashUnits[index].lpszCashUnitName = NULL;
	strcpy(cashUnits[index].cUnitID, "99999");
	strcpy(cashUnits[index].cCurrencyID, "   ");
	cashUnits[index].ulValues = 0;
	cashUnits[index].ulInitialCount = 0;
	cashUnits[index].ulCount = 4;
	cashUnits[index].ulRejectCount = 0;
	cashUnits[index].ulMinimum = 0;
	cashUnits[index].ulMaximum = 0;
	cashUnits[index].bAppLock = false;
	cashUnits[index].usStatus = WFS_CDM_STATCUOK;
	cashUnits[index].usNumPhysicalCUs = 1;

	physicalCUs[index].lpPhysicalPositionName = physicalPositionNames[index];
	strcpy(physicalCUs[index].cUnitID, cashUnits[index].cUnitID);
	physicalCUs[index].ulInitialCount = 0;
	physicalCUs[index].ulCount = 4;
	physicalCUs[index].ulRejectCount = 0;
	physicalCUs[index].ulMaximum = 200;
	physicalCUs[index].usPStatus = WFS_CDM_STATCUOK;
	physicalCUs[index].bHardwareSensor = false;

	index++;
	cashUnits[index].usNumber = index + 1;
	cashUnits[index].usType = WFS_CDM_TYPEBILLCASSETTE;
	cashUnits[index].lpszCashUnitName = NULL;
	strcpy(cashUnits[index].cUnitID, "00001");
	strcpy(cashUnits[index].cCurrencyID, "EUR");
	cashUnits[index].ulValues = 10000;
	cashUnits[index].ulInitialCount = 100;
	cashUnits[index].ulCount = 98;
	cashUnits[index].ulRejectCount = 1;
	cashUnits[index].ulMinimum = 0;
	cashUnits[index].ulMaximum = 0;
	cashUnits[index].bAppLock = false;
	cashUnits[index].usStatus = WFS_CDM_STATCUOK;
	cashUnits[index].usNumPhysicalCUs = 1;

	physicalCUs[index].lpPhysicalPositionName = physicalPositionNames[index];
	strcpy(physicalCUs[index].cUnitID, cashUnits[index].cUnitID);
	physicalCUs[index].ulInitialCount = cashUnits[index].ulInitialCount;
	physicalCUs[index].ulCount = cashUnits[index].ulCount;
	physicalCUs[index].ulRejectCount = cashUnits[index].ulRejectCount;
	physicalCUs[index].ulMaximum = 2200;
	physicalCUs[index].usPStatus = WFS_CDM_STATCUOK;
	physicalCUs[index].bHardwareSensor = false;

	index++;
	cashUnits[index].usNumber = index + 1;
	cashUnits[index].usType = WFS_CDM_TYPEBILLCASSETTE;
	cashUnits[index].lpszCashUnitName = NULL;
	strcpy(cashUnits[index].cUnitID, "00002");
	strcpy(cashUnits[index].cCurrencyID, "EUR");
	cashUnits[index].ulValues = 5000;
	cashUnits[index].ulInitialCount = 100;
	cashUnits[index].ulCount = 98;
	cashUnits[index].ulRejectCount = 1;
	cashUnits[index].ulMinimum = 0;
	cashUnits[index].ulMaximum = 0;
	cashUnits[index].bAppLock = false;
	cashUnits[index].usStatus = WFS_CDM_STATCUOK;
	cashUnits[index].usNumPhysicalCUs = 1;

	physicalCUs[index].lpPhysicalPositionName = physicalPositionNames[index];
	strcpy(physicalCUs[index].cUnitID, cashUnits[index].cUnitID);
	physicalCUs[index].ulInitialCount = cashUnits[index].ulInitialCount;
	physicalCUs[index].ulCount = cashUnits[index].ulCount;
	physicalCUs[index].ulRejectCount = cashUnits[index].ulRejectCount;
	physicalCUs[index].ulMaximum = 2200;
	physicalCUs[index].usPStatus = WFS_CDM_STATCUOK;
	physicalCUs[index].bHardwareSensor = false;

	index++;
	cashUnits[index].usNumber = index + 1;
	cashUnits[index].usType = WFS_CDM_TYPEBILLCASSETTE;
	cashUnits[index].lpszCashUnitName = NULL;
	strcpy(cashUnits[index].cUnitID, "00003");
	strcpy(cashUnits[index].cCurrencyID, "EUR");
	cashUnits[index].ulValues = 2000;
	cashUnits[index].ulInitialCount = 100;
	cashUnits[index].ulCount = 98;
	cashUnits[index].ulRejectCount = 1;
	cashUnits[index].ulMinimum = 0;
	cashUnits[index].ulMaximum = 0;
	cashUnits[index].bAppLock = false;
	cashUnits[index].usStatus = WFS_CDM_STATCUOK;
	cashUnits[index].usNumPhysicalCUs = 1;

	physicalCUs[index].lpPhysicalPositionName = physicalPositionNames[index];
	strcpy(physicalCUs[index].cUnitID, cashUnits[index].cUnitID);
	physicalCUs[index].ulInitialCount = cashUnits[index].ulInitialCount;
	physicalCUs[index].ulCount = cashUnits[index].ulCount;
	physicalCUs[index].ulRejectCount = cashUnits[index].ulRejectCount;
	physicalCUs[index].ulMaximum = 2200;
	physicalCUs[index].usPStatus = WFS_CDM_STATCUOK;
	physicalCUs[index].bHardwareSensor = false;

	index++;
	cashUnits[index].usNumber = index + 1;
	cashUnits[index].usType = WFS_CDM_TYPEBILLCASSETTE;
	cashUnits[index].lpszCashUnitName = NULL;
	strcpy(cashUnits[index].cUnitID, "00004");
	strcpy(cashUnits[index].cCurrencyID, "EUR");
	cashUnits[index].ulValues = 1000;
	cashUnits[index].ulInitialCount = 100;
	cashUnits[index].ulCount = 98;
	cashUnits[index].ulRejectCount = 1;
	cashUnits[index].ulMinimum = 0;
	cashUnits[index].ulMaximum = 0;
	cashUnits[index].bAppLock = false;
	cashUnits[index].usStatus = WFS_CDM_STATCUOK;
	cashUnits[index].usNumPhysicalCUs = 1;

	physicalCUs[index].lpPhysicalPositionName = physicalPositionNames[index];
	strcpy(physicalCUs[index].cUnitID, cashUnits[index].cUnitID);
	physicalCUs[index].ulInitialCount = cashUnits[index].ulInitialCount;
	physicalCUs[index].ulCount = cashUnits[index].ulCount;
	physicalCUs[index].ulRejectCount = cashUnits[index].ulRejectCount;
	physicalCUs[index].ulMaximum = 2200;
	physicalCUs[index].usPStatus = WFS_CDM_STATCUOK;
	physicalCUs[index].bHardwareSensor = false;

	return NewBuffer(env, &cashUnitInfo, sizeof(WFSCDMCUINFO));
}