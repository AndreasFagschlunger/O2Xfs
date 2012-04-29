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

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.xfs.XfsException;
import at.o2xfs.xfs.service.cmd.XfsCommand;

public class EjectingCardState extends EjectCardState {

	private final static Logger LOG = LoggerFactory
			.getLogger(EjectingCardState.class);

	private XfsCommand xfsCommand = null;

	protected EjectingCardState(final EjectCardCommand context,
			final XfsCommand xfsCommand) {
		super(context);
		this.xfsCommand = xfsCommand;
	}

	private void notifyCardPresented() {
		if (LOG.isDebugEnabled()) {
			final String method = "notifyCardPresented()";
			LOG.debug(method, "Notifying ...");
		}
		for (final EjectCardListener listener : context.getEjectCardListeners()) {
			listener.cardPresented();
		}
	}

	@Override
	public void cardPresented() {
		notifyCardPresented();
		context.setState(new CardPresentedState(context));
	}

	@Override
	public void cardTaken() {
		cardPresented();
		context.cardTaken();
	}

	@Override
	public void cancel() {
		try {
			xfsCommand.cancel();
		} catch (final XfsException e) {
			final String method = "cancel()";
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "Error cancelling XfsCommand", e);
			}
		}
	}

	@Override
	public void cancelled() {
		removeIDCServiceListener();
		notifyCommandCancelled();
		context.setState(new CardPresentState(context));
	}

	@Override
	void failed(final XfsException e) {
		removeIDCServiceListener();
		notifyCommandFailed(e);
	}

}
