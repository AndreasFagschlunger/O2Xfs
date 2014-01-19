package at.o2xfs.xfs.service;

public class ServiceNotFoundException extends RuntimeException {

	protected <E extends XfsService> ServiceNotFoundException(
			Class<E> serviceClass) {
		super("Class: " + serviceClass);
	}
}
