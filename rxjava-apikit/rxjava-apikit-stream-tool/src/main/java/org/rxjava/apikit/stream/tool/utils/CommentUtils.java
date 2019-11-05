package org.rxjava.apikit.stream.tool.utils;

import org.apache.commons.io.IOUtils;
import org.eclipse.jdt.core.dom.*;
import org.rxjava.apikit.stream.tool.info.ClassCommentInfo;
import org.rxjava.apikit.stream.tool.info.FieldCommentInfo;
import org.rxjava.apikit.stream.tool.info.JavadocInfo;
import org.rxjava.apikit.stream.tool.info.MethodCommentInfo;
import org.rxjava.apikit.stream.tool.test.AdminTestController;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author happy
 * 注释帮助类
 */
public class CommentUtils {
    /**
     * 类型声明
     */
    private TypeDeclaration typeDeclaration;

    public static void main(String[] args) {
        //从源码类获取注释信息
        Path sourceCodeAbsolutePath = FilePathUtils.getSourceCodeAbsolutePath("/Users/happy/liexiong/rxjava/rxjava-apikit/rxjava-apikit-stream-tool/src/main/java", AdminTestController.class);
        ClassCommentInfo classCommentInfo = CommentUtils.parseClass(sourceCodeAbsolutePath);
    }

    /**
     * 解析类注释
     *
     * @param javaFileAbsolutePath Java文件绝对路径
     */
    public static ClassCommentInfo parseClass(Path javaFileAbsolutePath) {
        CommentUtils commentUtils = new CommentUtils(javaFileAbsolutePath);
        ClassCommentInfo classCommentInfo = new ClassCommentInfo();
        JavadocInfo classComment = commentUtils.getClassComment();
        Optional.ofNullable(classComment).ifPresent(c -> {
            String firstRow = c.getFirstRow();
            String secendRow = c.getSecendRow();
            classCommentInfo.setComment(firstRow);
            classCommentInfo.setDesc(secendRow);
        });
        Map<String, MethodCommentInfo> classCommentInfoMap = new HashMap<>();
        for (MethodDeclaration methodDeclaration : commentUtils.typeDeclaration.getMethods()) {
            JavadocInfo javadocInfo = transform(methodDeclaration.getJavadoc());
            String comment = javadocInfo.getFirstRow();
            String desc = javadocInfo.getSecendRow();
            MethodCommentInfo methodCommentInfo = new MethodCommentInfo();
            methodCommentInfo.setComment(comment);
            methodCommentInfo.setDesc(desc);
            classCommentInfoMap.put(methodDeclaration.getName().getFullyQualifiedName(), methodCommentInfo);

            Map<String, FieldCommentInfo> fieldCommentInfoMap = new HashMap<>();
            javadocInfo.getInputParamComments(fieldCommentInfoMap);
            methodCommentInfo.setFieldCommentInfoMap(fieldCommentInfoMap);

            String returnParamComment = javadocInfo.getReturnParamComment();
            methodCommentInfo.setReturnComment(returnParamComment);
        }
        classCommentInfo.setMethodCommentMap(classCommentInfoMap);
        return classCommentInfo;
    }

    private CommentUtils(String filePath, Class cls) {
        this(Paths.get(filePath, cls.getPackage().getName().split("\\.")).resolve(cls.getSimpleName() + ".java"));
    }

    private CommentUtils(Path javaFilePath) {
        //将文件转为源码字符串
        String srcCode;
        try {
            srcCode = IOUtils.toString(javaFilePath.toUri(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("IO文件转字符串源码异常", e);
        }

        //抽象语法树解析源文件
        ASTParser parser = ASTParser.newParser(AST.JLS9);
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        parser.setSource(srcCode.toCharArray());
        CompilationUnit node = (CompilationUnit) parser.createAST(null);

        //获取第一个public类
        List types = node.types();
        List<TypeDeclaration> typeDeclarations = new ArrayList<>();
        if (!types.isEmpty()) {
            for (Object type : types) {
                TypeDeclaration typeDeclaration = (TypeDeclaration) type;
                typeDeclarations.add(typeDeclaration);
            }
        }
        Optional<TypeDeclaration> first = typeDeclarations
                .stream()
                .filter(t -> Modifier.isPublic(t.getModifiers()))
                .findFirst();

        if (!first.isPresent()) {
            throw new RuntimeException("未找到类");
        }

        this.typeDeclaration = first.get();
    }

    /**
     * 获取指定类的注释信息
     */
    public static Optional<CommentUtils> getCommentInfo(String srcMainJavaPath, Class clazz) {
        Path javaFilePath = Paths.get(srcMainJavaPath, (clazz.getPackage().getName()).split("\\.")).resolve(clazz.getSimpleName() + ".java");
        if (Files.exists(javaFilePath)) {
            return Optional.of(new CommentUtils(javaFilePath));
        }
        return Optional.empty();
    }

    /**
     * 获取方法注释信息
     */
    public JavadocInfo getMethodComment(String name) {
        Optional<MethodDeclaration> methodDeclarationOptional = Arrays.stream(typeDeclaration.getMethods()).filter((methodDeclaration) -> Objects.equals(methodDeclaration.getName().getIdentifier(), name)).findFirst();
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
        List tags = javadoc.tags();
        for (Object tag : tags) {
            TagElement tagElement = (TagElement) tag;
            String tagName = tagElement.getTagName();

            List fragments = tagElement.fragments();
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

    /**
     * 获取类注释信息
     */
    public JavadocInfo getClassComment() {
        return transform(typeDeclaration.getJavadoc());
    }

    /**
     * 获取字段注释信息
     */
    public JavadocInfo getFieldComment(String name) {
        Optional<FieldDeclaration> methodOpt = Arrays
                .stream(typeDeclaration.getFields())
                .filter(fieldDeclaration -> Objects.equals(fieldDeclaration.fragments().get(0).toString(), name))
                .findFirst();

        if (methodOpt.isPresent()) {
            FieldDeclaration fieldDeclaration = methodOpt.get();
            return transform(fieldDeclaration.getJavadoc());
        } else {
            return null;
        }
    }
}
