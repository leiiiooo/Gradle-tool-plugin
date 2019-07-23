package com.plugin.tools;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @author leyliang
 * @date 2019-07-22
 */

public class DoubleClickToolsDump implements Opcodes {

    public static byte[] dump() {

        ClassWriter cw = new ClassWriter(0);
        FieldVisitor fv;
        MethodVisitor mv;
        AnnotationVisitor av0;

        cw.visit(52, ACC_PUBLIC + ACC_SUPER, "com/tools/DoubleClickTools", null, "java/lang/Object", null);

        cw.visitSource("DoubleClickTools.java", null);

        {
            fv = cw.visitField(ACC_STATIC, "LAST_CLICK_TIME", "J", null, null);
            fv.visitEnd();
        }
        {
            fv = cw.visitField(ACC_FINAL + ACC_STATIC, "THRESHOLD_VALUE", "J", null, new Long(1000L));
            fv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(3, l0);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
            mv.visitInsn(RETURN);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLocalVariable("this", "Lcom/tools/DoubleClickTools;", null, l0, l1, 0);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "canClick", "()Z", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(8, l0);
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;", false);
            mv.visitVarInsn(ASTORE, 0);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLineNumber(9, l1);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J", false);
            mv.visitFieldInsn(GETSTATIC, "com/tools/DoubleClickTools", "LAST_CLICK_TIME", "J");
            mv.visitInsn(LSUB);
            mv.visitLdcInsn(new Long(1000L));
            mv.visitInsn(LCMP);
            Label l2 = new Label();
            mv.visitJumpInsn(IFLE, l2);
            Label l3 = new Label();
            mv.visitLabel(l3);
            mv.visitLineNumber(10, l3);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J", false);
            mv.visitFieldInsn(PUTSTATIC, "com/tools/DoubleClickTools", "LAST_CLICK_TIME", "J");
            Label l4 = new Label();
            mv.visitLabel(l4);
            mv.visitLineNumber(11, l4);
            mv.visitInsn(ICONST_1);
            mv.visitInsn(IRETURN);
            mv.visitLabel(l2);
            mv.visitLineNumber(13, l2);
            mv.visitFrame(Opcodes.F_APPEND, 1, new Object[]{"java/lang/Long"}, 0, null);
            Label l5 = new Label();
            mv.visitLabel(l5);
            mv.visitLineNumber(14, l5);
            mv.visitInsn(ICONST_0);
            mv.visitInsn(IRETURN);
            Label l6 = new Label();
            mv.visitLabel(l6);
            mv.visitLocalVariable("currentTime", "Ljava/lang/Long;", null, l1, l6, 0);
            mv.visitMaxs(4, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "cannotClick", "()Z", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(18, l0);
            mv.visitMethodInsn(INVOKESTATIC, "com/tools/DoubleClickTools", "canClick", "()Z", false);
            Label l1 = new Label();
            mv.visitJumpInsn(IFNE, l1);
            mv.visitInsn(ICONST_1);
            Label l2 = new Label();
            mv.visitJumpInsn(GOTO, l2);
            mv.visitLabel(l1);
            mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            mv.visitInsn(ICONST_0);
            mv.visitLabel(l2);
            mv.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[]{Opcodes.INTEGER});
            mv.visitInsn(IRETURN);
            mv.visitMaxs(1, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_STATIC, "<clinit>", "()V", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(4, l0);
            mv.visitInsn(LCONST_0);
            mv.visitFieldInsn(PUTSTATIC, "com/tools/DoubleClickTools", "LAST_CLICK_TIME", "J");
            mv.visitInsn(RETURN);
            mv.visitMaxs(2, 0);
            mv.visitEnd();
        }
        cw.visitEnd();

        return cw.toByteArray();
    }
}