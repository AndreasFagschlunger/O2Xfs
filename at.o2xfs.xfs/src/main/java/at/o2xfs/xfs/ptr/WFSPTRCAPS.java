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

import at.o2xfs.win32.BOOL;
import at.o2xfs.win32.DWORD;
import at.o2xfs.win32.LPSTR;
import at.o2xfs.win32.LPZZSTR;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.USHORT;
import at.o2xfs.win32.USHORTArray;
import at.o2xfs.win32.WORD;
import at.o2xfs.win32.ZSTR;
import at.o2xfs.xfs.XfsServiceClass;
import at.o2xfs.xfs.XfsVersion;
import at.o2xfs.xfs.util.KeyValueMap;
import at.o2xfs.xfs.util.XfsConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class WFSPTRCAPS
		extends Struct {

	private final WORD clazz = new WORD();
	private final WORD type = new WORD();
	private final BOOL compound = new BOOL();
	private final WORD resolution = new WORD();
	private final WORD readForm = new WORD();
	private final WORD writeForm = new WORD();
	private final WORD extents = new WORD();
	private final WORD control = new WORD();
	private USHORT maxRetract = null;
	private final USHORT maxMediaOnStacker = new USHORT();
	private final BOOL acceptMedia = new BOOL();
	private final BOOL multiPage = new BOOL();
	private final WORD paperSources = new WORD();
	private final BOOL mediaTaken = new BOOL();
	private final USHORT retractBins = new USHORT();
	private Pointer maxRetracts = null;
	private final WORD imageType = new WORD();
	private final WORD frontImageColorFormat = new WORD();
	private final WORD backImageColorFormat = new WORD();
	private final WORD codelineFormat = new WORD();
	private final WORD imageSource = new WORD();
	private final WORD charSupport = new WORD();
	private final BOOL dispensePaper = new BOOL();
	private final LPZZSTR extra = new LPZZSTR();
	private final DWORD guidLights = new DWORD();
	private final LPSTR windowsPrinter = new LPSTR();
	private final BOOL mediaPresented = new BOOL();
	private final USHORT autoRetractPeriod = new USHORT();
	private final BOOL retractToTransport = new BOOL();
	private final BOOL powerSaveControl = new BOOL();
	private final WORD coercivityType = new WORD();
	private final WORD controlPassbook = new WORD();
	private final WORD printSides = new WORD();
	private final BOOL antiFraudModule = new BOOL();

	private WFSPTRCAPS(final XfsVersion version) {
		if (version == null) {
			throw new IllegalArgumentException("version must not be null");
		}
		add(clazz);
		add(type);
		add(compound);
		add(resolution);
		add(readForm);
		add(writeForm);
		add(extents);
		add(control);
		if (version.isLE(XfsVersion.V2_00)) {
			maxRetract = new USHORT();
			add(maxRetract);
		}
		add(maxMediaOnStacker);
		add(acceptMedia);
		if (version.isGE(XfsVersion.V3_00)) {
			add(multiPage);
			add(paperSources);
			add(mediaTaken);
			add(retractBins);
			maxRetracts = new Pointer();
			add(maxRetracts);
			add(imageType);
			add(frontImageColorFormat);
			add(backImageColorFormat);
			add(codelineFormat);
			add(imageSource);
			add(charSupport);
			add(dispensePaper);
		} else {
			multiPage.allocate();
			paperSources.allocate();
			mediaTaken.allocate();
			retractBins.allocate();
			imageType.allocate();
			frontImageColorFormat.allocate();
			backImageColorFormat.allocate();
			codelineFormat.allocate();
			imageSource.allocate();
			charSupport.allocate();
			dispensePaper.allocate();
		}
		add(extra);
		if (version.isGE(XfsVersion.V3_10)) {
			add(guidLights);
			add(windowsPrinter);
			add(mediaPresented);
			add(autoRetractPeriod);
			add(retractToTransport);
			add(powerSaveControl);
		} else {
			guidLights.allocate();
			windowsPrinter.allocate();
			mediaPresented.allocate();
			autoRetractPeriod.allocate();
			retractToTransport.allocate();
			powerSaveControl.allocate();
		}
		if (version.isGE(XfsVersion.V3_20)) {
			add(coercivityType);
			add(controlPassbook);
			add(printSides);
			add(antiFraudModule);
		} else {
			coercivityType.allocate();
			controlPassbook.allocate();
			printSides.allocate();
			antiFraudModule.allocate();
		}
	}

	public WFSPTRCAPS(final Pointer p, final XfsVersion version) {
		this(version);
		assignBuffer(p);
	}

	public WFSPTRCAPS(final WFSPTRCAPS caps, final XfsVersion version) {
		this(version);
		allocate();
		clazz.set(caps.clazz);
		type.set(caps.type);
		compound.set(caps.isCompound());
		resolution.set(caps.resolution);
		readForm.set(caps.readForm);
		writeForm.set(caps.writeForm);
		extents.set(caps.extents);
		control.set(caps.control);
		if (version.isLE(XfsVersion.V2_00)) {
			maxRetract.set(caps.maxRetract);
		}
		maxMediaOnStacker.set(caps.maxMediaOnStacker);
		acceptMedia.set(caps.isAcceptMedia());
		multiPage.set(caps.isMultiPage());
		paperSources.set(caps.paperSources);
		mediaTaken.set(caps.isMediaTaken());
		retractBins.set(caps.retractBins);
		if (getRetractBins() > 0) {
			final List<Integer> retractCounts = caps.getMaxRetracts();
			if (!retractCounts.isEmpty()) {
				final USHORTArray maxRetracts = new USHORTArray(retractCounts.size());
				maxRetracts.allocate();
				for (int i = 0; i < maxRetracts.length; i++) {
					maxRetracts.get(i).set(retractCounts.get(i));
				}
				this.maxRetracts.pointTo(maxRetracts);
			}
		}
		imageType.set(caps.imageType);
		frontImageColorFormat.set(caps.frontImageColorFormat);
		backImageColorFormat.set(caps.backImageColorFormat);
		codelineFormat.set(caps.codelineFormat);
		imageSource.set(caps.imageSource);
		charSupport.set(caps.charSupport);
		dispensePaper.set(caps.isDispensePaper());
		extra.pointTo(KeyValueMap.toZZString(caps.getExtra()));
		// TODO: guidLights
		if (caps.getWindowsPrinter() != null) {
			windowsPrinter.pointTo(new ZSTR(caps.getWindowsPrinter()));
		}
		mediaPresented.set(caps.isMediaPresented());
		autoRetractPeriod.set(caps.autoRetractPeriod);
		retractToTransport.set(caps.isRetractToTransport());
		powerSaveControl.set(caps.isPowerSaveControl());
		coercivityType.set(caps.coercivityType);
		controlPassbook.set(caps.controlPassbook);
		printSides.set(caps.printSides);
		antiFraudModule.set(caps.isAntiFraudModule());
	}

	/**
	 * Specifies the logical service class, value is: {@link XfsServiceClass#WFS_SERVICE_CLASS_PTR}
	 */
	public XfsServiceClass getServiceClass() {
		return XfsConstants.valueOf(clazz, XfsServiceClass.class);
	}

	/**
	 * Specifies the type(s) of the physical device driven by the logical
	 * service, as a combination of the following flags: {@link PTRType}
	 */
	public Set<PTRType> getType() {
		return XfsConstants.of(type, PTRType.class);
	}

	/**
	 * Specifies whether the logical device is part of a compound physical
	 * device.
	 */
	public boolean isCompound() {
		return compound.booleanValue();
	}

	/**
	 * Specifies at which resolution(s) the physical device can print. Used by
	 * the application to select the level of print quality desired (e.g. as in
	 * Word for Windows); does not imply any absolute level of resolution, only
	 * relative. Specified as a combination of the following flags: {@link PTRResolution}
	 */
	public Set<PTRResolution> getResolution() {
		return XfsConstants.of(resolution, PTRResolution.class);
	}

	/**
	 * Specifies whether the device can read data from media, as a combination
	 * of the following flags (zero if none of the choices is supported): {@link PTRReadForm}
	 */
	public Set<PTRReadForm> getReadForm() {
		return XfsConstants.of(readForm, PTRReadForm.class);
	}

	/**
	 * Specifies whether the device can write data to the media, as a
	 * combination of the following flags (zero if none of the choices is
	 * supported): {@link PTRWriteForm}
	 */
	public Set<PTRWriteForm> getWriteForm() {
		return XfsConstants.of(writeForm, PTRWriteForm.class);
	}

	/**
	 * Specifies whether the device is able to measure the inserted media, as a
	 * combination of the following flags (zero if none of the choices is
	 * supported): {@link PTRExtents}
	 */
	public Set<PTRExtents> getExtents() {
		return XfsConstants.of(extents, PTRExtents.class);
	}

	/**
	 * Specifies the manner in which media can be controlled, as a combination
	 * of the following flags (zero if none of the choices is supported): {@link PTRMediaControl}
	 */
	public Set<PTRMediaControl> getControl() {
		return XfsConstants.of(control, PTRMediaControl.class);
	}

	/**
	 * Specifies the maximum number of media items that the stacker can hold
	 * (zero if not available).
	 */
	public int getMaxMediaOnStacker() {
		return maxMediaOnStacker.intValue();
	}

	/**
	 * Specifies whether the device is able to accept media while no execute
	 * command is running that is waiting explicitly for media to be inserted.
	 */
	public boolean isAcceptMedia() {
		return acceptMedia.booleanValue();
	}

	/**
	 * Specifies whether the device is able to support multiple page print jobs.
	 *
	 * @since 3.00
	 */
	public boolean isMultiPage() {
		return multiPage.booleanValue();
	}

	/**
	 * Specifies the Paper sources available for this printer as a combination
	 * of the following flags: {@link PTRPaperSource}
	 *
	 * @since 3.00
	 */
	public Set<PTRPaperSource> getPaperSources() {
		return XfsConstants.of(paperSources, PTRPaperSource.class);
	}

	/**
	 * Specifies whether the device is able to detect when the media is taken
	 * from the exit slot. If FALSE, the WFS_SRVE_PTR_MEDIATAKEN event is not
	 * fired. Its value is either TRUE or FALSE.
	 *
	 * @since 3.00
	 */
	public boolean isMediaTaken() {
		return mediaTaken.booleanValue();
	}

	/**
	 * Specifies the number of retract bins (zero if not supported).
	 */
	private int getRetractBins() {
		return retractBins.intValue();
	}

	/**
	 * A list of the maximum number of media items that each retract bin can
	 * hold (one count for each supported bin).
	 */
	public List<Integer> getMaxRetracts() {
		final List<Integer> numbers = new ArrayList<Integer>();
		if (maxRetract != null) {
			if (maxRetract.intValue() > 0) {
				numbers.add(Integer.valueOf(maxRetract.intValue()));
			}
		} else if (getRetractBins() > 0) {
			final USHORTArray array = new USHORTArray(maxRetracts, getRetractBins());
			for (int i = 0; i < array.length; i++) {
				final int num = array.get(i).intValue();
				numbers.add(Integer.valueOf(num));
			}
		}
		return numbers;
	}

	/**
	 * Specifies the image format supported by this device, as a combination of
	 * following flags (empty if not supported): {@link PTRImageType}
	 *
	 * @since 3.00
	 */
	public Set<PTRImageType> getImageType() {
		return XfsConstants.of(imageType, PTRImageType.class);
	}

	/**
	 * Specifies the front image color formats supported by this device, as a
	 * combination of following flags (empty if not supported):
	 *
	 * @since 3.00
	 */
	public Set<PTRImageColor> getFrontImageColorFormat() {
		return XfsConstants.of(frontImageColorFormat, PTRImageColor.class);
	}

	/**
	 * Specifies the back image color formats supported by this device, as a
	 * combination of following flags (empty if not supported): {@link PTRImageColor}
	 *
	 * @since 3.00
	 */
	public Set<PTRImageColor> getBackImageColorFormat() {
		return XfsConstants.of(backImageColorFormat, PTRImageColor.class);
	}

	/**
	 * Specifies the code line (MICR data) formats supported by this device, as
	 * a combination of following flags (empty if not supported): {@link PTRCodelineFormat}
	 *
	 * @since 3.00
	 */
	public Set<PTRCodelineFormat> getCodelineFormat() {
		return XfsConstants.of(codelineFormat, PTRCodelineFormat.class);
	}

	/**
	 * Specifies the source for the read image command supported by this device,
	 * as a combination of the following flags (empty if not supported): {@link PTRImageSource}
	 *
	 * @since 3.00
	 */
	public Set<PTRImageSource> getImageSource() {
		return XfsConstants.of(imageSource, PTRImageSource.class);
	}

	/**
	 * One or more flags specifying the character sets, in addition to single
	 * byte ASCII, that is supported by the Service Provider:
	 *
	 * @since 3.00
	 */
	public Set<PTRCharSupport> getCharSupport() {
		return XfsConstants.of(charSupport, PTRCharSupport.class);
	}

	/**
	 * Specifies whether the device is able to dispense paper.
	 *
	 * @since 3.00
	 */
	public boolean isDispensePaper() {
		return dispensePaper.booleanValue();
	}

	/**
	 * A list of vendor-specific, or any other extended, information.
	 */
	public Map<String, String> getExtra() {
		return KeyValueMap.from(extra);
	}

	/**
	 * Specifies which guidance lights are available.
	 *
	 * @since 3.10
	 */
	public DWORD getGuidLights() {
		return guidLights;
	}

	/**
	 * Specifies the name of the default logical Windows printer that is
	 * associated with this Service Provider. Applications should use this
	 * printer name to generate native printer files (i.e. .PRN) to be printed
	 * through the WFS_CMD_PTR_PRINT_RAW_FILE command. This value will be <code>null</code> if the Service Provider does
	 * not support the
	 * WFS_CMD_PTR_PRINT_RAW_FILE command.
	 *
	 * @since 3.10
	 */
	public String getWindowsPrinter() {
		final String s = windowsPrinter.toString();
		if (s.isEmpty()) {
			return null;
		}
		return s;
	}

	/**
	 * Specifies whether the device is able to detect when the media is
	 * presented to the user for removal. If TRUE, the
	 * WFS_EXEE_PTR_MEDIAPRESENTED event is fired. If FALSE, the
	 * WFS_EXEE_PTR_MEDIAPRESENTED event is not fired.
	 *
	 * @since 3.10
	 */
	public boolean isMediaPresented() {
		return mediaPresented.booleanValue();
	}

	/**
	 * Specifies the number of seconds before the device will automatically
	 * retract the presented media. If the command that generated the media is
	 * still active when the media is automatically retracted, the command will
	 * complete with a WFS_ERR_PTR_MEDIARETRACTED error. If the device does not
	 * retract media automatically this value will be zero.
	 *
	 * @since 3.10
	 */
	public int getAutoRetractPeriod() {
		return autoRetractPeriod.intValue();
	}

	/**
	 * Specifies whether the device is able to retract the previously ejected
	 * media to the transport.
	 *
	 * @since 3.10
	 */
	public boolean isRetractToTransport() {
		return retractToTransport.booleanValue();
	}

	/**
	 * Specifies whether power saving control is available.
	 *
	 * @since 3.10
	 */
	public boolean isPowerSaveControl() {
		return powerSaveControl.booleanValue();
	}

	/**
	 * Specifies the form write modes supported by this device, as a combination
	 * of the following flags: {@link PTRCoercivityType}
	 *
	 * @since 3.20
	 */
	public Set<PTRCoercivityType> getCoercivityType() {
		return XfsConstants.of(coercivityType, PTRCoercivityType.class);
	}

	/**
	 * Specifies how the passbook can be controlled with the
	 * WFS_CMD_PTR_CONTROL_PASSBOOK command, as a combination of the following
	 * flags: {@link PTRPassbookControl}
	 *
	 * @since 3.20
	 */
	public Set<PTRPassbookControl> getControlPassbook() {
		return XfsConstants.of(controlPassbook, PTRPassbookControl.class);
	}

	/**
	 * Specifies on which sides of the media this device can print as one of the
	 * following values:
	 *
	 * @since 3.20
	 */
	public PTRPrintSides getPrintSides() {
		return XfsConstants.valueOf(printSides, PTRPrintSides.class);
	}

	/**
	 * Specifies whether the anti-fraud module is available.
	 *
	 * @since 3.20
	 */
	public boolean isAntiFraudModule() {
		return antiFraudModule.booleanValue();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("class", getClass())
										.append("type", getType())
										.append("compound", isCompound())
										.append("resolution", getResolution())
										.append("readForm", getReadForm())
										.append("writeForm", getWriteForm())
										.append("extents", getExtents())
										.append("control", getControl())
										.append("maxMediaOnStacker", getMaxMediaOnStacker())
										.append("acceptMedia", isAcceptMedia())
										.append("multiPage", isMultiPage())
										.append("paperSources", getPaperSources())
										.append("mediaTaken", isMediaTaken())
										.append("maxRetracts", getMaxRetracts())
										.append("imageType", getImageType())
										.append("frontImageColorFormat", getFrontImageColorFormat())
										.append("backImageColorFormat", getBackImageColorFormat())
										.append("codelineFormat", getCodelineFormat())
										.append("imageSource", getImageSource())
										.append("charSupport", getCharSupport())
										.append("dispensePaper", isDispensePaper())
										.append("extra", getExtra())
										.append("guidLights", getGuidLights())
										.append("windowsPrinter", getWindowsPrinter())
										.append("mediaPresented", isMediaPresented())
										.append("autoRetractPeriod", getAutoRetractPeriod())
										.append("retractToTransport", isRetractToTransport())
										.append("powerSaveControl", isPowerSaveControl())
										.append("coercivityType", getCoercivityType())
										.append("controlPassbook", getControlPassbook())
										.append("printSides", getPrintSides())
										.append("antiFraudModule", isAntiFraudModule())
										.toString();
	}
}