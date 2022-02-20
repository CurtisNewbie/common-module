package com.curtisnewbie.common.processing;

import org.apache.poi.ss.usermodel.*;

import java.io.*;

/**
 * Abstract ExcelGenerator
 *
 * @author yongj.zhuang
 */
public abstract class AbstractExcelGenerator<T> implements ExcelGenerator<T> {

    protected Workbook workbook;

    public AbstractExcelGenerator() throws IOException {
        this.workbook = newWorkbook();
    }

    @Override
    public Workbook getWorkbook() {
        return workbook;
    }

}
