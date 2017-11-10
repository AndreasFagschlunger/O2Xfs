package at.o2xfs.xfs.idc.v3_00;

import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.USHORT;
import at.o2xfs.xfs.XfsExtra;
import at.o2xfs.xfs.idc.ChipPower;
import at.o2xfs.xfs.idc.IdcDeviceState;
import at.o2xfs.xfs.idc.Position;
import at.o2xfs.xfs.idc.RetainBin;
import at.o2xfs.xfs.idc.Security;
import at.o2xfs.xfs.win32.XfsWord;

public class IdcStatus3 extends Struct {

	protected final XfsWord<IdcDeviceState> deviceState = new XfsWord<>(IdcDeviceState.class);
	protected final XfsWord<Position> media = new XfsWord<>(Position.class);
	protected final XfsWord<RetainBin> retainBin = new XfsWord<>(RetainBin.class);
	protected final XfsWord<Security> security = new XfsWord<>(Security.class);
	protected final USHORT cards = new USHORT();
	protected final XfsWord<ChipPower> chipPower = new XfsWord<>(ChipPower.class);
	protected final XfsExtra extra = new XfsExtra();

	protected IdcStatus3() {
		add(deviceState);
		add(media);
		add(retainBin);
		add(security);
		add(cards);
		add(chipPower);
		add(extra);
	}

	public IdcStatus3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public IdcStatus3(IdcStatus3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(IdcStatus3 copy) {
		deviceState.set(copy.getDeviceState());
		media.set(copy.getMedia());
		retainBin.set(copy.getRetainBin());
		security.set(copy.getSecurity());
		cards.set(copy.getCards());
		chipPower.set(copy.getChipPower());
		extra.set(copy.getExtra());
	}

	public IdcDeviceState getDeviceState() {
		return deviceState.get();
	}

	public Position getMedia() {
		return media.get();
	}

	public RetainBin getRetainBin() {
		return retainBin.get();
	}

	public Security getSecurity() {
		return security.get();
	}

	public int getCards() {
		return cards.get();
	}

	public ChipPower getChipPower() {
		return chipPower.get();
	}

	public Map<String, String> getExtra() {
		return extra.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(getDeviceState())
				.append(getMedia())
				.append(getRetainBin())
				.append(getSecurity())
				.append(getCards())
				.append(getChipPower())
				.append(getExtra())
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof IdcStatus3) {
			IdcStatus3 status3 = (IdcStatus3) obj;
			return new EqualsBuilder()
					.append(getDeviceState(), status3.getDeviceState())
					.append(getMedia(), status3.getMedia())
					.append(getRetainBin(), status3.getRetainBin())
					.append(getSecurity(), status3.getSecurity())
					.append(getCards(), status3.getCards())
					.append(getChipPower(), status3.getChipPower())
					.append(getExtra(), status3.getExtra())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("deviceState", getDeviceState())
				.append("media", getMedia())
				.append("retainBin", getRetainBin())
				.append("security", getSecurity())
				.append("cards", getCards())
				.append("chipPower", getChipPower())
				.append("extra", getExtra())
				.toString();
	}
}