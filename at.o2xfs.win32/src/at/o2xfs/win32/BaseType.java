package at.o2xfs.win32;

public class BaseType extends Type {

	private final int size;

	protected BaseType(int size) {
		this.size = size;
	}

	@Override
	public final int getSize() {
		return size;
	}
}