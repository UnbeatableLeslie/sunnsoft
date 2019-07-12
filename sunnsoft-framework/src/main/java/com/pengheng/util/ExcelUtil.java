package com.pengheng.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtil {

	/**
	 * 设置excel的数据
	 *
	 * @param dataList
	 */
	private static Workbook exportExcelData(List titleList, List dataList) {
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("统计表");
		CellStyle headCellStyle = workbook.createCellStyle();
		headCellStyle.setAlignment(HorizontalAlignment.CENTER);
		headCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		headCellStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
		headCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headCellStyle.setBorderTop(BorderStyle.THIN);
		headCellStyle.setBorderLeft(BorderStyle.THIN);
		headCellStyle.setBorderRight(BorderStyle.THIN);
		headCellStyle.setBorderBottom(BorderStyle.THIN);
		headCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headCellStyle.setWrapText(true);
		Font headFont = workbook.createFont();
		headFont.setFontName("微软雅黑");
		headFont.setFontHeightInPoints(Short.parseShort("12"));
		headFont.setBold(true);
		headCellStyle.setFont(headFont);

		Row row = sheet.createRow(0);
		Cell cell = null;
		// 设置标题
		for (int i = 0; i < titleList.size(); i++) {
			Map<Object, Object> titleMap = (Map<Object, Object>) titleList.get(i);
			cell = row.createCell(i);
			cell.setCellValue(Toolkits.defaultString(titleMap.get("title")));
			cell.setCellStyle(headCellStyle);
		}

		// 创建表格内容。
		CellStyle dataCellStyle = workbook.createCellStyle();
		// 数据单元格对齐方式。
		dataCellStyle.setAlignment(HorizontalAlignment.CENTER);
		dataCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		dataCellStyle.setBorderTop(BorderStyle.THIN);
		dataCellStyle.setBorderBottom(BorderStyle.THIN);
		dataCellStyle.setBorderLeft(BorderStyle.THIN);
		dataCellStyle.setBorderRight(BorderStyle.THIN);
		// 数据单元格字体。
		Font dataFont = workbook.createFont();
		dataFont.setFontName("微软雅黑");
		dataFont.setFontHeightInPoints(Short.parseShort("10"));
		dataCellStyle.setFont(dataFont);

		// 设置内容
		for (int j = 0; j < dataList.size(); j++) {
			row = sheet.createRow(j + 1);
			Map<Object, Object> dataMap = (Map<Object, Object>) dataList.get(j);
			for (int k = 0; k < titleList.size(); k++) {
				Map<Object, Object> titleMap = (Map<Object, Object>) titleList.get(k);
				cell = row.createCell(k);
				cell.setCellValue(Toolkits.defaultString(dataMap.get(Toolkits.defaultString(titleMap.get("field")))));
				cell.setCellStyle(dataCellStyle);
			}
		}
		return workbook;
	}

	/**
	 * 导出excel
	 *
	 * @param fileName 文件输出路径
	 * @param dataList excel数据内容
	 */

	public static void exportExcel(String fileName, List titleList, List dataList) throws Exception {

		OutputStream fos = new FileOutputStream(fileName);
		Workbook workbook = exportExcelData(titleList, dataList);
		workbook.write(fos);
		fos.flush();
		fos.close();
	}

	public static void exportExcel(HttpServletResponse response, String fileName, List titleList, List dataList)
			throws Exception {

//        OutputStream fos = new FileOutputStream(fileName);
		setResponseHeader(response,fileName);
		OutputStream fos = response.getOutputStream();
		Workbook workbook = exportExcelData(titleList, dataList);
		workbook.write(fos);
		fos.flush();
		fos.close();
	}

	public static void setResponseHeader(HttpServletResponse response, String fileName) {
		try {
			try {
				fileName = new String(fileName.getBytes(), "utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response.setContentType("application/octet-stream;charset=utf-8");
	           response.setHeader("content-disposition", "attachment;filename=" + new String(fileName.getBytes(), "ISO8859-1") + ".xls" );
			response.addHeader("Pargam", "no-cache");
			response.addHeader("Cache-Control", "no-cache");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		String excelName = "D:/start.xls";
		List<Object> titleList = new ArrayList<>();
		Map<String, String> titleMap = new HashMap<>();
		titleMap.put("field", "id");
		titleMap.put("title", "标题");
		titleList.add(titleMap);
		titleMap = new HashMap<>();
		titleMap.put("field", "name");
		titleMap.put("title", "名字");
		titleList.add(titleMap);
		titleMap = new HashMap<>();
		titleMap.put("field", "age");
		titleMap.put("title", "年龄");
		titleList.add(titleMap);
		titleMap = new HashMap<>();
		titleMap.put("field", "hobby");
		titleMap.put("title", "爱好");
		titleList.add(titleMap);
		titleMap = new HashMap<>();
		titleMap.put("field", "international");
		titleMap.put("title", "国籍");
		titleList.add(titleMap);
		List dataList = new ArrayList<>();

		Map<Object, Object> userMap = new HashMap<>();
		userMap.put("id", "1");
		userMap.put("name", "张三");
		userMap.put("age", "18");
		userMap.put("hobby", "篮球");
		userMap.put("aa", "篮球");
		dataList.add(userMap);
		userMap = new HashMap<>();
		userMap.put("id", "2");
		userMap.put("name", "蔡徐坤");
		userMap.put("age", "18");
		userMap.put("hobby", "rap 篮球");
		userMap.put("aa", "篮球");
		dataList.add(userMap);
		userMap = new HashMap<>();
		userMap.put("id", "3");
		userMap.put("name", "TFBOYS");
		userMap.put("age", "18");
		userMap.put("hobby", "掏粪");
		userMap.put("aa", "篮球");
		dataList.add(userMap);
		ExcelUtil.exportExcel(excelName, titleList, dataList);
	}
}
