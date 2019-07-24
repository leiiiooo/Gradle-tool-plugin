package com.plugin.adapter


import com.plugin.filter.MethodFilter
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class PluginClassVisitor extends ClassVisitor {
    private def filterHashMap

    PluginClassVisitor(LinkedHashMap<String, MethodFilter> filterHashMap, int api, ClassVisitor classVisitor) {
        super(api, classVisitor)
        this.filterHashMap = filterHashMap
    }

    @Override
    MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = cv.visitMethod(access, name, descriptor, signature, exceptions)
        return new PluginMethodVisitor(filterHashMap, Opcodes.ASM5, methodVisitor, access, name, descriptor)
    }
}