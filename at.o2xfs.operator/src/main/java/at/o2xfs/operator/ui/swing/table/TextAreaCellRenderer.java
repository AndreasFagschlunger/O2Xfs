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

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.text.View;

public class TextAreaCellRenderer extends DefaultTableCellRenderer {

	private JTextArea textArea = null;

	public TextAreaCellRenderer() {
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(false);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		final Component template = super.getTableCellRendererComponent(table,
				value, isSelected, hasFocus, row, column);
		textArea.setForeground(template.getForeground());
		textArea.setBackground(template.getBackground());
		textArea.setBorder(((JLabel) template).getBorder());
		textArea.setText(String.valueOf(value));
		final TableColumn tableColumn = table.getColumnModel()
				.getColumn(column);
		final View view = textArea.getUI().getRootView(textArea);
		view.setSize(tableColumn.getWidth(), 0);
		final int rowHeight = (int) view.getPreferredSpan(View.Y_AXIS)
				+ (table.getIntercellSpacing().height * 2);
		if (table.getRowHeight(row) != rowHeight) {
			table.setRowHeight(row, rowHeight);
		}
		return textArea;
	}

}