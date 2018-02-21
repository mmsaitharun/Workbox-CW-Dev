package com.incture.pmc.services;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.incture.pmc.dao.ProcessEventsDao;
import com.incture.pmc.dto.ProcessAgeingResponse;
import com.incture.pmc.dto.ProcessDetailsDto;
import com.incture.pmc.dto.ProcessDetailsResponse;
import com.incture.pmc.dto.ProcessEventsDto;
import com.incture.pmc.dto.UserProcessDetailRequestDto;
import com.incture.pmc.dto.UserDetailsDto;
import com.incture.pmc.util.PMCConstant;
import com.incture.pmc.util.ServicesUtil;

/**
 * Session Bean implementation class ProcessFacade
 */
@Stateless
public class ProcessFacade implements ProcessFacadeLocal {

	// LogUtil logger = new LogUtil(ProcessFacade.class);

	@EJB
	EntityManagerProviderLocal em;

	@EJB
	private ConfigurationFacadeLocal config;





	/*
	 * @Override public List<AgingGraphDto> getProcessAgeingGraph(String graphTrendType) { List<AgingGraphDto> agingGraphDtos = null; if (!ServicesUtil.isEmpty(graphTrendType)) { ProcessEventsDao
	 * processEventsDao = new ProcessEventsDao(em.getEntityManager()); Calendar calendar = GregorianCalendar.getInstance(); Date startDate = null, endDate = null; if
	 * (PMCConstant.GRAPH_TREND_MONTH.equalsIgnoreCase(graphTrendType)) { List<ReportAgingDto> reportAgingConfigDtos = config.getAgeingBuckets(PMCConstant.PROCESS_AGING_REPORT); if
	 * (!ServicesUtil.isEmpty(reportAgingConfigDtos)) { List<String> segList = new ArrayList<String>(); for (ReportAgingDto reportAgingDto : reportAgingConfigDtos) { String segment =
	 * reportAgingDto.getLowerSegment() + " - " + (reportAgingDto.getHigherSegment()); segList.add(segment); } Map<String, List<Date>> segmentMap = ServicesUtil.dateSegmentMap(segList); try {
	 * List<Object[]> resultList = processEventsDao.getProcessAgingCountByDatesRange(segmentMap); if (!ServicesUtil.isEmpty(resultList)) { agingGraphDtos = new ArrayList<AgingGraphDto>(); for
	 * (Object[] obj : resultList) { AgingGraphDto graphDto = new AgingGraphDto(); graphDto.setRange((String) obj[0]); if (segList.contains((String) obj[0])) { segList.remove((String) obj[0]); }
	 * graphDto.setNoOfProcess((BigDecimal) obj[1]); graphDto.setProcessName((String) obj[2]); agingGraphDtos.add(graphDto); } } for (String segmentRange : segList) { AgingGraphDto graphDto = new
	 * AgingGraphDto(); graphDto.setRange(segmentRange); agingGraphDtos.add(graphDto); } Collections.sort(agingGraphDtos, new Comparator<AgingGraphDto>() {
	 * 
	 * @Override public int compare(AgingGraphDto o1, AgingGraphDto o2) { String[] o1Range = o1.getRange().split("-"); String[] o2Range = o2.getRange().split("-"); return
	 * (Integer.valueOf(o1Range[0].trim())).compareTo(Integer.valueOf(o2Range[0].trim())); } }); } catch (NoResultFault e) { System.err.println("NO RESULT FOUND"); e.printStackTrace(); } } } else {
	 * calendar.add(Calendar.DAY_OF_MONTH, -(PMCConstant.WEEK_RANGE - 1)); startDate = ServicesUtil.setInitialTime(calendar.getTime()); endDate = new Date(); List<String> weekDates =
	 * ServicesUtil.getWeekDateRangeInString(PMCConstant.WEEK_RANGE); try { List<Object[]> resultList = processEventsDao.getProcessAgingCountByDates(startDate, endDate); final DateFormat dateFormatter
	 * = new SimpleDateFormat(PMCConstant.PMC_DATE_FORMATE); if (!ServicesUtil.isEmpty(resultList)) { agingGraphDtos = new ArrayList<AgingGraphDto>(); for (Object[] obj : resultList) { AgingGraphDto
	 * graphDto = new AgingGraphDto(); graphDto.setProcessName((String) obj[0]); graphDto.setNoOfProcess((BigDecimal) obj[1]);
	 * graphDto.setRange(dateFormatter.format(ServicesUtil.resultAsDate(obj[2]))); if (weekDates.contains(dateFormatter.format(ServicesUtil.resultAsDate(obj[2])))) {
	 * weekDates.remove(dateFormatter.format(ServicesUtil.resultAsDate(obj[2]))); } agingGraphDtos.add(graphDto); } } for (String date : weekDates) { AgingGraphDto graphDto = new AgingGraphDto();
	 * graphDto.setRange(date); agingGraphDtos.add(graphDto); } Collections.sort(agingGraphDtos, new Comparator<AgingGraphDto>() {
	 * 
	 * @Override public int compare(AgingGraphDto o1, AgingGraphDto o2) { try { return dateFormatter.parse(o1.getRange()).compareTo(dateFormatter.parse(o2.getRange())); } catch (ParseException e) {
	 * System.err.println("Parse exception - " + e.getMessage()); } return 0; } }); } catch (NoResultFault e) { System.err.println("NO RESULT FOUND"); e.printStackTrace(); }
	 * 
	 * } } return agingGraphDtos; }
	 * 
	 * @Override public AgingResponseDto getProcessAgingTable(String ageingType) { AgingResponseDto responseDto = new AgingResponseDto(); List<AgingTableDto> processAgingTableDtos = null; Map<String,
	 * BigDecimal> headerMap = new LinkedHashMap<String, BigDecimal>();
	 * 
	 * if (!ServicesUtil.isEmpty(ageingType)) { ProcessEventsDao processEventsDao = new ProcessEventsDao(em.getEntityManager()); if (PMCConstant.GRAPH_TREND_MONTH.equalsIgnoreCase(ageingType)) {
	 * List<ReportAgingDto> reportAgingConfigDtos = config.getAgeingBuckets(PMCConstant.PROCESS_AGING_REPORT); if (!ServicesUtil.isEmpty(reportAgingConfigDtos)) { List<String> segList = new
	 * ArrayList<String>(); headerMap.put(PMCConstant.PROCESS_NAME_LABEL, new BigDecimal(0)); for (ReportAgingDto reportAgingDto : reportAgingConfigDtos) { String segment =
	 * reportAgingDto.getLowerSegment() + " - " + reportAgingDto.getHigherSegment(); segList.add(segment); headerMap.put(segment, new BigDecimal(0)); } headerMap.put(PMCConstant.PROCESS_TOTAL, new
	 * BigDecimal(0)); try { List<Object[]> resultList = processEventsDao.getProcessAgingCountByDatesRange(ServicesUtil.dateSegmentMap(segList)); if (!ServicesUtil.isEmpty(resultList)) {
	 * processAgingTableDtos = new ArrayList<AgingTableDto>(); for (Object[] obj : resultList) { AgingTableDto tempDto = new AgingTableDto((String) obj[2]); if
	 * (processAgingTableDtos.contains(tempDto)) { AgingTableDto existingDto = processAgingTableDtos.get(processAgingTableDtos.indexOf(tempDto)); Map<String, BigDecimal> existingMap =
	 * existingDto.getDataMap(); existingMap.put((String) obj[0], (BigDecimal) obj[1]); BigDecimal tempCount = headerMap.get((String) obj[0]); tempCount = tempCount.add((BigDecimal) obj[1]);
	 * headerMap.put((String) obj[0], tempCount); existingDto.setCount(existingDto.getCount().add((BigDecimal) obj[1]));
	 * 
	 * } else { AgingTableDto newDto = new AgingTableDto((String) obj[2]); Map<String, BigDecimal> newMap = new LinkedHashMap<String, BigDecimal>();
	 * 
	 * for (String segment : segList) { newMap.put(segment, new BigDecimal(0)); } BigDecimal tempCount = headerMap.get((String) obj[0]); tempCount = tempCount.add((BigDecimal) obj[1]);
	 * headerMap.put((String) obj[0], tempCount); newMap.put((String) obj[0], (BigDecimal) obj[1]); newDto.setDataMap(newMap); newDto.setCount((BigDecimal) obj[1]); processAgingTableDtos.add(newDto);
	 * Collections.sort(processAgingTableDtos); } } } } catch (NoResultFault e) { System.err.println("NO RESULT FOUND"); e.printStackTrace(); } } } else { Date startDate = null, endDate = null;
	 * Calendar calendar = GregorianCalendar.getInstance(); calendar.add(Calendar.DAY_OF_MONTH, -(PMCConstant.WEEK_RANGE - 1)); startDate = ServicesUtil.setInitialTime(calendar.getTime()); endDate =
	 * new Date(); List<String> weekDates = ServicesUtil.getWeekDateRangeInString(PMCConstant.WEEK_RANGE); headerMap.put(PMCConstant.PROCESS_NAME_LABEL, new BigDecimal(0)); for (String dates :
	 * weekDates) { AgingTableHeaderDto tableHeaderDto = new AgingTableHeaderDto(); tableHeaderDto.setColumnName(dates); headerMap.put(dates, new BigDecimal(0)); }
	 * headerMap.put(PMCConstant.PROCESS_TOTAL, new BigDecimal(0)); try { List<Object[]> resultList = processEventsDao.getProcessAgingCountByDates(startDate, endDate); DateFormat dateFormatter = new
	 * SimpleDateFormat(PMCConstant.PMC_DATE_FORMATE); if (!ServicesUtil.isEmpty(resultList)) { processAgingTableDtos = new ArrayList<AgingTableDto>(); for (Object[] obj : resultList) { AgingTableDto
	 * tempDto = new AgingTableDto((String) obj[0]); if (processAgingTableDtos.contains(tempDto)) { AgingTableDto existingDto = processAgingTableDtos.get(processAgingTableDtos.indexOf(tempDto));
	 * Map<String, BigDecimal> existingMap = existingDto.getDataMap(); existingMap.put(dateFormatter.format(ServicesUtil.resultAsDate(obj[2])), (BigDecimal) obj[1]); BigDecimal tempCount =
	 * headerMap.get(dateFormatter.format(ServicesUtil.resultAsDate(obj[2]))); tempCount = tempCount.add((BigDecimal) obj[1]); headerMap.put(dateFormatter.format(ServicesUtil.resultAsDate(obj[2])),
	 * tempCount); existingDto.setCount(existingDto.getCount().add((BigDecimal) obj[1])); } else { AgingTableDto newDto = new AgingTableDto((String) obj[0]); Map<String, BigDecimal> newMap = new
	 * LinkedHashMap<String, BigDecimal>(); for (String date : weekDates) { newMap.put(date, new BigDecimal(0)); } newMap.put(dateFormatter.format(ServicesUtil.resultAsDate(obj[2])), (BigDecimal)
	 * obj[1]); newDto.setDataMap(newMap); BigDecimal tempCount = headerMap.get(dateFormatter.format(ServicesUtil.resultAsDate(obj[2]))); tempCount = tempCount.add((BigDecimal) obj[1]);
	 * headerMap.put(dateFormatter.format(ServicesUtil.resultAsDate(obj[2])), tempCount); newDto.setCount((BigDecimal) obj[1]); processAgingTableDtos.add(newDto);
	 * Collections.sort(processAgingTableDtos); } } } } catch (NoResultFault e) { System.err.println("NO RESULT FOUND"); e.printStackTrace(); } } if (!ServicesUtil.isEmpty(processAgingTableDtos)) {
	 * AgingTableDto totalDto = new AgingTableDto(PMCConstant.PROCESS_TOTAL); Map<String, BigDecimal> totalMap = new LinkedHashMap<String, BigDecimal>(); Iterator<String> it =
	 * headerMap.keySet().iterator(); BigDecimal count = new BigDecimal(0); while (it.hasNext()) { String key = it.next(); if (!key.equals(PMCConstant.PROCESS_NAME_LABEL) &&
	 * !key.equals(PMCConstant.PROCESS_TOTAL)) { count = count.add(headerMap.get(key)); totalMap.put(key, headerMap.get(key)); } } totalDto.setCount(count); totalDto.setDataMap(totalMap);
	 * processAgingTableDtos.add(totalDto); responseDto.setStatus("SUCCESS"); responseDto.setHeaderMap(headerMap); responseDto.setTupleDtos(processAgingTableDtos); } else {
	 * responseDto.setStatus("FAILURE"); } } return responseDto; }
	 */

	@Override
	public ProcessDetailsResponse getProcessesByDuration(ProcessDetailsDto processDetailsDto) {
		ProcessEventsDao dao = new ProcessEventsDao(em.getEntityManager());
		return dao.getProcessByDuration(processDetailsDto);
	}

	@Override
	public ProcessDetailsResponse getProcessesByTaskOwner(UserProcessDetailRequestDto request) {
		request.setProcessName(config.getAllProcessNamesByRoleAsString(request.getProcessName()));
		ProcessEventsDao processEventsDao = new ProcessEventsDao(em.getEntityManager());
		return processEventsDao.getProcessesByTaskOwner(request);
	}

	@Override
	public ProcessEventsDto getProcessDetailsByInstance(String processId) {
		ProcessEventsDao processEventsDao = new ProcessEventsDao(em.getEntityManager());
		ProcessEventsDto processEventsDto = processEventsDao.getProcessDetail(processId);
		SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(PMCConstant.DETAIL_DATE_FORMATE);
		if (!ServicesUtil.isEmpty(processEventsDto) && !ServicesUtil.isEmpty(processEventsDto.getCompletedAt()))
			processEventsDto.setCompletedAtInString(simpleDateFormat1.format(processEventsDto.getCompletedAt()));
		if (!ServicesUtil.isEmpty(processEventsDto) && !ServicesUtil.isEmpty(processEventsDto.getStartedAt()))
			processEventsDto.setStartedAtInString(simpleDateFormat1.format(processEventsDto.getStartedAt()));
		return processEventsDto;
	}

	@Override
	public ProcessAgeingResponse getProcessAgeing(String ageingType, String processName) {
		ProcessEventsDao dao = new ProcessEventsDao(em.getEntityManager());

		processName = config.getAllProcessNamesByRoleAsString(processName);
		return dao.getProcessAgeing(ageingType, processName, config.getAgeingBuckets(PMCConstant.PROCESS_AGING_REPORT));

	}
	@Override
	public List<UserDetailsDto> getCreatedByList(String inputValue) {
		ProcessEventsDao dao = new ProcessEventsDao(em.getEntityManager());
		return dao.getCreatedByList(inputValue);

	}
	

	/*
	 * @Override public byte[] generateExcelByDuration(@WebParam(name = "processDetailsDto") ProcessDetailsDto processDetailsDto) { ProcessDetailsResponse resp =
	 * getProcessesByDuration(processDetailsDto); SXSSFWorkbook wb = new ExcelExportResponse().exportExcel(new String[] { "Request ID", "Name", "Subject", "Started At", "Started By" },
	 * resp.getProcessEventsList()); ByteArrayOutputStream outByteStream = new ByteArrayOutputStream(); try { wb.write(outByteStream); } catch (IOException e) { e.printStackTrace(); } byte[] outArray
	 * = outByteStream.toByteArray(); return outArray; }
	 */
}
