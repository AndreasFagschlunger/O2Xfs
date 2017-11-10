package at.o2xfs.xfs.idc.v3_00;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.xfs.idc.CardAction;
import at.o2xfs.xfs.idc.Position;
import at.o2xfs.xfs.win32.XfsWord;

public class CardAction3 extends Struct {

	protected final XfsWord<CardAction> action = new XfsWord<>(CardAction.class);
	protected final XfsWord<Position> position = new XfsWord<>(Position.class);

	protected CardAction3() {
		add(action);
		add(position);
	}

	public CardAction3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public CardAction3(CardAction3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(CardAction3 copy) {
		action.set(copy.getAction());
		position.set(copy.getPosition());
	}

	public CardAction getAction() {
		return action.get();
	}

	public Position getPosition() {
		return position.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getAction()).append(getPosition()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CardAction3) {
			CardAction3 cardAction3 = (CardAction3) obj;
			return new EqualsBuilder()
					.append(getAction(), cardAction3.getAction())
					.append(getPosition(), cardAction3.getPosition())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("action", getAction()).append("position", getPosition()).toString();
	}
}