package at.o2xfs.xfs;

import at.o2xfs.win32.ByteArray;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.ValueType;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class XfsExtra
		extends Pointer
		implements ValueType<Map<String, String>> {

	public void set(XfsExtra value) {
		set(value.get());
	}

	@Override
	public void set(Map<String, String> value) {
		pointTo(new ByteArray(encode(value)));
	}

	private byte[] encode(Map<String, String> value) {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		OutputStreamWriter writer = new OutputStreamWriter(outStream, StandardCharsets.US_ASCII);
		try {
			try {
				if (value.isEmpty()) {
					writer.append('\0');
				} else {
					for (Map.Entry<String, String> each : value.entrySet()) {
						writer.append(each.getKey()).append('=').append(each.getValue()).append('\0');
					}
				}
				writer.append('\0');
			} finally {
				writer.close();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return outStream.toByteArray();
	}

	@Override
	public Map<String, String> get() {
		Map<String, String> result = Collections.emptyMap();
		if (!NULL.equals(this)) {
			int length = 2;
			byte[] bytes = null;
			do {
				bytes = buffer(length++).get();
			} while (bytes[bytes.length - 2] != 0 || bytes[bytes.length - 1] != 0);
			result = decode(new String(bytes, 0, bytes.length - 2, StandardCharsets.US_ASCII));
		}
		return result;
	}

	private Map<String, String> decode(String s) {
		Map<String, String> result = new LinkedHashMap<String, String>();
		for (String each : s.split("\0")) {
			int beginIndex = each.indexOf('=');
			if (beginIndex == -1) {
				result.put(each, null);
				continue;
			}
			String key = each.substring(0, beginIndex);
			String value = each.substring(beginIndex + 1);
			result.put(key, value);
		}
		return result;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(get()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof XfsExtra) {
			return new EqualsBuilder().append(get(), ((XfsExtra) obj).get()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return String.valueOf(get());
	}
}