package at.o2xfs.xfs.win32;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import at.o2xfs.win32.BufferFactory;
import at.o2xfs.win32.ByteArray;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Type;
import at.o2xfs.win32.TypeConverter;

public class StringMapConverter implements TypeConverter<Map<String, String>> {

	public static final TypeConverter<Map<String, String>> US_ASCII = new StringMapConverter(1,
			StandardCharsets.US_ASCII);

	private static final char NUL = 0;

	private final int bytesPerChar;

	private final Charset charset;

	public StringMapConverter(int bytesPerChar, Charset charset) {
		this.bytesPerChar = bytesPerChar;
		this.charset = charset;
	}

	@Override
	public Type toNative(Map<String, String> value, BufferFactory bufferFactory) {
		Type result = Pointer.NULL;
		if (!value.isEmpty()) {
			byte[] bytes = encode(value);
			result = new ByteArray(bytes, bufferFactory);
		}
		return result;
	}

	private byte[] encode(Map<String, String> value) {
		StringBuilder builder = new StringBuilder();
		for (Map.Entry<String, String> entry : value.entrySet()) {
			builder.append(entry.getKey()).append('=');
			if (entry.getValue() != null) {
				builder.append(entry.getValue());
			}
			builder.append(NUL);
		}
		builder.append(NUL);
		return builder.toString().getBytes(charset);
	}

	@Override
	public Map<String, String> fromNative(Pointer pointer) {
		Map<String, String> result = new HashMap<>();
		if (!Pointer.NULL.equals(pointer)) {
			int length = bytesPerChar;
			byte[] buf;
			do {
				length += bytesPerChar;
				buf = pointer.buffer(length).get();
			} while (!isTerminated(buf));
			result = decode(new String(buf, 0, buf.length - (bytesPerChar * 2), charset));
		}
		return Collections.unmodifiableMap(result);
	}

	private boolean isTerminated(byte[] buf) {
		boolean result = true;
		for (int i = bytesPerChar * 2; i >= 1; i--) {
			if (buf[buf.length - i] != NUL) {
				result = false;
				break;
			}
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
}
