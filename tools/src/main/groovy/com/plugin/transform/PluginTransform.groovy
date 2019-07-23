package com.plugin.transform

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import com.google.common.collect.Sets
import com.plugin.adapter.PluginClassVisitor
import com.plugin.extention.FilterExtension
import com.plugin.extention.PluginToolsExtension
import com.plugin.filter.DoubleClickMethodFilter
import com.plugin.filter.MethodFilter
import com.plugin.tools.DoubleClickToolsDump
import org.gradle.api.Project
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes

class PluginTransform extends Transform {
    def project
    def needCreateDoubleClickHookClass
    def haveCreateDoubleClickHookClass
    HashMap<String, MethodFilter> filterHashMap = new HashMap<>()

    PluginTransform(Project project) {
        this.project = project
    }

    @Override
    String getName() {
        return "PluginTransform"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return Sets.immutableEnumSet(QualifiedContent.Scope.PROJECT)
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)

        transformInvocation.inputs.each {
            it.directoryInputs.each {
                if (it.file.isDirectory()) {
                    it.file.eachFileRecurse {
                        if (filterFile(it)) {
                            insertCode(it)
                        }
                    }
                }
                //check or create insertCode class
                checkOrCreateDoubleCheckClass(it)

                def dest = transformInvocation.outputProvider.getContentLocation(it.getName(), it.getContentTypes(), it.getScopes(), Format.DIRECTORY)
                FileUtils.copyDirectory(it.file, dest)
            }

            it.jarInputs.each {
                def jarName = it.getName()
                def md5Name = DigestUtils.md5Hex(jarInput.file.getAbsolutePath())
                if (jarName.endsWith(".jar")) {
                    jarName = jarName.substring(0, jarName.length() - 4)
                }
                def dest = transformInvocation.outputProvider.getContentLocation(jarName + md5Name, it.getContentTypes(), it.getScopes(), Format.JAR)
                FileUtils.copyFile(it.file, dest)
            }
        }
    }

    void insertCode(File file) {
        def cr = new ClassReader(file.bytes)
        def cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS)
        def classVisitor = new PluginClassVisitor(filterHashMap, Opcodes.ASM5, cw)
        cr.accept(classVisitor, ClassReader.EXPAND_FRAMES)

        def bytes = cw.toByteArray()

        def fos = new FileOutputStream(file.getParentFile().getAbsolutePath() + File.separator + file.name)
        fos.write(bytes)
        fos.close()
    }

    boolean filterFile(File file) {
        if (file == null) {
            return false
        }
        if (!file.name.endsWith(".class")
                || file.name.startsWith("R\$")
                || file.name == "BuildConfig.class"
                || file.name == "R.class") {
            return false
        }

        //check doubleClickExtension
        if (project != null) {
            PluginToolsExtension tools = project['tools']
            if (tools != null
                    && tools.doubleClickExtension != null
                    && tools.doubleClickExtension.need) {
                return doubleClickFilter(tools, file)
            }
        }

        return false
    }

    boolean doubleClickFilter(PluginToolsExtension toolsExtension, File file) {
        FilterExtension extension = toolsExtension.doubleClickExtension.filter
        if (extension == null ||
                ((extension.classNames == null || extension.classNames.size() == 0)
                        && (extension.packageNames == null || extension.packageNames.size() == 0)
                        && (extension.methodNames == null || extension.methodNames.size() == 0))) {
            needCreateDoubleClickHookClass = true
            filterHashMap.put(DoubleClickMethodFilter.TAG, DoubleClickMethodFilter.create(toolsExtension.doubleClickExtension.need, extension.methodNames))

            return true
        } else {
            def pass = false

            //package name
            if (extension.packageNames == null || extension.packageNames.size() == 0) {
                pass = true
            } else {
                extension.packageNames.each {
                    def packageName = it.replace(".", "/")
                    if (file.path.contains(packageName)) {
                        pass = true
                    }
                }
            }

            if (!pass) {
                return false
            }

            pass = false

            //class name
            if (extension.classNames == null && extension.classNames.size() == 0) {
                pass = true
            } else {
                extension.classNames.each {
                    if (file.name.substring(0, file.name.length() - 6).contains(it)) {
                        pass = true
                    }
                }
            }

            filterHashMap.put(DoubleClickMethodFilter.TAG, DoubleClickMethodFilter.create(toolsExtension.doubleClickExtension.need, extension.methodNames))

            if (pass) {
                needCreateDoubleClickHookClass = true
            }
            return pass
        }
    }

    void checkOrCreateDoubleCheckClass(DirectoryInput input) {
        if (!needCreateDoubleClickHookClass || haveCreateDoubleClickHookClass) {
            return
        }

        def currentPath = input.file.path
        def targetDir = "${currentPath}"
        FileUtils.mkdirs(new File(targetDir))

        FileOutputStream out = new FileOutputStream("${targetDir + File.separator}DoubleClickTools.class")
        out.write(DoubleClickToolsDump.dump())
        out.close()
        haveCreateDoubleClickHookClass = true
    }
}