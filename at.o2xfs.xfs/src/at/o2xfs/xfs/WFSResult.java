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

package at.o2xfs.xfs;

import java.nio.ByteBuffer;

import org.apache.commons.lang.builder.ToStringBuilder;

import at.o2xfs.win32.DWORD;
import at.o2xfs.win32.INT;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.SYSTEMTIME;
import at.o2xfs.win32.Structure;
import at.o2xfs.win32.Union;
import at.o2xfs.win32.Union.Field;
import at.o2xfs.xfs.type.HSERVICE;
import at.o2xfs.xfs.type.REQUESTID;
import at.o2xfs.xfs.util.XfsConstants;

/**
 * @author Andreas Fagschlunger
 */
public class WFSResult extends Structure {

	private final static String COMMANDCODE = "commandCode";
	private final static String EVENTID = "eventID";

	/**
	 * Request ID of the completed command; not used for event notifications
	 * other than Execute events.
	 */
	private final REQUESTID requestID = new REQUESTID();

	/**
	 * Service handle identifying the session that created the result, i.e. the
	 * service handle of the session that the event is sent to.
	 */
	private final HSERVICE service = new HSERVICE();

	/**
	 * Time the event occurred (local time, in a Win32 SYSTEMTIME structure).
	 */
	private final SYSTEMTIME timestamp = new SYSTEMTIME();

	/**
	 * Result handle (note that for synchronous WFSExecute and WFSGetInfo
	 * commands, this value is identical to the synchronous function return
	 * value).
	 */
	private final INT result = new INT();

	/**
	 * WFSExecute "command" code or WFSGetInfo "category" code; not used for
	 * other command completions.
	 * 
	 * ID of the event (for unsolicited events).
	 */
	private Union u = new Union(new Field[] {
			new Field(COMMANDCODE, new DWORD()),
			new Field(EVENTID, new DWORD()) });

	/**
	 * Pointer to the results of the command (if any) or the contents of the
	 * event notification.
	 */
	private Pointer buffer = new Pointer();

	public WFSResult() {
		add(requestID);
		add(service);
		add(timestamp);
		add(result);
		add(u);
		add(buffer);
	}

	public WFSResult(final Pointer pResult) {
		this();
		this.useBuffer(pResult);
	}

	public WFSResult(ByteBuffer byteBuffer) {
		this(byteBuffer, 0);
	}

	public WFSResult(ByteBuffer byteBuffer, int offset) {
		this();
		useBuffer(byteBuffer, offset);
	}

	public REQUESTID getRequestID() {
		return requestID;
	}

	public HSERVICE getService() {
		return service;
	}

	public SYSTEMTIME getTimestamp() {
		return timestamp;
	}

	public int getResult() {
		return result.intValue();
	}

	public <E extends Enum<E>> E getCommandCode(final Class<E> enumType) {
		final DWORD value = u.get(COMMANDCODE, DWORD.class);
		return XfsConstants.valueOf(value, enumType);
	}

	public <E extends Enum<E>> E getEventID(final Class<E> enumType) {
		final DWORD value = u.get(EVENTID, DWORD.class);
		return XfsConstants.valueOf(value, enumType);
	}

	public Pointer getBuffer() {
		return buffer;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("requestID", requestID)
				.append("service", service).append("timestamp", timestamp)
				.append("result", result).append("u", u)
				.append("buffer", buffer).toString();
	}
}
