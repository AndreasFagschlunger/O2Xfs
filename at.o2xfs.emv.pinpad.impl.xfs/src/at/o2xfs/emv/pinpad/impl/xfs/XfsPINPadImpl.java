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

package at.o2xfs.emv.pinpad.impl.xfs;

import at.o2xfs.common.ByteArrayBuilder;
import at.o2xfs.common.Hex;
import at.o2xfs.emv.cert.ICCPINEnciphermentPublicKeyCertificate;
import at.o2xfs.emv.cert.ICCPublicKeyCertificate;
import at.o2xfs.emv.cert.IssuerPublicKeyCertificate;
import at.o2xfs.emv.crypto.CAPublicKey;
import at.o2xfs.emv.pinpad.PINPad;
import at.o2xfs.emv.pinpad.PINPadException;
import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.xfs.XfsException;
import at.o2xfs.xfs.pin.PINAlgorithm;
import at.o2xfs.xfs.pin.PINEMVImportScheme;
import at.o2xfs.xfs.pin.PINFormat;
import at.o2xfs.xfs.pin.PINUse;
import at.o2xfs.xfs.pin.PinMode;
import at.o2xfs.xfs.pin.WfsPINBlock;
import at.o2xfs.xfs.pin.WfsPINEMVImportPublicKey;
import at.o2xfs.xfs.pin.WfsPINEMVImportPublicKey.WfsPINEMVImportPublicKeyBuilder;
import at.o2xfs.xfs.pin.WfsPINEMVImportPublicKeyOutput;
import at.o2xfs.xfs.pin.WfsPinCrypt;
import at.o2xfs.xfs.pin.WfsXData;
import at.o2xfs.xfs.service.ServiceNotFoundException;
import at.o2xfs.xfs.service.XfsServiceManager;
import at.o2xfs.xfs.service.pin.PINService;
import at.o2xfs.xfs.service.pin.cmd.GetPINBlockCallable;
import at.o2xfs.xfs.service.pin.cmd.PINEMVImportPublicKeyCallable;
import at.o2xfs.xfs.service.pin.cmd.PinCryptCommand;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

public class XfsPINPadImpl
		implements PINPad {

	private static final Logger LOG = LoggerFactory.getLogger(XfsPINPadImpl.class);

	private static final String KEY_CA = "KCA";

	private static final String KEY_ISSUER = "KISSUER";

	private static final String KEY_PIN = "KRSAPIN";

	private final XfsServiceManager xfsServiceManager;

	private PINService pinService = null;

	private byte[] pinBlock = null;

	public XfsPINPadImpl() {
		xfsServiceManager = XfsServiceManager.getInstance();
		initialize();
	}

	private void initialize() {
		try {
			pinService = xfsServiceManager.getService(PINService.class);
		} catch (ServiceNotFoundException e) {
			e.printStackTrace();
		}
	}

	public byte[] getPinBlock() {
		return pinBlock;
	}

	@Override
	public void loadCAPublicKey(CAPublicKey caPublicKey) throws PINPadException {
		byte[] importData = new ByteArrayBuilder().append(caPublicKey.getRID())
													.append(caPublicKey.getPublicKeyIndex())
													.append(caPublicKey.getHashAlgorithm())
													.append(caPublicKey.getPublicKeyAlgorithm())
													.append(caPublicKey.getModulus())
													.append(caPublicKey.getExponent())
													.append(caPublicKey.getCheckSum())
													.build();
		WfsPINEMVImportPublicKey publicKey = new WfsPINEMVImportPublicKeyBuilder().key(KEY_CA)
																					.use(PINUse.RSA_PUBLIC_VERIFY)
																					.importScheme(PINEMVImportScheme.CHKSUM_CA)
																					.importData(importData)
																					.build();
		importPublicKey(publicKey);
	}

	private void importPublicKey(WfsPINEMVImportPublicKey publicKey) throws PINPadException {
		final String method = "importPublicKey(WfsPINEMVImportPublicKey)";
		if (LOG.isInfoEnabled()) {
			LOG.info(method, "Import Public Key: " + publicKey);
		}
		try {
			PINEMVImportPublicKeyCallable command = new PINEMVImportPublicKeyCallable(pinService, publicKey);
			WfsPINEMVImportPublicKeyOutput result = command.call();
			if (LOG.isInfoEnabled()) {
				LOG.info(method, "Expiry Date: " + result.getExpiryDate());
			}
		} catch (Exception e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "Error importing key: " + publicKey, e);
			}
			throw new PINPadException(e);
		}
	}

	@Override
	public void loadIssuerPublicKey(IssuerPublicKeyCertificate issuerPublicKeyCertificate) throws PINPadException {
		byte[] importData = new ImportDataBuilder().append(issuerPublicKeyCertificate.getExponent())
													.append(issuerPublicKeyCertificate.getCertificate())
													.append(issuerPublicKeyCertificate.getRemainder())
													.append(issuerPublicKeyCertificate.getPAN())
													.build();
		WfsPINEMVImportPublicKey publicKey = new WfsPINEMVImportPublicKeyBuilder().key(KEY_ISSUER)
																					.use(PINUse.RSA_PUBLIC_VERIFY)
																					.importScheme(PINEMVImportScheme.ISSUER)
																					.importData(importData)
																					.sigKey(KEY_CA)
																					.build();
		importPublicKey(publicKey);
	}

	@Override
	public void loadICCPublicKey(ICCPublicKeyCertificate iccPublicKeyCertificate) throws PINPadException {
		byte[] importData = new ImportDataBuilder().append(iccPublicKeyCertificate.getExponent())
													.append(iccPublicKeyCertificate.getCertificate())
													.append(iccPublicKeyCertificate.getRemainder())
													.append(iccPublicKeyCertificate.getSDA())
													.append(iccPublicKeyCertificate.getPAN())
													.build();
		WfsPINEMVImportPublicKey publicKey = new WfsPINEMVImportPublicKeyBuilder().key(KEY_PIN)
																					.use(PINUse.RSA_PUBLIC)
																					.importScheme(PINEMVImportScheme.ICC)
																					.importData(importData)
																					.sigKey(KEY_ISSUER)
																					.build();
		importPublicKey(publicKey);
	}

	@Override
	public void loadICCPINEnciphermentPublicKey(ICCPINEnciphermentPublicKeyCertificate iccPINEnciphermentPublicKeyCertificate) throws PINPadException {
		byte[] importData = new ImportDataBuilder().append(iccPINEnciphermentPublicKeyCertificate.getExponent())
													.append(iccPINEnciphermentPublicKeyCertificate.getCertificate())
													.append(iccPINEnciphermentPublicKeyCertificate.getRemainder())
													.append(iccPINEnciphermentPublicKeyCertificate.getSDA())
													.append(iccPINEnciphermentPublicKeyCertificate.getPAN())
													.build();
		WfsPINEMVImportPublicKey publicKey = new WfsPINEMVImportPublicKeyBuilder().key(KEY_PIN)
																					.use(PINUse.RSA_PUBLIC)
																					.importScheme(PINEMVImportScheme.ICC_PIN)
																					.importData(importData)
																					.sigKey(KEY_ISSUER)
																					.build();
		importPublicKey(publicKey);
	}

	@Override
	public byte[] getEncipheredPIN(byte[] challenge) throws PINPadException {
		new PINCommand(pinService).execute();
		WfsPINBlock pinBlock = new WfsPINBlock();
		pinBlock.allocate();
		pinBlock.setCustomerData(Hex.encode(challenge));
		pinBlock.setFormat(PINFormat.EMV);
		pinBlock.setKey(KEY_PIN);
		return getPINBlock(pinBlock);
	}

	@Override
	public byte[] getPlaintextPIN() throws PINPadException {
		new PINCommand(pinService).execute();
		WfsPINBlock pinBlock = new WfsPINBlock();
		pinBlock.allocate();
		pinBlock.setFormat(PINFormat.EMV);
		return getPINBlock(pinBlock);
	}

	@Override
	public void getOnlinePIN() throws PINPadException {
		PINCommand pinCommand = new PINCommand(pinService);
		pinCommand.execute();
		WfsPINBlock pinBlock = new WfsPINBlock();
		pinBlock.allocate();
		byte[] customerData = new byte[5];
		new SecureRandom().nextBytes(customerData);
		pinBlock.setCustomerData(Hex.encode(customerData));
		pinBlock.setFormat(PINFormat.ISO1);
		pinBlock.setKey("PEK");
		pinBlock.setPadding((byte) 0);
		this.pinBlock = getPINBlock(pinBlock);
	}

	private byte[] getPINBlock(WfsPINBlock pinBlock) throws PINPadException {
		final String method = "getPINBlock(WfsPINBlock)";
		try {
			GetPINBlockCallable pinBlockCallable = new GetPINBlockCallable(pinService, pinBlock);
			WfsXData xData = pinBlockCallable.call();
			return xData.getData();
		} catch (Exception e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "Error getting PIN Block", e);
			}
			throw new PINPadException(e);
		}
	}

	private String toUnpackedString(byte[] data) {
		try {
			String hex = Hex.encode(data).toUpperCase();
			String unpacked = Hex.encode(hex.getBytes("US-ASCII"));
			return unpacked;
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public byte[] createMAC(byte[] data) throws XfsException {
		WfsPinCrypt pinCrypt = new WfsPinCrypt();
		pinCrypt.allocate();
		pinCrypt.setMode(PinMode.ENCRYPT);
		pinCrypt.setKey("MAK");
		pinCrypt.setAlgorithm(PINAlgorithm.TRIDESMAC);
		pinCrypt.setStartValue(new byte[8]);
		pinCrypt.setCryptData(data);
		return new PinCryptCommand(pinService, pinCrypt).call().getData();
	}
}