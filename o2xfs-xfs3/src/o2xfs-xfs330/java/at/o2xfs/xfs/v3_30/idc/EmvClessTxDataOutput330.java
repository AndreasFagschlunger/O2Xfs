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

package at.o2xfs.xfs.v3_30.idc;

import java.util.Optional;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.xfs.idc.CardholderAction;
import at.o2xfs.xfs.idc.DataSource;
import at.o2xfs.xfs.idc.TxOutcome;
import at.o2xfs.xfs.win32.XfsWord;

public class EmvClessTxDataOutput330 extends Struct {

	protected final XfsWord<DataSource> dataSource = new XfsWord<>(DataSource.class);
	protected final XfsWord<TxOutcome> txOutcome = new XfsWord<>(TxOutcome.class);
	protected final XfsWord<CardholderAction> cardholderAction = new XfsWord<>(CardholderAction.class);
	protected final Pointer dataRead = new Pointer();
	protected final Pointer clessOutcome = new Pointer();

	protected EmvClessTxDataOutput330() {
		add(dataSource);
		add(txOutcome);
		add(cardholderAction);
		add(dataRead);
		add(clessOutcome);
	}

	public EmvClessTxDataOutput330(Pointer p) {
		this();
		assignBuffer(p);
	}

	public EmvClessTxDataOutput330(EmvClessTxDataOutput330 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(EmvClessTxDataOutput330 copy) {
		dataSource.set(copy.getDataSource());
		txOutcome.set(copy.getTxOutcome());
		cardholderAction.set(copy.getCardholderAction());
		dataRead.pointTo(new HexData330(copy.getDataRead()));
		Optional<EmvClessOutcome330> clessOutcome = copy.getClessOutcome();
		if (clessOutcome.isPresent()) {
			this.clessOutcome.pointTo(new EmvClessOutcome330(clessOutcome.get()));
		}
	}

	public DataSource getDataSource() {
		return dataSource.get();
	}

	public TxOutcome getTxOutcome() {
		return txOutcome.get();
	}

	public CardholderAction getCardholderAction() {
		return cardholderAction.get();
	}

	public byte[] getDataRead() {
		return new HexData330(dataRead).getData();
	}

	public Optional<EmvClessOutcome330> getClessOutcome() {
		Optional<EmvClessOutcome330> result = Optional.empty();
		if (!Pointer.NULL.equals(clessOutcome)) {
			result = Optional.of(new EmvClessOutcome330(clessOutcome));
		}
		return result;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(getDataSource())
				.append(getTxOutcome())
				.append(getCardholderAction())
				.append(getDataRead())
				.append(getClessOutcome())
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof EmvClessTxDataOutput330) {
			EmvClessTxDataOutput330 emvClessTxDataOutput330 = (EmvClessTxDataOutput330) obj;
			return new EqualsBuilder()
					.append(getDataSource(), emvClessTxDataOutput330.getDataSource())
					.append(getTxOutcome(), emvClessTxDataOutput330.getTxOutcome())
					.append(getCardholderAction(), emvClessTxDataOutput330.getCardholderAction())
					.append(getDataRead(), emvClessTxDataOutput330.getDataRead())
					.append(getClessOutcome(), emvClessTxDataOutput330.getClessOutcome())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("dataSource", getDataSource())
				.append("txOutcome", getTxOutcome())
				.append("cardholderAction", getCardholderAction())
				.append("dataRead", getDataRead())
				.append("clessOutcome", getClessOutcome())
				.toString();
	}
}
