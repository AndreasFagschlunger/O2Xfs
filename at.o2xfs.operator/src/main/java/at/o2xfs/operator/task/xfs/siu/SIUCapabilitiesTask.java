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

import java.util.Map;

import at.o2xfs.operator.ui.content.table.Table;
import at.o2xfs.operator.ui.content.text.Label;
import at.o2xfs.xfs.XfsException;
import at.o2xfs.xfs.service.siu.SIUCapabilitiesCallable;
import at.o2xfs.xfs.service.siu.SIUService;
import at.o2xfs.xfs.siu.SIUAuxiliariesCapabilities;
import at.o2xfs.xfs.siu.SIUCapabilities;
import at.o2xfs.xfs.siu.SIUDoorCapabilities;
import at.o2xfs.xfs.siu.SIUGuidLightCapabilities;
import at.o2xfs.xfs.siu.SIUIndicatorCapabilities;
import at.o2xfs.xfs.siu.SIUSensorCapabilities;

public class SIUCapabilitiesTask extends SIUServiceTask {

	private Table table = null;

	public SIUCapabilitiesTask() {
		super();
	}

	public SIUCapabilitiesTask(SIUService service) {
		super(service);
	}

	@Override
	protected void execute() {
		String method = "execute()";
		try {
			final SIUCapabilities capabilities = new SIUCapabilitiesCallable(service).call();
			table = new Table(getClass(), "Capability", "Value");
			addRow("ServiceClass", capabilities.getServiceClass());
			addRow("Type", capabilities.getType());
			final SIUSensorCapabilities sensors = capabilities.getSensors();
			addRow("OperatorSwitch", sensors.getOperatorSwitch());
			addRow("TamperSensor", sensors.isTamperSensorAvailable());
			addRow("InternalTamperSensor", sensors.isInternalTamperSensorAvailable());
			addRow("SeismicSensor", sensors.isSeismicSensorAvailable());
			addRow("HeatSensor", sensors.isHeatSensorAvailable());
			addRow("ProximitySensor", sensors.isProximitySensorAvailable());
			addRow("AmbientLightSensor", sensors.isAmbientLightSensorAvailable());
			addRow("AudioJack", sensors.getAudioJackCapabilities());
			addRow("BootSwitch", sensors.isBootSwitchAvailable());
			addRow("ConsumerDisplaySensor", sensors.isConsumerDisplaySensorAvailable());
			addRow("OperatorCallButton", sensors.isOperatorCallButtonAvailable());
			addRow("HandsetCapabilities", sensors.getHandsetCapabilities());
			addRow("GeneralPurposeInputPorts", sensors.getGeneralPurposeInputPorts());
			final SIUDoorCapabilities doors = capabilities.getDoors();
			addRow("CabinetDoors", doors.getCabinetDoors());
			addRow("SafeDoors", doors.getSafeDoors());
			addRow("VandalShield", doors.getVandalShield());
			addRow("FrontCabinetDoors", doors.getFrontCabinetDoors());
			addRow("RearCabinetDoors", doors.getRearCabinetDoors());
			addRow("LeftCabinetDoors", doors.getLeftCabinetDoors());
			addRow("RightCabinetDoors", doors.getRightCabinetDoors());
			final SIUIndicatorCapabilities indicators = capabilities.getIndicators();
			addRow("OpenClosedIndicator", indicators.isOpenClosedIndicator());
			addRow("FasciaLight", indicators.isFasciaLight());
			addRow("AudioIndicator", indicators.isAudioIndicator());
			addRow("HeatingDevice", indicators.isHeatingDevice());
			addRow("ConsumerDisplayBacklight", indicators.isConsumerDisplayBacklight());
			addRow("SignageDisplay", indicators.isSignageDisplay());
			addRow("TransactionIndicators", indicators.getTransactionIndicators());
			addRow("GeneralPurposeOutputPorts", indicators.getGeneralPurposeOutputPorts());
			final SIUAuxiliariesCapabilities auxiliaries = capabilities.getAuxiliaries();
			addRow("VolumeControl", auxiliaries.getVolumeControl());
			addRow("UPSCapabilities", auxiliaries.getUPSCapabilities());
			addRow("RemoteStatusMonitor", auxiliaries.isRemoteStatusMonitor());
			addRow("AudibleAlarmDevice", auxiliaries.isAudibleAlarmDevice());
			addRow("EnhancedAudioControl", auxiliaries.getEnhancedAudioControl());
			final SIUGuidLightCapabilities guidLights = capabilities.getGuidLights();
			addRow("CardUnit", guidLights.isCardUnit());
			addRow("PINPad", guidLights.isPINPad());
			addRow("NoteDispenser", guidLights.isNoteDispenser());
			addRow("CoinDispenser", guidLights.isCoinDispenser());
			addRow("ReceiptPrinter", guidLights.isReceiptPrinter());
			addRow("PassbookPrinter", guidLights.isPassbookPrinter());
			addRow("EnvelopeDepository", guidLights.isEnvelopeDepository());
			addRow("ChequeUnit", guidLights.isChequeUnit());
			addRow("BillAcceptor", guidLights.isBillAcceptor());
			addRow("EnvelopeDispenser", guidLights.isEnvelopeDispenser());
			addRow("DocumentPrinter", guidLights.isDocumentPrinter());
			addRow("CoinAcceptor", guidLights.isCoinAcceptor());
			addRow("Scanner", guidLights.isScanner());
			for (final Map.Entry<String, String> entry : capabilities.getExtra().entrySet()) {
				table.addRow(entry.getKey(), entry.getValue());
			}
			getContent().setUIElement(table);
		} catch (final XfsException e) {
			showException(e);
		}
	}

	private void addRow(final String label, final Object value) {
		table.addRow(new Label(getClass(), label), value);
	}
}