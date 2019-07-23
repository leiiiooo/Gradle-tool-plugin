package com.plugin.extention

import com.google.common.collect.Lists

/**
 * 不同集合之间是与的关系，集合内部是或的关系
 */
class FilterExtension {
    ArrayList<String> packageNames = Lists.newArrayList()
    ArrayList<String> classNames = Lists.newArrayList()
    /**
     * 默认过滤onClick
     * access+name+descriptor
     */
    ArrayList<String> methodNames = Lists.newArrayList()

    void packageNames(String... packageNames) {
        packageNames.each {
            this.packageNames.add(it)
        }
    }

    void classNames(String... classNames) {
        classNames.each {
            this.classNames.add(it)
        }
    }

    void methodNames(String... methodNames) {
        methodNames.each {
            this.methodNames.add(it)
        }
    }
}