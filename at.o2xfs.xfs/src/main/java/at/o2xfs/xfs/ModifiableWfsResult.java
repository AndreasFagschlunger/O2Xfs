package at.o2xfs.xfs;

import java.time.LocalDateTime;

import at.o2xfs.win32.BufferFactory;
import at.o2xfs.win32.DWORD;
import at.o2xfs.win32.Type;

public class ModifiableWfsResult extends WFSResult {

	public ModifiableWfsResult(BufferFactory bufferFactory) {
		super();
		assignBuffer(bufferFactory.createBuffer(getSize()));
	}

	public ModifiableWfsResult requestId(long requestId) {
		this.requestID.set(requestId);
		return this;
	}

	public ModifiableWfsResult service(int service) {
		this.service.set(service);
		return this;
	}

	public ModifiableWfsResult timestamp(LocalDateTime timestamp) {
		this.timestamp.set(timestamp);
		return this;
	}

	public ModifiableWfsResult errorCode(int errorCode) {
		this.result.set(errorCode);
		return this;
	}

	public ModifiableWfsResult commandCode(long commandCode) {
		this.u.get(COMMANDCODE, DWORD.class).set(commandCode);
		return this;
	}

	public ModifiableWfsResult eventId(long eventId) {
		this.u.get(COMMANDCODE, DWORD.class).set(eventId);
		return this;
	}

	public ModifiableWfsResult buffer(Type buffer) {
		this.buffer.pointTo(buffer);
		return this;
	}
}
