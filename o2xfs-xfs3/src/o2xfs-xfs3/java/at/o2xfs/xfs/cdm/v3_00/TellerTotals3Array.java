package at.o2xfs.xfs.cdm.v3_00;

import at.o2xfs.win32.Pointer;
import at.o2xfs.xfs.win32.XfsZPointerArray;

class TellerTotals3Array extends XfsZPointerArray<TellerTotals3> {

	public TellerTotals3Array(TellerTotals3[] array) {
		super(array);
	}

	public TellerTotals3Array(Pointer aPointer) {
		super(aPointer);
	}

	@Override
	public TellerTotals3 copy(TellerTotals3 copy) {
		return new TellerTotals3(copy);
	}

	@Override
	public TellerTotals3[] get() {
		TellerTotals3[] result = new TellerTotals3[pointers.length];
		for (int i = 0; i < pointers.length; i++) {
			result[i] = copy(new TellerTotals3(pointers[i]));
		}
		return result;
	}
}