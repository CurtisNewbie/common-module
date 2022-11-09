package com.curtisnewbie.common.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Remote implementation of {@link RoleResourceValidator}
 *
 * @author yongj.zhuang
 */
@Slf4j
@Component
public class RemoteRoleResourceValidator implements RoleResourceValidator {

    // TODO impl this

    @Override
    public boolean validateRoleResource(String role, List<String> resources) {
        return true;
    }

}
