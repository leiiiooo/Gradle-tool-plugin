package com.plugin.filter

abstract class MethodFilter<T> {
    abstract boolean check(T t)
}