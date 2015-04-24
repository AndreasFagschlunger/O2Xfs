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

package at.o2xfs.xfs.pin;

import at.o2xfs.win32.BOOL;
import at.o2xfs.win32.DWORD;
import at.o2xfs.win32.DWORDArray;
import at.o2xfs.win32.LPSTR;
import at.o2xfs.win32.LPZZSTR;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.USHORT;
import at.o2xfs.win32.WORD;
import at.o2xfs.win32.ZSTR;
import at.o2xfs.xfs.XfsVersion;
import at.o2xfs.xfs.util.Bitmask;
import at.o2xfs.xfs.util.KeyValueMap;
import at.o2xfs.xfs.util.XfsConstants;

import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author Andreas Fagschlunger
 */
public class WFSPINCAPS
		extends Struct {

	/**
	 * Specifies the logical service class, value is: WFS_SERVICE_CLASS_PIN
	 */
	private final WORD clazz = new WORD();

	/**
	 * Specifies the type of the PIN pad security module as a combination of the
	 * following flags: {@link PINType}
	 */
	private final WORD type = new WORD();

	/**
	 * Specifies whether the logical device is part of a compound physical
	 * device and is either TRUE or FALSE.
	 */
	private final BOOL compound = new BOOL();

	/**
	 * Number of the keys which can be stored in the encryption/decryption
	 * module.
	 */
	private final USHORT keyNum = new USHORT();

	/**
	 * Supported encryption modes; a combination of the following flags: {@link PINAlgorithm}
	 */
	private final WORD algorithms = new WORD();

	/**
	 * Supported PIN formats; a combination of the following flags: {@link PINFormat}
	 */
	private final WORD pinFormats = new WORD();

	/**
	 * Supported derivation algorithms; a combination of the following flags: {@link PINDerivationAlgorithm}
	 */
	private final WORD derivationAlgorithms = new WORD();

	/**
	 * Supported presentation algorithms; a combination of the following flags: {@link PINPresentationAlgorithm}
	 */
	private final WORD presentationAlgorithms = new WORD();

	/**
	 * Specifies the type of the display used in the PIN pad module as one of
	 * the following flags: {@link PINDisplay}
	 */
	private final WORD display = new WORD();

	/**
	 * Specifies whether the PIN pad is directly physically connected to the ID
	 * card unit. The value of this parameter is either TRUE or FALSE.
	 */
	private final BOOL idConnect = new BOOL();

	/**
	 * Specifies whether an ID key is supported as a combination of the
	 * following flags: {@link PINIDKey}
	 */
	private final WORD idKey = new WORD();

	/**
	 * Specifies the algorithms for PIN validation supported by the service;
	 * combination of the following flags: {@link PINValidationAlgorithm}
	 */
	private final WORD validationAlgorithms = new WORD();

	/**
	 * Specifies the key check modes that are supported to check the correctness
	 * of an imported key value; can be a combination of the following flags: {@link PINKeyCheckMode}
	 *
	 * @since 3.00
	 */
	private final WORD keyCheckModes = new WORD();

	/**
	 * Points to a list of vendor-specific, or any other extended information.
	 * The information is returned as a series of �key=value� strings so that it
	 * is easily extendable by service providers. Each string is
	 * null-terminated, with the final string terminating with two null
	 * characters.
	 */
	private final LPZZSTR extra = new LPZZSTR();

	/**
	 * TODO
	 *
	 * @since 3.10
	 */
	private final DWORDArray guidLights = new DWORDArray(32);

	/**
	 * Specifies whether the device can retain the PIN after a pin processing
	 * command, e.g. WFS_CMD_PIN_GET_PINBLOCK, WFS_CMD_PIN_LOCAL_DES,
	 * WFS_CMD_PIN_PRESENT_IDC, etc:
	 *
	 * TRUE
	 *
	 * Applications may request, through the WFS_CMD_PIN_MAINTAIN_PIN command,
	 * that the PIN continues to be held within the device after use by a PIN
	 * processing command.
	 *
	 * FALSE
	 *
	 * The PIN will always be cleared by the device after processing. The
	 * WFS_CMD_PIN_MAINTAIN_PIN is not supported.
	 *
	 * @since 3.10
	 */
	private final BOOL pinCanPersistAfterUse = new BOOL();

	/**
	 * Specifies whether the PIN device will emit a key beep tone on key presses
	 * (of active keys or inactive keys), and if so, which mode it supports.
	 * Specified as a combination of the following flags: {@link PINAutoBeep}
	 *
	 * @since 3.10
	 */
	private final WORD autoBeep = new WORD();

	/**
	 * Identifies the HSM Vendor. lpsHSMVendor is NULL when the HSM Vendor is
	 * unknown or the HSM is not supported. The following is a list of known
	 * vendors� strings that lpsHSMVendor can contain for the support of German
	 * HSMs: "KRONE", "ASCOM", "IBM", "NCR"
	 *
	 * @since 3.10
	 */
	private final LPSTR hsmVendor = new LPSTR();

	/**
	 * Specifies whether the HSM supports journaling by the
	 * WFS_CMD_PIN_GET_JOURNAL command. The value of this parameter is either
	 * TRUE or FALSE. TRUE means the HSM supports journaling by
	 * WFS_CMD_GET_JOURNAL.
	 *
	 * @since 3.10
	 */
	private final BOOL hsmJournaling = new BOOL();

	/**
	 * Specifies which type(s) of Remote Key Loading/Authentication is supported
	 * as a combination of the following flags: {@link PINRSAAuthenticationScheme}
	 *
	 * @since 3.10
	 */
	private final DWORD rsaAuthenticationSchemes = new DWORD();

	/**
	 * Specify which type(s) of RSA Signature Algorithm(s) is supported as a
	 * combination of the following flags: {@link PINRSASignatureAlgorithm}
	 *
	 * @since 3.10
	 */
	private final DWORD rsaSignatureAlgorithms = new DWORD();

	/**
	 * Specify which type(s) of RSA Encipherment Algorithm(s) is supported as a
	 * combination of the following flags: {@link PINRSACryptAlgorithm}
	 *
	 * @since 3.10
	 */
	private final DWORD rsaCryptAlgorithms = new DWORD();

	/**
	 * Specifies which algorithm/method used to generate the public key check
	 * value/thumb print as a combination of the following flags: {@link PINRSAKeyCheckMode}
	 *
	 * @since 3.10
	 */
	private final DWORD rsaKeyCheckModes = new DWORD();

	/**
	 * Specifies which capabilities are supported by the Signature scheme as a
	 * combination of the following flags: {@link PINSignatureSchemes}
	 *
	 * @since 3.10
	 */
	private final DWORD signatureSchemes = new DWORD();

	/**
	 * Identifies the supported EMV Import Scheme(s) as a zero terminated array
	 * of modes. lpwEMVImportSchemes is set to NULL if the Import Scheme(s) are
	 * unknown or not supported. Otherwise lpwEMVImportSchemes lists all Import
	 * Scheme(s) supported by the PIN Service Provider from the following
	 * possible values: {@link PINEMVImportScheme}
	 *
	 * @since 3.10
	 */
	private final Pointer emvImportSchemes = new Pointer();

	/**
	 * Specifies which hash algorithm is supported for the calculation of the
	 * HASH as a combination of the following flags: {@link PINEMVHashAlgorithm}
	 *
	 * @since 3.10
	 */
	private final WORD emvHashAlgorithms = new WORD();

	/**
	 * Specifies whether the device is capable of importing keys in multiple
	 * parts. TRUE means the device supports the key import in multiple parts.
	 *
	 * @since 3.10
	 */
	private final BOOL keyImportThroughParts = new BOOL();

	/**
	 * Specifies the ENC_IO protocols supported to communicate with the
	 * encryption module as a combination of the following flags: {@link PINENCIOProtocols}
	 *
	 * @since 3.10
	 */
	private final WORD encIOProtocols = new WORD();

	/**
	 * Specifies whether the keypad used in the secure PIN pad module is
	 * integrated within a generic Win32 keyboard.
	 *
	 * @since 3.10
	 */
	private final BOOL typeCombined = new BOOL();

	/**
	 * Specifies whether the command WFS_CMD_PIN_SET_PINBLOCK_DATA must be
	 * called before the PIN is entered via WFS_CMD_PIN_GET_PIN and retrieved
	 * via WFS_CMD_PIN_GET_PINBLOCK.
	 *
	 * @since 3.10
	 */
	private final BOOL setPinblockDataRequired = new BOOL();

	/**
	 * Supported key block formats; a combination of the following flags: {@link PINKeyBlockImportFormat}
	 *
	 * @since 3.10
	 */
	private final WORD keyBlockImportFormats = new WORD();

	/**
	 * Specifies whether power saving control is available. This can either be
	 * TRUE if available or FALSE if not available.
	 *
	 * @since 3.10
	 */
	private final BOOL powerSaveControl = new BOOL();

	/**
	 * Specifies whether the anti-fraud module is available. This can either be
	 * TRUE if available or FALSE if not available.
	 *
	 * @since 3.20
	 */
	private BOOL antiFraudModule = new BOOL();

	public WFSPINCAPS(final XfsVersion xfsVersion) {
		add(clazz);
		add(type);
		add(compound);
		add(keyNum);
		add(algorithms);
		add(pinFormats);
		add(derivationAlgorithms);
		add(presentationAlgorithms);
		add(display);
		add(idConnect);
		add(idKey);
		add(validationAlgorithms);
		if (xfsVersion.isGE(XfsVersion.V3_00)) {
			add(keyCheckModes);
		} else {
			keyCheckModes.allocate();
		}
		add(extra);
		if (xfsVersion.isGE(XfsVersion.V3_10)) {
			add(guidLights);
			add(pinCanPersistAfterUse);
			add(autoBeep);
			add(hsmVendor);
			add(hsmJournaling);
			add(rsaAuthenticationSchemes);
			add(rsaSignatureAlgorithms);
			add(rsaCryptAlgorithms);
			add(rsaKeyCheckModes);
			add(signatureSchemes);
			add(emvImportSchemes);
			add(emvHashAlgorithms);
			add(keyImportThroughParts);
			add(encIOProtocols);
			add(typeCombined);
			add(setPinblockDataRequired);
			add(keyBlockImportFormats);
			add(powerSaveControl);
		} else {
			guidLights.allocate();
			pinCanPersistAfterUse.allocate();
			autoBeep.allocate();
			hsmVendor.allocate();
			hsmJournaling.allocate();
			rsaAuthenticationSchemes.allocate();
			rsaSignatureAlgorithms.allocate();
			rsaCryptAlgorithms.allocate();
			rsaKeyCheckModes.allocate();
			signatureSchemes.allocate();
			emvImportSchemes.allocate();
			emvHashAlgorithms.allocate();
			keyImportThroughParts.allocate();
			encIOProtocols.allocate();
			typeCombined.allocate();
			setPinblockDataRequired.allocate();
			keyBlockImportFormats.allocate();
			powerSaveControl.allocate();
		}
		if (xfsVersion.isGE(XfsVersion.V3_20)) {
			add(antiFraudModule);
		} else {
			antiFraudModule.allocate();
		}
	}

	public WFSPINCAPS(final XfsVersion xfsVersion, final Pointer p) {
		this(xfsVersion);
		assignBuffer(p);
	}

	/**
	 * Copy constructor.
	 */
	public WFSPINCAPS(final XfsVersion xfsVersion, final WFSPINCAPS pinCaps) {
		this(xfsVersion);
		allocate();
		clazz.set(pinCaps.clazz);
		type.set(pinCaps.type);
		compound.set(pinCaps.isCompound());
		keyNum.set(pinCaps.keyNum);
		algorithms.set(pinCaps.algorithms);
		pinFormats.set(pinCaps.pinFormats);
		derivationAlgorithms.set(pinCaps.derivationAlgorithms);
		presentationAlgorithms.set(pinCaps.presentationAlgorithms);
		display.set(pinCaps.display);
		idConnect.set(pinCaps.isIDConnect());
		idKey.set(pinCaps.idKey);
		validationAlgorithms.set(pinCaps.validationAlgorithms);
		keyCheckModes.set(pinCaps.keyCheckModes);
		extra.pointTo(KeyValueMap.toZZString(pinCaps.getExtra()));
		// FIXME guidLights, ...
		autoBeep.set(pinCaps.autoBeep);
		setHSMVendor(pinCaps.getHSMVendor());
		setHSMJournaling(pinCaps.isHSMJournaling());
		rsaAuthenticationSchemes.set(pinCaps.rsaAuthenticationSchemes);
		rsaSignatureAlgorithms.set(pinCaps.rsaSignatureAlgorithms);
		rsaCryptAlgorithms.set(pinCaps.rsaCryptAlgorithms);
		rsaKeyCheckModes.set(pinCaps.rsaKeyCheckModes);
		signatureSchemes.set(pinCaps.signatureSchemes);
		// FIXME emvImportSchemes.set(pinCaps.emvImportSchemes);
		emvHashAlgorithms.set(pinCaps.emvHashAlgorithms);
		setKeyImportThroughParts(pinCaps.isKeyImportThroughParts());
		encIOProtocols.set(pinCaps.encIOProtocols);
		setTypeCombined(pinCaps.isTypeCombined());
		setSetPinblockDataRequired(pinCaps.isSetPinblockDataRequired());
		keyBlockImportFormats.set(pinCaps.keyBlockImportFormats);
		setPowerSaveControl(pinCaps.isPowerSaveControl());

	}

	/**
	 * {@link #clazz}
	 */
	public Set<PINType> getType() {
		return XfsConstants.of(clazz, PINType.class);
	}

	/**
	 * {@link #compound}
	 */
	public boolean isCompound() {
		return compound.booleanValue();
	}

	/**
	 * {@link #keyNum}
	 */
	public int getKeyNum() {
		return keyNum.intValue();
	}

	/**
	 * {@link #algorithms}
	 */
	public Set<PINAlgorithm> getAlgorithms() {
		return XfsConstants.of(algorithms, PINAlgorithm.class);
	}

	/**
	 * {@link #pinFormats}
	 */
	public Set<PINFormat> getPINFormats() {
		return XfsConstants.of(pinFormats, PINFormat.class);
	}

	/**
	 * {@link #derivationAlgorithms}
	 */
	public Set<PINDerivationAlgorithm> getDerivationAlgorithms() {
		return XfsConstants.of(derivationAlgorithms, PINDerivationAlgorithm.class);
	}

	/**
	 * {@link #presentationAlgorithms}
	 */
	public Set<PINPresentationAlgorithm> getPresentationAlgorithms() {
		return XfsConstants.of(presentationAlgorithms, PINPresentationAlgorithm.class);
	}

	/**
	 * {@link #display}
	 */
	public PINDisplay getDisplay() {
		return XfsConstants.valueOf(display, PINDisplay.class);
	}

	/**
	 * {@link #idConnect}
	 */
	public boolean isIDConnect() {
		return idConnect.booleanValue();
	}

	/**
	 * {@link #idKey}
	 */
	public Set<PINIDKey> getIDKey() {
		return XfsConstants.of(idKey, PINIDKey.class);
	}

	/**
	 * {@link #validationAlgorithms}
	 */
	public Set<PINValidationAlgorithm> getValidationAlgorithms() {
		return XfsConstants.of(validationAlgorithms, PINValidationAlgorithm.class);
	}

	/**
	 * {@link #keyCheckModes}
	 */
	public Set<PINKeyCheckMode> getKeyCheckModes() {
		return XfsConstants.of(keyCheckModes, PINKeyCheckMode.class);
	}

	/**
	 * {@link #keyCheckModes}
	 */
	public void setKeyCheckModes(final Set<PINKeyCheckMode> keyCheckModes) {
		this.keyCheckModes.set((int) Bitmask.of(keyCheckModes));
	}

	/**
	 * {@link #extra}
	 */
	public Map<String, String> getExtra() {
		return KeyValueMap.from(extra);
	}

	/**
	 * {@link #guidLights}
	 */
	public DWORDArray getGuidLights() {
		return guidLights;
	}

	/**
	 * {@link #guidLights}
	 */
	public void setGuidLights(DWORDArray guidLights) {
	}

	/**
	 * {@link #pinCanPersistAfterUse}
	 */
	public boolean isPINCanPersistAfterUse() {
		return pinCanPersistAfterUse.booleanValue();
	}

	/**
	 * {@link #pinCanPersistAfterUse}
	 */
	public void setPINCanPersistAfterUse(final boolean pinCanPersistAfterUse) {
		this.pinCanPersistAfterUse.set(pinCanPersistAfterUse);
	}

	/**
	 * {@link #autoBeep}
	 */
	public Set<PINAutoBeep> getAutoBeep() {
		return XfsConstants.of(autoBeep, PINAutoBeep.class);
	}

	/**
	 * {@link #autoBeep}
	 */
	public void setAutoBeep(final Set<PINAutoBeep> autoBeep) {
		this.autoBeep.set((int) Bitmask.of(autoBeep));
	}

	/**
	 * {@link #hsmVendor}
	 */
	public String getHSMVendor() {
		return hsmVendor.toString();
	}

	/**
	 * {@link #hsmVendor}
	 */
	public void setHSMVendor(final String hsmVendor) {
		this.hsmVendor.pointTo(new ZSTR(hsmVendor));
	}

	/**
	 * {@link #hsmJournaling}
	 */
	public boolean isHSMJournaling() {
		return hsmJournaling.booleanValue();
	}

	/**
	 * {@link #hsmJournaling}
	 */
	public void setHSMJournaling(final boolean hsmJournaling) {
		this.hsmJournaling.set(hsmJournaling);
	}

	/**
	 * {@link #rsaAuthenticationSchemes}
	 */
	public Set<PINRSAAuthenticationScheme> getRSAAuthenticationSchemes() {
		return XfsConstants.of(rsaAuthenticationSchemes, PINRSAAuthenticationScheme.class);
	}

	/**
	 * {@link #rsaAuthenticationSchemes}
	 */
	public void setRSAAuthenticationSchemes(final Set<PINRSAAuthenticationScheme> rsaAuthenticationSchemes) {
		this.rsaAuthenticationSchemes.set((int) Bitmask.of(rsaAuthenticationSchemes));
	}

	/**
	 * {@link #rsaSignatureAlgorithms}
	 */
	public Set<PINRSASignatureAlgorithm> getRSASignatureAlgorithms() {
		return XfsConstants.of(rsaSignatureAlgorithms, PINRSASignatureAlgorithm.class);
	}

	/**
	 * {@link #rsaSignatureAlgorithms}
	 */
	public void setRSASignatureAlgorithms(final Set<PINRSASignatureAlgorithm> rsaSignatureAlgorithms) {
		this.rsaSignatureAlgorithms.set((int) Bitmask.of(rsaSignatureAlgorithms));
	}

	/**
	 * {@link #rsaCryptAlgorithms}
	 */
	public Set<PINRSACryptAlgorithm> getRSACryptAlgorithms() {
		return XfsConstants.of(rsaCryptAlgorithms, PINRSACryptAlgorithm.class);
	}

	/**
	 * {@link #rsaCryptAlgorithms}
	 */
	public void setRSACryptAlgorithms(final Set<PINRSACryptAlgorithm> rsaCryptAlgorithms) {
		this.rsaCryptAlgorithms.set((int) Bitmask.of(rsaCryptAlgorithms));
	}

	/**
	 * {@link #rsaKeyCheckModes}
	 */
	public Set<PINRSAKeyCheckMode> getRSAKeyCheckModes() {
		return XfsConstants.of(rsaKeyCheckModes, PINRSAKeyCheckMode.class);
	}

	/**
	 * {@link #rsaKeyCheckModes}
	 */
	public void setRSAKeyCheckModes(final Set<PINRSAKeyCheckMode> rsaKeyCheckModes) {
		this.rsaKeyCheckModes.set((int) Bitmask.of(rsaKeyCheckModes));
	}

	/**
	 * {@link #signatureSchemes}
	 */
	public Set<PINSignatureSchemes> getSignatureSchemes() {
		return XfsConstants.of(signatureSchemes, PINSignatureSchemes.class);
	}

	/**
	 * {@link #signatureSchemes}
	 */
	public void setSignatureSchemes(final Set<PINSignatureSchemes> signatureSchemes) {
		this.signatureSchemes.set((int) Bitmask.of(signatureSchemes));
	}

	/**
	 * {@link #emvImportSchemes}
	 */
	public Pointer getEMVImportSchemes() {
		return emvImportSchemes;
	}

	/**
	 * {@link #emvImportSchemes}
	 */
	public void setEMVImportSchemes(final Set<PINEMVImportScheme> emvImportSchemes) {
		// FIXME
	}

	/**
	 * {@link #emvHashAlgorithms}
	 */
	public Set<PINEMVHashAlgorithm> getEMVHashAlgorithms() {
		return XfsConstants.of(emvHashAlgorithms, PINEMVHashAlgorithm.class);
	}

	/**
	 * {@link #emvHashAlgorithms}
	 */
	public void setEMVHashAlgorithms(final Set<PINEMVHashAlgorithm> emvHashAlgorithms) {
		this.emvHashAlgorithms.set((int) Bitmask.of(emvHashAlgorithms));
	}

	/**
	 * {@link #keyImportThroughParts}
	 */
	public boolean isKeyImportThroughParts() {
		return keyImportThroughParts.booleanValue();
	}

	/**
	 * {@link #keyImportThroughParts}
	 */
	public void setKeyImportThroughParts(boolean keyImportThroughParts) {
		this.keyImportThroughParts.set(keyImportThroughParts);
	}

	/**
	 * {@link #encIOProtocols}
	 */
	public Set<PINENCIOProtocols> getEncIOProtocols() {
		return XfsConstants.of(encIOProtocols, PINENCIOProtocols.class);
	}

	/**
	 * {@link #encIOProtocols}
	 */
	public void setEncIOProtocols(final Set<PINENCIOProtocols> encIOProtocols) {
		this.encIOProtocols.set((int) Bitmask.of(encIOProtocols));
	}

	/**
	 * {@link #typeCombined}
	 */
	public boolean isTypeCombined() {
		return typeCombined.booleanValue();
	}

	/**
	 * {@link #typeCombined}
	 */
	public void setTypeCombined(final boolean typeCombined) {
		this.typeCombined.set(typeCombined);
	}

	/**
	 * {@link #setPinblockDataRequired}
	 */
	public boolean isSetPinblockDataRequired() {
		return setPinblockDataRequired.booleanValue();
	}

	/**
	 * {@link #setPinblockDataRequired}
	 */
	public void setSetPinblockDataRequired(boolean setPinblockDataRequired) {
		this.setPinblockDataRequired.set(setPinblockDataRequired);
	}

	/**
	 * {@link #keyBlockImportFormats}
	 */
	public Set<PINKeyBlockImportFormat> getKeyBlockImportFormats() {
		return XfsConstants.of(keyBlockImportFormats, PINKeyBlockImportFormat.class);
	}

	/**
	 * {@link #keyBlockImportFormats}
	 */
	public void setKeyBlockImportFormats(final Set<PINKeyBlockImportFormat> keyBlockImportFormats) {
		this.keyBlockImportFormats.set((int) Bitmask.of(keyBlockImportFormats));
	}

	/**
	 * {@link #powerSaveControl}
	 */
	public boolean isPowerSaveControl() {
		return powerSaveControl.booleanValue();
	}

	/**
	 * {@link #powerSaveControl}
	 */
	public void setPowerSaveControl(final boolean powerSaveControl) {
		this.powerSaveControl.set(powerSaveControl);
	}

	public boolean isAntiFraudModule() {
		return antiFraudModule.booleanValue();
	}

	public void setAntiFraudModule(final boolean antiFraudModule) {
		this.antiFraudModule.set(antiFraudModule);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("clazz", clazz)
										.append("type", getType())
										.append("compound", isCompound())
										.append("keyNum", getKeyNum())
										.append("algorithms", getAlgorithms())
										.append("pinFormats", getPINFormats())
										.append("derivationAlgorithms", getDerivationAlgorithms())
										.append("presentationAlgorithms", getPresentationAlgorithms())
										.append("display", getDisplay())
										.append("idConnect", isIDConnect())
										.append("idKey", getIDKey())
										.append("validationAlgorithms", getValidationAlgorithms())
										.append("keyCheckModes", getKeyCheckModes())
										.append("extra", getExtra())
										.append("guidLights", getGuidLights())
										.append("pinCanPersistAfterUse", isPINCanPersistAfterUse())
										.append("autoBeep", getAutoBeep())
										.append("hsmVendor", getHSMVendor())
										.append("hsmJournaling", isHSMJournaling())
										.append("rsaAuthenticationSchemes", getRSAAuthenticationSchemes())
										.append("rsaSignatureAlgorithms", getRSASignatureAlgorithms())
										.append("rsaCryptAlgorithms", getRSACryptAlgorithms())
										.append("rsaKeyCheckModes", getRSAKeyCheckModes())
										.append("signatureSchemes", getRSASignatureAlgorithms())
										.append("emvImportSchemes", getEMVImportSchemes())
										.append("emvHashAlgorithms", getEMVHashAlgorithms())
										.append("keyImportThroughParts", isKeyImportThroughParts())
										.append("encIOProtocols", getEncIOProtocols())
										.append("typeCombined", isTypeCombined())
										.append("setPinblockDataRequired", isSetPinblockDataRequired())
										.append("keyBlockImportFormats", getKeyBlockImportFormats())
										.append("powerSaveControl", isPowerSaveControl())
										.append("antiFraudModule", isAntiFraudModule())
										.toString();
	}
}