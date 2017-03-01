/*
 * Copyright (c) 2017, Andreas Fagschlunger. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package at.o2xfs.operator.task.xfs.siu;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.operator.ui.content.table.Table;
import at.o2xfs.operator.ui.content.text.Label;
import at.o2xfs.xfs.XfsException;
import at.o2xfs.xfs.service.siu.SIUService;
import at.o2xfs.xfs.service.siu.SIUServiceListener;
import at.o2xfs.xfs.service.siu.SIUStatusCallable;
import at.o2xfs.xfs.siu.SIUAuxiliary;
import at.o2xfs.xfs.siu.SIUDoor;
import at.o2xfs.xfs.siu.SIUGuidLight;
import at.o2xfs.xfs.siu.SIUIndicator;
import at.o2xfs.xfs.siu.SIUPortEvent;
import at.o2xfs.xfs.siu.SIUPortIndex;
import at.o2xfs.xfs.siu.SIUPortType;
import at.o2xfs.xfs.siu.SIUSensor;
import at.o2xfs.xfs.siu.SIUStatus;

public class SIUStatusTask extends SIUServiceTask implements SIUServiceListener {

	private static final Logger LOG = LoggerFactory.getLogger(SIUStatusTask.class);

	private static final int STATUS_COLUMN = 1;

	private final Map<SIUPortType, Map<Enum<?>, Integer>> indexes;

	private Table table = null;

	private SIUStatus siuStatus = null;

	public SIUStatusTask() {
		this(null);
	}

	public SIUStatusTask(final SIUService service) {
		super(service);
		indexes = new EnumMap<SIUPortType, Map<Enum<?>, Integer>>(SIUPortType.class);
		for (final SIUPortType portType : SIUPortType.values()) {
			indexes.put(portType, new HashMap<Enum<?>, Integer>());
		}
	}

	@Override
	protected void execute() {
		final String method = "execute()";
		try {
			siuStatus = new SIUStatusCallable(service).call();
			table = new Table(getClass(), "Component", "Status");
			addRow("Device", siuStatus.getDevice());
			addSensors();
			addDoors();
			addIndicators();
			addAuxiliaries();
			addGuidLights();
			getContent().setUIElement(table);
			service.addServiceListener(this);
		} catch (final XfsException e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "Error getting SIU status", e);
			}
			showException(e);
		}
	}

	@Override
	protected void close() {
		service.removeServiceListener(this);
	}

	private void addSensors() {
		addRow("Sensors", "");
		addSensor(SIUSensor.OPERATORSWITCH, "OperatorSwitch");
		addSensor(SIUSensor.TAMPER, "TamperSensor");
		addSensor(SIUSensor.INTTAMPER, "InternalTamperSensor");
		addSensor(SIUSensor.SEISMIC, "SeismicSensor");
		addSensor(SIUSensor.HEAT, "HeatSensor");
		addSensor(SIUSensor.PROXIMITY, "ProximitySensor");
		addSensor(SIUSensor.AMBLIGHT, "AmbientLightSensor");
		addSensor(SIUSensor.ENHANCEDAUDIO, "AudioJack");
		addSensor(SIUSensor.BOOT_SWITCH, "BootSwitchSensor");
		addSensor(SIUSensor.CONSUMER_DISPLAY, "ConsumerDisplay");
		addSensor(SIUSensor.OPERATOR_CALL_BUTTON, "OperatorCallButton");
		addSensor(SIUSensor.HANDSETSENSOR, "Handset");
		addSensor(SIUSensor.GENERALINPUTPORT, "GeneralInputPorts");
	}

	private void addSensor(final SIUSensor sensor, final String label) {
		final Object state = new SensorStateFactory().createState(sensor, siuStatus.getSensorState(sensor));
		addPortStatus(SIUPortType.SENSORS, sensor, label, state);
	}

	private void addDoors() {
		addRow("Doors", "");
		addDoor(SIUDoor.CABINET, "CabinetDoors");
		addDoor(SIUDoor.SAFE, "SafeDoors");
		addDoor(SIUDoor.VANDALSHIELD, "VandalShield");
		addDoor(SIUDoor.CABINET_FRONT, "FrontCabinetDoors");
		addDoor(SIUDoor.CABINET_REAR, "RearCabinetDoors");
		addDoor(SIUDoor.CABINET_LEFT, "LeftCabinetDoors");
		addDoor(SIUDoor.CABINET_RIGHT, "RightCabinetDoors");
	}

	private void addDoor(final SIUDoor door, final String label) {
		final int portStatus = siuStatus.getDoorState(door);
		final Object state = new DoorStateFactory().createState(door, portStatus);
		addPortStatus(SIUPortType.DOORS, door, label, state);
	}

	private void addIndicators() {
		addRow("Indicators", "");
		addIndicator(SIUIndicator.OPENCLOSE, "OpenClosedIndicator");
		addIndicator(SIUIndicator.FASCIALIGHT, "FasciaLight");
		addIndicator(SIUIndicator.AUDIO, "AudioIndicator");
		addIndicator(SIUIndicator.HEATING, "InternalHeating");
		addIndicator(SIUIndicator.CONSUMER_DISPLAY_BACKLIGHT, "ConsumerDisplayBacklight");
		addIndicator(SIUIndicator.SIGNAGEDISPLAY, "SignageDisplay");
		addIndicator(SIUIndicator.TRANSINDICATOR, "TransactionIndicators");
		addIndicator(SIUIndicator.GENERALOUTPUTPORT, "GeneralOutputPorts");

	}

	private void addIndicator(final SIUIndicator indicator, final String label) {
		final int portStatus = siuStatus.getIndicatorState(indicator);
		final Object state = new IndicatorStateFactory().createState(indicator, portStatus);
		addPortStatus(SIUPortType.INDICATORS, indicator, label, state);
	}

	private void addAuxiliaries() {
		addRow("Auxiliaries", "");
		addAuxiliary(SIUAuxiliary.VOLUME, "VolumeControl");
		addAuxiliary(SIUAuxiliary.UPS, "UPS");
		addAuxiliary(SIUAuxiliary.REMOTE_STATUS_MONITOR, "RemoteStatusMonitor");
		addAuxiliary(SIUAuxiliary.AUDIBLE_ALARM, "AudibleAlarm");
		addAuxiliary(SIUAuxiliary.ENHANCEDAUDIOCONTROL, "EnhancedAudioController");
	}

	private void addAuxiliary(final SIUAuxiliary auxiliary, final String label) {
		final int portStatus = siuStatus.getAuxiliaryState(auxiliary);
		final Object state = new AuxiliaryStateFactory().createState(auxiliary, portStatus);
		addPortStatus(SIUPortType.AUXILIARIES, auxiliary, label, state);
	}

	private void addGuidLights() {
		addRow("GuidLights", "");
		addGuidLight(SIUGuidLight.CARDUNIT, "CardUnit");
		addGuidLight(SIUGuidLight.PINPAD, "PINPad");
		addGuidLight(SIUGuidLight.NOTESDISPENSER, "NoteDispenser");
		addGuidLight(SIUGuidLight.COINDISPENSER, "CoinDispenser");
		addGuidLight(SIUGuidLight.RECEIPTPRINTER, "ReceiptPrinter");
		addGuidLight(SIUGuidLight.PASSBOOKPRINTER, "PassbookPrinter");
		addGuidLight(SIUGuidLight.ENVDEPOSITORY, "EnvelopeDepository");
		addGuidLight(SIUGuidLight.CHEQUEUNIT, "ChequeUnit");
		addGuidLight(SIUGuidLight.BILLACCEPTOR, "BillAcceptor");
		addGuidLight(SIUGuidLight.ENVDISPENSER, "EnvelopeDispenser");
		addGuidLight(SIUGuidLight.DOCUMENTPRINTER, "DocumentPrinter");
		addGuidLight(SIUGuidLight.COINACCEPTOR, "CoinAcceptor");
		addGuidLight(SIUGuidLight.SCANNER, "Scanner");
	}

	private void addGuidLight(final SIUGuidLight guidLight, final String label) {
		final int portStatus = siuStatus.getGuidLightState(guidLight);
		final Object state = new GuidLightStateFactory().createState(guidLight, portStatus);
		addPortStatus(SIUPortType.GUIDLIGHTS, guidLight, label, state);
	}

	private <E extends Enum<E> & SIUPortIndex> void addPortStatus(final SIUPortType portType, final E portIndex, final String label, final Object data) {
		addRow(label, data);
		final int index = table.getRowCount() - 1;
		final Map<Enum<?>, Integer> sensors = indexes.get(portType);
		sensors.put(portIndex, Integer.valueOf(index));
	}

	private void addRow(final String label, final Object data) {
		table.addRow(new Label(getClass().getSimpleName(), label), data);
	}

	@Override
	public void portStatus(final SIUPortEvent portEvent) {
		final SIUPortType portType = portEvent.getPortType();
		final int portStatus = portEvent.getPortStatus().intValue();
		switch (portType) {
			case SENSORS:
				final SIUSensor portIndex = portEvent.getPortIndex(SIUSensor.class);
				updateSensor(portIndex, portStatus);
				break;
			case DOORS:
				final SIUDoor door = portEvent.getPortIndex(SIUDoor.class);
				updateDoor(door, portStatus);
				break;
			case INDICATORS:
				final SIUIndicator indicator = portEvent.getPortIndex(SIUIndicator.class);
				updateIndicator(indicator, portStatus);
				break;
			case AUXILIARIES:
				final SIUAuxiliary auxiliary = portEvent.getPortIndex(SIUAuxiliary.class);
				updateAuxiliary(auxiliary, portStatus);
				break;
			case GUIDLIGHTS:
				final SIUGuidLight guidLight = portEvent.getPortIndex(SIUGuidLight.class);
				updateGuidLight(guidLight, portStatus);
				break;
		}
	}

	private void updateSensor(final SIUSensor sensor, final int portStatus) {
		final int index = indexes.get(SIUPortType.SENSORS).get(sensor);
		final Object oldState = table.getValueAt(index, STATUS_COLUMN);
		final Object newState = new SensorStateFactory().createState(sensor, portStatus);
		if (LOG.isInfoEnabled()) {
			final String method = "updateSensor(SIUSensor, int)";
			LOG.info(method, sensor + " has changed its state: oldState=" + oldState + ",newState=" + newState);
		}
		table.setValueAt(newState, index, STATUS_COLUMN);
	}

	private void updateDoor(final SIUDoor door, final int portStatus) {
		final int index = indexes.get(SIUPortType.DOORS).get(door).intValue();
		final Object oldState = table.getValueAt(index, STATUS_COLUMN);
		final Object newState = new DoorStateFactory().createState(door, portStatus);
		if (LOG.isInfoEnabled()) {
			final String method = "updateDoor(SIUDoor, int)";
			LOG.info(method, door + " has changed its state: oldState=" + oldState + ",newState=" + newState);
		}
		table.setValueAt(newState, index, STATUS_COLUMN);
	}

	private void updateIndicator(final SIUIndicator indicator, final int portStatus) {
		final int index = indexes.get(SIUPortType.INDICATORS).get(indicator).intValue();
		final Object oldState = table.getValueAt(index, STATUS_COLUMN);
		final Object newState = new IndicatorStateFactory().createState(indicator, portStatus);
		if (LOG.isInfoEnabled()) {
			final String method = "updateIndicator(SIUIndicator, int)";
			LOG.info(method, "Indicator: " + indicator + " has changed its state: oldState=" + oldState + ",newState=" + newState);
		}
		table.setValueAt(newState, index, STATUS_COLUMN);
	}

	private void updateAuxiliary(final SIUAuxiliary auxiliary, final int portStatus) {
		final int index = indexes.get(SIUPortType.AUXILIARIES).get(auxiliary);
		final Object oldState = table.getValueAt(index, STATUS_COLUMN);
		final Object newState = new AuxiliaryStateFactory().createState(auxiliary, portStatus);
		if (LOG.isInfoEnabled()) {
			final String method = "updateAuxiliary(SIUAuxiliary, int)";
			LOG.info(method, "Auxiliary: " + auxiliary + " has changed its state: oldState=" + oldState + ",newState=" + newState);
		}
		table.setValueAt(newState, index, STATUS_COLUMN);
	}

	private void updateGuidLight(final SIUGuidLight guidLight, final int portStatus) {
		final int index = indexes.get(SIUPortType.GUIDLIGHTS).get(guidLight);
		final Object oldState = table.getValueAt(index, STATUS_COLUMN);
		final Object newState = new GuidLightStateFactory().createState(guidLight, portStatus);
		if (LOG.isInfoEnabled()) {
			final String method = "updateGuidLight(SIUGuidLight, int)";
			LOG.info(method, "GuidLight: " + guidLight + " has changed its state: oldState=" + oldState + ",newState=" + newState);
		}
		table.setValueAt(newState, index, STATUS_COLUMN);
	}
}