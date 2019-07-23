package com.plugin.filter

import com.google.common.collect.Lists

class DoubleClickMethodFilter extends MethodFilter<String> {
    public static final String TAG = "DoubleClickMethodFilter"

    private static def ON_CLICK = "onClick"

    boolean enable
    ArrayList<String> methodNames = Lists.newArrayList(ON_CLICK)

    static DoubleClickMethodFilter create(boolean enable, ArrayList<String> methodNames) {
        def doubleClickMethodFilter = new DoubleClickMethodFilter()
        doubleClickMethodFilter.enable = enable

        if (methodNames && methodNames.size() > 0) {
            doubleClickMethodFilter.methodNames.addAll(methodNames)
        }

        return doubleClickMethodFilter
    }

    @Override
    String tag() {
        return TAG
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