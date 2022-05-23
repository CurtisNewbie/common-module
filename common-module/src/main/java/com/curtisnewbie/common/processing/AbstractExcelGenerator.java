package com.curtisnewbie.common.processing;

import com.curtisnewbie.common.util.ExcelContentFillingHelper;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.util.List;

/**
 * Abstract implementation of ExcelGenerator
 * <p>
 * Subclass may either override {@link #generate(List)} } method and write data to the workbook, or implement the
 * following methods and let this abstract do the rest
 * <ul>
 * <li>{@link #getTClass()}  return the class of the value object in the list, typically, this will be the class that represents the row</li>
 * <li>{@link #getTitle()} return the title on the sheet (by default it returns null)</li>
 * <li>{@link #getSheetName()} ()} return the name of the sheet</li>
 * </ul>
 *
 * @author yongj.zhuang
 */
public abstract class AbstractExcelGenerator<T> implements ExcelGenerator<T>, Closeable {

    protected Workbook workbook;

    public AbstractExcelGenerator() throws IOException {
        this.workbook = newWorkbook();
    }

    @Override
    public Workbook getWorkbook() {
        return workbook;
    }

    @Override
    public void close() throws IOException {
        workbook.close();
    }

    protected Sheet createSheet(String sheetName) {
        return workbook.createSheet(sheetName);
    }

    /**
     * Preprocess the sheet before generating the content
     */
    protected void preprocessing(Sheet sheet) {
        // do nothing
    }

    /**
     * Return the class of the value object in the list, typically, this will be the class that represents the row
     */
    abstract protected Class<T> getTClass();

    /**
     * Return the title on the sheet (this may return null, if the title is not needed at all)
     */
    protected String getTitle() {
        return null;
    }

    /**
     * Return the sheet name
     */
    abstract protected String getSheetName();

    @Override
    public void generate(List<T> t) {
        final Sheet sheet = createSheet(getSheetName());
        preprocessing(sheet);

        // fill title, column names and content
        ExcelContentFillingHelper.forDataClass(getTClass())
                .setDataList(t)
                .setTitle(getTitle()) // nullable
                .setSheet(sheet)
                .fillContent();
    }
}
