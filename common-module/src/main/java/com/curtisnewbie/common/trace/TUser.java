package com.curtisnewbie.common.trace;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * User's trace info
 *
 * @author yongj.zhuang
 */
@Data
@AllArgsConstructor
@Builder
public class TUser {

    private final int userId;
    private final String username;
    private final String userNo;
    private final String roleNo;

    @Deprecated private final String role;
    @Deprecated private final List<String> services;
}
