package com.curtisnewbie.common.util;

import com.curtisnewbie.common.data.IntWrapper;
import lombok.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.*;
import org.springframework.util.*;

import java.io.*;
import java.lang.reflect.*;
import java.math.*;
import java.time.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.stream.*;

import static com.curtisnewbie.common.util.AssertUtils.*;
import static com.curtisnewbie.common.util.ExceptionUtils.*;
import static com.curtisnewbie.common.util.OptionalUtils.*;
import static com.curtisnewbie.common.util.ReflectUtils.*;
import static java.lang.Integer.*;

/**
 * Excel Utils
 *
 * @author yongj.zhuang
 */
public final class ExcelUtils {

    public static final short DEF_CELL_HEIGHT = 750;

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
    public static Cell createStringCell(Row row, int col, String value) {
        Assert.notNull(row, "row == null");
        Assert.notNull(value, "value == null");

        final Cell cell = row.createCell(col, CellType.STRING);
        cell.setCellValue(value);
        cell.getCellStyle().setWrapText(true);
        cell.getCellStyle().setAlignment(HorizontalAlignment.CENTER);
        return cell;
    }

    /**
     * Create cell style
     */
    public static CellStyle createCellStyle(Workbook workbook) {
        return workbook.createCellStyle();
    }

    /**
     * Create cell style with borders
     */
    public static CellStyle createBorderedCellStyle(Workbook workbook) {
        final CellStyle cellStyle = createCellStyle(workbook);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setWrapText(true);
        return cellStyle;
    }

    /**
     * Create font
     */
    public static Font createFont(Workbook workbook) {
        return workbook.createFont();
    }

    /**
     * Create a merged cell as the title
     */
    public static <T> void createTitle(final Sheet sheet, final Row row, final String title, final int colWidth) {
        Assert.notNull(sheet, "sheet == null");
        Assert.notNull(row, "row == null");
        Assert.notNull(title, "title == null");

        // fixed row height
        row.setHeight(DEF_CELL_HEIGHT);

        final Cell firstCell = createStringCell(row, 0, title);

        // setup cell style
        final CellStyle cellStyle = createCellStyle(sheet.getWorkbook());
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);

        // font
        final Font font = createFont(sheet.getWorkbook());
        font.setBold(true);
        font.setFontHeightInPoints((short) (font.getFontHeightInPoints() + 3));
        cellStyle.setFont(font);
        firstCell.setCellStyle(cellStyle);

        for (int i = 1; i < colWidth; i++) {
            final Cell cell = row.createCell(i);
            cell.setCellStyle(cellStyle);
        }

        // merge these cells
        sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), 0, colWidth - 1));
    }

    /**
     * Create a title ({@link #createTitle(Sheet, Row, String, int)}), fill column names (the second row) and then the content
     *
     * @param sheet    sheet
     * @param data     content for the rows
     * @param titleOpt title
     */
    public static <T> void createTitleThenFillColNamesAndRows(final Sheet sheet, final Class<T> clazz, final List<T> data,
                                                              final Optional<String> titleOpt) {
        createTitleThenFillColNamesAndRows(sheet, clazz, data, 0, titleOpt);
    }

    /**
     * Create a title ({@link #createTitle(Sheet, Row, String, int)}), fill column names (the second row) and then the content
     *
     * @param sheet           sheet
     * @param data            content for the rows
     * @param indexOfFirstRow index of first row
     * @param titleOpt        title
     */
    public static <T> void createTitleThenFillColNamesAndRows(final Sheet sheet, final Class<T> clazz, final List<T> data,
                                                              final int indexOfFirstRow, final Optional<String> titleOpt) {

        Assert.notNull(sheet, "sheet == null");
        Assert.notNull(clazz, "clazz == null");
        Assert.notNull(data, "data == null");

        final IntWrapper rowIndex = new IntWrapper(indexOfFirstRow - 1);

        // parse the first object's class using reflection
        final List<AnnotatedExcelColumn> annotatedExcelColumns = parseExcelFields(clazz);

        // create a merged col as the title for the first row, if the title is present
        titleOpt.ifPresent(title -> createTitle(sheet, createRow(sheet, rowIndex.incr()), title, annotatedExcelColumns.size()));

        final List<String> colNames = annotatedExcelColumns
                .stream()
                .map(AnnotatedExcelColumn::getDisplayedColumnName)
                .collect(Collectors.toList());

        // the first row is the name of these columns
        final CellStyle colCellStyle = createBorderedCellStyle(sheet.getWorkbook());
        colCellStyle.setAlignment(HorizontalAlignment.CENTER);
        colCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        fillRow(createRow(sheet, rowIndex.incr()), colNames, Optional.of(colCellStyle));

        // starts filling content, for each row
        for (int i = 0; i < data.size(); i++) {
            final Row row = createRow(sheet, rowIndex.incr());
            final Object currData = data.get(i);

            // for each field of the object
            for (int j = 0; j < annotatedExcelColumns.size(); j++) {
                final AnnotatedExcelColumn col = annotatedExcelColumns.get(j);
                setCellReflectively(row, j, col.getField(), Optional.ofNullable(col.getToStringMethodName()), currData);
            }
        }
    }

    /**
     * Fill column names (the first row) and then the content
     *
     * @param sheet           sheet
     * @param data            content for the rows
     * @param indexOfFirstRow index of first row
     */
    public static <T> void fillColNamesAndRows(Sheet sheet, Class<T> clazz, List<T> data, int indexOfFirstRow) {
        createTitleThenFillColNamesAndRows(sheet, clazz, data, indexOfFirstRow, Optional.empty());
    }

    /**
     * Set value to the specified cell using reflection
     */
    public static void setCellReflectively(final Row row, final int col, final Field f, final Optional<String> toStringMethodName, final Object o) {
        final Function<Object, String> toStringFunc;
        if (toStringMethodName.isPresent() && StringUtils.hasText(toStringMethodName.get()) && o != null) {
            // invoke the method, and then call toString on the returned value
            toStringFunc = fieldValue -> {

                if (fieldValue == null) return "";

                // try to find the method with the toStringMethodName
                final Class fieldValueClz = fieldValue.getClass();
                final Optional<Method> fieldValueMethod = findMethod(toStringMethodName.get(), fieldValueClz);
                isTrue(fieldValueMethod.isPresent(), "Method %s not found from class %s", toStringMethodName.get(), fieldValueClz);

                final Object returnedFromInvoke = invokeMethod(fieldValueMethod.get(), fieldValue);
                return getNullableAndConvert(returnedFromInvoke, Object::toString, "");
            };
        } else {
            toStringFunc = Object::toString; // call toString directly
        }
        createStringCell(row, col, getNullableAndConvert(getFieldValue(f, o), toStringFunc, ""));
    }

    /**
     * Fill column names (with custom style)
     */
    public static void fillRow(Row row, List<String> names, Optional<CellStyle> cellStyle) {
        Assert.notNull(row, "row == null");
        Assert.notNull(names, "names == null");

        for (int i = 0; i < names.size(); i++) {
            final Cell cell = createStringCell(row, i, names.get(i));
            cellStyle.ifPresent(st -> cell.setCellStyle(st));
        }
    }

    /**
     * Fill column names
     */
    public static void fillRow(Row row, List<String> names) {
        fillRow(row, names, Optional.empty());
    }

    /**
     * Parse field annotated with {@link ExcelCol}
     */
    public static List<AnnotatedExcelColumn> parseExcelFields(Class<?> clz) {
        Assert.notNull(clz, "clazz is null, unable to parse field names");

        final List<AnnotatedExcelColumn> colList = new ArrayList<>();
        declaredFieldsAsStream(clz).forEach(f ->
                firstDeclaredAnnotation(f, ExcelCol.class)
                        .ifPresent(excelCol -> colList.add(new AnnotatedExcelColumn(f, excelCol.value(), excelCol.toStringMethod())))
        );
        return colList;
    }

    /**
     * Create row with default height ({@link #DEF_CELL_HEIGHT}
     */
    public static Row createRow(Sheet sheet, int rowIndex) {
        final Row row = sheet.createRow(rowIndex);
        row.setHeight(DEF_CELL_HEIGHT);
        return row;
    }

    /**
     * Parse Excel Rows of data
     * <p>
     * Supports following data types:
     * <ul>
     *    <li>Integer</li>
     *    <li>Double</li>
     *    <li>Float</li>
     *    <li>String</li>
     *    <li>LocalDateTime</li>
     *    <li>Date</li>
     *    <li>Boolean</li>
     *    <li>BigInteger</li>
     *    <li>BigDecimal</li>
     * </ul>
     */
    public static <T> List<T> parseExcelData(Workbook workbook, Class<T> tclz) {
        Assert.notNull(workbook, "workbook == null");
        Assert.notNull(tclz, "tclz == null");

        final int cnt = workbook.getNumberOfSheets();
        if (cnt < 1) return new ArrayList<>();

        final List<AnnotatedExcelColumn> cols = parseExcelFields(tclz);
        if (cols.isEmpty()) return new ArrayList<>();

        final Map<String /* column name */, Field> fmap = new HashMap<>();
        for (final AnnotatedExcelColumn aec : cols) {
            final Field field = aec.getField();
            field.setAccessible(true);
            fmap.put(aec.getDisplayedColumnName(), field);
        }

        // only parse the first sheet
        final Sheet fstSheet = workbook.getSheetAt(0);
        int rowPtr = 0;

        final FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();

        // first row
        Row row = fstSheet.getRow(rowPtr++);
        if (row == null) return new ArrayList<>();
        final List<String> titles = extractRowValues(row, formulaEvaluator);
        if (titles.isEmpty()) return new ArrayList<>();

        // only record the titles that we have
        final Map<String /* title */, Integer /* index */> idxMap = new HashMap<>();
        for (int i = 0; i < titles.size(); i++)
            idxMap.put(titles.get(i), i);

        final Constructor<T> defConstr = throwIfError((Callable<Constructor<T>>) tclz::getDeclaredConstructor);
        final List<T> data = new ArrayList<>();

        final DataFormatter fmtr = new DataFormatter();
        while ((row = fstSheet.getRow(rowPtr++)) != null) {
            final T t = throwIfError((Callable<T>) defConstr::newInstance);

            for (Map.Entry<String, Integer> entry : idxMap.entrySet()) {
                String key = entry.getKey();
                Integer idx = entry.getValue();

                final Field field = fmap.get(key);
                if (field == null) continue; // it's not a field in T class

                final Class<?> type = field.getType();
                final Cell cell = row.getCell(idx, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                if (cell == null) continue; // the cell is blank or null

                Object val = null;
                if (type.isAssignableFrom(Integer.class)) val = parseInt(fmtr.formatCellValue(cell, formulaEvaluator));
                else if (type.isAssignableFrom(Double.class)) val = cell.getNumericCellValue();
                else if (type.isAssignableFrom(Float.class)) val = cell.getNumericCellValue();
                else if (type.isAssignableFrom(String.class)) val = fmtr.formatCellValue(cell, formulaEvaluator);
                else if (type.isAssignableFrom(LocalDateTime.class)) val = cell.getLocalDateTimeCellValue();
                else if (type.isAssignableFrom(Date.class)) val = cell.getDateCellValue();
                else if (type.isAssignableFrom(Boolean.class)) val = cell.getBooleanCellValue();
                else if (type.isAssignableFrom(BigInteger.class)) {
                    final String sv = fmtr.formatCellValue(cell, formulaEvaluator);
                    if (StringUtils.hasText(sv)) val = new BigInteger(sv);
                } else if (type.isAssignableFrom(BigDecimal.class)) {
                    final String sv = fmtr.formatCellValue(cell, formulaEvaluator);
                    if (StringUtils.hasText(sv)) val = new BigDecimal(sv);
                }

                if (val != null) {
                    final Object fieldValue = val;
                    throwIfError(() -> field.set(t, fieldValue));
                }
            }
            data.add(t);
        }
        return data;
    }

    public static List<String> extractRowValues(Row row, FormulaEvaluator formulaEvaluator) {
        DataFormatter formatter = new DataFormatter();
        List<String> val = new ArrayList<>();
        for (Cell cell : row) {
            val.add(formatter.formatCellValue(cell, formulaEvaluator));
        }
        return val;
    }

    @Data
    @AllArgsConstructor
    public static class AnnotatedExcelColumn {
        /** field */
        private final Field field;
        /** name of column (being displayed in excel) */
        private final String displayedColumnName;
        /** method to convert the value to String */
        private final String toStringMethodName;
    }


}
