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

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import at.o2xfs.operator.task.TaskCommand;
import at.o2xfs.operator.ui.content.table.Table;
import at.o2xfs.operator.ui.content.table.TableListener;
import at.o2xfs.operator.ui.swing.i18n.Messages;
import at.o2xfs.operator.ui.swing.menu.MenuAction;
import at.o2xfs.operator.ui.swing.menu.MenuButton;
import at.o2xfs.operator.ui.swing.menu.TaskMenuAction;

public class RowSelector implements ListSelectionListener, TableListener {

	private JTable table = null;

	private Table model = null;

	private MenuButton upButton = null;

	private MenuButton downButton = null;

	private MenuButton confirmButton = null;

	class UpAction implements MenuAction {

		@Override
		public String getText() {
			return Messages.getText("Up");
		}

		@Override
		public void actionPerformed() {
			selectRow(table.getSelectedRow() - 1);
		}
	}

	class DownAction implements MenuAction {

		@Override
		public String getText() {
			return Messages.getText("Down");
		}

		@Override
		public void actionPerformed() {
			selectRow(table.getSelectedRow() + 1);
		}
	}

	public RowSelector(final JTable table, final Table model,
			final MenuButton upButton, final MenuButton downButton,
			final MenuButton confirmButton) {
		this.table = table;
		this.model = model;
		this.upButton = upButton;
		this.downButton = downButton;
		this.confirmButton = confirmButton;
		this.model.addTableListener(this);
		initComponents();
		updateCommands();
	}

	private void initComponents() {
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getSelectionModel().addListSelectionListener(this);
	}

	private void updateCommands() {
		table.setRowSelectionAllowed(model.hasCommands());
		if (model.hasCommands()) {
			if (table.getSelectedRow() == -1) {
				selectRow(0);
			}
			int selectedRow = table.getSelectedRow();
			upButton.setMenuAction(new UpAction());
			downButton.setMenuAction(new DownAction());
			upButton.setEnabled(selectedRow >= 1);
			downButton.setEnabled(selectedRow < table.getRowCount() - 1);
			TaskCommand taskCommand = model.getCommand(selectedRow);
			if (taskCommand != null) {
				confirmButton.setMenuAction(new TaskMenuAction(model
						.getCommand(selectedRow)));
			} else {
				confirmButton.removeMenuAction();
			}
		}
	}

	private void selectRow(final int index) {
		table.setRowSelectionInterval(index, index);
	}

	@Override
	public void tableChanged() {
		updateCommands();
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting()) {
			return;
		}
		updateCommands();
	}
}