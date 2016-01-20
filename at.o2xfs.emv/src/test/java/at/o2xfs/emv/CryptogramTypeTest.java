package at.o2xfs.emv;

import at.o2xfs.common.Hex;

import org.junit.Assert;
import org.junit.Test;

public class CryptogramTypeTest {

	@Test
	public void assertAAC() {
		CryptogramType actual = CryptogramInformationData.parse(Hex.decode("0F")).getCryptogramType();
		Assert.assertEquals(CryptogramType.AAC, actual);
	}

	@Test
	public void assertTC() {
		CryptogramType actual = CryptogramInformationData.parse(Hex.decode("40")).getCryptogramType();
		Assert.assertEquals(CryptogramType.TC, actual);
	}

	@Test
	public void assertARQC() {
		CryptogramType actual = CryptogramInformationData.parse(Hex.decode("80")).getCryptogramType();
		Assert.assertEquals(CryptogramType.ARQC, actual);
	}
}