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

package at.o2xfs.operator.ui.swing;

import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JScrollBar;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.operator.ui.swing.menu.DownAction;
import at.o2xfs.operator.ui.swing.menu.MenuButton;
import at.o2xfs.operator.ui.swing.menu.UpAction;

public class Scroller implements ComponentListener, AdjustmentListener {

	private static final Logger LOG = LoggerFactory.getLogger(Scroller.class);

	private UIFrame uiFrame = null;

	private JScrollBar scrollBar = null;

	private MenuButton upButton = null;

	private MenuButton downButton = null;

	public Scroller(final UIFrame uiFrame) {
		this.uiFrame = uiFrame;
		scrollBar = this.uiFrame.getContentScrollPane().getVerticalScrollBar();
		scrollBar.addComponentListener(this);
		scrollBar.addAdjustmentListener(this);
	}

	private void scrollUp() {
		final int unitIncrement = scrollBar.getUnitIncrement();
		final int value = scrollBar.getValue();
		int newValue = value - unitIncrement;
		if (newValue <= 0) {
			newValue = 0;
		}
		scrollBar.setValue(newValue);
	}

	private void scrollDown() {
		final int unitIncrement = scrollBar.getUnitIncrement();
		final int value = scrollBar.getValue();
		final int maxValue = scrollBar.getMaximum()
				- scrollBar.getVisibleAmount();
		int newValue = value + unitIncrement;
		if (newValue >= maxValue) {
			newValue = maxValue;
		}
		scrollBar.setValue(newValue);
	}

	@Override
	public void adjustmentValueChanged(final AdjustmentEvent e) {
		if (e.getValueIsAdjusting()) {
			return;
		} else if (upButton == null || downButton == null) {
			return;
		}
		System.err.println(e);
		enableOrDisableButtons();
	}

	private void enableOrDisableButtons() {
		final int value = scrollBar.getValue();
		final int maxValue = scrollBar.getMaximum()
				- scrollBar.getVisibleAmount();
		upButton.setEnabled(value > 0);
		downButton.setEnabled(value < maxValue);
	}

	@Override
	public void componentResized(final ComponentEvent e) {
		final int max = scrollBar.getMaximum();
		final int unitIncrement = (max / 100) * 3;
		scrollBar.setUnitIncrement(unitIncrement);
	}

	@Override
	public void componentMoved(final ComponentEvent e) {
		return;
	}

	@Override
	public void componentShown(final ComponentEvent e) {
		upButton = uiFrame.getUpButton();
		upButton.setMenuAction(new UpAction() {

			@Override
			public void actionPerformed() {
				scrollUp();
			}
		});
		downButton = uiFrame.getDownButton();
		downButton.setMenuAction(new DownAction() {

			@Override
			public void actionPerformed() {
				scrollDown();
			}
		});
		enableOrDisableButtons();
	}

	@Override
	public void componentHidden(final ComponentEvent e) {

	}
}