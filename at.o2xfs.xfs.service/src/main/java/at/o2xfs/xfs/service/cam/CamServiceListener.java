package at.o2xfs.xfs.service.cam;

import at.o2xfs.xfs.cam.Media;

public interface CamServiceListener {

	public void onMediaThreshold(Media threshold);
}
