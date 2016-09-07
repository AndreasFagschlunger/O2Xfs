package at.o2xfs.xfs.bcr;

import at.o2xfs.win32.Buffer;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Type;
import at.o2xfs.win32.ValueType;
import at.o2xfs.win32.WORD;
import at.o2xfs.xfs.util.XfsConstants;

public class Symbologies extends Type implements ValueType<Symbology[]> {

	private final WORD[] symbologies;

	public Symbologies(Symbology[] symbologies) {
		this.symbologies = new WORD[symbologies.length];
		allocate();
		set(symbologies);
	}

	public Symbologies(Pointer aPointer) {
		if (Pointer.NULL.equals(aPointer)) {
			symbologies = new WORD[0];
		} else {
			Buffer buffer = null;
			for (int i = 0;; i++) {
				buffer = aPointer.buffer((i * WORD.SIZE) + WORD.SIZE);
				Buffer subBuffer = buffer.subBuffer(i * WORD.SIZE, WORD.SIZE);
				WORD word = new WORD(subBuffer);
				if (word.intValue() == 0L) {
					symbologies = new WORD[i];
					assignBuffer(buffer);
					break;
				}
			}
		}
	}

	@Override
	public int getSize() {
		return symbologies.length == 0 ? 0 : WORD.SIZE + (symbologies.length * WORD.SIZE);
	}

	@Override
	public void set(Symbology[] value) {
		for (int i = 0; i < value.length; i++) {
			symbologies[i].set((int) value[i].getValue());
		}
	}

	@Override
	public Symbology[] get() {
		Symbology[] result = new Symbology[symbologies.length];
		for (int i = 0; i < symbologies.length; i++) {
			result[i] = XfsConstants.valueOf(symbologies[i].longValue(), Symbology.class);
		}
		return result;
	}
}