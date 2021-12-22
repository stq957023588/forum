package com.fool.demo;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author fool
 * @date 2021/12/15 15:29
 */
public class ReadWordTable {

    public static void main(String[] args) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream("E:\\temporary\\论坛设计.docx");
            XWPFDocument document = new XWPFDocument(inputStream);
            List<XWPFTable> tables = document.getTables();
            System.out.println(tables.size());

            for (XWPFTable table : tables) {
                List<XWPFTableRow> rows = table.getRows();
                List<String> defineStrings = new ArrayList<>(rows.size());
                for (int i = 1; i < rows.size(); i++) {
                    XWPFTableRow row = rows.get(i);
                    String field = row.getCell(0).getText();
                    String type = row.getCell(1).getText();
                    String check = row.getCell(2).getText();
                    String comment = row.getCell(3).getText();
                    comment = Optional.ofNullable(comment).orElse("");

                    String uniqueCheck = "唯一".equals(check) ? " unique " : "";
                    String autoIncr = "id".equalsIgnoreCase(field) ? " primary key auto_increment " : "";
                    String defineString = field + " " + type + " " + autoIncr + uniqueCheck + " comment '" + comment + "'";
                    defineStrings.add(defineString);

                }
                String collect = String.join(",", defineStrings);
                String sql = "create table table_name(" + collect + ")ENGINE=INNODB CHARSET=UTF8;";
                System.out.println(sql.toUpperCase());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
