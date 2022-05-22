package com.curtisnewbie.common.dao;

import com.curtisnewbie.common.trace.TUser;
import com.curtisnewbie.common.trace.TraceUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.springframework.jdbc.core.metadata.SqlServerCallMetaDataProvider;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Optional;

import static org.springframework.util.StringUtils.hasText;

/**
 * Mybatis Interceptor used to set update_by, create_by if possible
 *
 * @author yongj.zhuang
 * @see org.apache.ibatis.executor.Executor
 * @see <a href="https://mybatis.org/mybatis-3/configuration.html#plugins">Mybatis Plugins</a>
 */
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
})
@Slf4j
public class MBTraceInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        final Object[] args = invocation.getArgs();
        final MappedStatement ms = (MappedStatement) args[0];
        final Object param = args[1];
        final Executor exec = (Executor) invocation.getTarget();

        // preprocessing before insert/update/delete
        doPreProcessing(ms, param);

        // execute
        return exec.update(ms, param);
    }

    private void doPreProcessing(MappedStatement ms, Object param) {
        if (param == null) return;

        final Optional<TUser> tUser = TraceUtils.nullableTUser();
        if (!tUser.isPresent())
            return;

        final String username = tUser.get().getUsername();
        if (!hasText(username))
            return;

        if (param instanceof DaoSkeleton) {
            setTraceInfo(username, (DaoSkeleton) param, ms.getSqlCommandType());
        } else if (param instanceof Map) {
            // ParamMap
            final Map m = (Map) param;

            // check if any entry is a dao skeleton
            m.forEach((k, v) -> {
                if (v instanceof DaoSkeleton) {
                    setTraceInfo(username, (DaoSkeleton) v, ms.getSqlCommandType());
                }
            });
        }
    }

    private void setTraceInfo(String username, DaoSkeleton ds, SqlCommandType cmdType) {
        if (cmdType == SqlCommandType.UPDATE) ds.setUpdateBy(username);
        else if (cmdType == SqlCommandType.INSERT) ds.setCreateBy(username);
    }

}
