package com.plugin.extention

import org.gradle.api.Action
import org.gradle.util.ConfigureUtil


class DoubleClickExtension {
    def need
    def filter = new FilterExtension()

    void need(need) {
        this.need = need
    }

    void filter(Action<FilterExtension> action) {
        action?.execute(filter)
    }

    void filter(Closure<FilterExtension> c) {
        ConfigureUtil.configure(c, filter)
    }
}