package at.o2xfs.xfs.service.ptr.cmd;

import java.util.List;

import at.o2xfs.xfs.ptr.PTRImageStruct;
import at.o2xfs.xfs.service.cmd.event.CompleteEvent;

public class ReadImageCompleteEvent implements CompleteEvent {

	private final List<PTRImageStruct> images;

	public ReadImageCompleteEvent(List<PTRImageStruct> images) {
		this.images = images;
	}

	public List<PTRImageStruct> getImages() {
		return images;
	}
}