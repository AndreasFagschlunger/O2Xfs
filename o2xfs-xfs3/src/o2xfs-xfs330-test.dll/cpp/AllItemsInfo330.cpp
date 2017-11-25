/*
 * Copyright (c) 2017, Andreas Fagschlunger. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

#include "at_o2xfs_xfs_v3_30_cdm_AllItemsInfo330Test.h"

#include <Windows.h>
#include <XFSCDM.H>
#include "at.o2xfs.win32.h"

static WFSCDMALLITEMSINFO AllItemsInfo;
static WFSCDMITEMINFOALL ItemsList[3];
static LPWFSCDMITEMINFOALL lppItemsList[3];
static LPWSTR SerialNumber[] = { L"123", L"456", L"789" };
static LPSTR ImageFileName[] = { "file1.jpg", "file2.jpg", NULL };

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_130_cdm_AllItemsInfo330Test_buildAllItemsInfo330(JNIEnv *env, jobject obj) {
	int index = 0;
	ItemsList[index].usLevel = WFS_CDM_LEVEL_2;
	strcpy(ItemsList[index].cCurrencyID, "EUR");
	ItemsList[index].ulValue = 10;
	ItemsList[index].usRelease = 1;
	ItemsList[index].lpszSerialNumber = SerialNumber[index];
	ItemsList[index].lpszImageFileName = ImageFileName[index];
	ItemsList[index].wOnBlacklist = WFS_CDM_ONBLACKLIST;
	ItemsList[index].wItemLocation = WFS_CDM_LOCATION_CASHUNIT;
	ItemsList[index].usNumber = 1;
	lppItemsList[index] = &ItemsList[index];

	index++;
	ItemsList[index].usLevel = WFS_CDM_LEVEL_3;
	strcpy(ItemsList[index].cCurrencyID, "EUR");
	ItemsList[index].ulValue = 20;
	ItemsList[index].usRelease = 2;
	ItemsList[index].lpszSerialNumber = SerialNumber[index];
	ItemsList[index].lpszImageFileName = ImageFileName[index];
	ItemsList[index].wOnBlacklist = WFS_CDM_NOTONBLACKLIST;
	ItemsList[index].wItemLocation = WFS_CDM_LOCATION_CUSTOMER;
	ItemsList[index].usNumber = 2;
	lppItemsList[index] = &ItemsList[index];

	index++;
	ItemsList[index].usLevel = WFS_CDM_LEVEL_4;
	strcpy(ItemsList[index].cCurrencyID, "EUR");
	ItemsList[index].ulValue = 50;
	ItemsList[index].usRelease = 3;
	ItemsList[index].lpszSerialNumber = SerialNumber[index];
	ItemsList[index].lpszImageFileName = ImageFileName[index];
	ItemsList[index].wOnBlacklist = WFS_CDM_BLACKLISTUNKNOWN;
	ItemsList[index].wItemLocation = WFS_CDM_LOCATION_DEVICE;
	ItemsList[index].usNumber = 3;
	lppItemsList[index] = &ItemsList[index];

	AllItemsInfo.usCount = 3;
	AllItemsInfo.lppItemsList = lppItemsList;

	return NewBuffer(env, &AllItemsInfo, sizeof(WFSCDMALLITEMSINFO));
}
