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

package at.o2xfs.operator.task.xfs.cdm;

import at.o2xfs.operator.ui.UIContent;
import at.o2xfs.operator.ui.content.table.Table;
import at.o2xfs.operator.ui.content.text.InputText;
import at.o2xfs.operator.ui.content.text.Label;
import at.o2xfs.operator.ui.convert.ConverterException;
import at.o2xfs.operator.ui.convert.LongConverter;
import at.o2xfs.operator.ui.events.ModifyListener;
import at.o2xfs.operator.ui.validator.LongRangeValidator;
import at.o2xfs.operator.ui.validator.ValidatorException;
import at.o2xfs.xfs.v3_00.cdm.CashUnit3;
import at.o2xfs.xfs.v3_00.cdm.CurrencyExp3;

public class DispenseWizardPage implements ModifyListener {

	private DispenseWizard wizard;
	private boolean pageComplete;

	private UIContent uiContent;

	private final CashUnit3 cashUnit;
	private final CurrencyExp3 currencyExp;
	private final InputText inputText;
	private Long value = null;

	public DispenseWizardPage(CashUnit3 cashUnit, CurrencyExp3 currencyExp) {
		this.cashUnit = cashUnit;
		this.currencyExp = currencyExp;
		inputText = new InputText();
		inputText.addModifyListener(this);
	}

	public DispenseWizard getWizard() {
		return wizard;
	}

	void setWizard(DispenseWizard wizard) {
		this.wizard = wizard;
	}

	public CashUnit3 getCashUnit() {
		return cashUnit;
	}

	public Long getValue() {
		return value;
	}

	public void createControl(UIContent uiContent) {
		this.uiContent = uiContent;
		Table table = new Table("Key", "Value");
		table.addRow(new Label("Number"), cashUnit.getNumber());
		table.addRow(new Label("Name"), cashUnit.getCashUnitName());
		table.addRow(new Label("Unit ID"), new String(cashUnit.getUnitID()));
		table.addRow(new Label("Currency ID"), new String(cashUnit.getCurrencyID()));
		table.addRow(new Label("Value"), formatValue());
		table.addRow(new Label("Count"), cashUnit.getCount());
		uiContent.set(table, inputText);
		setPageComplete(validatePage());
	}

	private String formatValue() {
		String result = cashUnit.getValue() + " * 10^" + currencyExp.getExponent();
		return result;
	}

	public boolean isPageComplete() {
		return pageComplete;
	}

	public void setPageComplete(boolean pageComplete) {
		this.pageComplete = pageComplete;
		getWizard().updateButtons();
	}

	@Override
	public void onModifyText() {
		setPageComplete(validatePage());
	}

	private boolean validatePage() {
		boolean result = true;
		try {
			Long convertedValue = ((Long) LongConverter.INSTANCE.getAsObject(inputText.getValue()));
			if (convertedValue != null) {
				new LongRangeValidator(0L, cashUnit.getCount()).validate(convertedValue);
			}
			uiContent.clearMessage(inputText);
			value = convertedValue;
		} catch (ConverterException e) {
			uiContent.setMessage(inputText, e.getUIMessage());
			result = false;
		} catch (ValidatorException e) {
			uiContent.setMessage(inputText, e.getUIMessage());
			result = false;
		}
		return result;
	}
}
