package at.o2xfs.xfs.pin;

import at.o2xfs.win32.BYTE;
import at.o2xfs.win32.LPSTR;
import at.o2xfs.win32.Pointer;
import at.o2xfs.xfs.XfsStruct;
import at.o2xfs.xfs.XfsWord;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class WfsPinCrypt
		extends XfsStruct {

	private final XfsWord<PinMode> mode = new XfsWord<PinMode>(PinMode.class);
	private final LPSTR key = new LPSTR();
	private final Pointer keyEncKey = new Pointer();
	private final XfsWord<PINAlgorithm> algorithm = new XfsWord<PINAlgorithm>(PINAlgorithm.class);
	private final LPSTR startValueKey = new LPSTR();
	private final Pointer startValue = new Pointer();
	private final BYTE padding = new BYTE();
	private final BYTE compression = new BYTE();
	private final Pointer cryptData = new Pointer();

	public WfsPinCrypt() {
		add(mode).add(key)
					.add(keyEncKey)
					.add(algorithm)
					.add(startValueKey)
					.add(startValue)
					.add(padding)
					.add(compression)
					.add(cryptData);
	}

	public PinMode getMode() {
		return mode.get();
	}

	public void setMode(PinMode mode) {
		this.mode.set(mode);
	}

	public String getKey() {
		return key.toString();
	}

	public void setKey(String key) {
		this.key.put(key);
	}

	public WfsXData getKeyEncKey() {
		WfsXData result = null;
		if (!Pointer.NULL.equals(keyEncKey)) {
			result = new WfsXData(keyEncKey);
		}
		return result;
	}

	public XfsWord<PINAlgorithm> getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(PINAlgorithm algorithm) {
		this.algorithm.set(algorithm);
	}

	public String getStartValueKey() {
		return startValueKey.toString();
	}

	public byte[] getStartValue() {
		byte[] result = null;
		if (!Pointer.NULL.equals(startValue)) {
			result = new WfsXData(startValue).getData();
		}
		return result;
	}

	public void setStartValue(byte[] startValue) {
		this.startValue.pointTo(new WfsXData(startValue));
	}

	public byte getPadding() {
		return padding.byteValue();
	}

	public void setPadding(byte padding) {
		this.padding.set(padding);
	}

	public byte getCompression() {
		return compression.byteValue();
	}

	public void setCompression(byte compression) {
		this.compression.set(compression);
	}

	public byte[] getCryptData() {
		byte[] result = null;
		if (!Pointer.NULL.equals(cryptData)) {
			result = new WfsXData(cryptData).getData();
		}
		return result;
	}

	public void setCryptData(byte[] cryptData) {
		this.cryptData.pointTo(new WfsXData(cryptData));
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("mode", getMode())
										.append("key", getKey())
										.append("keyEncKey", getKeyEncKey())
										.append("algorithm", getAlgorithm())
										.append("startValueKey", getStartValueKey())
										.append("startValue", getStartValue())
										.append("padding", getPadding())
										.append("compression", getCompression())
										.append("cryptData", getCryptData())
										.toString();
	}
}