package at.o2xfs.xfs.service.cam;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.xfs.WFSResult;
import at.o2xfs.xfs.XfsServiceClass;
import at.o2xfs.xfs.XfsWord;
import at.o2xfs.xfs.cam.CamEventId;
import at.o2xfs.xfs.cam.Media;
import at.o2xfs.xfs.service.XfsService;
import at.o2xfs.xfs.service.XfsServiceManager;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CamService
		extends XfsService {

	private static final Logger LOG = LoggerFactory.getLogger(CamService.class);

	private final List<CamServiceListener> listeners;

	protected CamService(String logicalName) {
		super(logicalName, XfsServiceClass.CAM);
		listeners = new CopyOnWriteArrayList<>();
	}

	public void add(CamServiceListener l) {
		listeners.add(l);
	}

	public void remove(CamServiceListener l) {
		listeners.remove(l);
	}

	@Override
	public void fireServiceEvent(WFSResult wfsResult) {

	}

	@Override
	public void fireUserEvent(WFSResult wfsResult) {
		final String method = "fireUserEvent(WFSResult)";
		try {
			if (LOG.isDebugEnabled()) {
				LOG.debug(method, "wfsResult=" + wfsResult);
			}
			CamEventId eventId = wfsResult.getEventID(CamEventId.class);
			switch (eventId) {
				case MEDIATHRESHOLD:
					XfsWord<Media> threshold = new XfsWord<>(Media.class, wfsResult.getResults());
					notifyMediaThreshold(threshold.get());
					break;
				default:
					LOG.error(method, "Illegal CamEventId: " + eventId);

			}
		} finally {
			XfsServiceManager.getInstance().free(wfsResult);
		}
	}

	private void notifyMediaThreshold(Media threshold) {
		for (CamServiceListener each : listeners) {
			each.onMediaThreshold(threshold);
		}
	}
}