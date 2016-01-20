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

package at.o2xfs.win32.impl;

import at.o2xfs.common.Hex;
import at.o2xfs.win32.Buffer;
import at.o2xfs.win32.BufferOverflowException;

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

public class Win32BufferTest {

	private Win32BufferFactory factory = new Win32BufferFactory();

	@Test
	public final void putAndGet() {
		byte[] expecteds = getBytes(10);
		Buffer buffer = factory.createBuffer(expecteds.length);
		buffer.put(expecteds);
		Assert.assertArrayEquals(expecteds, buffer.get());
	}

	@Test(expected = BufferOverflowException.class)
	public final void bufferOverflow() {
		byte[] src = getBytes(4);
		Buffer buffer = factory.createBuffer(src.length);
		buffer.put(Hex.decode("1234567890"));
	}

	private byte[] getBytes(int length) {
		byte[] bytes = new byte[length];
		new Random().nextBytes(bytes);
		return bytes;
	}

	@Test
	public final void subBuffer() {
		byte[] src = Hex.decode("12345678");
		Buffer buffer = factory.createBuffer(src.length);
		buffer.put(src);
		Assert.assertArrayEquals(Hex.decode("3456"), buffer.subBuffer(1, 2).get());
	}
}