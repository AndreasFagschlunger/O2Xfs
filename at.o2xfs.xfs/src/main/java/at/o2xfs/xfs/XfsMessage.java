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

package at.o2xfs.xfs;

public enum XfsMessage implements XfsConstant {

	WFS_OPEN_COMPLETE(1L),
	WFS_CLOSE_COMPLETE(2L),
	WFS_LOCK_COMPLETE(3L),
	WFS_UNLOCK_COMPLETE(4L),
	WFS_REGISTER_COMPLETE(5L),
	WFS_DEREGISTER_COMPLETE(6L),
	WFS_GETINFO_COMPLETE(7L),
	WFS_EXECUTE_COMPLETE(8L),

	WFS_EXECUTE_EVENT(20L),
	WFS_SERVICE_EVENT(21L),
	WFS_USER_EVENT(22L),
	WFS_SYSTEM_EVENT(23L),

	WFS_TIMER_EVENT(100L);

	private final static long WM_USER = 0x0400L;

	private final long value;

	private XfsMessage(final long value) {
		this.value = WM_USER + value;
	}

	public boolean isOperationComplete() {
		switch (this) {
			case WFS_OPEN_COMPLETE:
			case WFS_CLOSE_COMPLETE:
			case WFS_LOCK_COMPLETE:
			case WFS_UNLOCK_COMPLETE:
			case WFS_REGISTER_COMPLETE:
			case WFS_DEREGISTER_COMPLETE:
			case WFS_GETINFO_COMPLETE:
			case WFS_EXECUTE_COMPLETE:
				return true;
			default:
				return false;
		}
	}

	public boolean isIntermediateEvent() {
		switch (this) {
			case WFS_EXECUTE_EVENT:
				return true;
			default:
				return false;
		}
	}

	@Override
	public long getValue() {
		return value;
	}
}