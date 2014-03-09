/*
 * Copyright (c) 2014, Andreas Fagschlunger. All rights reserved.
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

import java.util.LinkedHashMap;
import java.util.Map;

import at.o2xfs.win32.Struct;
import at.o2xfs.win32.Type;

public class XfsStruct extends Struct {

	public class XfsStructInit {

		private final XfsVersion version;

		private final Map<Type, XfsVersionRange> fields;

		public XfsStructInit(XfsVersion version) {
			this.version = version;
			fields = new LinkedHashMap<Type, XfsVersionRange>();
		}

		public XfsStructInit add(Type field) {
			fields.put(field, null);
			return this;
		}

		public XfsStructInit addUntil(Type field, XfsVersion version) {
			fields.put(field, new XfsVersionRange(version, version));
			return this;
		}

		public XfsStructInit addSince(Type field, XfsVersion version) {
			fields.put(field, new XfsVersionRange(version, null));
			return this;
		}

		public void init(XfsStruct struct) {
			for (Map.Entry<Type, XfsVersionRange> each : fields.entrySet()) {
				if (each.getValue() == null
						|| each.getValue().contains(version)) {
					struct.add(each.getKey());
				} else {
					each.getKey().allocate();
				}
			}
		}
	}
}