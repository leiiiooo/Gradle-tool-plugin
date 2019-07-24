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
    private def filterHashMap

    private def name
    private def descriptor

    PluginMethodVisitor(LinkedHashMap<String, MethodFilter> filterHashMap, int api, MethodVisitor methodVisitor, int access, String name, String descriptor) {
        super(api, methodVisitor, access, name, descriptor)
        this.name = name
        this.filterHashMap = filterHashMap
        this.descriptor = descriptor
    }

    @Override
    protected void onMethodEnter() {
        super.onMethodEnter()
        filterHashMap?.each {
            key, value ->
                if (key && key == DoubleClickMethodFilter.TAG) {
                    if (value.check(name)) {
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