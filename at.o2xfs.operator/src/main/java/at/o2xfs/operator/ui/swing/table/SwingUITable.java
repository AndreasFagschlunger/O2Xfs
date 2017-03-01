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

package at.o2xfs.operator.ui.swing.table;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.operator.config.Config;
import at.o2xfs.operator.ui.content.table.Table;

public class SwingUITable extends JTable {

	private static final Logger LOG = LoggerFactory
			.getLogger(SwingUITable.class);

	private Config config = null;

	private ContentTableModel model = null;

	private TableCellRenderer defaultRenderer = null;

	private Map<Class<?>, TableCellRenderer> renderers = null;

	public SwingUITable(final Config config, final Table table) {
		this.config = config;
		model = new ContentTableModel(table);
		setModel(model);
		defaultRenderer = new DefaultTableCellRenderer();
		renderers = new HashMap<Class<?>, TableCellRenderer>();
		initComponent();
	}

	private void initComponent() {
		getTableHeader().setReorderingAllowed(false);
		getTableHeader().setResizingAllowed(false);
		setFocusable(false);
		setColumnSelectionAllowed(false);
		setRowSelectionAllowed(false);
		loadCellRenderers();
	}

	private void loadCellRenderers() {
		final String method = "loadCellRenderers()";
		final Map<String, String> classMap = config.getMap("Table.Renderers");
		for (final Map.Entry<String, String> entry : classMap.entrySet()) {
			final String typeClassName = entry.getKey().trim();
			try {
				final Class<?> columnClass = Class.forName(typeClassName);
				final String rendererClassName = entry.getValue().trim();
				try {
					final Class<? extends TableCellRenderer> rendererClass = (Class<? extends TableCellRenderer>) Class
							.forName(rendererClassName);
					final TableCellRenderer renderer = rendererClass
							.newInstance();
					if (LOG.isDebugEnabled()) {
						LOG.debug(method, "columnClass=" + columnClass
								+ ",renderer=" + rendererClass);
					}
					renderers.put(columnClass, renderer);
				} catch (final Exception e) {
					if (LOG.isErrorEnabled()) {
						LOG.error(method, "Error loading TableCellRenderer: "
								+ rendererClassName, e);
					}
				}
			} catch (Exception e) {
				if (LOG.isErrorEnabled()) {
					LOG.error(method, "Error loading Class: " + typeClassName,
							e);
				}
			}
		}
	}

	@Override
	public TableCellRenderer getCellRenderer(int row, int column) {
		try {
			final Object value = getValueAt(row, column);
			if (value != null) {
				final TableCellRenderer renderer = getRendererForValue(value);
				if (renderer != null) {
					return renderer;
				}
			}
		} catch (final ArrayIndexOutOfBoundsException e) {
			System.err.println("row=" + row + ",column=" + column);
		}
		return defaultRenderer;
	}

	private TableCellRenderer getRendererForValue(final Object value) {
		for (final Map.Entry<Class<?>, TableCellRenderer> entry : renderers
				.entrySet()) {
			final Class<?> classRenderer = entry.getKey();
			if (classRenderer.isInstance(value)) {
				if (LOG.isDebugEnabled()) {
					final String method = "getRendererForValue(Object)";
					LOG.debug(method, "classRenderer=" + classRenderer
							+ ",value=" + value);
				}
				return entry.getValue();
			}
		}
		return null;
	}
}