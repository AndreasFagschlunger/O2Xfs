package at.o2xfs.xfs.service.pin;

import org.junit.Test;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.ULONG;
import at.o2xfs.win32.ZList;
import at.o2xfs.xfs.WFSResult;
import at.o2xfs.xfs.pin.PINInfoCommand;
import at.o2xfs.xfs.pin.WFSPINCAPS;
import at.o2xfs.xfs.pin.WFSPINFUNCKEYDETAIL;
import at.o2xfs.xfs.pin.WFSPINKEYDETAIL;
import at.o2xfs.xfs.pin.WFSPINSTATUS;
import at.o2xfs.xfs.service.XfsCommandTest;
import at.o2xfs.xfs.service.cmd.XfsCommand;
import at.o2xfs.xfs.service.cmd.XfsInfoCommand;

public class PINServiceTest extends XfsCommandTest {

	@Test
	public void pinStatus() throws Exception {
		for (final PINService pinService : xfsServiceManager
				.getServices(PINService.class)) {
			XfsCommand xfsCommand = new XfsInfoCommand(pinService,
					PINInfoCommand.WFS_INF_PIN_STATUS);
			WFSResult wfsResult = null;
			try {
				wfsResult = xfsCommand.call();
				WFSPINSTATUS pinStatus = new WFSPINSTATUS(
						pinService.getXfsVersion(), wfsResult.getResults());
				System.out.println(pinStatus);
			} finally {
				if (wfsResult != null) {
					xfsServiceManager.free(wfsResult);
				}
			}
		}
	}

	@Test
	public void pinCapabilities() throws Exception {
		for (final PINService pinService : xfsServiceManager
				.getServices(PINService.class)) {
			XfsCommand xfsCommand = new XfsInfoCommand(pinService,
					PINInfoCommand.WFS_INF_PIN_CAPABILITIES);
			WFSResult wfsResult = null;
			try {
				wfsResult = xfsCommand.call();
				WFSPINCAPS wfspincaps = new WFSPINCAPS(
						pinService.getXfsVersion(), wfsResult.getResults());
				System.out.println(wfspincaps);
			} finally {
				if (wfsResult != null) {
					xfsServiceManager.free(wfsResult);
				}
			}
		}
	}

	@Test
	public void keyDetail() throws Exception {
		for (final PINService pinService : xfsServiceManager
				.getServices(PINService.class)) {
			final XfsCommand xfsCommand = new XfsInfoCommand(pinService,
					PINInfoCommand.WFS_INF_PIN_KEY_DETAIL);
			WFSResult wfsResult = null;
			try {
				wfsResult = xfsCommand.call();
				ZList pKeyDetails = new ZList(wfsResult.getResults());
				for (Pointer pKeyDetail : pKeyDetails) {
					WFSPINKEYDETAIL keyDetail = new WFSPINKEYDETAIL(
							pinService.getXfsVersion(), pKeyDetail);
					System.out.println(keyDetail);
				}
			} finally {
				if (wfsResult != null) {
					xfsServiceManager.free(wfsResult);
				}
			}
		}
	}

	@Test
	public void funcKeyDetail() throws Exception {
		for (final PINService pinService : xfsServiceManager
				.getServices(PINService.class)) {
			final ULONG fdkMask = new ULONG();
			fdkMask.put(0xFFFFFFFFL);
			XfsCommand xfsCommand = new XfsInfoCommand(pinService,
					PINInfoCommand.WFS_INF_PIN_FUNCKEY_DETAIL, fdkMask);
			WFSResult wfsResult = null;
			try {
				wfsResult = xfsCommand.call();
				WFSPINFUNCKEYDETAIL funcKeyDetail = new WFSPINFUNCKEYDETAIL(
						wfsResult.getResults());
				System.out.println(funcKeyDetail);
			} finally {
				if (wfsResult != null) {
					xfsServiceManager.free(wfsResult);
				}
			}
		}
	}
}
