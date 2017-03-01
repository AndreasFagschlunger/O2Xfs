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

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class TableAdjuster implements TableModelListener {

	private JTable table = null;

	public TableAdjuster(final JTable t) {
		table = t;
		table.getModel().addTableModelListener(this);
	}

	private void adjustColumn(final int columnIndex) {
		final TableColumn column = table.getColumnModel()
				.getColumn(columnIndex);
		final TableCellRenderer headerRenderer = getHeaderRenderer(column);
		int width = getHeaderWidth(headerRenderer, column.getHeaderValue(),
				columnIndex);
		for (int row = 0; row < table.getRowCount(); row++) {
			width = Math.max(width, getWidth(row, columnIndex));
		}
		column.setMaxWidth(width);
		column.setMinWidth(width);
	}

	private int getHeaderWidth(final TableCellRenderer renderer,
			final Object value, final int column) {
		final Component c = renderer.getTableCellRendererComponent(table,
				value, false, false, -1, column);
		final int space = table.getIntercellSpacing().width * 2;
		final int width = c.getPreferredSize().width + space;
		return width;
	}

	private TableCellRenderer getHeaderRenderer(final TableColumn column) {
		final TableCellRenderer renderer = column.getHeaderRenderer();
		if (renderer != null) {
			return renderer;
		}
		return table.getTableHeader().getDefaultRenderer();
	}

	private int getWidth(final int row, int column) {
		final TableCellRenderer renderer = table.getCellRenderer(row, column);
		final Component c = table.prepareRenderer(renderer, row, column);
		final int space = table.getIntercellSpacing().width * 2;
		final int width = c.getPreferredSize().width + space;
		return width;
	}

	public void adjust() {
		for (int columnIndex = 0; columnIndex < table.getColumnCount() - 1; columnIndex++) {
			adjustColumn(columnIndex);
		}
	}

	public void tableChanged(final TableModelEvent e) {
		adjust();
	}
}