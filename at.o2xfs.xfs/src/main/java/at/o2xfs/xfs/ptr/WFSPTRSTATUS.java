/*
 * Copyright (c) 2014, Andreas Fagschlunger. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 
 * - Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * 
 * - Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package at.o2xfs.xfs.ptr;

import at.o2xfs.win32.DWORDArray;
import at.o2xfs.win32.LPZZSTR;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.USHORT;
import at.o2xfs.win32.WORD;
import at.o2xfs.win32.WORDArray;
import at.o2xfs.win32.ZList;
import at.o2xfs.xfs.XfsVersion;
import at.o2xfs.xfs.util.KeyValueMap;
import at.o2xfs.xfs.util.XfsConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class WFSPTRSTATUS
		extends Struct {

	private static final int WFS_PTR_GUIDLIGHTS_SIZE = 32;
	private static final int WFS_PTR_SUPPLYSIZE = 16;

	public static final int WFS_PTR_SUPPLYMAX = WFS_PTR_SUPPLYSIZE - 1;
	public static final int WFS_PTR_SUPPLYUPPER = 0;
	public static final int WFS_PTR_SUPPLYLOWER = 1;
	public static final int WFS_PTR_SUPPLYEXTERNAL = 2;
	public static final int WFS_PTR_SUPPLYAUX = 3;
	public static final int WFS_PTR_SUPPLYAUX2 = 4;
	public static final int WFS_PTR_SUPPLYPARK = 5;

	private final WORD device = new WORD();
	private final WORD media = new WORD();
	private final WORDArray paper;
	private final WORD toner = new WORD();
	private final WORD ink = new WORD();
	private final WORD lamp = new WORD();
	private WFSPTRRETRACTBINS retractBin = null;
	private Pointer retractBins = null;
	private final USHORT mediaOnStacker = new USHORT();
	private final LPZZSTR extra = new LPZZSTR();
	private final DWORDArray guidLights = new DWORDArray(WFS_PTR_GUIDLIGHTS_SIZE);
	private final WORD devicePosition = new WORD();
	private final USHORT powerSaveRecoveryTime = new USHORT();
	private final WORDArray paperType = new WORDArray(WFS_PTR_SUPPLYSIZE);
	private final WORD antiFraudModule = new WORD();

	private WFSPTRSTATUS(final XfsVersion xfsVersion) {
		add(device);
		add(media);
		if (xfsVersion.isGE(XfsVersion.V3_00)) {
			paper = new WORDArray(WFS_PTR_SUPPLYSIZE);
		} else {
			paper = new WORDArray(1);
		}
		add(paper);
		add(toner);
		add(ink);
		add(lamp);
		if (xfsVersion.isGE(XfsVersion.V3_00)) {
			retractBins = new Pointer();
			add(retractBins);
		} else {
			retractBin = new WFSPTRRETRACTBINS();
			add(retractBin);
		}
		add(mediaOnStacker);
		add(extra);
		if (xfsVersion.isGE(XfsVersion.V3_10)) {
			add(guidLights);
			add(devicePosition);
			add(powerSaveRecoveryTime);
		} else {
			guidLights.allocate();
			devicePosition.allocate();
			powerSaveRecoveryTime.allocate();
		}
		if (xfsVersion.isGE(XfsVersion.V3_20)) {
			add(paperType);
			add(antiFraudModule);
		} else {
			paperType.allocate();
			antiFraudModule.allocate();
		}
	}

	public WFSPTRSTATUS(final XfsVersion xfsVersion, final Pointer p) {
		this(xfsVersion);
		assignBuffer(p);
	}

	public WFSPTRSTATUS(final XfsVersion xfsVersion, final WFSPTRSTATUS ptrStatus) {
		this(xfsVersion);
		allocate();
	}

	/**
	 * Specifies the state of the print device as one of the following flags: {@link PTRDeviceState}
	 * 
	 * @since 2.00
	 */
	public PTRDeviceState getDevice() {
		return XfsConstants.valueOf(device, PTRDeviceState.class);
	}

	/**
	 * Specifies the state of the print media (i.e. receipt, statement,
	 * passbook, etc.) as one of the following values. This field does not apply
	 * to journal printers: {@link PTRMedia}
	 * 
	 * @since 2.00
	 */
	public PTRMedia getMedia() {
		return XfsConstants.valueOf(media, PTRMedia.class);
	}

	/**
	 * Specifies the state of the paper supplies. A number of paper supplies are
	 * defined below. Vendor specific paper supplies are defined starting from
	 * the end of the array. The maximum paper index is WFS_PTR_SUPPLYMAX.
	 */
	public PTRPaper[] getPaper() {
		final PTRPaper[] paperSupplies = new PTRPaper[WFS_PTR_SUPPLYSIZE];
		for (int i = 0; i < paperSupplies.length; i++) {
			if (i < paper.length) {
				paperSupplies[i] = XfsConstants.valueOf(paper.get(i), PTRPaper.class);
			} else {
				paperSupplies[i] = PTRPaper.NOTSUPP;
			}
		}
		return paperSupplies;
	}

	/**
	 * Specifies the state of the toner or ink supply or the state of the ribbon
	 * as one of the following values: {@link PTRToner}
	 * 
	 * @since 2.0
	 */
	public PTRToner getToner() {
		return XfsConstants.valueOf(toner, PTRToner.class);
	}

	/**
	 * Specifies the status of the stamping ink in the printer as one of the
	 * following values: {@link PTRInk}
	 * 
	 * @since 2.0
	 */
	public PTRInk getInk() {
		return XfsConstants.valueOf(ink, PTRInk.class);
	}

	/**
	 * Specifies the status of the printer imaging lamp as one of the following
	 * values: {@link PTRLamp}
	 * 
	 * @since 2.0
	 */
	public PTRLamp getLamp() {
		return XfsConstants.valueOf(lamp, PTRLamp.class);
	}

	/**
	 * 
	 * @return
	 */
	public List<WFSPTRRETRACTBINS> getRetractBins() {
		final List<WFSPTRRETRACTBINS> result = new ArrayList<WFSPTRRETRACTBINS>();
		if (retractBin != null) {
			if (!PTRRetractBin.NOTSUPP.equals(retractBin.getRetractBin())) {
				result.add(retractBin);
			}
		} else if (retractBins != null) {
			final ZList list = new ZList(retractBins);
			for (final Pointer p : list) {
				final WFSPTRRETRACTBINS bin = new WFSPTRRETRACTBINS(p);
				result.add(new WFSPTRRETRACTBINS(bin));
			}
		}
		return result;
	}

	/**
	 * The number of media on stacker; applicable only to printers with stacking
	 * capability.
	 * 
	 * @since 2.0
	 */
	public int getMediaOnStacker() {
		return mediaOnStacker.intValue();
	}

	/**
	 * Pointer to a list of vendor-specific, or any other extended, information.
	 * 
	 * @since 2.0
	 */
	public Map<String, String> getExtra() {
		return KeyValueMap.from(extra);
	}

	/**
	 * @since 3.10
	 */
	public DWORDArray getGuidLights() {
		return guidLights;
	}

	/**
	 * Specifies the device position. The device position value is independent
	 * of the fwDevice value, e.g. when the device position is reported as
	 * WFS_PTR_DEVICENOTINPOSITION, fwDevice can have any of the values defined
	 * above (including WFS_PTR_DEVONLINE or WFS_PTR_DEVOFFLINE). If the device
	 * is not in its normal operating position (i.e. WFS_PTR_DEVICEINPOSITION)
	 * then media may not be presented through the normal customer interface.
	 * This value is one of the following values: {@link PTRDevicePosition}
	 * 
	 * @since 3.10
	 */
	public PTRDevicePosition getDevicePosition() {
		return XfsConstants.valueOf(devicePosition, PTRDevicePosition.class);
	}

	/**
	 * Specifies the actual number of seconds required by the device to resume
	 * its normal operational state from the current power saving mode. This
	 * value is zero if either the power saving mode has not been activated or
	 * no power save control is supported.
	 * 
	 * @since 3.10
	 */
	public int getPowerSaveRecoveryTime() {
		return powerSaveRecoveryTime.intValue();
	}

	/**
	 * Specifies the type of paper loaded in the device. A number of paper types
	 * are defined below. Vendor specific paper types are defined starting from
	 * the end of the array. The maximum paper index is WFS_PTR_SUPPLYMAX.
	 * 
	 * @since 3.20
	 */
	public PTRPaperType[] getPaperType() {
		final PTRPaper[] supplies = getPaper();
		final PTRPaperType[] paperTypes = new PTRPaperType[WFS_PTR_SUPPLYSIZE];
		for (int i = 0; i < paperTypes.length; i++) {
			PTRPaperType paperType = PTRPaperType.TYPEUNKNOWN;
			if (i < paper.length && !PTRPaper.NOTSUPP.equals(supplies[i])) {
				paperType = XfsConstants.valueOf(paper.get(i), PTRPaperType.class);
			}
			paperTypes[i] = paperType;
		}
		return paperTypes;
	}

	/**
	 * Specifies the state of the anti-fraud module as one of the following
	 * values: {@link PTRAntiFraudModule}
	 * 
	 * @since 3.20
	 */
	public PTRAntiFraudModule getAntiFraudModule() {
		return XfsConstants.valueOf(antiFraudModule, PTRAntiFraudModule.class);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("device", getDevice())
										.append("media", getMedia())
										.append("paper", getPaper())
										.append("toner", getToner())
										.append("ink", getInk())
										.append("lamp", getLamp())
										.append("retractBins", getRetractBins())
										.append("mediaOnStacker", getMediaOnStacker())
										.append("extra", getExtra())
										.append("guidLights", getGuidLights())
										.append("devicePosition", getDevicePosition())
										.append("powerSaveRecoveryTime", getPowerSaveRecoveryTime())
										.append("paperType", getPaperType())
										.append("antiFraudModule", getAntiFraudModule())
										.toString();
	}

}