/*
 * Copyright 2011-2013 HTTL Team.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package top.rxjava.apikit.httl.spi.filters;

import top.rxjava.apikit.httl.spi.Filter;
import top.rxjava.apikit.httl.util.StringUtils;
import net.htmlparser.jericho.*;

import java.util.ArrayList;
import java.util.List;

/**
 * AttributeSyntaxFilter. (SPI, Singleton, ThreadSafe)
 *
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 * @see top.rxjava.apikit.httl.spi.translators.CompiledTranslator#setTemplateFilter(Filter)
 * @see top.rxjava.apikit.httl.spi.translators.InterpretedTranslator#setTemplateFilter(Filter)
 */
public class AttributeSyntaxFilter extends AbstractFilter {

    private String ifattr = "ifattr";

    private String setattr = "setattr";

    private String[] setDirective = new String[]{"var"};

    private String[] ifDirective = new String[]{"if"};

    private String[] elseifDirective = new String[]{"elseif"};

    private String[] elseDirective = new String[]{"else"};

    private String[] forDirective = new String[]{"for"};

    private String[] breakifDirective = new String[]{"breakif"};

    private String[] macroDirective = new String[]{"macro"};

    private String[] endDirective = new String[]{"end"};

    private String attributeNamespace;

    /**
     * httl.properties: set.directive=set
     */
    public void setSetDirective(String[] setDirective) {
        this.setDirective = setDirective;
    }

    /**
     * httl.properties: if.directive=if
     */
    public void setIfDirective(String[] ifDirective) {
        this.ifDirective = ifDirective;
    }

    /**
     * httl.properties: elseif.directive=elseif
     */
    public void setElseifDirective(String[] elseifDirective) {
        this.elseifDirective = elseifDirective;
    }

    /**
     * httl.properties: else.directive=else
     */
    public void setElseDirective(String[] elseDirective) {
        this.elseDirective = elseDirective;
    }

    /**
     * httl.properties: for.directive=for
     */
    public void setForDirective(String[] forDirective) {
        this.forDirective = forDirective;
    }

    /**
     * httl.properties: breakif.directive=breakif
     */
    public void setBreakifDirective(String[] breakifDirective) {
        this.breakifDirective = breakifDirective;
    }

    /**
     * httl.properties: macro.directive=macro
     */
    public void setMacroDirective(String[] macroDirective) {
        this.macroDirective = macroDirective;
    }

    /**
     * httl.properties: end.directive=end
     */
    public void setEndDirective(String[] endDirective) {
        this.endDirective = endDirective;
    }

    /**
     * httl.properties: attribute.namespace=httl
     */
    public void setAttributeNamespace(String attributeNamespace) {
        if (!attributeNamespace.endsWith(":")) {
            attributeNamespace = attributeNamespace + ":";
        }
        this.attributeNamespace = attributeNamespace;
    }

    private boolean isDirective(String name) {
        return StringUtils.inArray(name, setDirective)
                || StringUtils.inArray(name, ifDirective) || StringUtils.inArray(name, elseifDirective)
                || StringUtils.inArray(name, elseDirective) || StringUtils.inArray(name, forDirective)
                || StringUtils.inArray(name, breakifDirective) || StringUtils.inArray(name, macroDirective)
                || StringUtils.inArray(name, endDirective);
    }

    private boolean isBlockDirective(String name) {
        return StringUtils.inArray(name, ifDirective) || StringUtils.inArray(name, elseifDirective)
                || StringUtils.inArray(name, elseDirective) || StringUtils.inArray(name, forDirective)
                || StringUtils.inArray(name, macroDirective);
    }

    @Override
    public String filter(String key, String value) {
        Source source = new Source(value);
        OutputDocument document = new OutputDocument(source);
        replaceChildren(source, source, document);
        return document.toString();
    }

    // 替换子元素中的指令属性
    private void replaceChildren(Source source, Segment segment, OutputDocument document) {
        // 迭代子元素，逐个查找
        List<Element> elements = segment.getChildElements();
        if (elements != null) {
            for (Element element : elements) {
                if (element != null) {
                    // ---- 标签属性处理 ----
                    List<String> directiveNames = new ArrayList<String>();
                    List<String> directiveValues = new ArrayList<String>();
                    List<Attribute> directiveAttributes = new ArrayList<Attribute>();
                    // 迭代标签属性，查找指令属性
                    Attributes attributes = element.getAttributes();
                    if (attributes != null) {
                        for (Attribute attribute : attributes) {
                            if (attribute != null) {
                                String name = attribute.getName();
                                if (name != null && (isDirective(name) || (attributeNamespace != null && name.startsWith(attributeNamespace)) && isDirective(name.substring(attributeNamespace.length())))) { // 识别名称空间
                                    String directiveName = attributeNamespace != null ? name.substring(attributeNamespace.length()) : name;
                                    String value = attribute.getValue();
                                    directiveNames.add(directiveName);
                                    directiveValues.add(value);
                                    directiveAttributes.add(attribute);
                                }
                            }
                        }
                    }
                    // ---- 指令处理 ----
                    if (directiveNames.size() > 0) {
                        StringBuffer buf = new StringBuffer();
                        for (int i = 0; i < directiveNames.size(); i++) { // 按顺序添加块指令
                            String directiveName = (String) directiveNames.get(i);
                            String directiveValue = (String) directiveValues.get(i);
                            buf.append("#");
                            buf.append(directiveName);
                            buf.append("(");
                            buf.append(directiveValue);
                            buf.append(")");
                        }
                        document.insert(element.getBegin(), buf.toString()); // 插入块指令
                    }
                    // ---- 指令属性处理 ----
                    for (int i = 0; i < directiveAttributes.size(); i++) {
                        Attribute attribute = (Attribute) directiveAttributes.get(i);
                        document.remove(new Segment(source, attribute.getBegin() - 1, attribute.getEnd())); // 移除属性
                    }

                    if (attributes != null) {
                        //检查扩展的ifattr指令
                        for (Attribute attribute : attributes) {
                            if (attribute != null) {
                                String name = attribute.getName();
                                if (ifattr.equals(name)) {
                                    String val = attribute.getValue();
                                    String[] arr = val.split(",");
                                    String attrName = arr[0].trim();
                                    String expression = arr[1].trim();

                                    //修改原attribute
                                    Attribute oriattr = attributes.get(attrName);
                                    if (oriattr != null) {
                                        String buf = String.format("#if(%s)%s=\"%s\"#end()", expression, oriattr.getName(), oriattr.getValue());
                                        document.replace(new Segment(source, oriattr.getBegin(), oriattr.getEnd()), buf);
                                        document.remove(new Segment(source, attribute.getBegin(), attribute.getEnd())); // 移除ifattr控制属性
                                    }
                                }
                            }
                        }

                        //检查扩展的setattr指令
                        for (Attribute attribute : attributes) {
                            if (attribute != null) {
                                String name = attribute.getName();
                                if (setattr.equals(name)) {
                                    String val = attribute.getValue();
                                    String[] arr = val.split(",");
                                    String attrName = arr[0].trim();
                                    String expression = arr[1].trim();

                                    //将控制指令直接替换为动态属性赋值
                                    Attribute oriattr = attributes.get(attrName);
                                    String buf = String.format("%s=\"%s\"", attrName, expression);
                                    document.replace(new Segment(source, attribute.getBegin(), attribute.getEnd()), buf);

                                    //如果有已经存在的静态属性，直接删去即可
                                    if (oriattr != null) {
                                        document.remove(new Segment(source, attribute.getBegin(), attribute.getEnd())); // 移除setattr控制属性
                                    }
                                }
                            }
                        }
                    }

                    replaceChildren(source, element, document); // 递归处理子标签
                    // ---- 结束指令处理 ----
                    if (directiveNames.size() > 0) {
                        StringBuffer buf = new StringBuffer();
                        for (int i = directiveNames.size() - 1; i >= 0; i--) { // 倒序添加结束指令
                            String directiveName = (String) directiveNames.get(i);
                            if (isBlockDirective(directiveName)) {
                                buf.append("#");
                                buf.append(endDirective[0]);
                                buf.append("(");
                                buf.append(directiveName);
                                buf.append(")");
                            }
                        }
                        document.insert(element.getEnd(), buf.toString()); // 插入结束指令
                    }
                    // 清理临时容器
                    directiveNames.clear();
                    directiveValues.clear();
                    directiveAttributes.clear();
                }
            }
        }
    }

}