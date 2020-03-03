package org.rxjava.apikit.tool.utils;

import org.apache.commons.io.IOUtils;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.*;
import org.rxjava.apikit.tool.info.JavadocInfo;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author happy
 * Java Development Tools
 * Java开发工具类包装
 */
public class JdtClassWrapper {
    /**
     * 类型声明
     */
    private AbstractTypeDeclaration typeDeclaration;

    public static void main(String[] args) {
        JdtClassWrapper jdtClassWrapper = new JdtClassWrapper("/Users/happy/IdeaProjects/rxjava/rxjava-apikit/rxjava-apikit-tool/src/main/java", OrderStatus.class);
    }

    public JdtClassWrapper(String filePath, Class<?> cls) {
        this(Paths.get(filePath, cls.getPackage().getName().split("\\.")).resolve(cls.getSimpleName() + ".java"));
    }

    public JdtClassWrapper(Path javaFilePath) {
        //将文件转为源码字符串
        String srcCode;
        try {
            srcCode = IOUtils.toString(javaFilePath.toUri(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("IO文件转字符串源码异常", e);
        }

        //抽象语法树解析源文件
        Map<String,String> options = JavaCore.getOptions();
        options.put("org.eclipse.jdt.core.compiler.source", "1.8");

        ASTParser parser = ASTParser.newParser(AST.JLS9);
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        parser.setSource(srcCode.toCharArray());
        parser.setCompilerOptions(options);
        CompilationUnit node = (CompilationUnit) parser.createAST(null);

        //获取第一个public类
        List<?> types = node.types();

        Optional<?> first = types.stream()
                .filter(type -> (type instanceof TypeDeclaration || type instanceof EnumDeclaration)
                        && Modifier.isPublic(((AbstractTypeDeclaration) type).getModifiers())
                )
                .findFirst();

        if (!first.isPresent()) {
            throw new RuntimeException("未找到类");
        } else {
            this.typeDeclaration = (AbstractTypeDeclaration) first.get();
        }

    }

    public static Optional<JdtClassWrapper> getOptionalJavadocInfo(String path, Class<?> clazz) {
        Path javaFilePath = Paths.get(path, (clazz.getPackage().getName()).split("\\.")).resolve(clazz.getSimpleName() + ".java");
        if (Files.exists(javaFilePath)) {
            return Optional.of(new JdtClassWrapper(javaFilePath));
        }
        return Optional.empty();
    }

    public JavadocInfo getMethodComment(String name) {
        Optional<MethodDeclaration> methodDeclarationOptional = Arrays.stream(((TypeDeclaration) this.typeDeclaration).getMethods()).filter((methodDeclaration) -> Objects.equals(methodDeclaration.getName().getIdentifier(), name)).findFirst();
        if (!methodDeclarationOptional.isPresent()) {
            throw new RuntimeException("没有在源文件中找到方法:" + name);
        } else {
            MethodDeclaration methodDeclaration = methodDeclarationOptional.get();
            return transform(methodDeclaration.getJavadoc());
        }
    }

    private static JavadocInfo transform(Javadoc javadoc) {
        if (javadoc == null) {
            return null;
        }
        JavadocInfo javadocInfo = new JavadocInfo();
        List<?> tags = javadoc.tags();
        for (Object tag : tags) {
            TagElement tagElement = (TagElement) tag;
            String tagName = tagElement.getTagName();

            List<?> fragments = tagElement.fragments();
            List<String> fragmentsInfo = new ArrayList<>();
            for (Object fragment : fragments) {
                if (fragment instanceof TextElement) {
                    TextElement textElement = (TextElement) fragment;
                    fragmentsInfo.add(textElement.getText());
                } else {
                    fragmentsInfo.add(fragment.toString());
                }
            }
            javadocInfo.add(tagName, fragmentsInfo);
        }
        return javadocInfo;
    }

    public JavadocInfo getClassComment() {
        return transform(typeDeclaration.getJavadoc());
    }

    /**
     * 获取字段注释信息
     */
    public JavadocInfo getFieldComment(String name) {
        Optional<FieldDeclaration> methodOpt = Arrays
                .stream(((TypeDeclaration) typeDeclaration).getFields())
                .filter(fieldDeclaration -> Objects.equals(fieldDeclaration.fragments().get(0).toString(), name))
                .findFirst();

        if (methodOpt.isPresent()) {
            FieldDeclaration fieldDeclaration = methodOpt.get();
            return transform(fieldDeclaration.getJavadoc());
        } else {
            return null;
        }
    }

    /**
     * 获取枚举注释信息
     */
    public JavadocInfo getEnumElementComment(String name) {
        EnumDeclaration type = (EnumDeclaration) this.typeDeclaration;
        List<?> list = type.enumConstants();
        Optional<?> methodOpt = list.stream().filter((enumConstantDeclarationx) -> Objects.equals(((EnumConstantDeclaration) enumConstantDeclarationx).getName().getFullyQualifiedName(), name)).findFirst();
        if (methodOpt.isPresent()) {
            EnumConstantDeclaration enumConstantDeclaration = (EnumConstantDeclaration) methodOpt.get();
            return transform(enumConstantDeclaration.getJavadoc());
        } else {
            return null;
        }
    }
}
