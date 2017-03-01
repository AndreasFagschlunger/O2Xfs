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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import at.o2xfs.emv.tlv.TLV;
import at.o2xfs.emv.tlv.Tag;

final class ApplicationData {

	final static class ApplicationDataBuilder {

		private final Map<Tag, byte[]> dataObjects;

		private final List<DataAuthenticationRecord> dataAuthenticationRecords;

		public ApplicationDataBuilder() {
			dataObjects = new HashMap<Tag, byte[]>();
			dataAuthenticationRecords = new ArrayList<DataAuthenticationRecord>();
		}

		public void addRecord(TLV tlv) throws DuplicatePrimitiveDataException {
			if (tlv.getTag().isConstructed()) {
				for (TLV child : tlv.getChildren()) {
					addRecord(child);
				}
			} else {
				if (dataObjects.containsKey(tlv.getTag())) {
					throw new DuplicatePrimitiveDataException(tlv.getTag());
				}
				dataObjects.put(tlv.getTag(), tlv.getValue());
			}
		}

		public void addAuthenticationData(int sfi, int recordNumber, TLV tlv) {
			dataAuthenticationRecords.add(new DataAuthenticationRecord(sfi, recordNumber, tlv));
		}

		public ApplicationData build() {
			return new ApplicationData(this);
		}
	}

	private final Map<Tag, byte[]> dataObjects;

	private final List<DataAuthenticationRecord> dataAuthenticationRecords;

	private ApplicationData(ApplicationDataBuilder builder) {
		dataObjects = new HashMap<Tag, byte[]>(builder.dataObjects);
		dataAuthenticationRecords = new ArrayList<DataAuthenticationRecord>(builder.dataAuthenticationRecords);
	}

	List<DataAuthenticationRecord> getDataAuthenticationRecords() {
		return new ArrayList<DataAuthenticationRecord>(dataAuthenticationRecords);
	}

	Map<Tag, byte[]> getDataObjects() {
		return new HashMap<Tag, byte[]>(dataObjects);
	}
}