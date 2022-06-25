package com.curtisnewbie.common.util;

import com.curtisnewbie.common.processing.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.junit.jupiter.api.*;

import java.io.*;
import java.math.*;
import java.time.*;
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
        private Integer age;
        @ExcelCol("Title")
        private String title;
        @ExcelCol("A Nice Guy")
        private Boolean aNiceGuy;
        @ExcelCol("Birth")
        private LocalDateTime birth;
        @ExcelCol("BigInt Num")
        private BigInteger bigIntNum;
        @ExcelCol("BigDec Num")
        private BigDecimal bigDecNum;
    }

    public static class DDummyExcelParser extends AbstractExcelParser<DDummy> {

        @Override
        protected Class<DDummy> supplyTClass() {
            return DDummy.class;
        }
    }


}
