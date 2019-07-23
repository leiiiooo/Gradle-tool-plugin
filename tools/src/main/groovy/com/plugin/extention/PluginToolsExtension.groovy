package com.plugin.extention

import org.gradle.api.Action
import org.gradle.util.ConfigureUtil

class PluginToolsExtension {
    public DoubleClickExtension doubleClickExtension = new DoubleClickExtension()

    void doubleClickExtension(Action<DoubleClickExtension> action) {
        action.execute(doubleClickExtension)
    }

    void doubleClickExtension(Closure c) {
        ConfigureUtil.configure(c, doubleClickExtension)
    }
}