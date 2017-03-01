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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import at.o2xfs.emv.Candidate;
import at.o2xfs.emv.util.CandidateComparator;
import at.o2xfs.emv.util.StandardMessage;
import at.o2xfs.emv.util.StandardMessages;

public class TerminalGUI implements UserInterface {

	static {
		JFrame.setDefaultLookAndFeelDecorated(true);
	}

	private JFrame frame = null;

	private JTextArea textArea = null;

	private final Map<GUIControl, JButton> controls;

	public TerminalGUI() {
		controls = new HashMap<GUIControl, JButton>();
		initComponents();
	}

	private void initComponents() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				close();
			}
		});
		frame.setResizable(false);
		frame.setUndecorated(true);
		frame.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridwidth = 4;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets.left = 5;
		gbc.insets.right = 5;
		gbc.insets.top = 5;
		gbc.weightx = 1.0d;
		gbc.weighty = 1.0d;

		textArea = new JTextArea();
		textArea.setEditable(false);
		frame.add(new JScrollPane(textArea), gbc);

		gbc.gridx = 0;
		gbc.gridy++;
		gbc.gridwidth = 1;
		gbc.weightx = 0.0d;
		gbc.weighty = 0.0d;
		JButton button = new JButton("1");
		button.setFocusPainted(false);
		frame.add(button, gbc);
		controls.put(GUIControl.BUTTON_1, button);

		gbc.gridx++;
		button = new JButton("2");
		button.setFocusPainted(false);
		frame.add(button, gbc);
		controls.put(GUIControl.BUTTON_2, button);

		gbc.gridx++;
		button = new JButton("3");
		button.setFocusPainted(false);
		frame.add(button, gbc);
		controls.put(GUIControl.BUTTON_3, button);

		gbc.gridx++;
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setFocusPainted(false);
		frame.add(cancelButton, gbc);
		controls.put(GUIControl.BUTTON_CANCEL, cancelButton);

		gbc.gridx = 0;
		gbc.gridy++;
		button = new JButton("4");
		button.setFocusPainted(false);
		frame.add(button, gbc);
		controls.put(GUIControl.BUTTON_4, button);

		gbc.gridx++;
		button = new JButton("5");
		button.setFocusPainted(false);
		frame.add(button, gbc);
		controls.put(GUIControl.BUTTON_5, button);

		gbc.gridx++;
		button = new JButton("6");
		button.setFocusPainted(false);
		frame.add(button, gbc);
		controls.put(GUIControl.BUTTON_6, button);

		gbc.gridx++;
		JButton clearButton = new JButton("Clear");
		clearButton.setFocusPainted(false);
		controls.put(GUIControl.BUTTON_CLEAR, clearButton);

		frame.add(clearButton, gbc);

		gbc.gridx = 0;
		gbc.gridy++;
		button = new JButton("7");
		button.setFocusPainted(false);
		frame.add(button, gbc);
		controls.put(GUIControl.BUTTON_7, button);

		gbc.gridx++;
		button = new JButton("8");
		button.setFocusPainted(false);
		frame.add(button, gbc);
		controls.put(GUIControl.BUTTON_8, button);

		gbc.gridx++;
		button = new JButton("9");
		button.setFocusPainted(false);
		frame.add(button, gbc);
		controls.put(GUIControl.BUTTON_9, button);

		gbc.gridx = 1;
		gbc.gridy++;
		gbc.insets.bottom = 5;
		button = new JButton("0");
		button.setFocusPainted(false);
		frame.add(button, gbc);
		controls.put(GUIControl.BUTTON_0, button);

		gbc.gridx = 3;
		JButton enterButton = new JButton("Enter");
		enterButton.setFocusPainted(false);
		frame.add(enterButton, gbc);
		controls.put(GUIControl.BUTTON_OK, enterButton);

		frame.setSize(300, 250);
		frame.setLocationRelativeTo(null);
		frame.requestFocus();
	}

	private void close() {
		frame.setVisible(false);
		frame.dispose();
	}

	public void disableControls() {
		for (JButton button : controls.values()) {
			button.setEnabled(false);
		}
	}

	public JButton getControl(GUIControl control) {
		return controls.get(control);
	}

	public void print(String s) {
		textArea.setText(s);
	}

	public void append(String s) {
		textArea.append(s);
	}

	public void showPleaseWait() {
		print(StandardMessages.PLEASE_WAIT);
	}

	public void terminate(StandardMessage message) {
		new ShowMessage(this, message).execute();
		close();
	}

	@Override
	public void print(StandardMessage message) {
		print(StandardMessages.getMessage(message));
	}

	private boolean confirmCandidate(Candidate candidate) {
		ConfirmAction confirmAction = new ConfirmAction(this, "Proceed with " + getCandidateName(candidate) + "?");
		confirmAction.execute();
		return confirmAction.isConfirmed();
	}

	private String getCandidateName(Candidate candidate) {
		if (candidate.getApplicationPreferredName().length > 0 && candidate.getIssuerCodeTableIndex() != null) {
			return new String(candidate.getApplicationPreferredName(), Charset.forName("ISO-8859-" + candidate.getIssuerCodeTableIndex()));

		}
		return candidate.getLabel();
	}

	public Candidate selectCandidate(List<Candidate> candidates) {
		Candidate result = null;
		if (candidates.isEmpty()) {
			terminate(StandardMessages.NOT_ACCEPTED);
		} else if (candidates.size() == 1) {
			Candidate candidate = candidates.get(0);
			if (!candidate.isConfirmationRequired() || confirmCandidate(candidate)) {
				result = candidate;
			}
		} else {
			Candidate candidate = chooseCandidate(candidates);
			if (candidate != null) {
				result = candidate;
			}
		}
		return result;
	}

	private Candidate chooseCandidate(List<Candidate> candidates) {
		Collections.sort(candidates, new CandidateComparator());
		List<Item<Candidate>> items = new ArrayList<Item<Candidate>>();
		for (Candidate candidate : candidates) {
			items.add(new Item<Candidate>(getCandidateName(candidate), candidate));
		}
		do {
			SelectAction<Candidate> selectAction = new SelectAction<Candidate>(this, items);
			selectAction.execute();
			Candidate candidate = selectAction.getSelectedItem();
			if (candidate == null) {
				break;
			}
			if (confirmCandidate(candidate)) {
				return candidate;
			}
		} while (true);
		return null;
	}

	@Override
	public String performPinEntry() {
		PromptForPINAction promptForPINAction = new PromptForPINAction(this);
		promptForPINAction.execute();
		if (!promptForPINAction.isCancelled()) {
			return promptForPINAction.getPIN();
		}
		return null;
	}

	public void show() {
		disableControls();
		showPleaseWait();
		frame.setVisible(true);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new TerminalGUI().show();
	}
}