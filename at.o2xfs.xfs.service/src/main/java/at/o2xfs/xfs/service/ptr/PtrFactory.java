/*
 * Copyright (c) 2018, Andreas Fagschlunger. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package at.o2xfs.xfs.service.ptr;

import at.o2xfs.win32.Pointer;
import at.o2xfs.xfs.XfsVersion;
import at.o2xfs.xfs.v3_00.ptr.BinThreshold3;
import at.o2xfs.xfs.v3_00.ptr.FieldFailure3;
import at.o2xfs.xfs.v3_00.ptr.FormField3;
import at.o2xfs.xfs.v3_00.ptr.FormHeader3;
import at.o2xfs.xfs.v3_00.ptr.FormMedia3;
import at.o2xfs.xfs.v3_00.ptr.Image3;
import at.o2xfs.xfs.v3_00.ptr.ImageRequest3;
import at.o2xfs.xfs.v3_00.ptr.MediaDetected3;
import at.o2xfs.xfs.v3_00.ptr.MediaExt3;
import at.o2xfs.xfs.v3_00.ptr.MediaUnit3;
import at.o2xfs.xfs.v3_00.ptr.PaperThreshold3;
import at.o2xfs.xfs.v3_00.ptr.PrintForm3;
import at.o2xfs.xfs.v3_00.ptr.PtrCapabilities3;
import at.o2xfs.xfs.v3_00.ptr.PtrStatus3;
import at.o2xfs.xfs.v3_00.ptr.QueryField3;
import at.o2xfs.xfs.v3_00.ptr.RawData3;
import at.o2xfs.xfs.v3_00.ptr.RawDataIn3;
import at.o2xfs.xfs.v3_00.ptr.ReadForm3;
import at.o2xfs.xfs.v3_00.ptr.ReadFormOut3;
import at.o2xfs.xfs.v3_00.ptr.Reset3;
import at.o2xfs.xfs.v3_00.ptr.RetractBin3;
import at.o2xfs.xfs.v3_10.ptr.BinStatus310;
import at.o2xfs.xfs.v3_10.ptr.CodelineMapping310;
import at.o2xfs.xfs.v3_10.ptr.CodelineMappingOut310;
import at.o2xfs.xfs.v3_10.ptr.DefinitionLoaded310;
import at.o2xfs.xfs.v3_10.ptr.DevicePosition310;
import at.o2xfs.xfs.v3_10.ptr.FormField310;
import at.o2xfs.xfs.v3_10.ptr.FormHeader310;
import at.o2xfs.xfs.v3_10.ptr.HexData310;
import at.o2xfs.xfs.v3_10.ptr.LoadDefinition310;
import at.o2xfs.xfs.v3_10.ptr.MediaPresented310;
import at.o2xfs.xfs.v3_10.ptr.MediaRejected310;
import at.o2xfs.xfs.v3_10.ptr.MediaRetracted310;
import at.o2xfs.xfs.v3_10.ptr.PowerSaveChange310;
import at.o2xfs.xfs.v3_10.ptr.PowerSaveControl310;
import at.o2xfs.xfs.v3_10.ptr.PrintRawFile310;
import at.o2xfs.xfs.v3_10.ptr.PtrCapabilities310;
import at.o2xfs.xfs.v3_10.ptr.PtrStatus310;
import at.o2xfs.xfs.v3_10.ptr.SetGuidlight310;
import at.o2xfs.xfs.v3_10.ptr.SupplyReplenish310;
import at.o2xfs.xfs.v3_20.ptr.ControlPassbook320;
import at.o2xfs.xfs.v3_20.ptr.FormField320;
import at.o2xfs.xfs.v3_20.ptr.PtrCapabilities320;
import at.o2xfs.xfs.v3_20.ptr.PtrStatus320;
import at.o2xfs.xfs.v3_30.ptr.PtrCapabilities330;
import at.o2xfs.xfs.v3_30.ptr.PtrStatus330;
import at.o2xfs.xfs.v3_30.ptr.SetBlackMarkMode330;

public enum PtrFactory {

	INSTANCE;

	private <T> T doCreate(XfsVersion xfsVersion, Pointer p, Class<T> type) {
		Object result;
		if (BinStatus310.class.equals(type)) {
			result = createBinStatus(p);
		} else if (BinThreshold3.class.equals(type)) {
			result = createBinThreshold(p);
		} else if (CodelineMapping310.class.equals(type)) {
			result = createCodelineMapping(p);
		} else if (CodelineMappingOut310.class.equals(type)) {
			result = createCodelineMappingOut(p);
		} else if (ControlPassbook320.class.equals(type)) {
			result = createControlPassbook(p);
		} else if (DefinitionLoaded310.class.equals(type)) {
			result = createDefinitionLoaded(p);
		} else if (DevicePosition310.class.equals(type)) {
			result = createDevicePosition(p);
		} else if (FieldFailure3.class.equals(type)) {
			result = createFieldFailure(p);
		} else if (FormField3.class.equals(type)) {
			result = createFormField(p, xfsVersion);
		} else if (FormHeader3.class.equals(type)) {
			result = createFormHeader(p, xfsVersion);
		} else if (FormMedia3.class.equals(type)) {
			result = createFormMedia(p);
		} else if (HexData310.class.equals(type)) {
			result = createHexData(p);
		} else if (Image3.class.equals(type)) {
			result = createImage(p);
		} else if (ImageRequest3.class.equals(type)) {
			result = createImageRequest(p);
		} else if (LoadDefinition310.class.equals(type)) {
			result = createLoadDefinition(p);
		} else if (MediaDetected3.class.equals(type)) {
			result = createMediaDetected(p);
		} else if (MediaExt3.class.equals(type)) {
			result = createMediaExt(p);
		} else if (MediaPresented310.class.equals(type)) {
			result = createMediaPresented(p);
		} else if (MediaRejected310.class.equals(type)) {
			result = createMediaRejected(p);
		} else if (MediaRetracted310.class.equals(type)) {
			result = createMediaRetracted(p);
		} else if (MediaUnit3.class.equals(type)) {
			result = createMediaUnit(p);
		} else if (PaperThreshold3.class.equals(type)) {
			result = createPaperThreshold(p);
		} else if (PowerSaveChange310.class.equals(type)) {
			result = createPowerSaveChange(p);
		} else if (PowerSaveControl310.class.equals(type)) {
			result = createPowerSaveControl(p);
		} else if (PrintForm3.class.equals(type)) {
			result = createPrintForm(p);
		} else if (PrintRawFile310.class.equals(type)) {
			result = createPrintRawFile(p);
		} else if (PtrCapabilities3.class.equals(type)) {
			result = createPtrCapabilities(p, xfsVersion);
		} else if (PtrStatus3.class.equals(type)) {
			result = createPtrStatus(p, xfsVersion);
		} else if (QueryField3.class.equals(type)) {
			result = createQueryField(p);
		} else if (RawData3.class.equals(type)) {
			result = createRawData(p);
		} else if (RawDataIn3.class.equals(type)) {
			result = createRawDataIn(p);
		} else if (ReadForm3.class.equals(type)) {
			result = createReadForm(p);
		} else if (ReadFormOut3.class.equals(type)) {
			result = createReadFormOut(p);
		} else if (Reset3.class.equals(type)) {
			result = createReset(p);
		} else if (RetractBin3.class.equals(type)) {
			result = createRetractBin(p);
		} else if (SetBlackMarkMode330.class.equals(type)) {
			result = createSetBlackMarkMode(p);
		} else if (SetGuidlight310.class.equals(type)) {
			result = createSetGuidlight(p);
		} else if (SupplyReplenish310.class.equals(type)) {
			result = createSupplyReplenish(p);
		} else {
			throw new IllegalArgumentException(type.toString());
		}
		return type.cast(result);
	}

	private BinStatus310 createBinStatus(Pointer p) {
		return new BinStatus310(new BinStatus310(p));
	}

	private BinThreshold3 createBinThreshold(Pointer p) {
		return new BinThreshold3(new BinThreshold3(p));
	}

	private CodelineMapping310 createCodelineMapping(Pointer p) {
		return new CodelineMapping310(new CodelineMapping310(p));
	}

	private CodelineMappingOut310 createCodelineMappingOut(Pointer p) {
		return new CodelineMappingOut310(new CodelineMappingOut310(p));
	}

	private ControlPassbook320 createControlPassbook(Pointer p) {
		return new ControlPassbook320(new ControlPassbook320(p));
	}

	private DefinitionLoaded310 createDefinitionLoaded(Pointer p) {
		return new DefinitionLoaded310(new DefinitionLoaded310(p));
	}

	private DevicePosition310 createDevicePosition(Pointer p) {
		return new DevicePosition310(new DevicePosition310(p));
	}

	private FieldFailure3 createFieldFailure(Pointer p) {
		return new FieldFailure3(new FieldFailure3(p));
	}

	private FormField3 createFormField(Pointer p, XfsVersion xfsVersion) {
		FormField3 result;
		if (xfsVersion.compareTo(XfsVersion.V3_20) >= 0) {
			result = new FormField320(new FormField320(p));
		} else if (xfsVersion.compareTo(XfsVersion.V3_10) >= 0) {
			result = new FormField310(new FormField310(p));
		} else {
			result = new FormField3(new FormField3(p));
		}
		return result;
	}

	private FormHeader3 createFormHeader(Pointer p, XfsVersion xfsVersion) {
		FormHeader3 result;
		if (xfsVersion.compareTo(XfsVersion.V3_10) >= 0) {
			result = new FormHeader310(new FormHeader310(p));
		} else {
			result = new FormHeader3(new FormHeader3(p));
		}
		return result;
	}

	private FormMedia3 createFormMedia(Pointer p) {
		return new FormMedia3(new FormMedia3(p));
	}

	private HexData310 createHexData(Pointer p) {
		return new HexData310(new HexData310(p));
	}

	private Image3 createImage(Pointer p) {
		return new Image3(new Image3(p));
	}

	private ImageRequest3 createImageRequest(Pointer p) {
		return new ImageRequest3(new ImageRequest3(p));
	}

	private LoadDefinition310 createLoadDefinition(Pointer p) {
		return new LoadDefinition310(new LoadDefinition310(p));
	}

	private MediaDetected3 createMediaDetected(Pointer p) {
		return new MediaDetected3(new MediaDetected3(p));
	}

	private MediaExt3 createMediaExt(Pointer p) {
		return new MediaExt3(new MediaExt3(p));
	}

	private MediaPresented310 createMediaPresented(Pointer p) {
		return new MediaPresented310(new MediaPresented310(p));
	}

	private MediaRejected310 createMediaRejected(Pointer p) {
		return new MediaRejected310(new MediaRejected310(p));
	}

	private MediaRetracted310 createMediaRetracted(Pointer p) {
		return new MediaRetracted310(new MediaRetracted310(p));
	}

	private MediaUnit3 createMediaUnit(Pointer p) {
		return new MediaUnit3(new MediaUnit3(p));
	}

	private PaperThreshold3 createPaperThreshold(Pointer p) {
		return new PaperThreshold3(new PaperThreshold3(p));
	}

	private PowerSaveChange310 createPowerSaveChange(Pointer p) {
		return new PowerSaveChange310(new PowerSaveChange310(p));
	}

	private PowerSaveControl310 createPowerSaveControl(Pointer p) {
		return new PowerSaveControl310(new PowerSaveControl310(p));
	}

	private PrintForm3 createPrintForm(Pointer p) {
		return new PrintForm3(new PrintForm3(p));
	}

	private PrintRawFile310 createPrintRawFile(Pointer p) {
		return new PrintRawFile310(new PrintRawFile310(p));
	}

	private PtrCapabilities3 createPtrCapabilities(Pointer p, XfsVersion xfsVersion) {
		PtrCapabilities3 result;
		if (xfsVersion.compareTo(XfsVersion.V3_30) >= 0) {
			result = new PtrCapabilities330(new PtrCapabilities330(p));
		} else if (xfsVersion.compareTo(XfsVersion.V3_20) >= 0) {
			result = new PtrCapabilities320(new PtrCapabilities320(p));
		} else if (xfsVersion.compareTo(XfsVersion.V3_10) >= 0) {
			result = new PtrCapabilities310(new PtrCapabilities310(p));
		} else {
			result = new PtrCapabilities3(new PtrCapabilities3(p));
		}
		return result;
	}

	private PtrStatus3 createPtrStatus(Pointer p, XfsVersion xfsVersion) {
		PtrStatus3 result;
		if (xfsVersion.compareTo(XfsVersion.V3_30) >= 0) {
			result = new PtrStatus330(new PtrStatus330(p));
		} else if (xfsVersion.compareTo(XfsVersion.V3_20) >= 0) {
			result = new PtrStatus320(new PtrStatus320(p));
		} else if (xfsVersion.compareTo(XfsVersion.V3_10) >= 0) {
			result = new PtrStatus310(new PtrStatus310(p));
		} else {
			result = new PtrStatus3(new PtrStatus3(p));
		}
		return result;
	}

	private QueryField3 createQueryField(Pointer p) {
		return new QueryField3(new QueryField3(p));
	}

	private RawData3 createRawData(Pointer p) {
		return new RawData3(new RawData3(p));
	}

	private RawDataIn3 createRawDataIn(Pointer p) {
		return new RawDataIn3(new RawDataIn3(p));
	}

	private ReadForm3 createReadForm(Pointer p) {
		return new ReadForm3(new ReadForm3(p));
	}

	private ReadFormOut3 createReadFormOut(Pointer p) {
		return new ReadFormOut3(new ReadFormOut3(p));
	}

	private Reset3 createReset(Pointer p) {
		return new Reset3(new Reset3(p));
	}

	private RetractBin3 createRetractBin(Pointer p) {
		return new RetractBin3(new RetractBin3(p));
	}

	private SetBlackMarkMode330 createSetBlackMarkMode(Pointer p) {
		return new SetBlackMarkMode330(new SetBlackMarkMode330(p));
	}

	private SetGuidlight310 createSetGuidlight(Pointer p) {
		return new SetGuidlight310(new SetGuidlight310(p));
	}

	private SupplyReplenish310 createSupplyReplenish(Pointer p) {
		return new SupplyReplenish310(new SupplyReplenish310(p));
	}

	public static <T> T create(XfsVersion xfsVersion, Pointer p, Class<T> type) {
		return INSTANCE.doCreate(xfsVersion, p, type);
	}
}
