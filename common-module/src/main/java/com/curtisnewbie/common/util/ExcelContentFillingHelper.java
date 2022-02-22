package com.curtisnewbie.common.util;

import org.apache.poi.ss.usermodel.*;

import java.util.*;

/**
 * Helper for filling content in an Excel file
 *
 * @author yongj.zhuang
 */
public class ExcelContentFillingHelper<T> {

    private Sheet sheet;
    private int indexOfFirstRow = 0;
    private Class<T> dataClazz;
    private List<T> dataList;
    private String title;

    /**
     * Create new helper for given data class
     */
    public static <V> ExcelContentFillingHelper<V> forDataClass(Class<V> dataClazz) {
        final ExcelContentFillingHelper<V> helper = new ExcelContentFillingHelper<>();
        helper.dataClazz = dataClazz;
        return helper;
    }

    public ExcelContentFillingHelper<T> setSheet(Sheet sheet) {
        this.sheet = sheet;
        return this;
    }

    public ExcelContentFillingHelper<T> setIndexOfFirstRow(int indexOfFirstRow) {
        this.indexOfFirstRow = indexOfFirstRow;
        return this;
    }

    public ExcelContentFillingHelper<T> setDataList(List<T> dataList) {
        this.dataList = dataList;
        return this;
    }

    public ExcelContentFillingHelper<T> setTitle(String title) {
        this.title = title;
        return this;
    }

    public void fillContent() {
        ExcelUtils.createTitleThenFillColNamesAndRows(sheet, dataClazz, dataList, indexOfFirstRow, Optional.ofNullable(title));
    }
}
