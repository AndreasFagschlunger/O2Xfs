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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import at.o2xfs.emv.crypto.KeyStore;
import at.o2xfs.emv.crypto.KeyStoreFactory;
import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;

public class PropertiesKeyStoreFactoryImpl extends KeyStoreFactory {

	private static final Logger LOG = LoggerFactory
			.getLogger(PropertiesKeyStoreFactoryImpl.class);

	private final Properties keyStore;

	public PropertiesKeyStoreFactoryImpl() {
		keyStore = new Properties();
	}

	private void loadKeyStoreFromFile() {
		final String method = "createKeyStore()";
		File file = new File("keystore.properties");
		InputStream inStream = null;
		try {
			inStream = new FileInputStream(file);
			keyStore.load(inStream);
		} catch (Exception e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(
						method,
						"Error loading key store from file: "
								+ file.getAbsolutePath(), e);
			}
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
					if (LOG.isErrorEnabled()) {
						LOG.error(method, "Error closing stream", e);
					}
				}
			}
		}
	}

	@Override
	public KeyStore createKeyStore() {
		if (keyStore.isEmpty()) {
			loadKeyStoreFromFile();
		}
		return new PropertiesKeyStore(keyStore);
	}
}