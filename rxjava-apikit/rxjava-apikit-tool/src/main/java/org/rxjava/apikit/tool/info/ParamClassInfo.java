package org.rxjava.apikit.tool.info;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.jdt.core.dom.TagElement;
import org.eclipse.jdt.core.dom.TextElement;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import java.util.*;

/**
 * @author happy
 * 方法参数类信息（入参和出参）
 */
@Getter
@Setter
public class ParamClassInfo extends CommonClassInfo {
    private List<PropertyInfo> properties = new ArrayList<>();
    private Map<String, PropertyInfo> propertiesMap = new HashMap<>();
    /**
     * 父类类型信息
     */
    private ClassTypeInfo superType;
    private Class clazz;
    protected TypeDeclaration type;
    private List<String> typeParameters = new ArrayList<>();

    public JavaDocInfo getClassComment() {
        return transform(type.getJavadoc());
    }

    /**
     * 若是有泛型，则需要再获取
     */
    public String getInterfaceName() {
        if (this.getTypeParameters().size() > 0) {
            return super.getClassName() + "<T>";
        } else {
            return super.getClassName();
        }
    }

    /**
     * 获取分页信息
     */
    public String getPageInfo() {
        String pageInfo = "";
        if (this.getTypeParameters().size() > 0) {
            pageInfo = "\n    content?:T[];\n" +
                    "\n" +
                    "    size?: number;\n" +
                    "    \n" +
                    "    number?: number;";
        }
        return pageInfo;
    }

    protected static JavaDocInfo transform(org.eclipse.jdt.core.dom.Javadoc javadoc) {
        if (javadoc == null) {
            return null;
        }
        JavaDocInfo javadocInfo = new JavaDocInfo();
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

    public void addTypeParameter(String typeParameter) {
        typeParameters.add(typeParameter);
    }

    public void add(PropertyInfo attributeInfo) {
        properties.add(attributeInfo);
        propertiesMap.put(attributeInfo.getFieldName(), attributeInfo);
    }

    public void sortPropertys() {
        properties.sort(Comparator.comparing(FieldInfo::getFieldName));
    }


    public boolean hasGenerics() {
        return CollectionUtils.isNotEmpty(getTypeParameters());
    }

    public List<String> getTypeParameters() {
        return typeParameters;
    }
}
