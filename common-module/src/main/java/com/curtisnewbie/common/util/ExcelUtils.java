package com.curtisnewbie.common.util;

import lombok.*;
import org.apache.poi.ss.usermodel.*;
import org.springframework.util.*;

import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.stream.*;

import static com.curtisnewbie.common.util.OptionalUtils.*;

/**
 * Excel Utils
 *
 * @author yongj.zhuang
 */
public final class ExcelUtils {

    private ExcelUtils() {

    }

    /**
     * Create new empty workbook
     */
    public static Workbook create() throws IOException {
        return WorkbookFactory.create(false);
    }

    /**
     * Read and create a new workbook
     */
    public static Workbook create(InputStream inputStream) throws IOException {
        return WorkbookFactory.create(inputStream);
    }

    /**
     * Create new StringType Cell and set value
     */
    public static void createStringCell(Row row, int col, String value) {
        Assert.notNull(row, "row == null");
        Assert.notNull(value, "value == null");

        final Cell cell = row.createCell(col, CellType.STRING);
        cell.setCellValue(value);
    }

    /**
     * Fill column names (the first row) and then the content
     *
     * @param sheet           sheet
     * @param data            content for the rows
     * @param indexOfFirstRow index of first row
     */
    public static <T> void fillColNamesAndRows(Sheet sheet, List<T> data, int indexOfFirstRow) {
        Assert.notNull(sheet, "sheet == null");
        Assert.notNull(data, "rows == null");

        if (data.isEmpty()) return;

        final IntWrapper rowIndex = new IntWrapper(indexOfFirstRow - 1);

        // parse the first object's class using reflection
        final List<AnnotatedExcelColumn> annotatedExcelColumns = parseFieldNames(data.get(0));
        final List<String> colNames = annotatedExcelColumns
                .stream()
                .map(AnnotatedExcelColumn::getDisplayedColumnName)
                .collect(Collectors.toList());

        // the first row is the name of these columns
        fillRow(sheet.createRow(rowIndex.incr()), colNames);

        // starts filling content, for each row
        for (int i = 0; i < data.size(); i++) {
            final Row row = sheet.createRow(rowIndex.incr());
            final Object currData = data.get(i);

            // for each field of the object
            for (int j = 0; j < annotatedExcelColumns.size(); j++) {
                setCellReflective(row, j, annotatedExcelColumns.get(j).getField(), currData);
            }
        }
    }

    /**
     * Set value to the specified cell using reflection
     */
    public static void setCellReflective(Row row, int col, Field f, Object o) {
        f.setAccessible(true);
        try {
            createStringCell(row, col, getNullableAndConvert(f.get(o), Object::toString, ""));
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Failed to get value, fieldName: " + f.getName(), e);
        }
    }

    /**
     * Fill column names
     */
    public static void fillRow(Row row, List<String> names) {
        Assert.notNull(row, "row == null");
        Assert.notNull(names, "names == null");

        for (int i = 0; i < names.size(); i++) {
            createStringCell(row, i, names.get(i));
        }
    }

    /**
     * Parse field names using {@link ExcelColName}
     */
    public static List<AnnotatedExcelColumn> parseFieldNames(Object o) {
        Assert.notNull(o, "object is null, unable to parse field names");

        // fieldName -> column name
        final List<AnnotatedExcelColumn> colList = new ArrayList<>();
        final Class<?> clz = o.getClass();
        final Field[] declareFields = clz.getDeclaredFields();

        for (final Field f : declareFields) {
            final ExcelColName[] annot = f.getDeclaredAnnotationsByType(ExcelColName.class);
            if (annot.length == 0) continue;

            // only use the first annotation
            final ExcelColName ecn = annot[0];

            colList.add(new AnnotatedExcelColumn(f, ecn.value()));
        }
        return colList;
    }

    @Data
    @AllArgsConstructor
    public static class AnnotatedExcelColumn {
        private final Field field;
        private final String displayedColumnName;
    }
}
