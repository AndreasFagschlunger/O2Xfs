package at.o2xfs.xfs.idc.v3_00;

import at.o2xfs.win32.Pointer;
import at.o2xfs.xfs.win32.XfsZPointerArray;

public class CardDataZPointerArray3 extends XfsZPointerArray<CardData3> {

	public CardDataZPointerArray3(CardData3[] array) {
		super(array);
	}

	public CardDataZPointerArray3(Pointer aPointer) {
		super(aPointer);
	}

	@Override
	public CardData3 copy(CardData3 copy) {
		return new CardData3(copy);
	}

	@Override
	public CardData3[] get() {
		CardData3[] result = new CardData3[pointers.length];
		for (int i = 0; i < pointers.length; i++) {
			result[i] = copy(new CardData3(pointers[i]));
		}
		return result;
	}
}
