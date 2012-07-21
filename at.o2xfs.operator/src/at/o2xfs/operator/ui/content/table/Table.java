/*
 * Copyright (c) 2012, Andreas Fagschlunger. All rights reserved.
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

package at.o2xfs.operator.ui.content.table;

import java.util.ArrayList;
import java.util.List;

import at.o2xfs.operator.task.Task;
import at.o2xfs.operator.task.TaskCommand;
import at.o2xfs.operator.ui.content.text.Label;

public class Table {

	private String taskName = null;

	private Label[] columnNames = null;

	private List<Row> rows = null;

	private List<TableListener> tableListeners = null;

	public Table(final Class<? extends Task> taskClass,
			final String... columnNames) {
		tableListeners = new ArrayList<TableListener>();
		this.columnNames = new Label[columnNames.length];
		for (int i = 0; i < columnNames.length; i++) {
			this.columnNames[i] = new Label(taskClass).append(columnNames[i]);
		}
		rows = new ArrayList<Row>();
		taskName = taskClass.getSimpleName();
	}

	public void fireTableChanged() {
		for (final TableListener listener : tableListeners) {
			listener.tableChanged();
		}
	}

	public void addTableListener(final TableListener e) {
		tableListeners.add(e);
	}

	public void removeTableListener(final TableListener e) {
		tableListeners.remove(e);
	}

	public String getTaskName() {
		return taskName;
	}

	public boolean hasCommands() {
		for (final Row row : rows) {
			if (row.getCommand() != null) {
				return true;
			}
		}
		return false;
	}

	public TaskCommand getCommand(final int row) {
		return rows.get(row).getCommand();

	}

	public Label getColumnName(final int column) {
		return columnNames[column];
	}

	public int getRowCount() {
		return rows.size();
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public void addRow(final Object... rowData) {
		rows.add(new Row(rowData));
		fireTableChanged();
	}

	public void addRowWithCommand(final TaskCommand command,
			final Object... rowData) {
		if (command == null) {
			throw new IllegalArgumentException("TaskCommand must not be null");
		}
		rows.add(new Row(command, rowData));
		fireTableChanged();
	}

	public void setValueAt(final Object value, final int row, final int column) {
		rows.get(row).setRowData(column, value);
		fireTableChanged();
	}

	public Object getValueAt(final int row, final int column) {
		return rows.get(row).getRowData(column);
	}
}
