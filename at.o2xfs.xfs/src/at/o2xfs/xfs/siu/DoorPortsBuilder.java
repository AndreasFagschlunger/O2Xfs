package at.o2xfs.xfs.siu;

import at.o2xfs.win32.WORDArray;

public final class DoorPortsBuilder extends AbstractPortsBuilder {

	protected DoorPortsBuilder(final WORDArray ports) {
		super(ports);
	}

	public void setCabinetDoors(final SIUCabinetDoorsPortState port) {
		setPort(SIUDoor.CABINET, port);
	}

	public void setSafeDoors(final SIUSafeDoorsPortState port) {
		setPort(SIUDoor.SAFE, port);
	}

	public void setVandalShield(final SIUVandalShieldPortState port) {
		setPort(SIUDoor.VANDALSHIELD, port);
	}

	public void setFrontCabinetDoors(final SIUCabinetDoorsPortState port) {
		setPort(SIUDoor.CABINET_FRONT, port);
	}

	public void setRearCabinetDoors(final SIUCabinetDoorsPortState port) {
		setPort(SIUDoor.CABINET_REAR, port);
	}

	public void setLeftCabinetDoors(final SIUCabinetDoorsPortState port) {
		setPort(SIUDoor.CABINET_LEFT, port);
	}

	public void setRightCabinetDoors(final SIUCabinetDoorsPortState port) {
		setPort(SIUDoor.CABINET_RIGHT, port);
	}
}
