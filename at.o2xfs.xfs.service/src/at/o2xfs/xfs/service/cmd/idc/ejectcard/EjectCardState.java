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

import at.o2xfs.xfs.XfsException;

abstract class EjectCardState {

	protected EjectCardCommand context = null;

	protected EjectCardState(final EjectCardCommand context) {
		this.context = context;
	}

	protected void removeIDCServiceListener() {
		context.getIDCService().removeIDCServiceListener(context);
	}

	protected void notifyCommandSuccessful() {
		for (final EjectCardListener listener : context.getEjectCardListeners()) {
			listener.commandSuccessful();
		}
	}

	protected void notifyCommandCancelled() {
		for (final EjectCardListener listener : context.getEjectCardListeners()) {
			listener.commandCancelled();
		}
	}

	protected void notifyCommandFailed(final XfsException e) {
		for (final EjectCardListener listener : context.getEjectCardListeners()) {
			listener.commandFailed(e);
		}
	}

	void ejectCard() {
		throw new IllegalStateException();
	}

	void cardPresented() {
		throw new IllegalStateException();
	}

	void takeCard() {
		throw new IllegalStateException();
	}

	void cardTaken() {
		throw new IllegalStateException();
	}

	void cardRetainWarning() {
		throw new IllegalStateException();
	}

	void retainCard() {
		throw new IllegalStateException();
	}

	void cardRetained() {
		throw new IllegalStateException();
	}

	void cancel() {
		throw new IllegalStateException();
	}

	void cancelled() {
		throw new IllegalStateException();
	}

	void failed(final XfsException e) {
		throw new IllegalStateException();
	}
}
