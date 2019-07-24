package com.plugin.extention

import com.google.common.collect.Lists

/**
 * 不同集合之间是与的关系，集合内部是或的关系
 */
class FilterExtension {
    def packageNames = Lists.newArrayList()
    def classNames = Lists.newArrayList()
    /**
     * 默认过滤onClick
     * access+name+descriptor
     */
    def methodNames = Lists.newArrayList()

    void packageNames(packageNames) {
        packageNames?.each {
            this.packageNames.add(it)
        }
    }

    void classNames(classNames) {
        classNames?.each {
            this.classNames.add(it)
        }
    }

    void methodNames(methodNames) {
        methodNames?.each {
            this.methodNames.add(it)
        }
    }
}