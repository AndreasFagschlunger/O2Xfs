package at.o2xfs.xfs.v3_00.siu;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.xfs.siu.SIUGuidLight;
import at.o2xfs.xfs.siu.SIUGuidLightPortState;
import at.o2xfs.xfs.win32.XfsWord;

public class SetGuidLight3 extends Struct {

	protected final XfsWord<SIUGuidLight> guidLight = new XfsWord<>(SIUGuidLight.class);
	protected final XfsWord<SIUGuidLightPortState> command = new XfsWord<>(SIUGuidLightPortState.class);

	protected SetGuidLight3() {
		add(guidLight);
		add(command);
	}

	public SetGuidLight3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public SetGuidLight3(SetGuidLight3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(SetGuidLight3 copy) {
		guidLight.set(copy.getGuidLight());
		command.set(copy.getCommand());
	}

	public SIUGuidLight getGuidLight() {
		return guidLight.get();
	}

	public SIUGuidLightPortState getCommand() {
		return command.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getGuidLight()).append(getCommand()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SetGuidLight3) {
			SetGuidLight3 setGuidlight3 = (SetGuidLight3) obj;
			return new EqualsBuilder()
					.append(getGuidLight(), setGuidlight3.getGuidLight())
					.append(getCommand(), setGuidlight3.getCommand())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("guidLight", getGuidLight()).append("command", getCommand()).toString();
	}
}