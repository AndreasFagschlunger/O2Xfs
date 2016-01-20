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

import at.o2xfs.win32.DWORD;
import at.o2xfs.win32.LPSTR;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.xfs.XfsWord;
import at.o2xfs.xfs.util.XfsConstants;

import org.apache.commons.lang3.builder.ToStringBuilder;

public final class WfsPINEMVImportPublicKey
		extends Struct {

	public static final class WfsPINEMVImportPublicKeyBuilder {

		private String key = null;

		private PINUse use = null;

		private PINEMVImportScheme importScheme = null;

		private byte[] importData = null;

		private String sigKey = null;

		public WfsPINEMVImportPublicKeyBuilder key(String key) {
			this.key = key;
			return this;
		}

		public WfsPINEMVImportPublicKeyBuilder use(PINUse use) {
			this.use = use;
			return this;
		}

		public WfsPINEMVImportPublicKeyBuilder importScheme(PINEMVImportScheme importScheme) {
			this.importScheme = importScheme;
			return this;
		}

		public WfsPINEMVImportPublicKeyBuilder importData(byte[] importData) {
			this.importData = importData;
			return this;
		}

		public WfsPINEMVImportPublicKeyBuilder sigKey(String sigKey) {
			this.sigKey = sigKey;
			return this;
		}

		public WfsPINEMVImportPublicKey build() {
			WfsPINEMVImportPublicKey importPublicKey = new WfsPINEMVImportPublicKey();
			importPublicKey.allocate();
			importPublicKey.setKey(key);
			importPublicKey.setUse(use);
			importPublicKey.setImportScheme(importScheme);
			if (importData != null) {
				importPublicKey.setImportData(new WfsXData(importData));
			}
			importPublicKey.setSigKey(sigKey);
			return importPublicKey;
		}
	}

	private LPSTR key = new LPSTR();
	private DWORD use = new DWORD();
	private XfsWord<PINEMVImportScheme> importScheme = new XfsWord<>(PINEMVImportScheme.class);
	private Pointer importData = new Pointer();
	private LPSTR sigKey = new LPSTR();

	private WfsPINEMVImportPublicKey() {
		add(key);
		add(use);
		add(importScheme);
		add(importData);
		add(sigKey);
	}

	public WfsPINEMVImportPublicKey(WfsPINEMVImportPublicKey importPublicKey) {
		this();
		allocate();
		setKey(importPublicKey.getKey());
		use.set(importPublicKey.use);
		importScheme.set(importPublicKey.getImportScheme());
		setImportData(importPublicKey.getImportData());
		setSigKey(importPublicKey.getSigKey());
	}

	public void setKey(String key) {
		this.key.put(key);
	}

	public String getKey() {
		return key.toString();
	}

	public void setUse(PINUse use) {
		this.use.set(use.getValue());
	}

	public PINUse getUse() {
		return XfsConstants.valueOf(use, PINUse.class);
	}

	public void setImportScheme(PINEMVImportScheme importScheme) {
		this.importScheme.set(importScheme);
	}

	public PINEMVImportScheme getImportScheme() {
		return XfsConstants.valueOf(importScheme, PINEMVImportScheme.class);
	}

	public void setImportData(WfsXData importData) {
		this.importData.pointTo(importData);
	}

	public WfsXData getImportData() {
		if (Pointer.NULL.equals(importData)) {
			return null;
		}
		return new WfsXData(importData);
	}

	public void setSigKey(String sigKey) {
		this.sigKey.put(sigKey);
	}

	public String getSigKey() {
		return sigKey.toString();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("key", getKey())
										.append("use", getUse())
										.append("importScheme", getImportScheme())
										.append("importData", getImportData())
										.append("sigKey", getSigKey())
										.toString();
	}
}