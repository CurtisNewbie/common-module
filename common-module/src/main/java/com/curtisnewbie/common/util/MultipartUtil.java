package com.curtisnewbie.common.util;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.*;
import org.apache.commons.io.*;
import org.springframework.web.multipart.*;
import org.springframework.web.multipart.commons.*;

import java.io.*;

/**
 * Util for {@link MultipartFile}
 *
 * @author yongj.zhuang
 */
public final class MultipartUtil {

    private MultipartUtil() {
    }

    /** 组装 MultipartFile, 不需要创建临时文件 */
    public static MultipartFile toMultipartFile(InputStream inputStream, String fileName) throws IOException {
        FileItem fileItem = new DiskFileItemFactory().createItem("file", "*/*", Boolean.TRUE, fileName);
        IOUtils.copy(inputStream, fileItem.getOutputStream());
        return new CommonsMultipartFile(fileItem);
    }


}
