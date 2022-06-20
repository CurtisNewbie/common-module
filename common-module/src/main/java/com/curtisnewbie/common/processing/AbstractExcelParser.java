package com.curtisnewbie.common.processing;

import com.curtisnewbie.common.util.*;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.util.*;

/**
 * Abstract Implementation of ExcelParser (T must be a class that only contains String type fields)
 *
 * @author yongj.zhuang
 */
public abstract class AbstractExcelParser<T> implements ExcelParser<T> {

    @Override
    public List<T> parse(InputStream inputStream) throws IOException {
        final Workbook workbook = createWorkbook(inputStream);
        return ExcelUtils.parseExcelData(workbook, supplyTClass());
    }

    abstract protected Class<T> supplyTClass();

}
