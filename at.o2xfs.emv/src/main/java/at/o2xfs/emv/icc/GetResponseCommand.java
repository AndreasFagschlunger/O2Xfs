package at.o2xfs.emv.icc;

public class GetResponseCommand
		extends CAPDU {

	private GetResponseCommand(int le) {
		super(0x00, 0xC0, 0x00, 0x00, le);
	}

	public static GetResponseCommand create(int le) {
		return new GetResponseCommand(le);
	}
}