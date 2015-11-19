package com.gcs.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.web.multipart.MultipartFile;

import com.gcs.app.entity.WechatUser;


public class ExcelUtil {
	
	
	private static void extractedExcel(HSSFWorkbook wb, HSSFCellStyle cs, HSSFCellStyle cs2) {
		// 创建两种字体
		HSSFFont f = wb.createFont();
		HSSFFont f2 = wb.createFont();

		// 创建第一种字体样式
		f.setFontHeightInPoints((short) 14);
		f.setColor(HSSFColor.BLACK.index);
		f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		f.setFontName("仿宋_GB2312");

		// 创建第二种字体样式
		f2.setFontHeightInPoints((short) 11);
		f2.setColor(HSSFColor.BLACK.index);
		// f2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		f2.setFontName("仿宋_GB2312");

		// 设置第一种单元格的样式
		cs.setFont(f);
		cs.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cs.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cs.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cs.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cs.setAlignment(HSSFCellStyle.ALIGN_LEFT); // 居左对齐
		// cs.setDataFormat(df.getFormat("#,##0.0"));

		// 设置第二种单元格的样式
		cs2.setFont(f2);
		cs2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cs2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cs2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cs2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cs2.setAlignment(HSSFCellStyle.ALIGN_LEFT); // 居左对齐
	}

	
	

	/**	读取excel文件里的内容
	 * 
	 * @param file
	 * @param sheetNum	从第几个表开始
	 * @param lineNum	从第几行开始
	 * @param column	从第几列开始
	 * @param fileType  文件的版本
	 * @return
	 * @throws Exception
	 */
	public static Map<String, List<List<String>>> getExcelData(MultipartFile file, int sheetNum, int lineNum, int column,int fileType) throws Exception {
		List<String> cellList = null;
		Map<String, List<List<String>>> map = new HashMap<String, List<List<String>>>();
		
		try {
			/** 
			 * 根据版本选择创建Workbook的方式 
			 * 1:xls类型，2003版本        2：xlsx类型，2007
			 * */
			Workbook workbook = null;
			if(fileType == 1){
				workbook = new HSSFWorkbook(file.getInputStream()); // 读取excel表到工作薄中
			}else{
				workbook = new XSSFWorkbook(file.getInputStream());
			}
			
			int sheetSum = workbook.getNumberOfSheets(); // 获取工作簿中的表单数量

			for (int k = sheetNum - 1; k < sheetSum; k++) {
				List<List<String>> rowList = new ArrayList<List<String>>();
				Sheet sheet = workbook.getSheetAt(k); // 给定索引处得到hssfsheet对象
				String sheetName = sheet.getSheetName(); // 得到的表名称

				if (sheet != null) {
					for (int j = lineNum - 1; j < sheet.getLastRowNum() + 1; j++) { // 循环遍历sheet
						Row row = sheet.getRow(j); // 获取行
						cellList = new ArrayList<String>();
						for (int i = column - 1; i < row.getLastCellNum(); i++) {
							Cell cell = row.getCell(i); // 获取每一个单元格中的值
							String firstCell = null;
							if (cell != null) {
								firstCell = getString(cell);
							}
							cellList.add(firstCell);
						}
						rowList.add(cellList);
					}
				}
				map.put(sheetName, rowList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 
	 * CONVERT OTHER TYPE INTO STRING
	 * 
	 * @param cell
	 * @return
	 */
	public static String getString(Cell cell) {
		int i = cell.getCellType();
		if (i == HSSFCell.CELL_TYPE_STRING) {
			return cell.getStringCellValue();
		} else if (i == HSSFCell.CELL_TYPE_NUMERIC) {
			if (HSSFDateUtil.isCellDateFormatted(cell)) {  
	        double d = cell.getNumericCellValue();  
	        Date date = HSSFDateUtil.getJavaDate(d);
	        return date.toString();
			}else{
				Double dou = cell.getNumericCellValue();
				return String.valueOf(dou);				
			}	
		
//			Double dou = cell.getNumericCellValue();
//			return String.valueOf(dou);		
		} else if (i == HSSFCell.CELL_TYPE_BOOLEAN) {
			Boolean bol = cell.getBooleanCellValue();
			return String.valueOf(bol);
		} else if (i == HSSFCell.CELL_TYPE_FORMULA) {
			return cell.getCellFormula();
		} else {
			return null;
		}
	}
	
	/**
	 * 
	 * 水资源论证报告数据导出
	 * 
	 * @param excelmodel
	 * @return
	 */
	public static HSSFWorkbook userListDataReport(List<WechatUser> excelmodel) {
		// 创建excel工作簿
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		wb.setSheetName(0, "人员信息");
		HSSFRow row = sheet.createRow((short) 0);
		// 创建两种单元格格式

		HSSFCellStyle cs = wb.createCellStyle();
		HSSFCellStyle cs2 = wb.createCellStyle();

		// DataFormat df = wb.createDataFormat();

		extractedExcel(wb, cs, cs2);

		HSSFCell cell = row.createCell(0);
		cell.setCellValue("部门代码");
		cell.setCellStyle(cs);

		cell = row.createCell(1);
		cell.setCellValue("部门名称");
		cell.setCellStyle(cs);

		cell = row.createCell(2);
		cell.setCellValue("警员编号");
		cell.setCellStyle(cs);

		cell = row.createCell(3);
		cell.setCellValue("姓名");
		cell.setCellStyle(cs);

		cell = row.createCell(4);
		cell.setCellValue("手机号码");
		cell.setCellStyle(cs);

		cell = row.createCell(5);
		cell.setCellValue("电子邮箱");
		cell.setCellStyle(cs);

		cell = row.createCell(6);
		cell.setCellValue("微信账号");
		cell.setCellStyle(cs);
		cell.setCellStyle(cs);

		for (int i = 0; i < excelmodel.size(); i++) {
			row = sheet.createRow(i + 1);
			cell = row.createCell(0);
			cell.setCellType(cell.CELL_TYPE_STRING);
			cell.setCellValue(StringUtils.changeNull(excelmodel.get(i).getBak4()));
			cell.setCellStyle(cs2);

			cell = row.createCell(1);
			cell.setCellType(cell.CELL_TYPE_STRING);
			cell.setCellValue(StringUtils.changeNull(excelmodel.get(i).getDepartmentstr()));
			cell.setCellStyle(cs2);

			cell = row.createCell(2);
			cell.setCellType(cell.CELL_TYPE_STRING);
			cell.setCellValue(StringUtils.changeNull(excelmodel.get(i).getPoliceID()));
			cell.setCellStyle(cs2);

			cell = row.createCell(3);
			cell.setCellType(cell.CELL_TYPE_STRING);
			cell.setCellValue(StringUtils.changeNull(excelmodel.get(i).getName()));
			cell.setCellStyle(cs2);

			cell = row.createCell(4);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(StringUtils.changeNull(excelmodel.get(i).getMobile()));
			cell.setCellStyle(cs2);

			cell = row.createCell(5);
			cell.setCellType(cell.CELL_TYPE_STRING);
			cell.setCellValue(StringUtils.changeNull(excelmodel.get(i).getEmail()));
			cell.setCellStyle(cs2);

			cell = row.createCell(6);
			cell.setCellType(cell.CELL_TYPE_STRING);
			cell.setCellValue(StringUtils.changeNull(excelmodel.get(i).getWeixinid()));
			cell.setCellStyle(cs2);

			sheet.autoSizeColumn((short) i);
		}

		return wb;

	}
	
	public static Map<String, List<List<Cell>>> getExcelData(InputStream file, int fileType) throws Exception {
		Map<String, List<List<Cell>>> map = new HashMap<String, List<List<Cell>>>();
//		FileInputStream fis = new FileInputStream(file);
		Workbook workbook = fileType == 1 ? new HSSFWorkbook(file) : new XSSFWorkbook(file);
		
		List<List<Cell>> rowList = null;
		List<Cell> cellList = null;
		int sheetSum = workbook.getNumberOfSheets(); // 获取工作簿中的表单数量
		
		for (int i = 0; i < sheetSum; i++) {
			Sheet sheet = workbook.getSheetAt(i); 
			String sheetName = sheet.getSheetName(); // 得到的表名称
			if (sheet != null) {
				rowList = new ArrayList<List<Cell>>();
				try {
					for (int j = 0; j <= sheet.getLastRowNum(); j++) { // 循环遍历sheet
						Row row = sheet.getRow(j); // 获取行
						cellList = new ArrayList<Cell>();
						for (int k = 0; k < row.getLastCellNum(); k++) {
							Cell cell = row.getCell(k); // 获取每一个单元格中的值
							cellList.add(cell);
						}
						rowList.add(cellList);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("数据："+rowList);
			}
			map.put(sheetName, rowList);
		}
		
		return map;
	}
	
}
