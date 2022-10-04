package com.curtisnewbie.common.util;

import com.alibaba.excel.*;
import com.alibaba.excel.write.metadata.*;
import org.springframework.util.*;

import javax.servlet.http.*;
import java.io.*;
import java.net.*;
import java.nio.charset.*;
import java.util.*;

import static com.curtisnewbie.common.util.Runner.*;

/**
 * Utils for alibaba Easy Excel
 *
 * @author yongj.zhuang
 */
public final class EzExcelUtils {

    private EzExcelUtils() {
    }

    /**
     * Resolve the path to file under 'resource' folder
     */
    public static String resolveResourcePath(String resourcePath) {
        final URL url = EzExcelUtils.class.getClassLoader().getResource(resourcePath);
        Assert.notNull(url, String.format("File not found, resource path: %s", resourcePath));
        return url.getPath();
    }

    /**
     * Generate excel based on template
     *
     * @param params       list of params (for rows)
     * @param outputStream outputStream
     * @param templatePath path to template file
     */
    public static void writeExcel(List<?> params, OutputStream outputStream, String templatePath) {
        try (ExcelWriter excelWriter = EasyExcel.write(new BufferedOutputStream(outputStream)).withTemplate(templatePath).build()) {
            WriteSheet writeSheet = EasyExcel.writerSheet().build();
            excelWriter.fill(params, writeSheet);
            excelWriter.finish();
        }
    }

    /**
     * Convenience method to set response header for exporting excel file
     *
     * @param response servlet response
     * @param filename filename
     * @param isCn     whether the filename contains chinese characters
     */
    public static void setExcelResponseHeaders(HttpServletResponse response, String filename, boolean isCn) {
        response.setContentType("application/excel");
        response.setCharacterEncoding("utf-8");
        final String fileName = tryCall(() -> URLEncoder.encode(filename, "utf-8"));
        if (isCn)
            response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
        else
            response.setHeader("Content-disposition", "attachment; filename=" + filename);
    }
}

