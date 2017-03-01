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

package at.o2xfs.emv.demo.pinpad;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPasswordField;

public class PINPadGUI {

	static {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
	}

	private JDialog frame = null;

	private JPasswordField pinField = null;

	private boolean cancelled = false;

	private boolean entered = false;

	public PINPadGUI() {
		initComponents();
	}

	private void initComponents() {
		frame = new JDialog();
		frame.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				cancel();
			}
		});
		frame.setResizable(false);
		frame.setUndecorated(true);
		frame.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 4;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets.left = 5;
		gbc.insets.right = 5;
		gbc.insets.top = 5;
		pinField = new JPasswordField();
		pinField.setEditable(false);
		frame.add(pinField, gbc);

		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy++;
		frame.add(newNumberButton("1"), gbc);

		gbc.gridx++;
		frame.add(newNumberButton("2"), gbc);

		gbc.gridx++;
		frame.add(newNumberButton("3"), gbc);

		gbc.gridx++;
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setFocusPainted(false);
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cancel();
			}
		});
		frame.add(cancelButton, gbc);

		gbc.gridx = 0;
		gbc.gridy++;
		frame.add(newNumberButton("4"), gbc);

		gbc.gridx++;
		frame.add(newNumberButton("5"), gbc);

		gbc.gridx++;
		frame.add(newNumberButton("6"), gbc);

		gbc.gridx++;
		JButton clearButton = new JButton("Clear");
		clearButton.setFocusPainted(false);
		clearButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				pinField.setText("");
			}
		});
		frame.add(clearButton, gbc);

		gbc.gridx = 0;
		gbc.gridy++;
		frame.add(newNumberButton("7"), gbc);

		gbc.gridx++;
		frame.add(newNumberButton("8"), gbc);

		gbc.gridx++;
		frame.add(newNumberButton("9"), gbc);

		gbc.gridx = 1;
		gbc.gridy++;
		gbc.insets.bottom = 5;
		frame.add(newNumberButton("0"), gbc);

		gbc.gridx = 3;
		JButton enterButton = new JButton("Enter");
		enterButton.setFocusPainted(false);
		enterButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				enter();
			}
		});
		frame.add(enterButton, gbc);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.requestFocus();
	}

	private void cancel() {
		synchronized (this) {
			cancelled = true;
			notifyAll();
		}
	}

	private void enter() {
		synchronized (this) {
			entered = true;
			notifyAll();
		}
	}

	public String getPIN() {
		try {
			synchronized (this) {
				cancelled = false;
				entered = false;
				frame.setVisible(true);
				while (cancelled == false && entered == false) {
					wait();
				}
				frame.setVisible(false);
				frame.dispose();
				if (entered) {
					return new String(pinField.getPassword());
				}
			}
			return null;
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	private JButton newNumberButton(final String number) {
		JButton button = new JButton(number);
		button.setFocusPainted(false);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String pin = new String(pinField.getPassword());
				pin += number;
				pinField.setText(pin);
			}
		});
		return button;
	}

	public static final void main(String[] args) throws Exception {
		PINPadGUI pinPadGUI = new PINPadGUI();
		String pin = pinPadGUI.getPIN();
		System.out.println("PIN: " + pin);
	}
}