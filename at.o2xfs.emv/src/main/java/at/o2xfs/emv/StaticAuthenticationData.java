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

package at.o2xfs.emv;

import java.util.ArrayList;
import java.util.List;

import at.o2xfs.common.Bytes;
import at.o2xfs.emv.tlv.TLV;
import at.o2xfs.emv.tlv.Tag;
import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;

public class StaticAuthenticationData {

	private static final Logger LOG = LoggerFactory
			.getLogger(StaticAuthenticationData.class);

	private final EMVTransaction transaction;

	public StaticAuthenticationData(EMVTransaction transaction) {
		this.transaction = transaction;
	}

	public byte[] getData() throws StaticAuthenticationDataException {
		List<byte[]> authenticationData = new ArrayList<byte[]>();
		List<DataAuthenticationRecord> records = transaction
				.getDataAuthenticationRecords();
		for (DataAuthenticationRecord record : records) {
			TLV tlv = record.getTLV();
			if (!EMVTag.READ_RECORD.getTag().equals(tlv.getTag())) {
				throw new StaticAuthenticationDataException(
						"Illegal Record SFI: " + record.getSFI()
								+ ", Record Number: "
								+ record.getRecordNumber() + ", TLV: "
								+ record.getTLV());
			}
			if (record.getSFI() <= 10) {
				authenticationData.add(tlv.getValue());
			} else {
				authenticationData.add(tlv.getBytes());
			}
		}
		authenticationData.add(processTagList());
		return Bytes.concat(authenticationData);
	}

	private byte[] processTagList() throws StaticAuthenticationDataException {
		List<Tag> tagList = getStaticDataAuthenticationTagList();
		if (tagList.isEmpty()) {
			return Bytes.EMPTY;
		}
		Tag tag = tagList.get(0);
		if (!EMVTag.APPLICATION_INTERCHANGE_PROFILE.getTag().equals(tag)
				|| tagList.size() != 1) {
			throw new StaticAuthenticationDataException(
					"Illegal Static Data Authentication Tag List: " + tagList);
		}
		return transaction
				.getMandatoryData(EMVTag.APPLICATION_INTERCHANGE_PROFILE);
	}

	private List<Tag> getStaticDataAuthenticationTagList() {
		List<Tag> tagList = new ArrayList<Tag>();
		try {
			byte[] data = transaction
					.getData(EMVTag.STATIC_DATA_AUTHENTICATION_TAG_LIST);
			for (int offset = 0; offset < data.length;) {
				Tag tag = Tag.parse(data, offset);
				tagList.add(tag);
				offset += tag.size();
			}
		} catch (DataNotFoundException e) {
			if (LOG.isDebugEnabled()) {
				final String method = "getStaticDataAuthenticationTagList()";
				LOG.debug(method,
						"Static Data Authentication Tag List does not exist");
			}
		}
		return tagList;
	}
}