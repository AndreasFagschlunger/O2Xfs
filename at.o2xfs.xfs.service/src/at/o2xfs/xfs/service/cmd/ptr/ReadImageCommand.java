package at.o2xfs.xfs.service.cmd.ptr;

import java.util.ArrayList;
import java.util.List;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.ZList;
import at.o2xfs.xfs.ptr.PTRExecuteCommand;
import at.o2xfs.xfs.ptr.PTRImageRequestStruct;
import at.o2xfs.xfs.ptr.PTRImageStruct;
import at.o2xfs.xfs.service.cmd.AbstractAsyncXfsCommand;
import at.o2xfs.xfs.service.cmd.XfsCommand;
import at.o2xfs.xfs.service.cmd.XfsExecuteCommand;
import at.o2xfs.xfs.service.ptr.PTRService;

public class ReadImageCommand
		extends
		AbstractAsyncXfsCommand<ReadImageCommandListener, ReadImageCompleteEvent> {

	private static final Logger LOG = LoggerFactory
			.getLogger(ReadImageCommand.class);

	private final PTRService ptrService;

	private final PTRImageRequestStruct imageRequest;

	public ReadImageCommand(PTRService ptrService) {
		this(ptrService, null);
	}

	public ReadImageCommand(PTRService ptrService,
			PTRImageRequestStruct imageRequest) {
		if (ptrService == null) {
			throw new IllegalArgumentException("ptrService must not be null");
		}
		this.ptrService = ptrService;
		this.imageRequest = imageRequest;
	}

	@Override
	protected XfsCommand createCommand() {
		return new XfsExecuteCommand(ptrService, PTRExecuteCommand.READ_IMAGE,
				imageRequest);
	}

	@Override
	protected ReadImageCompleteEvent createCompleteEvent(Pointer results) {
		final String method = "createCompleteEvent(Pointer)";
		List<PTRImageStruct> images = new ArrayList<PTRImageStruct>();
		ZList list = new ZList(results);
		for (Pointer p : list) {
			PTRImageStruct image = new PTRImageStruct(
					ptrService.getXfsVersion(), p);
			if (LOG.isDebugEnabled()) {
				LOG.debug(method, "image=" + image);
			}
			images.add(new PTRImageStruct(ptrService.getXfsVersion(), image));
		}
		return new ReadImageCompleteEvent(images);
	}
}