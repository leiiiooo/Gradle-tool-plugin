package com.plugin.filter

abstract class MethodFilter<T> {
    abstract String tag()

    abstract boolean check(T t)
}