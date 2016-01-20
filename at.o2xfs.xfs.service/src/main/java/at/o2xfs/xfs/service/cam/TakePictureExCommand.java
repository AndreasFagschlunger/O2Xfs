package at.o2xfs.xfs.service.cam;

import at.o2xfs.xfs.cam.CamExecuteCommand;
import at.o2xfs.xfs.cam.v320.TakepictEx;
import at.o2xfs.xfs.service.cmd.XfsCommand;
import at.o2xfs.xfs.service.cmd.XfsExecuteCommand;

public class TakePictureExCommand
		extends TakePictureCommand {

	public TakePictureExCommand(CamService service, TakepictEx takepict) {
		super(service, takepict);
	}

	@Override
	protected XfsCommand createCommand() {
		return new XfsExecuteCommand<CamExecuteCommand>(service, CamExecuteCommand.TAKE_PICTURE_EX, takepict);
	}
}