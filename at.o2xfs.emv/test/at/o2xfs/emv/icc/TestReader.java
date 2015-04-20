package at.o2xfs.emv.icc;

import at.o2xfs.common.Hex;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;

@Ignore
public class TestReader
		extends AbstractICReader {

	private class TPDU {

		private final String ctpdu;
		private final String rtpdu;

		private TPDU(String ctpdu, String rtpdu) {
			this.ctpdu = ctpdu;
			this.rtpdu = rtpdu;
		}
	}

	private final byte[] atr;

	private final List<TPDU> tpdus;

	private int tpduIndex = 0;

	public TestReader(byte[] atr) {
		this.atr = atr;
		tpdus = new ArrayList<TPDU>();
	}

	public TestReader expect(String ctpdu, String rtpdu) {
		tpdus.add(new TPDU(ctpdu, rtpdu));
		return this;
	}

	@Override
	protected byte[] internalReset() throws IOException {
		return atr;
	}

	@Override
	protected RAPDU internalTransmit(byte[] ctpdu) throws IOException {
		TPDU tpdu = tpdus.get(tpduIndex++);
		Assert.assertEquals(tpdu.ctpdu, Hex.encode(ctpdu));
		return new RAPDU(Hex.decode(tpdu.rtpdu));
	}

}
