package com.dlg.wdlg.util;

import com.dlg.wdlg.exception.BusinessException;
import com.google.common.io.LineReader;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

public class JsonFileUtil {

    public static String loadJsonFile(File file) {
        try {
            return FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new BusinessException("loadJsonFile fail : " + e);
        }
    }

    public static Reader loadJsonFileReader(File file) {
        String json = loadJsonFile(file);
        return new StringReader(json);
    }

    public static LineReader loadJsonAsLineReader(File file) {
        return new LineReader(loadJsonFileReader(file));
    }

    public static String nextLine(LineReader lineReader) {
        try {
            return lineReader.readLine();
        } catch (IOException e) {
            throw new BusinessException("nextLine fail : " + e);
        }
    }

}
