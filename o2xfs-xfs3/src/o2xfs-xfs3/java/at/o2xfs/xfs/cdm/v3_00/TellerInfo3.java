package at.o2xfs.xfs.cdm.v3_00;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.USHORT;
import at.o2xfs.xfs.win32.XfsCharArray;

public class TellerInfo3 extends Struct {

	protected final USHORT tellerID = new USHORT();
	protected final XfsCharArray currencyID = new XfsCharArray(3);

	protected TellerInfo3() {
		add(tellerID);
		add(currencyID);
	}

	public TellerInfo3(Pointer p) {
		this();
		assignBuffer(p);
	}

	private TellerInfo3(int tellerID, char[] currencyID) {
		this();
		allocate();
		this.tellerID.set(tellerID);
		this.currencyID.set(currencyID);
	}

	public TellerInfo3(TellerInfo3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(TellerInfo3 copy) {
		tellerID.set(copy.getTellerID());
		currencyID.set(copy.getCurrencyID());
	}

	public int getTellerID() {
		return tellerID.get();
	}

	public char[] getCurrencyID() {
		return currencyID.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getTellerID()).append(getCurrencyID()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TellerInfo3) {
			TellerInfo3 tellerInfo3 = (TellerInfo3) obj;
			return new EqualsBuilder().append(getTellerID(), tellerInfo3.getTellerID()).append(getCurrencyID(), tellerInfo3.getCurrencyID()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("tellerID", getTellerID()).append("currencyID", getCurrencyID()).toString();
	}

	public static final TellerInfo3 build(int tellerID, char[] currencyID) {
		return new TellerInfo3(tellerID, currencyID);
	}
}