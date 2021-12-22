package com.fool.demo;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;

public class PoiTest {


    public static void main(String[] args) throws IOException {
        openWord();
        // operateExcel();
    }

    public static void openWord() throws IOException {
        String filePath = "E:\\documents\\对接文档.docx";

        XWPFDocument xwpfDocument = new XWPFDocument(new FileInputStream(filePath));

    }


    public static void operateExcel() {
        String excelFilePath = "E:\\temporary\\省市地区代码.xlsx";

        String currentParentCode = null;

        File file = new File(excelFilePath);
        try (InputStream input = new FileInputStream(file)) {
            byte[] bytes = IOUtils.toByteArray(input);
            Workbook workbook = new XSSFWorkbook(new ByteArrayInputStream(bytes));
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1, len = sheet.getLastRowNum(); i < len; i++) {
                Row row = sheet.getRow(i);

                String code = String.valueOf(row.getCell(0).getNumericCellValue());
                String name = row.getCell(1).getStringCellValue().replaceAll(" ", "");

                if (currentParentCode != null && code.startsWith(currentParentCode.substring(0, 3))) {
                    row.getCell(2).setCellValue(currentParentCode);
                }

                if (code.endsWith("00")) {
                    currentParentCode = code;
                }


            }
            workbook.write(new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
