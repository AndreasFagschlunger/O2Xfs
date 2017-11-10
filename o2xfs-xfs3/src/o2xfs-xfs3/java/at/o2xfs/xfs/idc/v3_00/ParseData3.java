package at.o2xfs.xfs.idc.v3_00;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.LPSTR;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;

public class ParseData3 extends Struct {

	protected final LPSTR formName = new LPSTR();
	protected final Pointer cardData = new Pointer();

	protected ParseData3() {
		add(formName);
		add(cardData);
	}

	public ParseData3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public ParseData3(ParseData3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(ParseData3 copy) {
		formName.set(copy.getFormName());
		cardData.pointTo(new CardDataZPointerArray3(copy.getCardData()));
	}

	public String getFormName() {
		return formName.get();
	}

	public CardData3[] getCardData() {
		return new CardDataZPointerArray3(cardData).get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getFormName()).append(getCardData()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ParseData3) {
			ParseData3 parseData3 = (ParseData3) obj;
			return new EqualsBuilder()
					.append(getFormName(), parseData3.getFormName())
					.append(getCardData(), parseData3.getCardData())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("formName", getFormName()).append("cardData", getCardData()).toString();
	}
}