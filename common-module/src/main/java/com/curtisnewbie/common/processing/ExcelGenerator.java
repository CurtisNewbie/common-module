package com.curtisnewbie.common.processing;

import com.curtisnewbie.common.util.*;
import org.apache.poi.ss.usermodel.*;

import java.io.*;

/**
 * Generator of Excel file
 *
 * @author yongj.zhuang
 */
public interface ExcelGenerator<T> {

    /**
     * Write workbook to OutputStream
     */
    default void write(OutputStream out) throws IOException {
        try (OutputStream op = out) {
            getWorkbook().write(op);
        }
    }

    /**
     * Generate content and write to OutputStream
     */
    default void generateAndWrite(T t, OutputStream out) throws IOException {
        generate(t);
        write(out);
    }

    /**
     * Generate content
     */
    void generate(T t);

    /**
     * Get workbook
     */
    Workbook getWorkbook();

    /**
     * Create a new workbook
     */
    default Workbook newWorkbook() throws IOException {
        return ExcelUtils.create();
    }

}
