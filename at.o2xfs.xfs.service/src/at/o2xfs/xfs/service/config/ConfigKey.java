package at.o2xfs.xfs.service.config;

import java.util.ArrayList;
import java.util.List;

public class ConfigKey<T> {

	public static class Builder<T> {

		private final List<String> keys;

		private T defaultValue = null;

		public Builder() {
			keys = new ArrayList<String>();
		}

		private String getKey() {
			StringBuilder key = new StringBuilder();
			int iMax = keys.size() - 1;
			for (int i = 0; iMax != -1; i++) {
				key.append(keys.get(i));
				if (i == iMax) {
					break;
				}
				key.append('.');
			}
			return key.toString();
		}

		public Builder<T> key(String key) {
			keys.add(key);
			return this;
		}

		public Builder<T> defaultValue(T defaultValue) {
			this.defaultValue = defaultValue;
			return this;
		}

		public ConfigKey<T> build() {
			return new ConfigKey<T>(this);
		}
	}

	private final String key;

	private final T defaultValue;

	private ConfigKey(Builder<T> builder) {
		this.key = builder.getKey();
		this.defaultValue = builder.defaultValue;
	}

	public String getKey() {
		return key;
	}

	public T getDefaultValue() {
		return defaultValue;
	}
}
