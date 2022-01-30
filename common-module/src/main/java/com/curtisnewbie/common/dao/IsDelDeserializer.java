package com.curtisnewbie.common.dao;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * JsonDeserializer for {@link IsDel}
 *
 * @author yongjie.zhuang
 */
public class IsDelDeserializer extends JsonDeserializer<IsDel> {

    @Override
    public IsDel deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        final String s = p.getValueAsString();
        if (!StringUtils.hasText(s))
            return null;

        int n = Integer.parseInt(s);
        for (IsDel e : IsDel.values())
            if (e.getValue() == n)
                return e;

        return null;
    }
}
