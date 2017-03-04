/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leosys.core.utils;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
/**
 * 导出到Excel文档
 * 
 * @author 
 * @date 2015/08/01
 */
public class ExcelUtil {
	
	private String title;
	
	private String[] headArr;
	
	private ExcelRenderer[] rendererArr;
	
	public ExcelUtil(ExcelRenderer[] rendererArr){
		this();
		this.rendererArr = rendererArr;
	}

	public ExcelUtil() {
		title = "表格标题";
		headArr = new String[5];
		for(short i=0; i<headArr.length; i++)
			headArr[i]= "列名－"+i;
	}
	
	public void exportExcel(List<?> dataList,OutputStream out) throws Exception{
		HSSFWorkbook workbook = null;
		HSSFSheet sheet = null;
		HSSFRow row = null;
		HSSFCell cell = null;
		HSSFCellStyle titleStyle = null;
		int rowIndex = 0;
		try{
			workbook = new HSSFWorkbook();// 声明一个工作薄
			sheet = workbook.createSheet("数据报表统计");// 生成一个表格		
			sheet.setDefaultColumnWidth((short) 30);// 设置表格默认列宽度为15个字节	
			titleStyle = workbook.createCellStyle();//创建一个样式
			titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
			titleStyle.setFillForegroundColor(HSSFColor.WHITE.index);
			titleStyle.setFillBackgroundColor(HSSFColor.WHITE.index);	
			titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			
			HSSFFont font = workbook.createFont();
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			font.setFontHeightInPoints((short)17);
			titleStyle.setFont(font);
			
			row = sheet.createRow(rowIndex++);
			row.setHeight((short)600);
			for(short i=0; i<headArr.length; i++){		
				cell = row.createCell(i); //创建单元格
				if(i==0)
					cell.setCellValue(new HSSFRichTextString(title));
	            cell.setCellStyle(titleStyle);  
			}
            // 四个参数分别是：起始行，起始列，结束行，结束列 	
            sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) (headArr.length-1)));  
			
            titleStyle=workbook.createCellStyle();
            titleStyle.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
            titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            
            font = workbook.createFont();
            font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);	
            font.setFontHeightInPoints((short)13);
            titleStyle.setFont(font);// 把字体应用到当前的样式	
			row = sheet.createRow(rowIndex++);// 产生表格标题行
			for (short i = 0; i < headArr.length; i++) {
				cell = row.createCell(i);
				cell.setCellStyle(titleStyle);
				cell.setCellValue(new HSSFRichTextString(headArr[i]));
			}			
			//内容数据
			titleStyle=workbook.createCellStyle();
			titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            if(dataList== null || dataList.isEmpty())
            	return;
			short dataType = 0;//默认为字符串数组
			if(dataList.get(0) instanceof Map<?, ?>)
				dataType = 1;
			else if(dataList.get(0) instanceof List<?>)
				dataType = 2;
			if(dataType == 0){
				String[] dataArr = null;
				for (Object data : dataList) {
					dataArr = (String[]) data;
					if (dataArr == null)
						continue;
					row = sheet.createRow(rowIndex++);
					for(short i=0; i<headArr.length; i++){
		            	if(i<dataArr.length){
		            		Object val = dataArr[i];
		            		if(rendererArr != null&&rendererArr[i] != null)
		            			val = rendererArr[i].renderer(dataArr[i],i,dataArr);		            		
		            		fillCell(row, titleStyle, font, i, val);
		            	}
	            	}
	            }
			}else if(dataType == 1){
				Map<?, ?> map = null;
				for (Object data : dataList) {
					map = (Map<?, ?>) data;
					if (map == null)
						continue;
					Object [] dataArr= map.values().toArray();
					if (dataArr == null)
						continue;
					row = sheet.createRow(rowIndex++);
					for(short i=0; i<headArr.length; i++){
		            	if(i<dataArr.length){
		            		Object val = dataArr[i];
		            		if(rendererArr != null && rendererArr[i] != null)
		            			val = rendererArr[i].renderer(dataArr[i],i,dataArr);	
		            		fillCell(row, titleStyle, font, i, val);
		            	}
	            	}
	            }				
			}else if(dataType == 2){
				List<?> list = null;
				for (Object data : dataList) {
					list = (List<?>) data;
					if (list == null || list.isEmpty())
						continue;
					row = sheet.createRow(rowIndex++);
					for(short i=0; i<headArr.length; i++){
		            	if(i<list.size()){
		            		Object val =  list.get(i);
		            		if(rendererArr != null && rendererArr[i] != null)
		            			val = rendererArr[i].renderer(list.get(i), i, list);
		            		fillCell(row, titleStyle, font, i, val);
		            	}
	            	}
	            }				
			}else
				throw new Exception("导出excel文档失败，原因：不知道的数据类别");
            workbook.write(out);
		}catch (Exception e) {
			throw new Exception("导出excel文档失败，异常："+e.getMessage());
		}finally{
//			if(out != null){
//				try {
//					out.close();
//				} catch (IOException e) {}
//			}
		}
	}
	
	private void fillCell(HSSFRow row,HSSFCellStyle style, HSSFFont font,Short cellIndex, Object value){
		if(value == null)
			value = "";
//			System.out.println(value.getClass().getName());
		HSSFCell cell = row.createCell(cellIndex);
		cell.setCellStyle(style);
//		cell.
		HSSFRichTextString richStr = null;
		
		if(value instanceof Timestamp){
			richStr = new HSSFRichTextString(this.toDateEnFull(((Timestamp)value)));
		}else				
			richStr = new HSSFRichTextString(value.toString());
		
		richStr.applyFont(font);
		if(richStr != null)
			cell.setCellValue(richStr);
	}
	
        public String toDateEnFull(Timestamp value){
        
           
            String tsStr = "";  
            DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");  
            try {  
                //方法一  
                tsStr = sdf.format(value);  
               
              
               
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        return tsStr;
        }
	public static void main(String[] args) {
		File file = new File("E:/out.xls");
		FileOutputStream out = null;
		try {
			if(file.exists())
				file.delete();
			if(file.createNewFile())
				out = new FileOutputStream(file);
//			List<String[]> list = new ArrayList<String[]>();
//			String[] dataArr = null;
//			for(short r=0; r<20; r++){
//				dataArr = new String[new ExcelUtil().headArr.length];
//				for(short i=0; i<dataArr.length; i++){
//					dataArr[i]=r+"=data="+i;
//				}
//				list.add(dataArr);
//			}
			
//			List<Map<String, ?>> list = new ArrayList<Map<String, ?>>();
//			Map<String, Object> dataArr = null;
//			for(short r=0; r<20; r++){
//				dataArr = new HashMap<String, Object>();
////				dataArr = new String[new ExcelUtil().headArr.length];
//				for(short i=0; i<new ExcelUtil().headArr.length; i++){
//					dataArr.put(r+""+i, r+"=data="+i);
//				}
//				list.add(dataArr);
//			}
			
			List<List<?>> dataList = new ArrayList<List<?>>();
			for(short r=0; r<20; r++){
				List<Object> list = new ArrayList<Object>();
				for(short i=0; i<new ExcelUtil().headArr.length; i++){
					list.add(r+"=data="+i);
				}
				dataList.add(list);
			}
			new ExcelUtil().exportExcel(dataList, out);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String[] getHeadArr() {
		return headArr;
	}

	public void setHeadArr(String[] headArr) {
		this.headArr = headArr;
	}
}
