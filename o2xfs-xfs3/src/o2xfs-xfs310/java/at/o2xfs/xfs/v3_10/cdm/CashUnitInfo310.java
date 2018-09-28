package at.o2xfs.xfs.v3_10.cdm;

import at.o2xfs.win32.Pointer;
import at.o2xfs.xfs.v3_00.cdm.CashUnitInfo3;

public class CashUnitInfo310 extends CashUnitInfo3 {

	public CashUnitInfo310() {
		super();
	}

	public CashUnitInfo310(CashUnitInfo310 copy) {
		super();
		allocate();
		tellerID.set(copy.getTellerID());
		count.set(copy.getCount());
		list.pointTo(new CashUnits310(copy.getList()));
	}

	public CashUnitInfo310(Pointer p) {
		super(p);
	}

	@Override
	public CashUnit310[] getList() {
		return new CashUnits310(list, getCount()).get();
	}
}
