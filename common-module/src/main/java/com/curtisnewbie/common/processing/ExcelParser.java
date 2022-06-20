package com.curtisnewbie.common.processing;

import com.curtisnewbie.common.util.*;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.util.*;

/**
 * Parser
 *
 * @author yongj.zhuang
 */
public interface ExcelParser<T> {

    /**
     * Parse excel file
     */
    List<T> parse(InputStream inputStream) throws IOException;

    /**
     * Create Workbook
     */
    default Workbook createWorkbook(InputStream inputStream) throws IOException {
        return ExcelUtils.create(inputStream);
    }
}
