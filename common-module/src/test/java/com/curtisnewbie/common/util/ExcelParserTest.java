package com.curtisnewbie.common.util;

import com.curtisnewbie.common.processing.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.junit.jupiter.api.*;

import java.io.*;
import java.util.*;

/**
 * @author yongj.zhuang
 */
@Slf4j
public class ExcelParserTest {

    @Test
    public void should_parse_excel_file() throws IOException {
        final DDummyExcelParser dDummyExcelParser = new DDummyExcelParser();
        final List<DDummy> ddummies = dDummyExcelParser.parse(ExcelParserTest.class.getClassLoader()
                .getResourceAsStream("ddummy.xls"));
        Assertions.assertNotNull(ddummies);
        log.info("Dummies: {}", ddummies);
    }

    @Data
    public static class DDummy {
        @ExcelCol("Name")
        private String name;
        @ExcelCol("Age")
        private String age;
        @ExcelCol("Title")
        private String title;
    }

    public static class DDummyExcelParser extends AbstractExcelParser<DDummy> {

        @Override
        protected Class<DDummy> supplyTClass() {
            return DDummy.class;
        }
    }


}
