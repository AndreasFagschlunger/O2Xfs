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

#include "cim/at_o2xfs_xfs_v3_00_cim_NoteTypeList3Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

static WFSCIMNOTETYPELIST NoteTypeList;
static WFSCIMNOTETYPE NoteTypes[8];
static LPWFSCIMNOTETYPE lppNoteTypes[8];

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_v3_100_cim_NoteTypeList3Test_buildNoteTypeList3(JNIEnv *env, jobject obj) {
	NoteTypeList.usNumOfNoteTypes = 8;
	NoteTypeList.lppNoteTypes = lppNoteTypes;

	int noteTypeIndex = 0;
	lppNoteTypes[noteTypeIndex] = &NoteTypes[noteTypeIndex];
	NoteTypes[noteTypeIndex].usNoteID = 1;
	strcpy(NoteTypes[noteTypeIndex].cCurrencyID, "EUR");
	NoteTypes[noteTypeIndex].ulValues = 1;
	NoteTypes[noteTypeIndex].usRelease = 0;
	NoteTypes[noteTypeIndex].bConfigured = TRUE;

	noteTypeIndex++;
	lppNoteTypes[noteTypeIndex] = &NoteTypes[noteTypeIndex];
	NoteTypes[noteTypeIndex].usNoteID = 2;
	strcpy(NoteTypes[noteTypeIndex].cCurrencyID, "EUR");
	NoteTypes[noteTypeIndex].ulValues = 2;
	NoteTypes[noteTypeIndex].usRelease = 0;
	NoteTypes[noteTypeIndex].bConfigured = TRUE;

	noteTypeIndex++;
	lppNoteTypes[noteTypeIndex] = &NoteTypes[noteTypeIndex];
	NoteTypes[noteTypeIndex].usNoteID = 3;
	strcpy(NoteTypes[noteTypeIndex].cCurrencyID, "EUR");
	NoteTypes[noteTypeIndex].ulValues = 5;
	NoteTypes[noteTypeIndex].usRelease = 0;
	NoteTypes[noteTypeIndex].bConfigured = TRUE;

	noteTypeIndex++;
	lppNoteTypes[noteTypeIndex] = &NoteTypes[noteTypeIndex];
	NoteTypes[noteTypeIndex].usNoteID = 4;
	strcpy(NoteTypes[noteTypeIndex].cCurrencyID, "EUR");
	NoteTypes[noteTypeIndex].ulValues = 10;
	NoteTypes[noteTypeIndex].usRelease = 0;
	NoteTypes[noteTypeIndex].bConfigured = TRUE;

	noteTypeIndex++;
	lppNoteTypes[noteTypeIndex] = &NoteTypes[noteTypeIndex];
	NoteTypes[noteTypeIndex].usNoteID = 5;
	strcpy(NoteTypes[noteTypeIndex].cCurrencyID, "EUR");
	NoteTypes[noteTypeIndex].ulValues = 20;
	NoteTypes[noteTypeIndex].usRelease = 0;
	NoteTypes[noteTypeIndex].bConfigured = TRUE;

	noteTypeIndex++;
	lppNoteTypes[noteTypeIndex] = &NoteTypes[noteTypeIndex];
	NoteTypes[noteTypeIndex].usNoteID = 6;
	strcpy(NoteTypes[noteTypeIndex].cCurrencyID, "EUR");
	NoteTypes[noteTypeIndex].ulValues = 50;
	NoteTypes[noteTypeIndex].usRelease = 0;
	NoteTypes[noteTypeIndex].bConfigured = TRUE;

	noteTypeIndex++;
	lppNoteTypes[noteTypeIndex] = &NoteTypes[noteTypeIndex];
	NoteTypes[noteTypeIndex].usNoteID = 7;
	strcpy(NoteTypes[noteTypeIndex].cCurrencyID, "EUR");
	NoteTypes[noteTypeIndex].ulValues = 100;
	NoteTypes[noteTypeIndex].usRelease = 0;
	NoteTypes[noteTypeIndex].bConfigured = TRUE;

	noteTypeIndex++;
	lppNoteTypes[noteTypeIndex] = &NoteTypes[noteTypeIndex];
	NoteTypes[noteTypeIndex].usNoteID = 8;
	strcpy(NoteTypes[noteTypeIndex].cCurrencyID, "EUR");
	NoteTypes[noteTypeIndex].ulValues = 200;
	NoteTypes[noteTypeIndex].usRelease = 0;
	NoteTypes[noteTypeIndex].bConfigured = TRUE;

	return NewBuffer(env, &NoteTypeList, sizeof(WFSCIMNOTETYPELIST));
}
