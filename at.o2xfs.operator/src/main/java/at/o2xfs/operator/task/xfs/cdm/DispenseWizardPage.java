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
import at.o2xfs.xfs.cdm.v3_00.CashUnit3;
import at.o2xfs.xfs.cdm.v3_00.CurrencyExp3;

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