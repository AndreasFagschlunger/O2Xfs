package at.o2xfs.xfs.cam.v320;

import at.o2xfs.win32.Pointer;
import at.o2xfs.xfs.XfsWord;
import at.o2xfs.xfs.cam.AntiFraudModule;
import at.o2xfs.xfs.cam.CamStatus;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class CamStatusV3_20
		extends CamStatus {

	private final XfsWord<AntiFraudModule> antiFraudModule = new XfsWord<AntiFraudModule>(AntiFraudModule.class);

	private CamStatusV3_20() {
		super();
		add(antiFraudModule);
	}

	public CamStatusV3_20(Pointer p) {
		this();
		assignBuffer(p);
	}

	public CamStatusV3_20(CamStatusV3_20 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(CamStatusV3_20 status) {
		set((CamStatus) status);
		antiFraudModule.set(status.getAntiFraudModule());
	}

	public AntiFraudModule getAntiFraudModule() {
		return antiFraudModule.get();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).appendSuper(super.toString())
										.append("antiFraudModule", getAntiFraudModule())
										.toString();
	}
}