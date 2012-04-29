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

package at.o2xfs.xfs.service.cmd.idc.ejectcard;

import java.util.Timer;
import java.util.TimerTask;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.xfs.XfsException;
import at.o2xfs.xfs.idc.IDCExecuteCommand;
import at.o2xfs.xfs.service.cmd.XfsCommand;
import at.o2xfs.xfs.service.cmd.XfsExecuteCommand;

public class CardPresentedState extends EjectCardState {

	private final static Logger LOG = LoggerFactory
			.getLogger(CardPresentState.class);

	private Timer retainTimer = null;

	protected CardPresentedState(final EjectCardCommand context) {
		super(context);
		startTimer();
	}

	private void notifyCardTaken() {
		for (final EjectCardListener listener : context.getEjectCardListeners()) {
			listener.cardTaken();
		}
	}

	private void startTimer() {
		retainTimer = new Timer();
		retainTimer.schedule(new TimerTask() {

			@Override
			public void run() {
				context.cardRetainWarning();
			}
		}, 15000L);
		retainTimer.schedule(new TimerTask() {

			@Override
			public void run() {
				context.retainCard();
			}
		}, 30000L);
	}

	@Override
	void cardPresented() {
		return;
	}

	@Override
	void cardRetainWarning() {
		for (final EjectCardListener listener : context.getEjectCardListeners()) {
			listener.cardRetainWarning();
		}
	}

	@Override
	void retainCard() {
		final String method = "retainCard()";
		final XfsCommand retainCommand = new XfsExecuteCommand(
				context.getIDCService(),
				IDCExecuteCommand.WFS_CMD_IDC_RETAIN_CARD);
		try {
			retainCommand.execute(context);
			context.setState(new RetainingCardState(context));
		} catch (final XfsException e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "Error executing retain card command", e);
			}
			removeIDCServiceListener();
			notifyCommandFailed(e);
		}
	}

	@Override
	void cardTaken() {
		retainTimer.cancel();
		removeIDCServiceListener();
		notifyCardTaken();
		notifyCommandSuccessful();
		context.setState(new CardTakenState(context));
	}

	@Override
	void cancel() {
		return; // ignore cancel request
	}
}
