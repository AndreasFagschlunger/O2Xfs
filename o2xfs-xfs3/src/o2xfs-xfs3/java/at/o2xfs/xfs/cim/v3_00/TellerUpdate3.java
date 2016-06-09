package at.o2xfs.xfs.cim.v3_00;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.xfs.cim.Action;
import at.o2xfs.xfs.win32.XfsWord;

public class TellerUpdate3 extends Struct {

	protected final XfsWord<Action> action = new XfsWord<>(Action.class);
	protected final Pointer tellerDetails = new Pointer();

	protected TellerUpdate3() {
		add(action);
		add(tellerDetails);
	}

	public TellerUpdate3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public TellerUpdate3(TellerUpdate3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(TellerUpdate3 copy) {
		action.set(copy.getAction());
		tellerDetails.pointTo(new TellerDetails3(copy.getTellerDetails()));
	}

	public Action getAction() {
		return action.get();
	}

	public TellerDetails3 getTellerDetails() {
		return new TellerDetails3(tellerDetails);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getAction()).append(getTellerDetails()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TellerUpdate3) {
			TellerUpdate3 tellerUpdate3 = (TellerUpdate3) obj;
			return new EqualsBuilder().append(getAction(), tellerUpdate3.getAction()).append(getTellerDetails(), tellerUpdate3.getTellerDetails()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("action", getAction()).append("tellerDetails", getTellerDetails()).toString();
	}
}