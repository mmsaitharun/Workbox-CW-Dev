package com.incture.pmc.reports;

import java.io.ByteArrayOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.incture.pmc.dto.DownloadReportResponseDto;
import com.incture.pmc.dto.ReportFormattedDto;
import com.incture.pmc.dto.ResponseMessage;
import com.incture.pmc.util.ServicesUtil;

import sun.misc.BASE64Encoder;

/**
 * @author Saurabh
 *
 */
public class Excel extends File {

	@SuppressWarnings({ "deprecation", "resource" })
	public DownloadReportResponseDto pushData(ReportFormattedDto reportFormattedDto) {
		DownloadReportResponseDto responseDto = new DownloadReportResponseDto();
		ResponseMessage message = new ResponseMessage();
		System.err.println("[PMC] REPORT - Excel  - pushData() - Started with ReportFormattedDto - " + reportFormattedDto);
		if (!ServicesUtil.isEmpty(reportFormattedDto)) {
			Workbook workbook = new XSSFWorkbook();
			Sheet detailSheet = null;
			if (!ServicesUtil.isEmpty(reportFormattedDto.getSheetName())) {
				detailSheet = workbook.createSheet(reportFormattedDto.getSheetName());

			}
			byte[] bytes = null;
			try {
				CellStyle cellStyle = workbook.createCellStyle();
				cellStyle.setFillForegroundColor(HSSFColor.CORNFLOWER_BLUE.index);
				cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
				cellStyle.setBorderRight(CellStyle.BORDER_THIN);
				cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
				cellStyle.setBorderTop(CellStyle.BORDER_THIN);
				Font headerFont = workbook.createFont();
				headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
				cellStyle.setFont(headerFont);

				CellStyle dataCellStyle = workbook.createCellStyle();
				dataCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
				dataCellStyle.setBorderRight(CellStyle.BORDER_THIN);
				dataCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
				dataCellStyle.setBorderTop(CellStyle.BORDER_THIN);

				/**
				 * Printing Header on 1st row of sheet
				 */
				int cellNumber = 0;
				Row row1 = null;
				if (!ServicesUtil.isEmpty(reportFormattedDto.getHeaders())) {
					row1 = detailSheet.createRow(0);
					for (String header : reportFormattedDto.getHeaders()) {
						Cell headerCell = row1.createCell(cellNumber++);
						headerCell.setCellStyle(cellStyle);
						headerCell.setCellValue(header);
					}
					detailSheet.createFreezePane(0, 1);
				}

				/**
				 * Printing Report Data List from 2nd row of sheet till end of Report Data.
				 */
				int rowNumber = 1;
				if (!ServicesUtil.isEmpty(reportFormattedDto.getDataRows())) {
					for (Object[] obj : reportFormattedDto.getDataRows()) {
						Row row = detailSheet.createRow(rowNumber++);
						for (int i = 0; i < obj.length; i++) {
							Cell dataCell = row.createCell(i);
							dataCell.setCellStyle(dataCellStyle);
							dataCell.setCellValue(obj[i] == null ? "" : obj[i].toString());
						}
					}
				}
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				workbook.write(bos);
				bytes = bos.toByteArray();
				if (!ServicesUtil.isEmpty(bytes)) {
					BASE64Encoder encoder = new BASE64Encoder();
					if (bytes != null) {
						responseDto.setFile(bytes);
						responseDto.setBase64(encoder.encode(bytes));
						responseDto.setFilename(detailSheet.getSheetName());
						message.setStatus("SUCCESS");
						message.setStatusCode("0");
						message.setMessage("EXCEL Bytes Successfully created");
						responseDto.setMessage(message);
					}
				}
			} catch (Exception e) {
				message.setStatus("FAILURE");
				message.setStatusCode("0");
				message.setMessage("EXCEL Bytes creation Failed");
				responseDto.setMessage(message);
				System.err.println("[PMC] REPORT - Excel  - pushData() - Exception Caught while building Excel sheet - " + e.getMessage());
			}
		} else {
			message.setStatus("SUCCESS");
			message.setStatusCode("1");
			message.setMessage("NO REPORT DATA ");
			responseDto.setMessage(message);
			System.err.println("ReportFormattedDto is null, So No Sheet Generated.");

		}
		System.err.println("[PMC] REPORT - Excel  - pushData() - Ended with DownloadReportResponseDto - " + responseDto);
		return responseDto;
	}
}
