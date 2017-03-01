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

import javax.swing.table.AbstractTableModel;

import at.o2xfs.common.Assert;
import at.o2xfs.operator.ui.content.table.Table;
import at.o2xfs.operator.ui.content.table.TableListener;
import at.o2xfs.operator.ui.swing.i18n.Messages;

public class ContentTableModel extends AbstractTableModel implements
		TableListener {

	private final Table model;

	public ContentTableModel(final Table model) {
		Assert.notNull(model);
		this.model = model;
		model.addTableListener(this);
	}

	@Override
	public String getColumnName(final int column) {
		return Messages.getText(model.getColumnName(column));
	}

	@Override
	public int getRowCount() {
		return model.getRowCount();
	}

	@Override
	public int getColumnCount() {
		return model.getColumnCount();
	}

	@Override
	public Object getValueAt(final int rowIndex, final int columnIndex) {
		Object result = model.getValueAt(rowIndex, columnIndex);
		return result;
	}

	@Override
	public void tableChanged() {
		fireTableDataChanged();
	}
}