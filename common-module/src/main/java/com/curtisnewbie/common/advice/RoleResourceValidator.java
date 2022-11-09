package com.curtisnewbie.common.advice;

import java.util.Comparator;
import java.util.List;

/**
 * Validator of role resources
 *
 * @author yongj.zhuang
 */
public interface RoleResourceValidator {

    /**
     * Validate role resources
     *
     * @param role      role
     * @param resources resources
     * @return true if the role has all the resources specified, else false
     */
    boolean validateRoleResource(String role, List<String> resources);

    /**
     * Whether the role resources validator is a local bean, local bean is always preferred
     */
    default boolean isLocalBean() {
        return false;
    }

    /** Comparator that prefer local bean */
    class LocalBeanPreferredComparator implements Comparator<RoleResourceValidator> {

        @Override
        public int compare(RoleResourceValidator o1, RoleResourceValidator o2) {
            return Integer.compare(o1.isLocalBean() ? 0 : 1, o2.isLocalBean() ? 0 : 1);
        }
    }

}
