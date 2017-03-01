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
import java.util.EnumSet;
import java.util.Set;

import at.o2xfs.emv.util.StandardMessages;

public class PromptForPINAction extends GUIAction {

	private final StringBuilder pin;

	private boolean finished = false;

	private boolean cancelled = false;

	public PromptForPINAction(TerminalGUI terminalUI) {
		super(terminalUI);
		pin = new StringBuilder();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (getControlByActionEvent(e)) {
			case BUTTON_0:
				addDigit('0');
				break;
			case BUTTON_1:
				addDigit('1');
				break;
			case BUTTON_2:
				addDigit('2');
				break;
			case BUTTON_3:
				addDigit('3');
				break;
			case BUTTON_4:
				addDigit('4');
				break;
			case BUTTON_5:
				addDigit('5');
				break;
			case BUTTON_6:
				addDigit('6');
				break;
			case BUTTON_7:
				addDigit('7');
				break;
			case BUTTON_8:
				addDigit('8');
				break;
			case BUTTON_9:
				addDigit('9');
				break;
			case BUTTON_CANCEL:
				cancel();
				break;
			case BUTTON_CLEAR:
				clear();
				break;
			case BUTTON_OK:
				enter();
				break;

		}
	}

	private void addDigit(char c) {
		pin.append(c);
		printEnterPINMessage();
	}

	private void clear() {
		pin.delete(0, pin.length());
		printEnterPINMessage();
	}

	private void printEnterPINMessage() {
		terminalGUI.print(StandardMessages.ENTER_PIN);
		terminalGUI.append("\n");
		for (int i = 0; i < pin.length(); i++) {
			terminalGUI.append("*");
		}
	}

	private void cancel() {
		synchronized (this) {
			clear();
			cancelled = true;
			finished = true;
			notifyAll();
		}
	}

	private void enter() {
		synchronized (this) {
			finished = true;
			notifyAll();
		}
	}

	@Override
	Set<GUIControl> getGUIControls() {
		return EnumSet.allOf(GUIControl.class);
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public String getPIN() {
		if (cancelled) {
			throw new IllegalStateException("PIN entry was cancelled");
		}
		return pin.toString();
	}

	@Override
	void doExecute() {
		try {
			printEnterPINMessage();
			synchronized (this) {
				while (!finished) {
					wait();
				}
			}
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

}