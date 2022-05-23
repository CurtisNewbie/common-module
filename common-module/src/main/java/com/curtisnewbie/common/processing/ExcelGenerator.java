package com.curtisnewbie.common.processing;

import com.curtisnewbie.common.util.*;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.util.List;

/**
 * Generator of Excel file
 * <p>
 * Subclasses will need to implement the following method:
 * <ul>
 *  <li>{@link #generate(List)} method that writes the content to the workbook (same one returned by {@link #getWorkbook()})</li>
 * </ul>
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
    default void generateAndWrite(List<T> t, OutputStream out) throws IOException {
        generate(t);
        write(out);
    }

    /**
     * Generate content
     */
    void generate(List<T> t);

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
