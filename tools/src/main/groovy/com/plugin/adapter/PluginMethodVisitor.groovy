package com.plugin.adapter

import com.plugin.filter.DoubleClickMethodFilter
import com.plugin.filter.MethodFilter
import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.commons.AdviceAdapter

/**
 * method adapter
 */
class PluginMethodVisitor extends AdviceAdapter {
    HashMap<String, MethodFilter> filterHashMap

    def name
    def descriptor

    PluginMethodVisitor(HashMap<String, MethodFilter> filterHashMap, int i, MethodVisitor methodVisitor, int i1, String name, String descriptor) {
        super(i, methodVisitor, i1, name, descriptor)
        this.name = name
        this.filterHashMap = filterHashMap
        this.descriptor = descriptor
    }

    @Override
    protected void onMethodEnter() {
        super.onMethodEnter()
        if (filterHashMap && filterHashMap.size() > 0) {
            filterHashMap.each {
                if (it && it.key == DoubleClickMethodFilter.TAG) {
                    if (it.value.check(name)) {
                        mv.visitMethodInsn(INVOKESTATIC, "com/tools/DoubleClickTools", "cannotClick", "()Z", false)
                        Label l1 = new Label()
                        mv.visitJumpInsn(IFEQ, l1)
                        mv.visitInsn(RETURN)
                        mv.visitLabel(l1)
                    }
                }
            }
        }
    }
}