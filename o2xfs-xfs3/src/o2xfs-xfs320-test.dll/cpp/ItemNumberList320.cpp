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

#include "at_o2xfs_xfs_v3_20_cdm_ItemNumberList320Test.h"

#include <Windows.h>
#include <XFSCDM.H>
#include "at.o2xfs.win32.h"

static WFSCDMITEMNUMBERLIST ItemNumberList;
static WFSCDMITEMNUMBER ItemNumber[3];
static LPWFSCDMITEMNUMBER lppItemNumber[3];

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_120_cdm_ItemNumberList320Test_buildItemNumberList320(JNIEnv *env, jobject object) {
	ItemNumberList.usNumOfItemNumbers = 3;
	ItemNumberList.lppItemNumber = lppItemNumber;

	strcpy(ItemNumber[0].cCurrencyID, "EUR");
	ItemNumber[0].ulValues = 10;
	ItemNumber[0].usRelease = 0;
	ItemNumber[0].ulCount = 1;
	ItemNumber[0].usNumber = 0;
	lppItemNumber[0] = &ItemNumber[0];

	strcpy(ItemNumber[1].cCurrencyID, "EUR");
	ItemNumber[1].ulValues = 20;
	ItemNumber[1].usRelease = 0;
	ItemNumber[1].ulCount = 1;
	ItemNumber[1].usNumber = 0;
	lppItemNumber[1] = &ItemNumber[1];

	strcpy(ItemNumber[2].cCurrencyID, "EUR");
	ItemNumber[2].ulValues = 100;
	ItemNumber[2].usRelease = 0;
	ItemNumber[2].ulCount = 1;
	ItemNumber[2].usNumber = 0;
	lppItemNumber[2] = &ItemNumber[2];

	return NewBuffer(env, &ItemNumberList, sizeof(WFSCDMITEMNUMBERLIST));
}
