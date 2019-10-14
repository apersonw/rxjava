package org.rxjava.apikit.tool.wrapper;

import org.apache.commons.io.IOUtils;
import org.eclipse.jdt.core.dom.*;
import org.rxjava.apikit.tool.info.JavadocInfo;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author happy
 * Java Development Tools Class Wapper
 * Java开发工具类
 */
public class JdtClassWappper {
    protected CompilationUnit node;
    protected TypeDeclaration type;

    public JdtClassWappper(String path, Class cls) throws IOException {
        this(Paths.get(path, cls.getPackage().getName().split("\\.")).resolve(cls.getSimpleName() + ".java"), cls);
    }

    public JdtClassWappper(Path javaFilePath, Class cls) throws IOException {
        String code = IOUtils.toString(javaFilePath.toUri(), "UTF-8");

        ASTParser parser = ASTParser.newParser(AST.JLS9);
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        parser.setSource(code.toCharArray());
        CompilationUnit node = (CompilationUnit) parser.createAST(null);

        Optional first = node
                .types()
                .stream()
                .filter(t -> {
                    TypeDeclaration type = (TypeDeclaration) t;
                    return Modifier.isPublic(type.getModifiers());
                })
                .findFirst();

        if (!first.isPresent()) {
            throw new RuntimeException("未找到主类");
        }

        TypeDeclaration type = (TypeDeclaration) first.get();
        this.node = node;
        this.type = type;
    }

    public static Optional<JdtClassWappper> check(Class clazz, String path) throws IOException {
        Path javaFilePath = Paths.get(path, (clazz.getPackage().getName()).split("\\.")).resolve(clazz.getSimpleName() + ".java");
        if (Files.exists(javaFilePath)) {
            return Optional.of(new JdtClassWappper(javaFilePath, clazz));
        }
        return Optional.empty();
    }

    public JavadocInfo getClassComment() {
        return transform(type.getJavadoc());
    }

    protected static JavadocInfo transform(Javadoc javadoc) {
        if (javadoc == null) {
            return null;
        }
        JavadocInfo javadocInfo = new JavadocInfo();
        List tags = javadoc.tags();
        for (Object tag : tags) {
            TagElement tagElement = (TagElement) tag;
            String tagName = tagElement.getTagName();

            List fragments = tagElement.fragments();
            ArrayList<String> fragmentsInfo = new ArrayList<>();
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

    public JavadocInfo getFieldComment(String name) {
        Optional<FieldDeclaration> methodOpt = Arrays
                .stream(type.getFields())
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
