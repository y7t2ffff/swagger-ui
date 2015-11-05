package com.nani.common;

/**
 * chou7658@gmail.com
 */


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Xlsx轉json
 *
 * @author nani12a
 */
public class Xlsx {

  /**
   * Xlsx轉jJSONARRAY 傳入檔案回傳JSON 格式為 第一列標題(jsonKey) 其他為資料列 若有空格則看policy定義
   *
   * @see
   * https://poi.apache.org/apidocs/org/apache/poi/ss/usermodel/class-use/Row.MissingCellPolicy.html
   * @param f
   * @param policy
   * @return
   * @throws IOException
   * @throws InvalidFormatException
   */
  static public JSONArray getXlsxToJsonarray(String path) throws IOException, InvalidFormatException {
    //file path
    File f = new File(path);
    //creat workbool
    Workbook wb = WorkbookFactory.create(f);
    //get sheet 0 
    Sheet sheet = wb.getSheetAt(0);
    //title row
    Row titleRow = sheet.getRow(0);
    //data row
    Row dataRow = null;
    //data story array
    JSONArray ja = new JSONArray();
    // tmp
    JSONObject jtmp = null;
    //for all row without first row(title row)
    for (int i = 1; i <= sheet.getLastRowNum(); i++) {
      //creat tmp
      jtmp = new JSONObject();
      //get datarow 
      dataRow = sheet.getRow(i);
      //get title row  push key and data
      for (int j = 0; j < titleRow.getLastCellNum(); j++) {

        if (titleRow.getCell(j) != null) {
          titleRow.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
        }

        if (dataRow.getCell(j) != null) {
          dataRow.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
        }
        jtmp.put(
                titleRow.getCell(j, Row.CREATE_NULL_AS_BLANK).getStringCellValue(),
                dataRow.getCell(j, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
      }
      ja.put(jtmp);
    }
    f.exists();
    return ja;
  }

  /**
   * Xlsx轉jJSONARRAY 傳入檔案回傳JSON 格式為 第一列標題(jsonKey) 其他為資料列 若有空格則看policy定義
   *
   * @param path 檔案路徑
   * @param sheetName sheet名稱
   * @return data json
   * @throws IOException
   * @throws InvalidFormatException
   */
  static public JSONArray getXlsxToJsonarray(String path, String sheetName) throws IOException, InvalidFormatException {
    //file path
    File f = new File(path);
    //creat workbool
    Workbook wb = WorkbookFactory.create(f);
    //get sheet 0 
    Sheet sheet = wb.getSheet(sheetName);
    //title row
    Row titleRow = sheet.getRow(0);
    //data row
    Row dataRow = null;
    //data story array
    JSONArray ja = new JSONArray();
    // tmp
    JSONObject jtmp = null;
    //for all row without first row(title row)
    for (int i = 1; i <= sheet.getLastRowNum(); i++) {
      //creat tmp
      jtmp = new JSONObject();
      //get datarow 
      dataRow = sheet.getRow(i);
      //get title row  push key and data
      for (int j = 0; j < titleRow.getLastCellNum(); j++) {
        if (titleRow.getCell(j) != null) {
          titleRow.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
        }

        if (dataRow.getCell(j) != null) {
          dataRow.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
        }
        jtmp.put(
                titleRow.getCell(j, Row.CREATE_NULL_AS_BLANK).getStringCellValue(),
                dataRow.getCell(j, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
      }
      ja.put(jtmp);
    }

    f.exists();
    return ja;
  }

  /**
   *
   * @param path
   * @param policy
   * @return
   * @throws IOException
   * @throws InvalidFormatException
   */
  static public JSONArray getXlsxToJsonarray(String path, MissingCellPolicy policy) throws IOException, InvalidFormatException {
    //file path
    File f = new File(path);
    //creat workbool
    System.out.println("loadong...");
    Workbook wb = WorkbookFactory.create(f);
    //get sheet 0 
    Sheet sheet = wb.getSheetAt(0);
    //title row
    Row titleRow = sheet.getRow(0);
    //data row
    Row dataRow = null;
    //data story array
    JSONArray ja = new JSONArray();
    // tmp
    JSONObject jtmp = null;
    //for all row without first row(title row)
    for (int i = 1; i <= sheet.getLastRowNum(); i++) {
      if (i % 100 == 0) {
        System.out.println(i);
      }
      //creat tmp
      jtmp = new JSONObject();
      //get datarow 
      dataRow = sheet.getRow(i);
      //get title row  push key and data
      for (int j = 0; j < titleRow.getLastCellNum(); j++) {
        if (titleRow.getCell(j) != null) {
          titleRow.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
        }

        if (dataRow.getCell(j) != null) {
          dataRow.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
        }

        jtmp.put(
                titleRow.getCell(j, policy).getStringCellValue(),
                dataRow.getCell(j, policy).getStringCellValue());
      }
      ja.put(jtmp);
    }
    wb = null;
    f.exists();
    return ja;
  }
  /**
   *
   * @param path
   * @param policy
   * @return
   * @throws IOException
   * @throws InvalidFormatException
   */
  static public JSONArray getXlsxToJsonarray(InputStream is, MissingCellPolicy policy) throws IOException, InvalidFormatException {
    //file path
    System.out.println("loadong...");
    Workbook wb = WorkbookFactory.create(is);
    //get sheet 0 
    Sheet sheet = wb.getSheetAt(0);
    //title row
    Row titleRow = sheet.getRow(0);
    //data row
    Row dataRow = null;
    //data story array
    JSONArray ja = new JSONArray();
    // tmp
    JSONObject jtmp = null;
    //for all row without first row(title row)
    for (int i = 1; i <= sheet.getLastRowNum(); i++) {
      if (i % 100 == 0) {
        System.out.println(i);
      }
      //creat tmp
      jtmp = new JSONObject();
      //get datarow 
      dataRow = sheet.getRow(i);
      //get title row  push key and data
      for (int j = 0; j < titleRow.getLastCellNum(); j++) {
        if (titleRow.getCell(j) != null) {
          titleRow.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
        }

        if (dataRow.getCell(j) != null) {
          dataRow.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
        }

        jtmp.put(
                titleRow.getCell(j, policy).getStringCellValue(),
                dataRow.getCell(j, policy).getStringCellValue());
      }
      ja.put(jtmp);
    }
    wb = null;
    is.close();
    return ja;
  }
  /**
   * Xlsx轉jJSONARRAY 傳入檔案回傳JSON 格式為 第一列標題(jsonKey) 其他為資料列 若有空格則回傳空白
   *
   * @param path excel 路徑(xlsx)
   * @return jsonarray格式
   * @throws IOException
   * @throws InvalidFormatException
   */
  static public JSONArray getXlsxToJsonarray(File f) throws IOException, InvalidFormatException {
    //creat workbool
    Workbook wb = WorkbookFactory.create(f);
    //get sheet 0 
    Sheet sheet = wb.getSheetAt(0);
    //title row
    Row titleRow = sheet.getRow(0);
    //data row
    Row dataRow = null;
    //data story array
    JSONArray ja = new JSONArray();
    // tmp
    JSONObject jtmp = null;
    //for all row without first row(title row)
    for (int i = 1; i <= sheet.getLastRowNum(); i++) {
      //creat tmp
      jtmp = new JSONObject();
      //get datarow 
      dataRow = sheet.getRow(i);
      //get title row  push key and data
      for (int j = 0; j < titleRow.getLastCellNum(); j++) {
        jtmp.put(
                titleRow.getCell(j, Row.CREATE_NULL_AS_BLANK).getStringCellValue(),
                dataRow.getCell(j, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
      }
      ja.put(jtmp);
    }
    f.exists();
    return ja;
  }

  /**
   * Xlsx轉jJSONARRAY 傳入檔案回傳JSON 格式為 第一列標題(jsonKey) 其他為資料列 若有空格則回傳空白
   *
   * @param f excel file
   * @param sheetName sheetName
   * @return jsonarray data
   * @throws IOException
   * @throws InvalidFormatException
   */
  static public JSONArray getXlsxToJsonarray(File f, String sheetName) throws IOException, InvalidFormatException {
    //creat workbool
    Workbook wb = WorkbookFactory.create(f);
    //get sheet 0 
    Sheet sheet = wb.getSheet(sheetName);
    //title row
    Row titleRow = sheet.getRow(0);
    //data row
    Row dataRow = null;
    //data story array
    JSONArray ja = new JSONArray();
    // tmp
    JSONObject jtmp = null;
    //for all row without first row(title row)
    for (int i = 1; i <= sheet.getLastRowNum(); i++) {
      //creat tmp
      jtmp = new JSONObject();
      //get datarow 
      dataRow = sheet.getRow(i);
      //get title row  push key and data
      for (int j = 0; j < titleRow.getLastCellNum(); j++) {

        if (dataRow.getCell(j, Row.CREATE_NULL_AS_BLANK).getCellType() == Cell.CELL_TYPE_FORMULA) {
          continue;
        }
        dataRow.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
        jtmp.put(
                titleRow.getCell(j, Row.CREATE_NULL_AS_BLANK).getStringCellValue(),
                dataRow.getCell(j, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
      }
      ja.put(jtmp);
    }
    f.exists();
    return ja;
  }

  /**
   * Xlsx轉jJSONARRAY 傳入檔案回傳JSON 格式為 第一列標題(jsonKey) 其他為資料列 若有空格則看policy定義
   *
   * @see
   * https://poi.apache.org/apidocs/org/apache/poi/ss/usermodel/class-use/Row.MissingCellPolicy.html
   * @param f
   * @param policy
   * @return
   * @throws IOException
   * @throws InvalidFormatException
   */
  static public JSONArray getXlsxToJsonarray(File f, MissingCellPolicy policy) throws IOException, InvalidFormatException {
    //creat workbool
    Workbook wb = WorkbookFactory.create(f);
    //get sheet 0 
    Sheet sheet = wb.getSheetAt(0);
    //title row
    Row titleRow = sheet.getRow(0);
    //data row
    Row dataRow = null;
    //data story array
    JSONArray ja = new JSONArray();
    // tmp
    JSONObject jtmp = null;
    //for all row without first row(title row)
    for (int i = 1; i <= sheet.getLastRowNum(); i++) {
      //creat tmp
      jtmp = new JSONObject();
      //get datarow 
      dataRow = sheet.getRow(i);
      //get title row  push key and data
      for (int j = 0; j < titleRow.getLastCellNum(); j++) {
        jtmp.put(
                titleRow.getCell(j, policy).getStringCellValue(),
                dataRow.getCell(j, policy).getStringCellValue());
      }
      ja.put(jtmp);
    }
    f.exists();
    return ja;
  }
}
