package at.o2xfs.xfs.service.cam;

import at.o2xfs.xfs.service.EmptyCompleteEvent;
import at.o2xfs.xfs.service.cmd.event.CommandListener;

public interface TakePictureListener
		extends CommandListener<EmptyCompleteEvent> {

	public void onInvalidData();
}
