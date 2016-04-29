package at.o2xfs.xfs.service.cdm.xfs3;

import at.o2xfs.xfs.cdm.CdmInfoCommand;
import at.o2xfs.xfs.cdm.v3_30.AllItemsInfo3_30;
import at.o2xfs.xfs.cdm.v3_30.GetAllItemsInfo3_30;
import at.o2xfs.xfs.service.ReflectiveInfoCommand;
import at.o2xfs.xfs.service.cdm.CdmService;

public class GetAllItemsInfoCommand extends ReflectiveInfoCommand<CdmService, CdmInfoCommand, AllItemsInfo3_30> {

	public GetAllItemsInfoCommand(CdmService service, GetAllItemsInfo3_30 getAllItemsInfo) {
		super(service, CdmInfoCommand.GET_ALL_ITEMS_INFO, getAllItemsInfo, AllItemsInfo3_30.class);
	}
}