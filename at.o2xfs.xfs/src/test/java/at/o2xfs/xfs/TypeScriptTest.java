package at.o2xfs.xfs;

import at.o2xfs.xfs.cdm.CdmDeviceState;

public class TypeScriptTest {

	public static void main(String[] args) {
		for (XfsServiceClass each : XfsServiceClass.values()) {
			System.out.println("export const " + each.name() + ": ServiceClass = {id: " + each.getValue() + ", name: '" + each.getName() + "'};");
		}

		Class<? extends Enum<? extends XfsConstant>> clazz = CdmDeviceState.class;
		System.out.println("enum " + clazz.getSimpleName() + " {");
		Enum<? extends XfsConstant> enums[] = clazz.getEnumConstants();
		int iMax = enums.length - 1;
		for (int i = 0; iMax != -1; i++) {
			System.out.print("    " + enums[i].name() + " = " + ((XfsConstant) enums[i]).getValue());
			if (i == iMax) {
				System.out.println();
				break;
			}
			System.out.println(",");
		}
		System.out.println("}");
	}

}
