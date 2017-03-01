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

package at.o2xfs.operator.task;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.operator.config.Config;

public class TaskConfig {

	private static final Logger LOG = LoggerFactory.getLogger(TaskConfig.class);

	private static TaskConfig taskConfig = null;

	private Properties properties = null;

	private TaskConfig() {
		properties = new Properties();
		loadProperties();
	}

	private void loadProperties() {
		final String method = "loadProperties()";
		final InputStream in = getClass()
				.getResourceAsStream("task.properties");
		try {
			properties.load(in);
		} catch (final IOException e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "Error loading Properties", e);
			}
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (final IOException e) {
					if (LOG.isErrorEnabled()) {
						LOG.error(method, "Error closing InputStream: " + in, e);
					}
				}
			}
		}
		if (LOG.isDebugEnabled()) {
			for (final Map.Entry<Object, Object> entry : properties.entrySet()) {
				LOG.debug(method, entry.getKey() + "=" + entry.getValue());
			}
		}
	}

	private Properties createProperties(final Task task) {
		final String method = "createProperties(Task)";
		final Properties taskProperties = new Properties();
		final String taskPrefix = task.getClass().getSimpleName() + ".";
		for (final Map.Entry<Object, Object> entry : properties.entrySet()) {
			String key = String.valueOf(entry.getKey());
			if (key.startsWith(taskPrefix)) {
				key = key.substring(key.indexOf('.') + 1);
				taskProperties.put(key, entry.getValue());
			}
		}
		if (LOG.isDebugEnabled()) {
			for (final Map.Entry<Object, Object> entry : taskProperties
					.entrySet()) {
				LOG.debug(method, entry.getKey() + "=" + entry.getValue());
			}
		}
		return taskProperties;
	}

	private Config createConfig(final Task task) {
		return new Config(createProperties(task));
	}

	private static final TaskConfig getInstance() {
		if (taskConfig == null) {
			synchronized (TaskConfig.class) {
				if (taskConfig == null) {
					taskConfig = new TaskConfig();
				}
			}
		}
		return taskConfig;
	}

	public static final Config getConfig(final Task task) {
		return getInstance().createConfig(task);
	}
}