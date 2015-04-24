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

package at.o2xfs.xfs.idc;

import at.o2xfs.win32.ByteArray;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.ULONG;
import at.o2xfs.xfs.XfsVersion;
import at.o2xfs.xfs.XfsWord;
import at.o2xfs.xfs.util.XfsConstants;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author Andreas Fagschlunger
 *
 */
public class WFSIDCCARDDATA
		extends Struct {

	/**
	 * Specifies the source of the card data as one of the following flags: {@link IDCTrack}
	 */
	private final XfsWord<IDCTrack> dataSource = new XfsWord<>(IDCTrack.class);

	/**
	 * Status of reading the card data. Possible values are: {@link IDCData}
	 */
	private final XfsWord<IDCData> status = new XfsWord<>(IDCData.class);

	/**
	 * Specifies the length of {@link #data}.
	 */
	private final ULONG dataLength = new ULONG();

	/**
	 * Points to the data read from the track/chip or the value returned by the
	 * security module.
	 */
	private final Pointer pData = new Pointer();

	/**
	 * Indicates whether a loco or hico magnetic stripe is being written.
	 *
	 * @since 3.00
	 */
	private XfsWord<IDCWriteMethod> writeMethod = new XfsWord<>(IDCWriteMethod.class);

	public WFSIDCCARDDATA(final XfsVersion xfsVersion) {
		add(dataSource);
		add(status);
		add(dataLength);
		add(pData);
		if (xfsVersion.isGE(XfsVersion.V3_00)) {
			add(writeMethod);
		} else {
			writeMethod.allocate();
		}
	}

	/**
	 * @param pCardData
	 *            pointer to card data structure
	 */
	public WFSIDCCARDDATA(final XfsVersion xfsVersion, final Pointer pCardData) {
		this(xfsVersion);
		assignBuffer(pCardData);
	}

	/**
	 * Copy constructor.
	 */
	public WFSIDCCARDDATA(final XfsVersion xfsVersion, final WFSIDCCARDDATA cardData) {
		this(xfsVersion);
		allocate();
		dataSource.set(cardData.getDataSource());
		status.set(cardData.getStatus());
		dataLength.set(cardData.dataLength);
		if (!Pointer.NULL.equals(cardData.pData)) {
			pData.pointTo(new ByteArray(cardData.getData()));
		}
		writeMethod.set(cardData.getWriteMethod());
	}

	public void setDataSource(IDCTrack dataSource) {
		this.dataSource.set(dataSource);
	}

	public IDCTrack getDataSource() {
		return XfsConstants.valueOf(dataSource, IDCTrack.class);
	}

	public void setStatus(final IDCData status) {
		this.status.set(status);
	}

	public IDCData getStatus() {
		return XfsConstants.valueOf(status, IDCData.class);
	}

	private void setDataLength(long l) {
		dataLength.set(l);
	}

	private long getDataLength() {
		return dataLength.longValue();
	}

	public void setData(final byte[] data) {
		setDataLength(data.length);
		ByteArray byteArray = new ByteArray(data);
		pData.pointTo(byteArray);
	}

	public byte[] getData() {
		byte[] result = null;
		if (!Pointer.NULL.equals(pData)) {
			result = pData.buffer((int) getDataLength()).get();
		}
		return result;
	}

	public IDCWriteMethod getWriteMethod() {
		return XfsConstants.valueOf(writeMethod, IDCWriteMethod.class);
	}

	public void setWriteMethod(final IDCWriteMethod writeMethod) {
		this.writeMethod.set(writeMethod);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("dataSource", getDataSource())
										.append("status", getStatus())
										.append("dataLength", getDataLength())
										.append("data", getData())
										.append("writeMethod", getWriteMethod())
										.toString();
	}

}