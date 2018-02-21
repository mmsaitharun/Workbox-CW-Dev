package com.incture.pmc.responseDto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.incture.pmc.dto.ResponseMessage;
import com.incture.pmc.dto.WorkloadRangeDto;
@XmlRootElement
public class WorkloadRangeResponse {

	private List<WorkloadRangeDto> workloadRangeDtos;
	private ResponseMessage responseMessage;

	public ResponseMessage getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(ResponseMessage responseMessage) {
		this.responseMessage = responseMessage;
	}

	public List<WorkloadRangeDto> getWorkloadRangeDtos() {
		return workloadRangeDtos;
	}

	public void setWorkloadRangeDtos(List<WorkloadRangeDto> workloadRangeDtos) {
		this.workloadRangeDtos = workloadRangeDtos;
	}

	@Override
	public String toString() {
		return "WorkloadRangeResponse [workloadRangeDtos=" + workloadRangeDtos + "]";
	}

}
