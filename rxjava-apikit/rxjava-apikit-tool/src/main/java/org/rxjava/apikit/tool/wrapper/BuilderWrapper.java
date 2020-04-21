package org.rxjava.apikit.tool.wrapper;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.rxjava.apikit.tool.generator.Context;
import org.rxjava.apikit.tool.info.JavaDocInfo;
import org.rxjava.apikit.tool.info.CommonClassInfo;
import org.rxjava.apikit.tool.info.ClassTypeInfo;
import org.rxjava.apikit.tool.utils.CommentUtils;

import java.util.List;

/**
 * @author happy
 * 生成器包装类
 */
@Setter
@Getter
public class BuilderWrapper<T extends CommonClassInfo> {
    /**
     * 类信息
     */
    protected T classInfo;
    /**
     * 发布的根包
     */
    private String distRootPackage;
    /**
     * 微服务Id
     */
    private String serviceId;
    /**
     * 发布的文件夹
     */
    protected String distFolder;
    /**
     * 上下文
     */
    protected Context context;

    BuilderWrapper(Context context, T classInfo, String distRootPackage) {
        this.context = context;
        this.classInfo = classInfo;
        this.distRootPackage = distRootPackage;
    }

    public String getDistClassName() {
        return classInfo.getClassName();
    }

    public String getFullDistPackage() {
        //源码所在的包
        String sourcePackage = classInfo.getPackageName();
        //源码所在的根包
        String sourceRootPackage = context.getRootPackage();

        String distFolder = sourcePackage.replace(sourceRootPackage, "");

        if (distFolder.startsWith(".")) {
            distFolder = distFolder.substring(1);
        }
        //如果源码所在的包根根包不一致，则仅仅获取源码所在的文件夹
        String[] floders = distFolder.split("\\.");
        if (!sourcePackage.contains(sourceRootPackage) && floders.length > 1) {
            distFolder = floders[floders.length - 1];
        }
        return getFullDistPackage(distFolder);
    }

    public String getDistPackage() {
        return getFullDistPackage(getDistFolder());
    }

    public String getFullDistPackage(String distFolder) {
        if (StringUtils.isEmpty(distFolder)) {
            if (distRootPackage == null) {
                return "";
            }
            return distRootPackage;
        } else {
            if (StringUtils.isEmpty(distRootPackage)) {
                return distFolder;
            } else {
                return distRootPackage + "." + distFolder;
            }
        }
    }

    void findTypes(ClassTypeInfo type, List<ClassTypeInfo> list) {
        list.add(type);
        if (CollectionUtils.isNotEmpty(type.getTypeArguments())) {
            type.getTypeArguments().forEach(t -> findTypes(t, list));
        }
    }

    public String getSourceFullName() {
        return classInfo.getPackageName() + "." + classInfo.getClassName();
    }

    public String comment(String start) {
        JavaDocInfo comment = classInfo.getJavaDocInfo();
        return CommentUtils.getComment(comment, start);
    }

    public static String formatBaseComment(JavaDocInfo comment, String start) {
        return CommentUtils.getBaseComment(comment, start);
    }

    public static String formatComment(JavaDocInfo comment, String start) {
        return CommentUtils.getComment(comment, start);
    }
}
