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

package at.o2xfs.ctapi;

import at.o2xfs.common.Hex;
import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.win32.Buffer;
import at.o2xfs.win32.ByteArray;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.UINT8;
import at.o2xfs.win32.USHORT;
import at.o2xfs.win32.ZSTR;

public final class CTAPI {

	static {
		System.loadLibrary("at.o2xfs.win32");
		System.loadLibrary("at.o2xfs.ctapi");
	}

	private static final Logger LOG = LoggerFactory.getLogger(CTAPI.class);

	public static final String DEFAULT_LIBRARY = "CT32.dll";

	private Pointer moduleHandle = null;

	private Pointer initFunctionAddress = null;

	private Pointer dataFunctionAddress = null;

	private Pointer closeFunctionAddress = null;

	public CTAPI() {
		this(System.getProperty("ctapi.library", DEFAULT_LIBRARY));
	}

	public CTAPI(String fileName) {
		loadLibrary(fileName);
		addShutdownHook();
	}

	private void loadLibrary(final String fileName) {
		final String method = "loadLibrary(String)";
		if (LOG.isDebugEnabled()) {
			LOG.debug(method, "fileName=" + fileName);
		}
		if (moduleHandle != null) {
			throw new IllegalStateException();
		}
		try {
			moduleHandle = new Pointer(loadLibrary0(new ZSTR(fileName)));
			initFunctionAddress = new Pointer(getFunctionAddress("CT_init"));
			dataFunctionAddress = new Pointer(getFunctionAddress("CT_data"));
			closeFunctionAddress = new Pointer(getFunctionAddress("CT_close"));
		} catch (final NativeException e) {
			throw new RuntimeException("Error loading library: " + fileName, e);
		}
	}

	private native Buffer loadLibrary0(final ZSTR fileName) throws NativeException;

	private Buffer getFunctionAddress(final String name) {
		try {
			return getFunctionAddress0(moduleHandle, new ZSTR(name));
		} catch (final NativeException e) {
			throw new RuntimeException(	"Error getting function address: moduleHandle=" + moduleHandle
												+ ",name="
												+ name,
										e);
		}
	}

	private native Buffer getFunctionAddress0(final Pointer aModuleHandle, final ZSTR name) throws NativeException;

	/**
	 *
	 *
	 * @param ctn
	 *            Logical card terminal number
	 * @param pn
	 *            Port number of the physical interface
	 */
	public void init(int ctn, int pn) throws CTException {
		final int rc = init0(initFunctionAddress, new USHORT(ctn), new USHORT(pn));
		CTException.throwFor(rc);
	}

	private native int init0(Pointer address, USHORT ctn, USHORT pn);

	public byte[] data(int ctn, int dad, int sad, byte[] command) throws CTException {
		final String method = "data(int, int, int, byte[])";
		if (LOG.isDebugEnabled()) {
			LOG.debug(method, "ctn=" + ctn + ",dad=" + dad + ",sad=" + sad + ",command=" + Hex.encode(command));
		}
		ByteArray cmdBuf = new ByteArray(command);
		ByteArray rspBuf = new ByteArray(258);
		USHORT lenr = new USHORT(rspBuf.getSize());
		int rc = data0(dataFunctionAddress, new USHORT(ctn), new UINT8(dad), new UINT8(sad), cmdBuf, lenr, rspBuf);
		CTException.throwFor(rc);
		byte[] rsp = new byte[lenr.intValue()];
		System.arraycopy(rspBuf.toByteArray(), 0, rsp, 0, rsp.length);
		if (LOG.isDebugEnabled()) {
			LOG.debug(method, "rsp=" + Hex.encode(rsp));
		}
		return rsp;
	}

	private native int data0(	Pointer address,
								USHORT ctn,
								UINT8 dad,
								UINT8 sad,
								ByteArray command,
								USHORT lenr,
								ByteArray response);

	public void close(int ctn) throws CTException {
		final int rc = close0(closeFunctionAddress, new USHORT(ctn));
		CTException.throwFor(rc);
	}

	private native int close0(Pointer address, USHORT ctn);

	private void addShutdownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread() {

			@Override
			public void run() {
				if (moduleHandle != null) {
					try {
						freeLibrary0(moduleHandle);
						moduleHandle = null;
					} catch (final NativeException e) {
						final String method = "run()";
						LOG.error(method, "Error freeing library: moduleHandle=" + moduleHandle, e);
					}
				}
			}

		});
	}

	private native void freeLibrary0(final Pointer aModuleHandle) throws NativeException;
}