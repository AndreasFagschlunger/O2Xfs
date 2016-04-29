package at.o2xfs.operator.ui.content.text;

import java.util.ArrayList;
import java.util.List;

import at.o2xfs.operator.ui.UIComponent;
import at.o2xfs.operator.ui.events.ModifyListener;

public class InputText implements UIComponent {

	private String value;

	private final List<ModifyListener> modifyListeners;

	public InputText() {
		value = null;
		modifyListeners = new ArrayList<>();
	}

	public void addModifyListener(ModifyListener listener) {
		modifyListeners.add(listener);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
		fireModifyText();
	}

	private void fireModifyText() {
		for (ModifyListener each : modifyListeners) {
			each.onModifyText();
		}
	}
}