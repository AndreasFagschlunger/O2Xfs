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
import java.util.List;
import java.util.Set;

public class SelectAction<E> extends GUIAction {

	private List<Item<E>> items;

	private boolean wait = true;

	private E selectedItem = null;

	public SelectAction(TerminalGUI terminalGUI, List<Item<E>> items) {
		super(terminalGUI);
		this.items = items;
	}

	@Override
	Set<GUIControl> getGUIControls() {
		Set<GUIControl> controls = EnumSet.of(GUIControl.BUTTON_CANCEL);
		for (int i = 1; i <= items.size(); i++) {
			switch (i) {
				case 1:
					controls.add(GUIControl.BUTTON_1);
					break;
				case 2:
					controls.add(GUIControl.BUTTON_2);
					break;
				case 3:
					controls.add(GUIControl.BUTTON_3);
					break;
				case 4:
					controls.add(GUIControl.BUTTON_4);
					break;
				case 5:
					controls.add(GUIControl.BUTTON_5);
					break;
				case 6:
					controls.add(GUIControl.BUTTON_6);
					break;
				case 7:
					controls.add(GUIControl.BUTTON_7);
					break;
				case 8:
					controls.add(GUIControl.BUTTON_8);
					break;
				case 9:
					controls.add(GUIControl.BUTTON_9);
					break;
			}
		}
		return controls;
	}

	@Override
	void doExecute() {
		terminalGUI.print("Please select:\n");
		for (int i = 0; i < items.size(); i++) {
			terminalGUI.append((i + 1) + ") " + items.get(i).getName() + "\n");
		}
		synchronized (this) {
			try {
				wait = true;
				while (wait) {
					wait();
				}
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public E getSelectedItem() {
		return selectedItem;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (getControlByActionEvent(e)) {
			case BUTTON_CANCEL:
				selectItem(-1);
				break;
			case BUTTON_1:
				selectItem(0);
				break;
			case BUTTON_2:
				selectItem(1);
				break;
			case BUTTON_3:
				selectItem(2);
				break;
			case BUTTON_4:
				selectItem(3);
				break;
			case BUTTON_5:
				selectItem(4);
				break;
			case BUTTON_6:
				selectItem(5);
				break;
			case BUTTON_7:
				selectItem(6);
				break;
			case BUTTON_8:
				selectItem(7);
				break;
			case BUTTON_9:
				selectItem(8);
				break;
			default:
				return;
		}
	}

	private synchronized void selectItem(int index) {
		if (index != -1) {
			selectedItem = items.get(index).getItem();
		}
		wait = false;
		notifyAll();
	}

}