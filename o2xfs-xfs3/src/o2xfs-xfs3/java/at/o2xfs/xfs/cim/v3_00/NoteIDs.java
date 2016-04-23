package at.o2xfs.xfs.cim.v3_00;

import at.o2xfs.win32.Buffer;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Type;
import at.o2xfs.win32.USHORT;
import at.o2xfs.win32.ValueType;

class NoteIDs extends Type implements ValueType<int[]> {

	private final USHORT[] noteIDs;

	public NoteIDs(int[] noteIDs) {
		this.noteIDs = new USHORT[noteIDs.length];
		allocate();
		set(noteIDs);
	}

	public NoteIDs(Pointer aPointer) {
		if (Pointer.NULL.equals(aPointer)) {
			noteIDs = new USHORT[0];
		} else {
			Buffer buffer = null;
			for (int i = 0;; i++) {
				buffer = aPointer.buffer((i * USHORT.SIZE) + USHORT.SIZE);
				Buffer subBuffer = buffer.subBuffer(i * USHORT.SIZE, USHORT.SIZE);
				USHORT uShort = new USHORT(subBuffer);
				if (uShort.intValue() == 0L) {
					noteIDs = new USHORT[i];
					assignBuffer(buffer);
					break;
				}
			}
		}
	}

	@Override
	public int getSize() {
		return noteIDs.length == 0 ? 0 : USHORT.SIZE + (noteIDs.length * USHORT.SIZE);
	}

	@Override
	public void set(int[] value) {
		for (int i = 0; i < noteIDs.length; i++) {
			noteIDs[i].set(value[i]);
		}
	}

	@Override
	public int[] get() {
		int result[] = new int[noteIDs.length];
		for (int i = 0; i < noteIDs.length; i++) {
			result[i] = noteIDs[i].intValue();
		}
		return result;
	}
}