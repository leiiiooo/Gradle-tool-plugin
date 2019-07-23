package com.plugin.extention

import org.gradle.api.Action
import org.gradle.util.ConfigureUtil


class DoubleClickExtension {
    boolean need
    FilterExtension filter = new FilterExtension()

    void need(boolean need) {
        this.need = need
    }

    void filter(Action<FilterExtension> action) {
        action.execute(filter)
    }

    void filter(Closure<FilterExtension> c) {
        ConfigureUtil.configure(c, filter)
    }
}