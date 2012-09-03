/*
 * Copyright (c) 2012, Andreas Fagschlunger. All rights reserved.
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

package at.o2xfs.ctapi;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.win32.UINT8;
import at.o2xfs.win32.USHORT;

public final class CTAPI {

	static {
		System.loadLibrary("CT-API");
	}

	private static final Logger LOG = LoggerFactory.getLogger(CTAPI.class);

	private String fileName = "CT32.dll";

	private long moduleHandle = 0L;

	private long initFunctionAddress = 0L;

	private long dataFunctionAddress = 0L;

	private long closeFunctionAddress = 0L;

	public CTAPI() {
		loadLibrary(fileName);
		addShutdownHook();
	}

	private void loadLibrary(final String fileName) {
		if (moduleHandle != 0L) {
			throw new IllegalStateException();
		}
		try {
			moduleHandle = loadLibrary0(fileName);
			initFunctionAddress = getFunctionAddress("CT_init");
			dataFunctionAddress = getFunctionAddress("CT_data");
			closeFunctionAddress = getFunctionAddress("CT_close");
		} catch (final NativeException e) {
			throw new RuntimeException("Error loading library: " + fileName, e);
		}
	}

	private native long loadLibrary0(final String fileName)
			throws NativeException;

	private long getFunctionAddress(final String name) {
		try {
			return getFunctionAddress0(moduleHandle, name);
		} catch (final NativeException e) {
			throw new RuntimeException(
					"Error getting function address: moduleHandle="
							+ moduleHandle + ",name=" + name, e);
		}
	}

	private native long getFunctionAddress0(final long moduleHandle,
			final String name) throws NativeException;

	/**
	 * 
	 * 
	 * @param ctn
	 *            Logical card terminal number
	 * @param pn
	 *            Port number of the physical interface
	 */
	public void init(int ctn, int pn) throws CTException {
		final int rc = init0(initFunctionAddress, new USHORT(ctn).buffer(),
				new USHORT(pn).buffer());
		CTException.throwFor(rc);
	}

	private native int init0(long address, ByteBuffer ctn, ByteBuffer pn);

	public byte[] data(int ctn, int dad, int sad, byte[] command)
			throws CTException {
		final ByteBuffer cmdBuf = createBuffer(command.length);
		final ByteBuffer rspBuf = createBuffer(258);
		final USHORT lenr = new USHORT(rspBuf.capacity());
		cmdBuf.put(command);
		final int rc = data0(dataFunctionAddress, new USHORT(ctn).buffer(),
				new UINT8(dad).buffer(), new UINT8(sad).buffer(), cmdBuf,
				lenr.buffer(), rspBuf);
		CTException.throwFor(rc);
		final byte[] rsp = new byte[lenr.intValue()];
		rspBuf.get(rsp, 0, rsp.length);
		return rsp;
	}

	private ByteBuffer createBuffer(final int capacity) {
		final ByteBuffer buffer = ByteBuffer.allocateDirect(capacity);
		buffer.order(ByteOrder.nativeOrder());
		return buffer;
	}

	private native int data0(long address, ByteBuffer ctn, ByteBuffer dad,
			ByteBuffer sad, ByteBuffer command, ByteBuffer lenr,
			ByteBuffer response);

	public void close(int ctn) throws CTException {
		final int rc = close0(closeFunctionAddress, new USHORT(ctn).buffer());
		CTException.throwFor(rc);
	}

	private native int close0(long address, ByteBuffer ctn);

	private void addShutdownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread() {

			public void run() {
				if (moduleHandle != 0L) {
					try {
						freeLibrary0(moduleHandle);
						moduleHandle = 0L;
					} catch (final NativeException e) {
						final String method = "run()";
						LOG.error(method,
								"Error freeing library: moduleHandle="
										+ moduleHandle, e);
					}
				}
			}

		});
	}

	private native void freeLibrary0(final long moduleHandle)
			throws NativeException;
}
