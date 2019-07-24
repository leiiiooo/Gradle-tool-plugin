package com.plugin.filter

import com.google.common.collect.Lists

class DoubleClickMethodFilter extends MethodFilter<String> {
    static final def TAG = "DoubleClickMethodFilter"
    
    private static def ON_CLICK = "onClick"

    private def enable
    private def methodNames = Lists.newArrayList(ON_CLICK)

    static DoubleClickMethodFilter create(enable, methodNames) {
        def doubleClickMethodFilter = new DoubleClickMethodFilter()
        doubleClickMethodFilter.enable = enable

        if (methodNames && methodNames.size() > 0) {
            methodNames.each {
                doubleClickMethodFilter.methodNames << it
            }
        }

        return doubleClickMethodFilter
    }

    @Override
    boolean check(String name) {
        boolean tag = false
        if (!enable) {
            return false
        }

        methodNames.each {
            if (it == name) {
                tag = true
            }
        }

        return tag
    }
}