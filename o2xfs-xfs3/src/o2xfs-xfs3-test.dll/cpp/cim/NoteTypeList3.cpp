#include "cim/at_o2xfs_xfs_cim_v3_00_NoteTypeList3Test.h"

#include <Windows.h>
#include <XFSCIM.H>
#include "at.o2xfs.win32.h"

static WFSCIMNOTETYPELIST NoteTypeList;
static WFSCIMNOTETYPE NoteTypes[8];
static LPWFSCIMNOTETYPE lppNoteTypes[8];

JNIEXPORT jobject JNICALL Java_at_o2xfs_xfs_cim_v3_100_NoteTypeList3Test_buildNoteTypeList3(JNIEnv *env, jobject obj) {
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