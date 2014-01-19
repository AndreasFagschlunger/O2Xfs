package at.o2xfs.xfs.pin;

import org.apache.commons.lang.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.USHORT;
import at.o2xfs.win32.WORD;
import at.o2xfs.xfs.util.XfsConstants;

public class WfsPINEntry extends Struct {

	private USHORT digits = new USHORT();
	private WORD completion = new WORD();

	public WfsPINEntry() {
		add(digits);
		add(completion);
	}

	public WfsPINEntry(Pointer p) {
		this();
		assignBuffer(p);
	}

	public WfsPINEntry(WfsPINEntry copy) {
		this();
		allocate();
		digits.put(copy.digits);
		completion.put(copy.completion);
	}

	public int getDigits() {
		return digits.intValue();
	}

	public void setDigits(int digits) {
		this.digits.put(digits);
	}

	public PINCompletion getCompletion() {
		return XfsConstants.valueOf(completion, PINCompletion.class);
	}

	public void setCompletion(PINCompletion completion) {
		this.completion.put(completion.getValue());
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("digits", getDigits())
				.append("completion", getCompletion()).toString();
	}
}
