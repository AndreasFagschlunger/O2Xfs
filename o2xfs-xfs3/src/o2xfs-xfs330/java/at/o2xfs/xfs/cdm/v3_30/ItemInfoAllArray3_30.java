package at.o2xfs.xfs.cdm.v3_30;

import at.o2xfs.win32.Pointer;
import at.o2xfs.xfs.win32.XfsPointerArray;

class ItemInfoAllArray3_30 extends XfsPointerArray<ItemInfoAll3_30> {

	public ItemInfoAllArray3_30(ItemInfoAll3_30[] array) {
		super(array);
	}

	public ItemInfoAllArray3_30(Pointer p, int length) {
		super(p, length);
	}

	@Override
	public ItemInfoAll3_30[] get() {
		ItemInfoAll3_30[] result = new ItemInfoAll3_30[pointers.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = copy(new ItemInfoAll3_30(pointers[i]));
		}
		return result;
	}

	@Override
	public ItemInfoAll3_30 copy(ItemInfoAll3_30 copy) {
		return new ItemInfoAll3_30(copy);
	}
}