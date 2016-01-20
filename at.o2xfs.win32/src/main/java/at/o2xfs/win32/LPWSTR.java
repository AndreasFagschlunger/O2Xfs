package at.o2xfs.win32;

import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class LPWSTR
		extends Pointer
		implements ValueType<String> {

	public void set(LPWSTR value) {
		set(value.get());
	}

	@Override
	public void set(String value) {
		pointTo(WString.valueOf(value));
	}

	@Override
	public String get() {
		if (!NULL.equals(this)) {
			for (int size = 2;; size += 2) {
				byte[] bytes = buffer(size).get();
				if (bytes[bytes.length - 1] == 0 && bytes[bytes.length - 2] == 0) {
					return new String(bytes, 0, size - 2, StandardCharsets.UTF_16LE);
				}
			}
		}
		return null;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(get()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof LPWSTR) {
			return new EqualsBuilder().append(get(), ((LPWSTR) obj).get()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return String.valueOf(get());
	}
}