package top.rxjava.apikit.tool.utils;

import org.apache.commons.io.IOUtils;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.*;
import top.rxjava.apikit.tool.info.JavaDocInfo;

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
    private final AbstractTypeDeclaration typeDeclaration;

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
        Map<String, String> options = JavaCore.getOptions();
        //设置源码兼容模式为1.8
        options.put(JavaCore.COMPILER_SOURCE, "11");

        ASTParser parser = ASTParser.newParser(AST.JLS11);
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
            throw new RuntimeException("未找到类:" + srcCode);
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

    public JavaDocInfo getMethodComment(String name) {
        Optional<MethodDeclaration> methodDeclarationOptional = Arrays.stream(((TypeDeclaration) this.typeDeclaration).getMethods()).filter((methodDeclaration) -> Objects.equals(methodDeclaration.getName().getIdentifier(), name)).findFirst();
        if (!methodDeclarationOptional.isPresent()) {
            throw new RuntimeException("没有在源文件中找到方法:" + name);
        } else {
            MethodDeclaration methodDeclaration = methodDeclarationOptional.get();
            return transform(methodDeclaration.getJavadoc());
        }
    }

    private static JavaDocInfo transform(Javadoc javadoc) {
        if (javadoc == null) {
            return null;
        }
        JavaDocInfo javaDocInfo = new JavaDocInfo();
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
            javaDocInfo.add(tagName, fragmentsInfo);
        }
        return javaDocInfo;
    }

    public JavaDocInfo getClassComment() {
        return transform(typeDeclaration.getJavadoc());
    }

    /**
     * 获取字段注释信息
     */
    public JavaDocInfo getFieldComment(String name) {
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
    public JavaDocInfo getEnumElementComment(String name) {
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
