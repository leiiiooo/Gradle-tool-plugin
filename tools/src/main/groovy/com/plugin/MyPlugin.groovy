package com.plugin

import com.android.build.gradle.AppExtension
import com.plugin.extention.PluginToolsExtension
import com.plugin.transform.PluginTransform
import org.gradle.api.Plugin
import org.gradle.api.Project

class MyPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        println("Tools Plugin")
        project.extensions.create("tools", PluginToolsExtension.class,)
        //注册transform
        def android = project.extensions.findByType(AppExtension.class)
        if (android) {
            //含有android 拓展
            android.registerTransform(new PluginTransform(project))
        }
    }
}