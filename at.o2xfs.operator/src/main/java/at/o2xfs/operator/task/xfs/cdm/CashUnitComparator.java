package at.o2xfs.operator.task.xfs.cdm;

import java.util.Comparator;

import at.o2xfs.xfs.cdm.v3_00.CashUnit3;

public class CashUnitComparator implements Comparator<CashUnit3> {

	@Override
	public int compare(CashUnit3 cashUnit1, CashUnit3 cashUnit2) {
		return Long.valueOf(cashUnit1.getValue()).compareTo(Long.valueOf(cashUnit2.getValue()));
	}
}