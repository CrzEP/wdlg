package com.dlg.wdlg.util;

import com.dlg.wdlg.exception.BusinessException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * csv 工具类
 */
public class CsvUtil {

    /**
     * 加载csv文件
     *
     * @param filePath filePath
     * @param heads    heads 头,长度等于读取每行长度
     * @return 内容集合
     */
    public static List<List<String>> loadCsvFile(String filePath, String[] heads) {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new BusinessException("file not exist");
        }
        return loadCsvContent(heads, file);
    }

    /**
     * 加载内容
     *
     * @param heads 头
     * @param file  文件
     * @return 内容
     */
    private static List<List<String>> loadCsvContent(String[] heads, File file) {
        List<List<String>> contents = new ArrayList<>();
        try (Reader reader = new FileReader(file)) {
            CSVFormat csvFormat = CSVFormat.Builder.create().setHeader(heads).build();
            CSVParser parser = new CSVParser(reader, csvFormat);
            for (CSVRecord record : parser) {
                List<String> row = new ArrayList<>(heads.length);
                for (String head : heads) {
                    String value = record.get(head);
                    row.add(value);
                }
                contents.add(row);
            }
            return contents;
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

}
