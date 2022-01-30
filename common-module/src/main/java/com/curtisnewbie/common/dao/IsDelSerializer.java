package com.curtisnewbie.common.dao;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * JsonSerializer for {@link IsDel}
 *
 * @author yongjie.zhuang
 */
public class IsDelSerializer extends JsonSerializer<IsDel> {

    @Override
    public void serialize(IsDel value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null)
            gen.writeNull();

        gen.writeNumber(value.getValue());
    }
}
