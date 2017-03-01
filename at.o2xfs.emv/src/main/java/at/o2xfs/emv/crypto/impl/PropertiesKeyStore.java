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

package at.o2xfs.emv.crypto.impl;

import java.util.Properties;

import at.o2xfs.common.Hex;
import at.o2xfs.emv.crypto.CAPublicKey;
import at.o2xfs.emv.crypto.CAPublicKey.CAPublicKeyBuilder;
import at.o2xfs.emv.crypto.CertificateNotFoundException;
import at.o2xfs.emv.crypto.KeyStore;
import at.o2xfs.emv.crypto.KeyStoreException;
import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;

class PropertiesKeyStore implements KeyStore {

	private final static Logger LOG = LoggerFactory
			.getLogger(PropertiesKeyStore.class);

	private final Properties keyStore;

	protected PropertiesKeyStore(Properties keyStore) {
		this.keyStore = keyStore;
	}

	private String keyID(byte[] rid, byte[] index) {
		return Hex.encode(rid) + "_" + Hex.encode(index);
	}

	@Override
	public CAPublicKey getCAPublicKey(byte[] rid, byte[] index)
			throws KeyStoreException {
		final String method = "getCAPublicKey(byte[], byte[])";
		if (LOG.isInfoEnabled()) {
			LOG.info(method, "Get CA for RID: " + Hex.encode(rid)
					+ ", Public Key Index: " + Hex.encode(index));
		}
		String key = keyID(rid, index);
		String value = keyStore.getProperty(key);
		if (value == null) {
			throw new CertificateNotFoundException(rid, index);
		}
		CAPublicKeyBuilder builder = new CAPublicKeyBuilder().rid(rid)
				.publicKeyIndex(index);
		return parseKey(builder, value);
	}

	private CAPublicKey parseKey(CAPublicKeyBuilder builder, String value) {
		String[] parts = value.split("_");
		builder.modulus(Hex.decode(parts[0]));
		builder.exponent(Hex.decode(parts[1]));
		builder.checkSum(Hex.decode(parts[2]));
		return builder.build();

	}

}