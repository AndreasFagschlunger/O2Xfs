package at.o2xfs.xfs.service.cdm.xfs3;

import at.o2xfs.xfs.cdm.CdmInfoCommand;
import at.o2xfs.xfs.cdm.v3_30.GetItemInfo3_30;
import at.o2xfs.xfs.cdm.v3_30.ItemInfo3_30;
import at.o2xfs.xfs.service.ReflectiveInfoCommand;
import at.o2xfs.xfs.service.cdm.CdmService;

public class GetItemInfoCommand extends ReflectiveInfoCommand<CdmService, CdmInfoCommand, ItemInfo3_30> {

	public GetItemInfoCommand(CdmService service, GetItemInfo3_30 getItemInfo) {
		super(service, CdmInfoCommand.GET_ITEM_INFO, getItemInfo, ItemInfo3_30.class);
	}
}