package at.o2xfs.xfs.pin;

import org.apache.commons.lang.builder.ToStringBuilder;

import at.o2xfs.win32.BYTE;
import at.o2xfs.win32.LPSTR;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.WORD;
import at.o2xfs.xfs.util.XfsConstants;

public class WfsPINBlock extends Struct {

	private LPSTR customerData = new LPSTR();
	private LPSTR xorData = new LPSTR();
	private BYTE padding = new BYTE();
	private WORD format = new WORD();
	private LPSTR key = new LPSTR();
	private LPSTR keyEncKey = new LPSTR();

	public WfsPINBlock() {
		add(customerData);
		add(xorData);
		add(padding);
		add(format);
		add(key);
		add(keyEncKey);
	}

	public String getCustomerData() {
		return customerData.toString();
	}

	public void setCustomerData(String customerData) {
		this.customerData.pointTo(customerData);
	}

	public LPSTR getXORData() {
		return xorData;
	}

	public void setXORData(String xorData) {
		this.xorData.pointTo(xorData);
	}

	public byte getPadding() {
		return padding.getByte();
	}

	public void setPadding(byte padding) {
		this.padding.put(padding);
	}

	public PINFormat getFormat() {
		return XfsConstants.valueOf(format, PINFormat.class);
	}

	public void setFormat(PINFormat format) {
		this.format.put(format.getValue());
	}

	public String getKey() {
		return key.toString();
	}

	public void setKey(String key) {
		this.key.pointTo(key);
	}

	public String getKeyEncKey() {
		return keyEncKey.toString();
	}

	public void setKeyEncKey(String keyEncKey) {
		this.keyEncKey.pointTo(keyEncKey);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("customerData", getCustomerData())
				.append("xorData", getXORData())
				.append("padding", getPadding()).append("format", getFormat())
				.append("key", getKey()).append("keyEncKey", getKeyEncKey())
				.toString();
	}
}
