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

package at.o2xfs.emv.demo.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.JButton;

public abstract class GUIAction implements ActionListener {

	protected final TerminalGUI terminalGUI;

	public GUIAction(TerminalGUI terminalGUI) {
		this.terminalGUI = terminalGUI;
	}

	abstract Set<GUIControl> getGUIControls();

	public void execute() {
		enableControlsAndAddActionListener();
		try {
			doExecute();
		} finally {
			disableControlsAndRemoveActionListener();
			terminalGUI.showPleaseWait();
		}
	}

	abstract void doExecute();

	private void enableControlsAndAddActionListener() {
		for (GUIControl control : getGUIControls()) {
			JButton button = terminalGUI.getControl(control);
			button.setEnabled(true);
			button.addActionListener(this);
		}
	}

	private void disableControlsAndRemoveActionListener() {
		for (GUIControl control : getGUIControls()) {
			JButton button = terminalGUI.getControl(control);
			button.setEnabled(false);
			button.removeActionListener(this);
		}
	}

	protected GUIControl getControlByActionEvent(ActionEvent e) {
		for (GUIControl control : GUIControl.values()) {
			if (terminalGUI.getControl(control) == e.getSource()) {
				return control;
			}
		}
		return null;
	}
}