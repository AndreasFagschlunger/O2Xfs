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
